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

/**
 * Managed bean responsible for handling order management operations in the admin interface.
 * This bean provides functionality to view and manage orders in the system.
 */
@Named
@ViewScoped
public class OrderManagementBean implements Serializable {

    /**
     * List of all orders in the system
     */
    private List<OrderResponseDTO> orders;
    /**
     * REST template for making HTTP requests
     */
    private RestTemplate restTemplate = new RestTemplate();
    /**
     * Current user session bean
     */
    @Inject
    private SessionUserBean sessionUserBean;

    /**
     * Initializes the bean by fetching all orders.
     * Called automatically after bean construction.
     */
    @PostConstruct
    public void init() {
        fetchOrders();
    }

    /**
     * Redirects to home page if the current user is not an administrator.
     *
     * @throws IOException if there's an error during redirect
     */
    public void redirectIfNotAdmin() throws IOException {
        if (sessionUserBean.getUser() == null || !sessionUserBean.getUser().getIsAdmin()) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("home.xhtml");
        }
    }

    /**
     * Fetches all orders from the backend service.
     * Updates the orders list with the retrieved data.
     */
    public void fetchOrders() {
        String url = "http://localhost:4000/orders";
        ResponseEntity<OrderResponseDTO[]> response = restTemplate.getForEntity(url, OrderResponseDTO[].class);
        orders = Arrays.asList(Objects.requireNonNull(response.getBody()));
    }

    /**
     * Generates a display-friendly order ID.
     *
     * @param orderId the original UUID of the order
     * @return a formatted order ID string prefixed with "ORD-"
     */
    public String getOrderDisplayId(String orderId) {
        return "ORD-" + Math.abs(orderId.hashCode());  // consistent display-friendly id
    }

    /**
     * Generates navigation URL for viewing order details.
     *
     * @param orderId the ID of the order to view
     * @return navigation string with faces-redirect parameter
     */
    public String viewOrderDetails(String orderId) {
        return "/order-details.xhtml?id=" + orderId + "&faces-redirect=true";
    }

    /**
     * Gets the list of all orders.
     *
     * @return list of order response DTOs
     */
    public List<OrderResponseDTO> getOrders() {
        return orders;
    }
}
