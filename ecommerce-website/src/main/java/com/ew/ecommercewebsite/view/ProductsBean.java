package com.ew.ecommercewebsite.view;

import com.ew.ecommercewebsite.dto.entity.ProductResponseDTO;
import jakarta.annotation.PostConstruct;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequestScope
public class ProductsBean {
    private List<ProductResponseDTO> products = new ArrayList<>();
    private String searchQuery = "";
    private List<ProductResponseDTO> filteredProducts;

    @PostConstruct
    public void init(){
        try{
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://localhost:4000/products";
            ResponseEntity<ProductResponseDTO[]> response = restTemplate.getForEntity(url, ProductResponseDTO[].class);
            products = Arrays.asList(response.getBody());
            filteredProducts = products;
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void filterProducts(){
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            filteredProducts = new ArrayList<>(products);
        } else {
            String lower = searchQuery.toLowerCase();
            filteredProducts = products.stream()
                    .filter(p -> p.getName().toLowerCase().contains(lower) ||
                            p.getDescription().toLowerCase().contains(lower))
                    .collect(Collectors.toList());
        }
    }

    public List<ProductResponseDTO> getProducts(){
        return products;
    }

    public String getSearchQuery() { return searchQuery; }
    public void setSearchQuery(String searchQuery) { this.searchQuery = searchQuery; }
    public List<ProductResponseDTO> getFilteredProducts() { return filteredProducts; }
}
