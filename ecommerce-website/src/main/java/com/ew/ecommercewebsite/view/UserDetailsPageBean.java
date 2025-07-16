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

@Named
@ViewScoped
public class UserDetailsPageBean implements Serializable {

    private SessionUserBean sessionUserBean;
    private List<OrderResponseDTO> orders = new ArrayList<>();
    private RestTemplate restTemplate = new RestTemplate();
    private Map<String, String> orderDisplayIds = new HashMap<>();

    @Inject
    public UserDetailsPageBean(@Named SessionUserBean sessionUserBean) {
        this.sessionUserBean = sessionUserBean;
    }

    @PostConstruct
    public void init() {
        if(sessionUserBean.getUser() != null){
            UUID userId = sessionUserBean.getUser().getId();
            String url = "http://localhost:4000/orders/userId/" + userId;
            try {
                OrderResponseDTO[] response = restTemplate.getForObject(url, OrderResponseDTO[].class);
                if (response != null) {
                    orders = Arrays.asList(response);
                    for(int i = 0; i < orders.size(); i++){
                        orderDisplayIds.put(orders.get(i).getId(), String.format("ORD-%05d", i + 1));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace(); // Optional: show a FacesMessage
            }
        }
    }

    public User getUser() {
        return sessionUserBean.getUser();
    }

    public List<OrderResponseDTO> getOrders() {
        return orders;
    }

    public void redirectIfNotLoggedIn() throws IOException {
        if (sessionUserBean.getUser() == null) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
        }
    }

    public String getOrderDisplayId(String orderId){
        return orderDisplayIds.getOrDefault(orderId, "ORD-XXXXX");
    }
}
