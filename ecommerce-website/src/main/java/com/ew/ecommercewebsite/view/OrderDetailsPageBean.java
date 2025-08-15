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

/**
 * Manages the order details page, handling the display of order items and their associated products.
 */
@Named
@RequestScoped
public class OrderDetailsPageBean implements Serializable{
    /**
     * The current user's session information
     */
    private SessionUserBean sessionUserBean;
    /**
     * The unique identifier of the order
     */
    private String orderId;
    /**
     * List of order items with their associated product details
     */
    private List<OrderItemWithProduct> orderItems = new ArrayList<>();
    /**
     * Formatted display ID for the order
     */
    private String displayId;
    /**
     * REST template for making HTTP requests
     */

    private RestTemplate restTemplate;

    /**
     * Constructs a new OrderDetailsPageBean and initializes the REST template.
     */
    public OrderDetailsPageBean() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * Initializes the order details page by fetching order items and their associated products.
     * Redirects to home page if no order ID is provided.
     *
     * @throws IOException if there's an error during page redirection
     */
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

    /**
     * Generates a numerical index for the order ID for display purposes.
     *
     * @param orderId the unique identifier of the order
     * @return a numeric index between 0 and 99999
     */
    private int getOrderIndex(String orderId) {
        // Simplified – could be replaced by a stored value in order if available
        return Math.abs(orderId.hashCode()) % 100000;
    }

    /**
     * @return the order's unique identifier
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * @param orderId the order ID to set
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * @return list of order items with their associated products
     */
    public List<OrderItemWithProduct> getOrderItems() {
        return orderItems;
    }

    /**
     * @return the formatted display ID of the order
     */
    public String getDisplayId() {
        return displayId;
    }

    /**
     * Inner class that combines order item information with its associated product details.
     */
    public static class OrderItemWithProduct implements Serializable {
        /** The order item details */
        private final OrderItemResponseDTO item;
        /** The associated product details */
        private final ProductResponseDTO product;

        /**
         * Constructs a new OrderItemWithProduct with the specified item and product.
         *
         * @param item the order item details
         * @param product the associated product details
         */
        public OrderItemWithProduct(OrderItemResponseDTO item, ProductResponseDTO product) {
            this.item = item;
            this.product = product;
        }

        /**
         * @return the order item details
         */
        public OrderItemResponseDTO getCartItem() {
            return item;
        }

        /**
         * @return the associated product details
         */
        public ProductResponseDTO getProduct() {
            return product;
        }

        /**
         * @return the quantity of the ordered item
         */
        public int getQuantity() {
            return Integer.parseInt(item.getQuantity());
        }
    }
}
