package com.ew.ecommercewebsite.view;

import com.ew.ecommercewebsite.dto.entity.OrderResponseDTO;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Named
@ViewScoped
public class OrderManagementBean implements Serializable {

    private List<OrderResponseDTO> orders;
    private RestTemplate restTemplate = new RestTemplate();
    @Inject
    private SessionUserBean sessionUserBean;

    @PostConstruct
    public void init() {
        fetchOrders();
    }

    public void redirectIfNotAdmin() throws IOException {
        if (sessionUserBean.getUser() == null || !sessionUserBean.getUser().getIsAdmin()) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("home.xhtml");
        }
    }

    public void fetchOrders() {
        String url = "http://localhost:4000/orders";
        ResponseEntity<OrderResponseDTO[]> response = restTemplate.getForEntity(url, OrderResponseDTO[].class);
        orders = Arrays.asList(Objects.requireNonNull(response.getBody()));
    }

    public String getOrderDisplayId(String orderId) {
        return "ORD-" + Math.abs(orderId.hashCode());  // consistent display-friendly id
    }

    public String viewOrderDetails(String orderId) {
        return "/order-details.xhtml?id=" + orderId + "&faces-redirect=true";
    }

    public List<OrderResponseDTO> getOrders() {
        return orders;
    }
}
