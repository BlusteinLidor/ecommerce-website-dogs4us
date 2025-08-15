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

/**
 * Bean class responsible for managing product-related operations in the view layer.
 * Handles product listing and filtering functionality.
 */
@Component
@RequestScope
public class ProductsBean {
    /**
     * List containing all available products
     */
    private List<ProductResponseDTO> products = new ArrayList<>();
    /**
     * Current search query string for filtering products
     */
    private String searchQuery = "";
    /**
     * List of products filtered based on search query
     */
    private List<ProductResponseDTO> filteredProducts;

    /**
     * Initializes the bean by fetching all products from the backend service.
     * Called automatically after bean construction.
     */
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

    /**
     * Filters the products list based on the current search query.
     * Filters by product name and description, case-insensitive.
     */
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

    /**
     * Returns the complete list of products.
     *
     * @return List of all products
     */
    public List<ProductResponseDTO> getProducts(){
        return products;
    }

    /**
     * @return Current search query string
     */
    public String getSearchQuery() {
        return searchQuery;
    }

    /**
     * Sets the search query string for filtering products
     *
     * @param searchQuery The search term to filter by
     */
    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    /**
     * @return List of filtered products based on search query
     */
    public List<ProductResponseDTO> getFilteredProducts() { return filteredProducts; }
}
