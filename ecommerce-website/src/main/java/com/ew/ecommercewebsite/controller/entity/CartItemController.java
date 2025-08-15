package com.ew.ecommercewebsite.controller.entity;


import com.ew.ecommercewebsite.dto.entity.CartItemRequestDTO;
import com.ew.ecommercewebsite.dto.entity.CartItemResponseDTO;
import com.ew.ecommercewebsite.service.entity.CartItemService;
import com.ew.ecommercewebsite.utils.CartItemId;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST Controller for managing shopping cart items.
 * Provides endpoints for CRUD operations on cart items.
 */
@RestController
@RequestMapping("/cart-items")
public class CartItemController {
    /**
     * Service layer component handling cart item business logic
     */
    private final CartItemService cartItemService;

    /**
     * Constructs CartItemController with required service dependency
     *
     * @param cartItemService the service handling cart item operations
     */
    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    /**
     * Retrieves all cart items
     *
     * @return ResponseEntity containing list of all cart items
     */
    @GetMapping
    public ResponseEntity<List<CartItemResponseDTO>> getCartItems(){
        List<CartItemResponseDTO> cartItems = cartItemService.getCartItems();
        return ResponseEntity.ok().body(cartItems);
    }

    /**
     * Creates a new cart item
     *
     * @param cartItemRequestDTO the cart item data to create
     * @return ResponseEntity containing the created cart item
     */
    @PostMapping
    public ResponseEntity<CartItemResponseDTO> createCartItem(@Valid @RequestBody CartItemRequestDTO cartItemRequestDTO){
        CartItemResponseDTO cartItemResponseDTO = cartItemService.createCartItem(cartItemRequestDTO);

        return ResponseEntity.ok().body(cartItemResponseDTO);
    }

    /**
     * Updates an existing cart item
     *
     * @param userId             ID of the user who owns the cart item
     * @param productId          ID of the product in the cart
     * @param cartItemRequestDTO updated cart item data
     * @return ResponseEntity containing the updated cart item
     */
    @PutMapping("/userId/{userId}/productId/{productId}")
    public ResponseEntity<CartItemResponseDTO> updateCartItem(@PathVariable UUID userId, @PathVariable UUID productId, @Validated({Default.class}) @RequestBody CartItemRequestDTO cartItemRequestDTO){
        CartItemId id = new CartItemId(userId, productId);
        CartItemResponseDTO cartItemResponseDTO = cartItemService.updateCartItem(id, cartItemRequestDTO);

        return ResponseEntity.ok().body(cartItemResponseDTO);
    }

    /**
     * Deletes a specific cart item
     *
     * @param userId    ID of the user who owns the cart item
     * @param productId ID of the product to remove from cart
     * @return ResponseEntity with no content
     */
    @DeleteMapping("/userId/{userId}/productId/{productId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable UUID userId, @PathVariable UUID productId){
        CartItemId id = new CartItemId(userId, productId);
        cartItemService.deleteCartItem(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves all cart items for a specific user
     *
     * @param userId ID of the user whose cart items to retrieve
     * @return ResponseEntity containing list of user's cart items
     */
    @GetMapping("/userId/{userId}")
    public ResponseEntity<List<CartItemResponseDTO>> getCartItemByUserId(@PathVariable UUID userId){

        List<CartItemResponseDTO> cartItemResponseDTOs = cartItemService.getCartItemsByUserId(userId);
        return ResponseEntity.ok().body(cartItemResponseDTOs);
    }

    /**
     * Deletes all cart items for a specific user
     *
     * @param userId ID of the user whose cart items to delete
     * @return ResponseEntity with no content
     */
    @DeleteMapping("/userId/{userId}")
    public ResponseEntity<Void> deleteCartItemsByUserId(@PathVariable UUID userId){
        cartItemService.deleteCartItemsByUserId(userId);
        return ResponseEntity.noContent().build();
    }

}
