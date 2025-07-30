package com.ew.ecommercewebsite.view;

import com.ew.ecommercewebsite.dto.entity.CartItemRequestDTO;
import com.ew.ecommercewebsite.dto.entity.ProductResponseDTO;
import com.ew.ecommercewebsite.exception.ProductOutOfStockException;
import com.ew.ecommercewebsite.model.Product;
import com.ew.ecommercewebsite.repository.CartItemRepository;
import com.ew.ecommercewebsite.utils.CartItemId;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
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

@Named
//@RequestScoped
@ViewScoped
public class ProductPageBean implements Serializable {
    private ProductResponseDTO product;
    @Autowired
    private SessionUserBean sessionUserBean;
    private final RestTemplate restTemplate = new RestTemplate();
    private CartItemRepository cartItemRepository;
    private CartPageBean cartPageBean;
    private int quantity = 1;
    @Getter
    @Setter
    private String customFields;
    @Getter
    @Setter
    private Dictionary<String, String> customFieldsDict = new Hashtable<>();

    public ProductPageBean(){}

    @Autowired
    public ProductPageBean(CartItemRepository cartItemRepository, CartPageBean cartPageBean){
        this.cartItemRepository = cartItemRepository;
        this.cartPageBean = cartPageBean;
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
    public void init() throws IOException{
        String productIdContext = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");

        if(productIdContext != null && !productIdContext.isBlank()){
            try {
                URL url = new URL("http://localhost:4000/products/" + productIdContext);
                ObjectMapper mapper = new ObjectMapper();
                this.product = mapper.readValue(url, ProductResponseDTO.class);
                getProductCustomizations();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect("home.xhtml?faces-redirect=true");
        }
    }

    public void addToCart(){
        UUID userId = sessionUserBean.getUser().getId();
        UUID productId = UUID.fromString(product.getId());

        CartItemRequestDTO dto = new CartItemRequestDTO();
        dto.setUserId(userId.toString());
        dto.setProductId(productId.toString());
        dto.setCustomizationPreview(customFieldsDict.toString());
        System.out.println("Customization preview: " + customFieldsDict.toString());
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
            customFieldsDict.put(s, "");
        }
    }

}
