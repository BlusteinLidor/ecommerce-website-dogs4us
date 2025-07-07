package com.ew.ecommercewebsite.view;

import com.ew.ecommercewebsite.dto.entity.ProductResponseDTO;
import com.ew.ecommercewebsite.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;

@Named
@RequestScoped
public class ProductPageBean {
    private ProductResponseDTO product;

    public ProductResponseDTO getProduct() {
        return product;
    }

    public void setProduct(ProductResponseDTO product) {
        this.product = product;
    }

    @PostConstruct
    public void init(){
        String productId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");

        if(productId != null){
            try {
                URL url = new URL("http://localhost:4000/products/" + productId);
                ObjectMapper mapper = new ObjectMapper();
                this.product = mapper.readValue(url, ProductResponseDTO.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
