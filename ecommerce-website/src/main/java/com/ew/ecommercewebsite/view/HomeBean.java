package com.ew.ecommercewebsite.view;

import com.ew.ecommercewebsite.dto.entity.ProductResponseDTO;
import com.ew.ecommercewebsite.model.Product;
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
public class HomeBean {
    private List<ProductResponseDTO> featuredProducts = new ArrayList<>();

    @PostConstruct
    public void init(){
        try{
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://localhost:4000/products";
            ResponseEntity<ProductResponseDTO[]> response = restTemplate.getForEntity(url, ProductResponseDTO[].class);

            featuredProducts = Arrays.asList(response.getBody());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<ProductResponseDTO> getFeaturedProducts(){
        return featuredProducts;
    }
}
