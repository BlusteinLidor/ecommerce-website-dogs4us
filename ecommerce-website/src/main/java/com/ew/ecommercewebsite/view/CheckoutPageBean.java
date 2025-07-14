package com.ew.ecommercewebsite.view;

import com.ew.ecommercewebsite.dto.entity.CartItemResponseDTO;
import com.ew.ecommercewebsite.dto.entity.CartItemWithProductResponseDTO;
import com.ew.ecommercewebsite.dto.entity.OrderRequestDTO;
import com.ew.ecommercewebsite.dto.entity.ProductResponseDTO;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Named
@ViewScoped
public class CheckoutPageBean implements Serializable {

    private final CartPageBean cartPageBean;
    @Autowired
    private SessionUserBean sessionUserBean;

    private List<CartItemWithProductResponseDTO> cartItems = new ArrayList<>();
    private RestTemplate restTemplate = new RestTemplate();

    private String name;
    private String address;
    private String phone;

    @Inject
    public CheckoutPageBean(@Named CartPageBean cartPageBean) {
        this.cartPageBean = cartPageBean;
    }

    @PostConstruct
    public void init() {
        if (sessionUserBean == null || sessionUserBean.getUser() == null) {
            return;
        }

        UUID userId = sessionUserBean.getUser().getId();
        String url = "http://localhost:4000/cart-items/userId/" + userId;

        CartItemResponseDTO[] items = restTemplate.getForObject(url, CartItemResponseDTO[].class);
        for (CartItemResponseDTO item : items) {
            ProductResponseDTO product = restTemplate.getForObject(
                    "http://localhost:4000/products/" + item.getProductId(), ProductResponseDTO.class);

            CartItemWithProductResponseDTO fullItem = new CartItemWithProductResponseDTO();
            fullItem.setCartItem(item);
            fullItem.setProduct(product);
            cartItems.add(fullItem);
        }

        // Pre-fill user name if available
        this.name = sessionUserBean.getUser().getName();
    }

    public void placeOrder() {
        // TODO: Implement actual order saving logic here (call order endpoint, etc.)
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Order placed successfully!", null));

        double totalPrice = getTotalCost();

        UUID userId = sessionUserBean.getUser().getId();
        String url = "http://localhost:4000/cart-items/userId/" + userId;

        try{
            cartPageBean.setCartItemCount(0);
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect("order-confirmation.xhtml?faces-redirect=true");
            restTemplate.delete(url);
        }
        catch (IOException e){
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error placing order", null));
            cartPageBean.setCartItemCount(cartItems.size());
        }

//        Create Order Entity
        url = "http://localhost:4000/orders";
        OrderRequestDTO order = new OrderRequestDTO();
        order.setUser(userId.toString());
        order.setOrderDate(LocalDate.now().toString());
        order.setTotalAmount(String.format("%.2f" ,totalPrice));
        restTemplate.postForEntity(url, order, Void.class);

//        @TODO
//        Create Order Items


    }

    public void redirectIfNotLoggedIn() throws IOException {
        if (sessionUserBean.getUser() == null) {
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect("home.xhtml?faces-redirect=true");
        }
    }

    public List<CartItemWithProductResponseDTO> getCartItems() {
        return cartItems;
    }

    public double getTotalCost() {
        return cartItems.stream()
                .mapToDouble(item -> Double.parseDouble(item.getProduct().getPrice()) * Integer.parseInt(item.getCartItem().getQuantity()))
                .sum();
    }

    // Getters and setters for name, address, phone
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}

