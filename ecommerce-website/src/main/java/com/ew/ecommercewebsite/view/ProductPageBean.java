package com.ew.ecommercewebsite.view;

import com.ew.ecommercewebsite.dto.entity.CartItemRequestDTO;
import com.ew.ecommercewebsite.dto.entity.ProductResponseDTO;
import com.ew.ecommercewebsite.exception.ProductOutOfStockException;
import com.ew.ecommercewebsite.model.Product;
import com.ew.ecommercewebsite.repository.CartItemRepository;
import com.ew.ecommercewebsite.utils.CartItemId;
import com.ew.ecommercewebsite.utils.CustomField;
import com.ew.ecommercewebsite.utils.Data;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.model.SelectItem;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.SessionScope;

import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * Manages the product details page functionality, including product display and cart operations.
 * Uses ViewScoped to maintain state during user interaction with a specific product.
 */
@Named
//@RequestScoped
@ViewScoped
public class ProductPageBean implements Serializable {
    /**
     * The currently displayed product
     */
    private ProductResponseDTO product;
    /**
     * User session information
     */
    @Autowired
    private SessionUserBean sessionUserBean;
    /**
     * REST client for API calls
     */
    private final RestTemplate restTemplate = new RestTemplate();
    /**
     * Repository for cart item operations
     */
    private CartItemRepository cartItemRepository;
    /**
     * Bean for cart page operations
     */
    private CartPageBean cartPageBean;
    /**
     * Selected quantity of the product
     */
    private int quantity = 1;
    /**
     * Raw string of custom fields
     */
    @Getter
    @Setter
    private String customFields;
    /**
     * List of customizable fields for the product
     */
    @Getter
    @Setter
    private List<CustomField> customFieldItemList = new ArrayList<>();
    /**
     * Available color options
     */
    @Getter
    @Setter
    private List<String> colorSelectItemList = new ArrayList<>();
    /**
     * Available size options
     */
    @Getter
    @Setter
    private List<String> sizeSelectItemList = new ArrayList<>();

    /**
     * Default constructor
     */
    public ProductPageBean() {
    }

    /**
     * Constructor with dependencies injection
     *
     * @param cartItemRepository Repository for cart operations
     * @param cartPageBean       Bean for cart page functionality
     */
    @Autowired
    public ProductPageBean(CartItemRepository cartItemRepository, CartPageBean cartPageBean){
        this.cartItemRepository = cartItemRepository;
        this.cartPageBean = cartPageBean;
    }

    /**
     * Gets the current product being displayed
     *
     * @return The product data transfer object
     */
    public ProductResponseDTO getProduct() {
        return product;
    }

    /**
     * Gets the selected quantity
     *
     * @return Current quantity value
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the product quantity
     *
     * @param quantity New quantity value
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Increases the quantity by one if less than 99
     */
    public void incrementQuantity(){
        if(this.quantity < 99){
            this.quantity++;
        }
    }

    /**
     * Decreases the quantity by one if greater than 1
     */
    public void decrementQuantity(){
        if(this.quantity > 1){
            this.quantity--;
        }
    }

    /**
     * Initializes the bean by loading product details and customization options
     *
     * @throws IOException If there's an error reading the product data
     */
    @PostConstruct
    public void init() throws IOException{
        String productIdContext = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");

        if(productIdContext != null && !productIdContext.isBlank()){
            try {
                URL url = new URL("http://localhost:4000/products/" + productIdContext);
                ObjectMapper mapper = new ObjectMapper();
                this.product = mapper.readValue(url, ProductResponseDTO.class);
                getProductCustomizations();
                sizeSelectItemList.addAll(Data.SIZE_LIST);
                colorSelectItemList.addAll(Data.COLOR_LIST);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect("home.xhtml?faces-redirect=true");
        }
    }

    /**
     * Adds the current product to the shopping cart with selected quantity and customizations
     * Shows success message on completion or warning if product is out of stock
     */
    public void addToCart(){
        UUID userId = sessionUserBean.getUser().getId();
        UUID productId = UUID.fromString(product.getId());

        CartItemRequestDTO dto = new CartItemRequestDTO();
        dto.setUserId(userId.toString());
        dto.setProductId(productId.toString());
        StringBuilder previewBuilder = new StringBuilder();
        for (CustomField field : customFieldItemList) {
            if(field.getValue() == null || field.getValue().isEmpty()){
                field.setValue("-");
            }
            previewBuilder.append(field.getName()).append(": ").append(field.getValue()).append("; ");
        }
        dto.setCustomizationPreview(previewBuilder.toString().trim());
        CartItemId cartItemId = new CartItemId(userId, productId);
        if(cartItemRepository.existsById(cartItemId)){
            try{
                int previousQuantity = cartItemRepository.findById(cartItemId).orElseThrow().getQuantity();
                int newQuantity = previousQuantity + quantity;
                dto.setQuantity(String.valueOf(newQuantity));
                restTemplate.put("http://localhost:4000/cart-items/userId/"
                        + userId.toString() + "/productId/" + productId.toString() , dto);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Added to cart!", null));
            }
            catch (HttpClientErrorException e){
                FacesContext context = FacesContext.getCurrentInstance();
                context.getExternalContext().getFlash().setKeepMessages(true);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Not enough in stock", null));
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        else{
            try{
                dto.setQuantity(Integer.toString(quantity));
                restTemplate.postForObject("http://localhost:4000/cart-items", dto, Void.class);

                cartPageBean.setCartItemCount(cartPageBean.getCartItemCount() + 1);

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Added to cart!", null));
            } catch (HttpClientErrorException e){
                FacesContext context = FacesContext.getCurrentInstance();
                context.getExternalContext().getFlash().setKeepMessages(true);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Not enough in stock", null));
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Processes the product's customizable fields and populates the customization lists
     * Converts the raw customization string into a list of CustomField objects
     */
    public void getProductCustomizations(){
        customFields = product.getCustomizableFields();
        if(customFields == null || customFields.isBlank()){
            customFields = "None";
        }
        customFields = customFields.replaceAll("\\[", "").replaceAll("\\]","");
        List<String> customFieldsList;
        if(customFields.contains(",")){
            customFieldsList = List.of(customFields.split(","));
        }
        else{
            customFieldsList = List.of(customFields);
        }
        for(String s : customFieldsList){
            CustomField field = new CustomField(s.trim(), "");
            customFieldItemList.add(field);
        }
    }

}
