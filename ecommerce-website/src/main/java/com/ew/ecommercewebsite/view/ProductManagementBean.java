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
    }

    public void saveProduct() {
        String url = "http://localhost:4000/products";

        if (editMode) {
            restTemplate.put(url + "/" + currentProductId, selectedProduct);
        } else {
            ResponseEntity<ProductResponseDTO> response = restTemplate.postForEntity(url, selectedProduct, ProductResponseDTO.class);
            ProductResponseDTO productResponse = response.getBody();
            System.out.println("product" + productResponse);
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

    public void deleteProduct(UUID id) {
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

