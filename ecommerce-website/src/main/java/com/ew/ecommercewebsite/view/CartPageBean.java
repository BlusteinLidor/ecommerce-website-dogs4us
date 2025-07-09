package com.ew.ecommercewebsite.view;

import com.ew.ecommercewebsite.dto.entity.CartItemResponseDTO;
import com.ew.ecommercewebsite.dto.entity.CartItemWithProductResponseDTO;
import com.ew.ecommercewebsite.dto.entity.ProductResponseDTO;
import jakarta.annotation.PostConstruct;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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

    @PostConstruct
    public void init(){
        if (sessionUserBean == null || sessionUserBean.getUser() == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Please login", null));
            return;
        }

        cartItems = new ArrayList<>();

        if(sessionUserBean.getUser() != null){
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
    }

    public List<CartItemWithProductResponseDTO> getCartItems(){
        return cartItems;
    }
}
