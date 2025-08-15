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

/**
 * Bean class responsible for managing the home page data, specifically handling featured products.
 * Uses RequestScope to create a new instance for each HTTP request.
 */
@Component
@RequestScope
public class HomeBean {
    /**
     * List of featured products to be displayed on the home page.
     * Initialized as an empty ArrayList and populated with the first 3 products from the product list.
     */
    private List<ProductResponseDTO> featuredProducts = new ArrayList<>();

    /**
     * Initializes the featured products list by fetching products from the REST API.
     * Called automatically after bean construction.
     * Retrieves all products and selects the first 3 as featured products.
     */
    @PostConstruct
    public void init(){
        try{
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://localhost:4000/products";
            ResponseEntity<ProductResponseDTO[]> response = restTemplate.getForEntity(url, ProductResponseDTO[].class);
            List<ProductResponseDTO> products = Arrays.asList(response.getBody());
//            featuredProducts = Arrays.asList(response.getBody());
            featuredProducts = products.subList(0, 3);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Returns the list of featured products.
     *
     * @return List of ProductResponseDTO objects representing the featured products
     */
    public List<ProductResponseDTO> getFeaturedProducts(){
        return featuredProducts;
    }
}
