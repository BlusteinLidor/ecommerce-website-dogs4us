package com.ew.ecommercewebsite.view;

import com.ew.ecommercewebsite.dto.entity.CartItemRequestDTO;
import com.ew.ecommercewebsite.dto.entity.ProductResponseDTO;
import com.ew.ecommercewebsite.model.Product;
import com.ew.ecommercewebsite.repository.CartItemRepository;
import com.ew.ecommercewebsite.utils.CartItemId;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.SessionScope;

import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

@Named
//@RequestScoped
@ViewScoped
public class ProductPageBean implements Serializable {
    private ProductResponseDTO product;
    @Autowired
    private SessionUserBean sessionUserBean;
    private final RestTemplate restTemplate = new RestTemplate();
    private final CartItemRepository cartItemRepository;

    private int quantity = 1;

    @Autowired
    public ProductPageBean(CartItemRepository cartItemRepository){
        this.cartItemRepository = cartItemRepository;
    }

    public ProductResponseDTO getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void incrementQuantity(){
        if(this.quantity < 99){
            this.quantity++;
        }
    }

    public void decrementQuantity(){
        if(this.quantity > 1){
            this.quantity--;
        }
    }

    @PostConstruct
    public void init(){
        String productId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");

        if(productId != null){
            try {
                URL url = new URL("http://localhost:4000/products/" + productId);
                ObjectMapper mapper = new ObjectMapper();
                this.product = mapper.readValue(url, ProductResponseDTO.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addToCart(){
        UUID userId = sessionUserBean.getUser().getId();
        UUID productId = UUID.fromString(product.getId());

        CartItemRequestDTO dto = new CartItemRequestDTO();
        dto.setUserId(userId.toString());
        dto.setProductId(productId.toString());
        dto.setCustomizationPreview(""); // or null if not used
        CartItemId cartItemId = new CartItemId(userId, productId);
        if(cartItemRepository.existsById(cartItemId)){
            int previousQuantity = cartItemRepository.findById(cartItemId).orElseThrow().getQuantity();
            int newQuantity = previousQuantity + quantity;
            dto.setQuantity(String.valueOf(newQuantity));
            restTemplate.put("http://localhost:4000/cart-items/userId/"
                    + userId.toString() + "/productId/" + productId.toString() , dto);
        }
        else{
            dto.setQuantity(Integer.toString(quantity));
            restTemplate.postForObject("http://localhost:4000/cart-items", dto, Void.class);
        }

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Added to cart!", null));
    }

}
