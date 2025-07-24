package com.ew.ecommercewebsite.view;

import com.ew.ecommercewebsite.dto.entity.ProductRequestDTO;
import com.ew.ecommercewebsite.dto.entity.ProductResponseDTO;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
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

    private List<ProductResponseDTO> products;
    private ProductRequestDTO selectedProduct = new ProductRequestDTO();
    private boolean editMode = false;
    private String currentProductId;


    @Inject
    private SessionUserBean sessionUserBean;

    private RestTemplate restTemplate = new RestTemplate();

    @PostConstruct
    public void init() {
        fetchProducts();
    }

    public void fetchProducts() {
        String url = "http://localhost:4000/products";
        products = Arrays.asList(restTemplate.getForObject(url, ProductResponseDTO[].class));
        System.out.println("Products fetched: " + products.size());
    }

    public void testFunc(){
        System.out.println("in test");
    }

    public void saveProduct() {
        String url = "http://localhost:4000/products";
        System.out.println("in save product");
        if (editMode) {
            restTemplate.put(url + "/" + currentProductId, selectedProduct);
            System.out.println("product name in put: " + selectedProduct.getName());
        } else {
//            ResponseEntity<ProductResponseDTO> response = restTemplate.postForEntity(url, selectedProduct, ProductResponseDTO.class);
            try {
                ResponseEntity<ProductResponseDTO> response =
                        restTemplate.postForEntity(url, selectedProduct, ProductResponseDTO.class);

                if (response.getStatusCode().is2xxSuccessful()) {
                    System.out.println("Product saved: " + response.getBody().getName());
                } else {
                    System.out.println("Unexpected response: " + response.getStatusCode());
                }

            } catch (HttpClientErrorException e) {
                System.out.println("Client error: " + e.getStatusCode());
                System.out.println(e.getResponseBodyAsString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Save product called"));
        }

        selectedProduct = new ProductRequestDTO();
        editMode = false;
        fetchProducts();
    }

    public void editProduct(ProductResponseDTO prod) {
        selectedProduct = new ProductRequestDTO();
        currentProductId = prod.getId();
        selectedProduct.setName(prod.getName());
        selectedProduct.setDescription(prod.getDescription());
        selectedProduct.setPrice(prod.getPrice());
        selectedProduct.setCategory(prod.getCategory());
        selectedProduct.setImageURL(prod.getImageURL());
        List<String> customizableFields = new ArrayList<>();
        customizableFields.add(prod.getCustomizableFields());
        selectedProduct.setCustomizableFields(customizableFields);
        selectedProduct.setStockQuantity(prod.getStockQuantity());
        editMode = true;
    }

    public void deleteProduct(String id) {
        restTemplate.delete("http://localhost:4000/products/" + id);
        fetchProducts();
    }

    public void redirectIfNotAdmin() throws IOException {
        if (sessionUserBean.getUser() == null || !sessionUserBean.getUser().getIsAdmin()) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("home.xhtml");
        }
    }

    public List<ProductResponseDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductResponseDTO> products) {
        this.products = products;
    }

    public ProductRequestDTO getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(ProductRequestDTO selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public String getCurrentProductId() {
        return currentProductId;
    }

    public void setCurrentProductId(String currentProductId) {
        this.currentProductId = currentProductId;
    }

    public SessionUserBean getSessionUserBean() {
        return sessionUserBean;
    }

    public void setSessionUserBean(SessionUserBean sessionUserBean) {
        this.sessionUserBean = sessionUserBean;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }
}

