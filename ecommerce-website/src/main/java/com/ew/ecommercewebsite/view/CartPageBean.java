package com.ew.ecommercewebsite.view;

import com.ew.ecommercewebsite.dto.entity.CartItemRequestDTO;
import com.ew.ecommercewebsite.dto.entity.CartItemResponseDTO;
import com.ew.ecommercewebsite.dto.entity.CartItemWithProductResponseDTO;
import com.ew.ecommercewebsite.dto.entity.ProductResponseDTO;
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

@Named
@Component
@ViewScoped
public class CartPageBean implements Serializable {
    @Autowired
    private SessionUserBean sessionUserBean;
    private RestTemplate restTemplate = new RestTemplate();
    private List<CartItemWithProductResponseDTO> cartItems;
    private int cartItemCount;
    private double totalCartPrice;
    private final CartItemService cartItemService;

    public CartPageBean(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostConstruct
    public void init(){
        if (sessionUserBean == null || sessionUserBean.getUser() == null) {
            return;
        }

        fetchCart();

    }

    public void fetchCart() {
        cartItems = new ArrayList<>();
        try{
            UUID userId = sessionUserBean.getUser().getId();
            String url = "http://localhost:4000/cart-items/userId/" + userId;

            CartItemResponseDTO[] items = restTemplate.getForObject(url, CartItemResponseDTO[].class);

            for (CartItemResponseDTO item : items) {
                String productUrl = "http://localhost:4000/products/" + item.getProductId();
                ProductResponseDTO product = restTemplate.getForObject(productUrl, ProductResponseDTO.class);

                CartItemWithProductResponseDTO fullItem = new CartItemWithProductResponseDTO();
                fullItem.setCartItem(item);
                fullItem.setProduct(product);
                cartItems.add(fullItem);
            }
        }
        catch (HttpClientErrorException e) {
            // If 400 BAD_REQUEST means "no cart items", treat as empty cart
            if (e.getStatusCode().value() == 400) {
                cartItems = new ArrayList<>();
            } else {
                e.printStackTrace(); // Or use logging
            }
        }
        catch (Exception e){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error fetching cart items", null));
            e.printStackTrace();
        }


        cartItemCount = cartItems.size();

        totalCartPrice = cartItems.stream()
                .mapToDouble(item -> Double.parseDouble(item.getProduct().getPrice()) * Integer.parseInt(item.getCartItem().getQuantity()))
                .sum();
    }

    public void incrementQuantity(CartItemResponseDTO cartItem) {
        cartItem.setQuantity(cartItem.getQuantity() + 1);
        updateCart(cartItem);
    }

    public void decrementQuantity(CartItemResponseDTO cartItem) {
        int quantity = Integer.parseInt(cartItem.getQuantity());
        if (quantity > 1) {
            cartItem.setQuantity(String.valueOf(quantity - 1));
            updateCart(cartItem);
        } else {
            removeItem(cartItem);
        }
    }

    public void removeItem(CartItemResponseDTO cartItem) {
        restTemplate.delete("http://localhost:4000/cart-items/userId/" + cartItem.getUserId() + "/productId/" + cartItem.getProductId());
        fetchCart(); // Refresh
    }

    private void updateCart(CartItemResponseDTO cartItem) {
        // cartItemResponseDTo to cartItemRequestDTO
        CartItemRequestDTO cartItemRequestDTO = new CartItemRequestDTO();
        cartItemRequestDTO.setQuantity(cartItem.getQuantity());
        cartItemRequestDTO.setUserId(cartItem.getUserId());
        cartItemRequestDTO.setProductId(cartItem.getProductId());
        cartItemRequestDTO.setCustomizationPreview(cartItem.getCustomizationPreview());
        restTemplate.put("http://localhost:4000/cart-items/userId/" + cartItem.getUserId() + "/productId/" + cartItem.getProductId(), cartItemRequestDTO);
        fetchCart();
    }

    public List<CartItemWithProductResponseDTO> getCartItems(){
        return cartItems;
    }

    public double getTotalCartPrice(){
        return totalCartPrice;
    }

    public boolean isCartEmpty() {
        return cartItems == null || cartItems.isEmpty();
    }

    public int getCartItemCount() {
        return cartItemCount;
    }

    public void setCartItemCount(int cartItemCount) {
        this.cartItemCount = cartItemCount;
    }
}
