package com.ew.ecommercewebsite.view;

import com.ew.ecommercewebsite.dto.entity.OrderResponseDTO;
import com.ew.ecommercewebsite.dto.entity.UserResponseDTO;
import com.ew.ecommercewebsite.model.User;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * Bean class that manages user details and related orders in the user details page.
 * This class is view scoped and maintains the state for a single user's session.
 */
@Named
@ViewScoped
public class UserDetailsPageBean implements Serializable {

    /**
     * The session bean containing the current logged-in user's information
     */
    private SessionUserBean sessionUserBean;
    /**
     * List of orders associated with the current user
     */
    private List<OrderResponseDTO> orders = new ArrayList<>();
    /**
     * REST template for making HTTP requests
     */
    private RestTemplate restTemplate = new RestTemplate();
    /**
     * Map containing order IDs and their corresponding display format
     */
    private Map<String, String> orderDisplayIds = new HashMap<>();

    /**
     * Constructs a new UserDetailsPageBean with the provided session user bean.
     *
     * @param sessionUserBean The session bean containing user information
     */
    @Inject
    public UserDetailsPageBean(@Named SessionUserBean sessionUserBean) {
        this.sessionUserBean = sessionUserBean;
    }

    /**
     * Initializes the bean by fetching the user's orders from the backend service.
     * Creates display IDs for each order in the format "ORD-XXXXX".
     */
    @PostConstruct
    public void init() {
        // check that the user is connected
        if(sessionUserBean.getUser() != null){
            UUID userId = sessionUserBean.getUser().getId();
            String url = "http://localhost:4000/orders/userId/" + userId;
            try {
                // Get the orders of the user
                OrderResponseDTO[] response = restTemplate.getForObject(url, OrderResponseDTO[].class);
                if (response != null) {
                    // Store orders from response array into orders list and create display IDs
                    orders = Arrays.asList(response);
                    for(int i = 0; i < orders.size(); i++){
                        orderDisplayIds.put(orders.get(i).getId(), String.format("ORD-%05d", i + 1));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Returns the current logged-in user.
     *
     * @return The current User object from the session
     */
    public User getUser() {
        return sessionUserBean.getUser();
    }

    /**
     * Returns the list of orders for the current user.
     *
     * @return List of OrderResponseDTO objects
     */
    public List<OrderResponseDTO> getOrders() {
        return orders;
    }

    /**
     * Redirects to the login page if no user is currently logged in.
     *
     * @throws IOException If there is an error during redirect
     */
    public void redirectIfNotLoggedIn() throws IOException {
        if (sessionUserBean.getUser() == null) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
        }
    }

    /**
     * Returns a formatted display ID for a given order ID.
     *
     * @param orderId The UUID of the order
     * @return A formatted string in the format "ORD-XXXXX"
     */
    public String getOrderDisplayId(String orderId){
        return orderDisplayIds.getOrDefault(orderId, "ORD-XXXXX");
    }
}
