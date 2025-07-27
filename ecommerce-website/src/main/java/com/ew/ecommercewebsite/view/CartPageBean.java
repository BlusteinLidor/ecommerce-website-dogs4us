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
    private boolean isItemOutOfStock;

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
        isItemOutOfStock = false;
        try{
            UUID userId = sessionUserBean.getUser().getId();
            String url = "http://localhost:4000/cart-items/userId/" + userId;

            CartItemResponseDTO[] items = restTemplate.getForObject(url, CartItemResponseDTO[].class);

            for (CartItemResponseDTO item : items) {
                String productUrl = "http://localhost:4000/products/" + item.getProductId();
                ProductResponseDTO product = restTemplate.getForObject(productUrl, ProductResponseDTO.class);

                if(Integer.parseInt(product.getStockQuantity()) == 0){
                    isItemOutOfStock = true;
                }


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

    public boolean isItemOutOfStock() {
        return isItemOutOfStock;
    }

    public void setItemOutOfStock(boolean itemOutOfStock) {
        isItemOutOfStock = itemOutOfStock;
    }
}
