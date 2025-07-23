package com.ew.ecommercewebsite.view;

import com.ew.ecommercewebsite.dto.entity.OrderItemResponseDTO;
import com.ew.ecommercewebsite.dto.entity.ProductResponseDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Named
@RequestScoped
public class OrderDetailsPageBean implements Serializable{
    private SessionUserBean sessionUserBean;
    private String orderId;
    private List<OrderItemWithProduct> orderItems = new ArrayList<>();
    private String displayId;

    private RestTemplate restTemplate;

    public OrderDetailsPageBean() {
        this.restTemplate = new RestTemplate();
    }

    public void init() throws IOException {
        if (orderId != null) {
            String url = "http://localhost:4000/order-items/orderId/" + orderId;
            try {
                OrderItemResponseDTO[] response = restTemplate.getForObject(url, OrderItemResponseDTO[].class);
//                int index = 0;
                for (OrderItemResponseDTO item : response) {
                    UUID productId = UUID.fromString(item.getProductId());
                    ProductResponseDTO product = restTemplate.getForObject("http://localhost:4000/products/" + productId, ProductResponseDTO.class);
                    orderItems.add(new OrderItemWithProduct(item, product));
                }
//                displayId = String.format("ORD-%05d", getOrderIndex(orderId));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect("home.xhtml?faces-redirect=true");
        }
    }

    // Simulate lookup of order position (replace with better logic if needed)
    private int getOrderIndex(String orderId) {
        // Simplified – could be replaced by a stored value in order if available
        return Math.abs(orderId.hashCode()) % 100000;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<OrderItemWithProduct> getOrderItems() {
        return orderItems;
    }

    public String getDisplayId() {
        return displayId;
    }

    public static class OrderItemWithProduct implements Serializable {
        private final OrderItemResponseDTO item;
        private final ProductResponseDTO product;

        public OrderItemWithProduct(OrderItemResponseDTO item, ProductResponseDTO product) {
            this.item = item;
            this.product = product;
        }

        public OrderItemResponseDTO getCartItem() {
            return item;
        }

        public ProductResponseDTO getProduct() {
            return product;
        }

        public int getQuantity() {
            return Integer.parseInt(item.getQuantity());
        }
    }
}
