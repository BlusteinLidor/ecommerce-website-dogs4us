package com.ew.ecommercewebsite.view;

import com.ew.ecommercewebsite.dto.entity.*;
import com.ew.ecommercewebsite.model.CartItem;
import com.ew.ecommercewebsite.utils.CustomField;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Manages the checkout process for the e-commerce website.
 * Handles order placement, user information, and cart management during checkout.
 */
@Named
@ViewScoped
public class CheckoutPageBean implements Serializable {

    /**
     * Reference to cart management bean
     */
    private final CartPageBean cartPageBean;
    /**
     * User session information
     */
    @Autowired
    private SessionUserBean sessionUserBean;

    /**
     * List of cart items with product details
     */
    private List<CartItemWithProductResponseDTO> cartItems = new ArrayList<>();
    /**
     * REST client for API calls
     */
    private RestTemplate restTemplate = new RestTemplate();

    /**
     * Customer's name for shipping
     */
    private String name;
    /**
     * Delivery address
     */
    private String address;
    /**
     * Contact phone number
     */
    private String phone;

    /**
     * Constructs a new CheckoutPageBean with the specified cart bean.
     * @param cartPageBean The cart management bean to be used
     */
    @Inject
    public CheckoutPageBean(CartPageBean cartPageBean) {
        this.cartPageBean = cartPageBean;
    }

    /**
     * Initializes the checkout page by loading cart items and user information.
     * Called after bean construction.
     */
    @PostConstruct
    public void init() {
        if (sessionUserBean == null || sessionUserBean.getUser() == null) {
            return;
        }

        UUID userId = sessionUserBean.getUser().getId();
        String url = "http://localhost:4000/cart-items/userId/" + userId;

        CartItemResponseDTO[] items = restTemplate.getForObject(url, CartItemResponseDTO[].class);
        for (CartItemResponseDTO item : items) {
            ProductResponseDTO product = restTemplate.getForObject(
                    "http://localhost:4000/products/" + item.getProductId(), ProductResponseDTO.class);

            CartItemWithProductResponseDTO fullItem = new CartItemWithProductResponseDTO();
            fullItem.setCartItem(item);
            fullItem.setProduct(product);
            cartItems.add(fullItem);
        }

        // Pre-fill user name if available
        this.name = sessionUserBean.getUser().getName();
    }

    /**
     * Processes the order placement, creates order records, updates product stock,
     * and handles customizations. Redirects to confirmation page on success.
     */
    public void placeOrder() {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Order placed successfully!", null));

        double totalPrice = getTotalCost();

        UUID userId = sessionUserBean.getUser().getId();

        // Create new order record in the database with user ID, date and total amount
        String createOrderUrl = "http://localhost:4000/orders";
        OrderRequestDTO order = new OrderRequestDTO();
        order.setUser(userId.toString());
        order.setOrderDate(LocalDate.now().toString());
        order.setTotalAmount(String.format("%.2f" ,totalPrice));
        ResponseEntity<OrderResponseDTO> response = restTemplate.postForEntity(createOrderUrl, order, OrderResponseDTO.class);
        OrderResponseDTO orderResponse = response.getBody();
        String orderId = orderResponse.getId();

        // Create order items and their customizations, storing details like quantity, price and custom fields
        String createOrderItemUrl = "http://localhost:4000/order-items";
        for(CartItemWithProductResponseDTO item : cartItems){
            String productId = item.getCartItem().getProductId();
            String quantity = item.getCartItem().getQuantity();
            String unitPrice = item.getProduct().getPrice();
            String customizationRef = item.getCartItem().getCustomizationPreview();

            if(customizationRef == null || customizationRef.isBlank()){
                customizationRef = "None";
            }
            customizationRef = customizationRef.replaceAll("\\[", "").replaceAll("\\]","");

            OrderItemRequestDTO orderItem = new OrderItemRequestDTO();
            orderItem.setOrderId(orderId);
            orderItem.setProductId(productId);
            orderItem.setQuantity(quantity);
            orderItem.setUnitPrice(unitPrice);
            orderItem.setCustomizationReference(customizationRef);
            restTemplate.postForObject(createOrderItemUrl, orderItem, OrderItemResponseDTO.class);

//          Example structure:  Name: Billie; Phone: 050-6075637;
            List<CustomField> customFields = new ArrayList<>();
            String[] customSplitList = customizationRef.split(";");
            for(String customField : customSplitList){
                if(customField.contains(":")){
                    customFields.add(new CustomField(customField.split(":")[0].trim(),
                            customField.split(":")[1].trim()));
                }
            }
            for(CustomField customField : customFields){
                CustomizationRequestDTO customization = new CustomizationRequestDTO();
                customization.setOrder(orderId);
                customization.setProduct(productId);
                customization.setFieldName(customField.getName());
                if(customField.getValue() == null || customField.getValue().isEmpty()){
                    customization.setFieldValue("-");
                }
                else{
                    customization.setFieldValue(customField.getValue());
                }
                restTemplate.postForObject("http://localhost:4000/customizations", customization, CustomizationResponseDTO.class);
            }

            // Update product inventory by reducing stock quantity based on ordered amount
            ProductRequestDTO product = new ProductRequestDTO();
            // get productResponseDTO
            ProductResponseDTO productResponseDTO = restTemplate.getForObject(
                    "http://localhost:4000/products/" + productId, ProductResponseDTO.class);

            List<String> customizableFields = new ArrayList<>();
            customizableFields.add(productResponseDTO.getCustomizableFields());
            product.setCustomizableFields(customizableFields);
            product.setDescription(productResponseDTO.getDescription());
            product.setPrice(productResponseDTO.getPrice());
            int stockQuantity = Integer.parseInt(productResponseDTO.getStockQuantity());
            int cartItemQuantity = Integer.parseInt(quantity);
            int newStockQuantity = stockQuantity - cartItemQuantity;
            product.setStockQuantity(String.valueOf(newStockQuantity));
            product.setName(productResponseDTO.getName());
            product.setImageURL(productResponseDTO.getImageURL());
            product.setCategory(productResponseDTO.getCategory());

            try{
                restTemplate.put("http://localhost:4000/products/" + productId, product);
            } catch (Exception e){
                e.printStackTrace();
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error placing order", null));
                cartPageBean.setCartItemCount(cartItems.size());
                return;
            }

        }


        // Clean up cart and redirect to confirmation page after successful order placement
        String deleteCartItemsUrl = "http://localhost:4000/cart-items/userId/" + userId;


        try{
            FacesContext.getCurrentInstance().getExternalContext()
                    .getSessionMap().put("orderConfirmed", true);
            cartPageBean.setCartItemCount(0);
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect("order-confirmation.xhtml?faces-redirect=true");
            restTemplate.delete(deleteCartItemsUrl);
        }
        catch (IOException e){
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error placing order", null));
            cartPageBean.setCartItemCount(cartItems.size());
        }


    }

    /**
     * Checks if user is logged in and redirects to home page if not.
     * @throws IOException if redirect fails
     */
    public void redirectIfNotLoggedIn() throws IOException {
        if (sessionUserBean.getUser() == null) {
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect("home.xhtml?faces-redirect=true");
        }
    }

    /**
     * Returns the list of cart items with their product details.
     * @return List of cart items with product information
     */
    public List<CartItemWithProductResponseDTO> getCartItems() {
        return cartItems;
    }

    /**
     * Calculates the total cost of all items in the cart.
     * @return Total cost of the order
     */
    public double getTotalCost() {
        return cartItems.stream()
                .mapToDouble(item -> Double.parseDouble(item.getProduct().getPrice()) * Integer.parseInt(item.getCartItem().getQuantity()))
                .sum();
    }

    // Getters and setters for name, address, phone
    /**
     * @return Customer's name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name Customer's name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Delivery address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address Delivery address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return Contact phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone Contact phone number to set
     */
    public void setPhone(String phone) { this.phone = phone; }
}

