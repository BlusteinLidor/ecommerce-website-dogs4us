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

@Named
@ViewScoped
public class CheckoutPageBean implements Serializable {

    private final CartPageBean cartPageBean;
    @Autowired
    private SessionUserBean sessionUserBean;

    private List<CartItemWithProductResponseDTO> cartItems = new ArrayList<>();
    private RestTemplate restTemplate = new RestTemplate();

    private String name;
    private String address;
    private String phone;

    @Inject
    public CheckoutPageBean(CartPageBean cartPageBean) {
        this.cartPageBean = cartPageBean;
    }

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

    public void placeOrder() {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Order placed successfully!", null));

        double totalPrice = getTotalCost();

        UUID userId = sessionUserBean.getUser().getId();

        //        Create Order Entity
        String createOrderUrl = "http://localhost:4000/orders";
        OrderRequestDTO order = new OrderRequestDTO();
        order.setUser(userId.toString());
        order.setOrderDate(LocalDate.now().toString());
        order.setTotalAmount(String.format("%.2f" ,totalPrice));
        ResponseEntity<OrderResponseDTO> response = restTemplate.postForEntity(createOrderUrl, order, OrderResponseDTO.class);
        OrderResponseDTO orderResponse = response.getBody();
        String orderId = orderResponse.getId();

//        Create Order Items
        String createOrderItemUrl = "http://localhost:4000/order-items";
        for(CartItemWithProductResponseDTO item : cartItems){
            String productId = item.getCartItem().getProductId();
            String quantity = item.getCartItem().getQuantity();
            String unitPrice = item.getProduct().getPrice();
            String customizationRef = item.getCartItem().getCustomizationPreview();
            OrderItemRequestDTO orderItem = new OrderItemRequestDTO();
            orderItem.setOrderId(orderId);
            orderItem.setProductId(productId);
            orderItem.setQuantity(quantity);
            orderItem.setUnitPrice(unitPrice);
            orderItem.setCustomizationReference(customizationRef);
            restTemplate.postForObject(createOrderItemUrl, orderItem, OrderItemResponseDTO.class);

//            Name: Billie; Phone: 050-6075637;
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

            // Reduce the stock quantity of the product
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

    public void redirectIfNotLoggedIn() throws IOException {
        if (sessionUserBean.getUser() == null) {
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect("home.xhtml?faces-redirect=true");
        }
    }

    public List<CartItemWithProductResponseDTO> getCartItems() {
        return cartItems;
    }

    public double getTotalCost() {
        return cartItems.stream()
                .mapToDouble(item -> Double.parseDouble(item.getProduct().getPrice()) * Integer.parseInt(item.getCartItem().getQuantity()))
                .sum();
    }

    // Getters and setters for name, address, phone
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}

