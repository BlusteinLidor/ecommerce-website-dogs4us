package com.ew.ecommercewebsite.view;

import com.ew.ecommercewebsite.dto.entity.ProductRequestDTO;
import com.ew.ecommercewebsite.dto.entity.ProductResponseDTO;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Managed bean responsible for handling product management operations in the admin interface.
 * This bean provides functionality for CRUD operations on products.
 */
@Named
@ViewScoped
public class ProductManagementBean implements Serializable {

    /**
     * Current product being created or edited
     */
    @Setter
    @Getter
    private ProductRequestDTO product;
    /**
     * REST client for making HTTP requests
     */
    private final RestTemplate restTemplate = new RestTemplate();
    /**
     * Comma-separated string of customizable fields
     */
    @Setter
    @Getter
    private String customizableFieldsString;
    /**
     * List of all products in the system
     */
    @Setter
    @Getter
    private List<ProductResponseDTO> products;
    /**
     * Current user session information
     */
    @Inject
    private SessionUserBean sessionUserBean;
    /**
     * Flag indicating if the form is in edit mode
     */
    @Setter
    @Getter
    private boolean isEditMode = false;
    /**
     * ID of the product being edited
     */
    @Setter
    @Getter
    private String selectedProductId;

    /**
     * Initializes the bean by creating a new product instance and fetching existing products.
     * Called after bean construction.
     */
    @PostConstruct
    public void init() {
        product = new ProductRequestDTO();
        fetchProducts();
    }

    /**
     * Retrieves all products from the backend service.
     * Updates the products list with the fetched data.
     */
    public void fetchProducts() {
        String url = "http://localhost:4000/products";
        products = Arrays.asList(restTemplate.getForObject(url, ProductResponseDTO[].class));
        if (products.isEmpty()) {
            products = new ArrayList<>();
        }
        System.out.println("Products fetched: " + products.size());
    }

    /**
     * Saves a new product to the system.
     * Processes customizable fields and sends the product data to the backend.
     */
    public void saveProduct() {
        try {
            String[] tempArr = customizableFieldsString.split(",");
            for (String s : tempArr) {
                s = s.trim();
            }
            List<String> customizableList = List.of(tempArr);
            System.out.println("Customizable fields list: " + customizableList);
            product.setCustomizableFields(customizableList);
            String url = "http://localhost:4000/products";
            ResponseEntity<Void> response = restTemplate.postForEntity(url, product, Void.class);
            System.out.println("Response status: " + response.getStatusCode());
            fetchProducts();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Product added successfully!", null));

            product = new ProductRequestDTO(); // reset form
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to add product.", null));
            e.printStackTrace();
        }
    }

    /**
     * Prepares the form for editing an existing product.
     *
     * @param prod The product to be edited
     */
    public void editProduct(ProductResponseDTO prod) {
        // Set edit mode and store selected product ID
        isEditMode = true;
        selectedProductId = prod.getId();

        // Copy all basic product properties from the input parameter
        product.setName(prod.getName());
        product.setDescription(prod.getDescription());
        product.setPrice(prod.getPrice());
        product.setCategory(prod.getCategory());
        product.setImageURL(prod.getImageURL());

        // Process customizable fields if they exist
        List<String> customizableList = new ArrayList<>();
        if (prod.getCustomizableFields() != null || !prod.getCustomizableFields().isEmpty()) {
            if(customizableFieldsString != null && customizableFieldsString.contains(",")){
                String[] tempArr = customizableFieldsString.split(",");
                for (String s : tempArr) {
                    s = s.trim();
                }
                customizableList = List.of(tempArr);
                System.out.println("Customizable fields list: " + customizableList);
            }
            else{
                customizableList.add(customizableFieldsString);
                System.out.println("Customizable field: " + customizableList);

            }
        }
        product.setCustomizableFields(customizableList);
        product.setStockQuantity(prod.getStockQuantity());
    }

    /**
     * Updates an existing product in the system.
     * Sends the modified product data to the backend.
     */
    public void updateProduct() {
        try{
            restTemplate.put("http://localhost:4000/products/" + selectedProductId, product);
            fetchProducts();
            isEditMode = false;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Product updated successfully!", null));
        } catch(Exception e){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to update product.", null));
            e.printStackTrace();
        }
    }

    /**
     * Deletes a product from the system.
     *
     * @param id The ID of the product to be deleted
     */
    public void deleteProduct(String id) {
        restTemplate.delete("http://localhost:4000/products/" + id);
        fetchProducts();
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Product deleted successfully!", null));
    }

    /**
     * Cancels the edit mode and resets the form.
     */
    public void cancelEditMode() {
        isEditMode = false;
        selectedProductId = null;
        product = new ProductRequestDTO();
    }

    /**
     * Redirects to home page if the current user is not an admin.
     *
     * @throws IOException if redirect fails
     */
    public void redirectIfNotAdmin() throws IOException {
        if (sessionUserBean.getUser() == null || !sessionUserBean.getUser().getIsAdmin()) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("home.xhtml");
        }
    }

}

