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

@Named
@ViewScoped
public class ProductManagementBean implements Serializable {

    @Setter
    @Getter
    private ProductRequestDTO product;
    private final RestTemplate restTemplate = new RestTemplate();
    @Setter
    @Getter
    private String customizableFieldsString;
    @Setter
    @Getter
    private List<ProductResponseDTO> products;
    @Inject
    private SessionUserBean sessionUserBean;
    @Setter
    @Getter
    private boolean isEditMode = false;
    @Setter
    @Getter
    private String selectedProductId;

    @PostConstruct
    public void init() {
        product = new ProductRequestDTO();
        fetchProducts();
    }

    public void fetchProducts() {
        String url = "http://localhost:4000/products";
        products = Arrays.asList(restTemplate.getForObject(url, ProductResponseDTO[].class));
        if (products.isEmpty()) {
            products = new ArrayList<>();
        }
        System.out.println("Products fetched: " + products.size());
    }

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

    public void editProduct(ProductResponseDTO prod) {
        isEditMode = true;
        selectedProductId = prod.getId();
        product.setName(prod.getName());
        product.setDescription(prod.getDescription());
        product.setPrice(prod.getPrice());
        product.setCategory(prod.getCategory());
        product.setImageURL(prod.getImageURL());
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

    public void deleteProduct(String id) {
        restTemplate.delete("http://localhost:4000/products/" + id);
        fetchProducts();
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Product deleted successfully!", null));
    }

    public void cancelEditMode() {
        isEditMode = false;
        selectedProductId = null;
        product = new ProductRequestDTO();
    }

    public void redirectIfNotAdmin() throws IOException {
        if (sessionUserBean.getUser() == null || !sessionUserBean.getUser().getIsAdmin()) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("home.xhtml");
        }
    }

}

