package com.ew.ecommercewebsite.view;

import com.ew.ecommercewebsite.dto.entity.CartItemRequestDTO;
import com.ew.ecommercewebsite.dto.entity.CartItemResponseDTO;
import com.ew.ecommercewebsite.dto.entity.CartItemWithProductResponseDTO;
import com.ew.ecommercewebsite.dto.entity.ProductResponseDTO;
import com.ew.ecommercewebsite.exception.ProductOutOfStockException;
import com.ew.ecommercewebsite.service.entity.CartItemService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Managed bean for handling shopping cart operations in the e-commerce application.
 * This class manages cart items, their quantities, and total price calculations.
 */
@Named
@Component
@ViewScoped
public class CartPageBean implements Serializable {
    /**
     * User session information
     */
    @Autowired
    private SessionUserBean sessionUserBean;
    /**
     * REST template for making HTTP requests
     */
    private RestTemplate restTemplate = new RestTemplate();
    /**
     * List of cart items with their associated product details
     */
    private List<CartItemWithProductResponseDTO> cartItems;
    /**
     * Total number of items in the cart
     */
    private int cartItemCount;
    /**
     * Total price of all items in the cart
     */
    private double totalCartPrice;
    /**
     * Service for cart item operations
     */
    private final CartItemService cartItemService;
    /**
     * Flag indicating if any item in the cart is out of stock
     */
    private boolean isItemOutOfStock;

    /**
     * Constructs a new CartPageBean with the specified cart item service.
     *
     * @param cartItemService The service for handling cart item operations
     */
    public CartPageBean(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    /**
     * Initializes the cart page bean after construction.
     * Fetches cart items if a user is logged in.
     */
    @PostConstruct
    public void init(){
        if (sessionUserBean == null || sessionUserBean.getUser() == null) {
            return;
        }
        fetchCart();

    }

    /**
     * Fetches the current user's cart items and their associated products.
     * Updates cart item count and total price.
     */
    public void fetchCart() {
        // Initialize cart state
        cartItems = new ArrayList<>();
        isItemOutOfStock = false;
        try {
            // Get current user's cart items from the backend
            UUID userId = sessionUserBean.getUser().getId();
            String url = "http://localhost:4000/cart-items/userId/" + userId;

            CartItemResponseDTO[] items = restTemplate.getForObject(url, CartItemResponseDTO[].class);

            // For each cart item, fetch its associated product details
            for (CartItemResponseDTO item : items) {
                String productUrl = "http://localhost:4000/products/" + item.getProductId();
                ProductResponseDTO product = restTemplate.getForObject(productUrl, ProductResponseDTO.class);

                // Check if any product in cart is out of stock
                if (Integer.parseInt(product.getStockQuantity()) == 0) {
                    isItemOutOfStock = true;
                }

                // Combine cart item and product information
                CartItemWithProductResponseDTO fullItem = new CartItemWithProductResponseDTO();
                fullItem.setCartItem(item);
                fullItem.setProduct(product);
                cartItems.add(fullItem);
            }
        } catch (HttpClientErrorException e) {
            // Handle case where user has no items in cart (400 BAD_REQUEST)
            if (e.getStatusCode().value() == 400) {
                cartItems = new ArrayList<>();
            } else {
                e.printStackTrace(); // Or use logging
            }
        } catch (Exception e) {
            // Handle general errors during cart fetch
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error fetching cart items", null));
            e.printStackTrace();
        }

        // Update cart summary information
        cartItemCount = cartItems.size();

        // Calculate total price of all items in cart
        totalCartPrice = cartItems.stream()
                .mapToDouble(item -> Double.parseDouble(item.getProduct().getPrice()) * Integer.parseInt(item.getCartItem().getQuantity()))
                .sum();

    }

    /**
     * Increments the quantity of a cart item by one.
     *
     * @param cartItem The cart item to increment
     */
    public void incrementQuantity(CartItemResponseDTO cartItem) {
        cartItem.setQuantity(String.valueOf(Integer.parseInt(cartItem.getQuantity()) + 1));
        try{
            updateCart(cartItem);
        } catch (HttpClientErrorException e){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Not enough in stock", null));
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Decrements the quantity of a cart item by one.
     * Removes the item if quantity becomes less than 1.
     *
     * @param cartItem The cart item to decrement
     */
    public void decrementQuantity(CartItemResponseDTO cartItem) {
        int quantity = Integer.parseInt(cartItem.getQuantity());
        if (quantity > 1) {
            cartItem.setQuantity(String.valueOf(quantity - 1));
            updateCart(cartItem);
        } else {
            removeItem(cartItem);
        }
    }

    /**
     * Removes a specific item from the cart.
     *
     * @param cartItem The cart item to remove
     */
    public void removeItem(CartItemResponseDTO cartItem) {
        restTemplate.delete("http://localhost:4000/cart-items/userId/" + cartItem.getUserId() + "/productId/" + cartItem.getProductId());
        fetchCart(); // Refresh
    }

    /**
     * Updates a cart item's information in the backend.
     *
     * @param cartItem The cart item to update
     */
    private void updateCart(CartItemResponseDTO cartItem) {
        CartItemRequestDTO cartItemRequestDTO = new CartItemRequestDTO();
        cartItemRequestDTO.setQuantity(cartItem.getQuantity());
        cartItemRequestDTO.setUserId(cartItem.getUserId());
        cartItemRequestDTO.setProductId(cartItem.getProductId());
        cartItemRequestDTO.setCustomizationPreview(cartItem.getCustomizationPreview());
        try{
            restTemplate.put("http://localhost:4000/cart-items/userId/" + cartItem.getUserId() + "/productId/" + cartItem.getProductId(), cartItemRequestDTO);
        } catch (HttpClientErrorException e){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Not enough in stock", null));
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        fetchCart();
    }

    /**
     * Gets all items in the cart with their product information.
     *
     * @return List of cart items with product details
     */
    public List<CartItemWithProductResponseDTO> getCartItems(){
        return cartItems;
    }

    /**
     * Gets the total price of all items in the cart.
     *
     * @return Total cart price
     */
    public double getTotalCartPrice(){
        return totalCartPrice;
    }

    /**
     * Checks if the cart is empty.
     *
     * @return true if cart is empty, false otherwise
     */
    public boolean isCartEmpty() {
        return cartItems == null || cartItems.isEmpty();
    }

    /**
     * Gets the total number of items in the cart.
     *
     * @return Number of items in cart
     */
    public int getCartItemCount() {
        return cartItemCount;
    }

    /**
     * Sets the total number of items in the cart.
     *
     * @param cartItemCount Number of items to set
     */
    public void setCartItemCount(int cartItemCount) {
        this.cartItemCount = cartItemCount;
    }

    /**
     * Checks if any item in the cart is out of stock.
     *
     * @return true if any item is out of stock, false otherwise
     */
    public boolean isItemOutOfStock() {
        return isItemOutOfStock;
    }

    /**
     * Sets the out of stock status for items in the cart.
     *
     * @param itemOutOfStock The out of stock status to set
     */
    public void setItemOutOfStock(boolean itemOutOfStock) {
        isItemOutOfStock = itemOutOfStock;
    }
}
