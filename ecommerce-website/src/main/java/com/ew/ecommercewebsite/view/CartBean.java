package com.ew.ecommercewebsite.view;

import com.ew.ecommercewebsite.dto.entity.CartItemResponseDTO;
import com.ew.ecommercewebsite.dto.entity.ProductResponseDTO;
import com.ew.ecommercewebsite.model.User;
import jakarta.annotation.PostConstruct;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequestScope
public class CartBean {

    private List<CartItemResponseDTO> cartItems = new ArrayList<>();
    SessionUserBean sessionUserBean = new SessionUserBean();

    @PostConstruct
    public void init(){
        try{
            RestTemplate restTemplate = new RestTemplate();
            User user = sessionUserBean.getUser();
            String url = "http://localhost:4000/cart-items/" + user.getId();
            ResponseEntity<CartItemResponseDTO[]> response = restTemplate.getForEntity(url, CartItemResponseDTO[].class);
            cartItems = Arrays.asList(response.getBody());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<CartItemResponseDTO> getCartItems(){
        return cartItems;
    }

}
