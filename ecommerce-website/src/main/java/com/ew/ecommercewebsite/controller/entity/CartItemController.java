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

@RestController
@RequestMapping("/cart-items")
public class CartItemController {
    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponseDTO>> getCartItems(){
        List<CartItemResponseDTO> cartItems = cartItemService.getCartItems();
        return ResponseEntity.ok().body(cartItems);
    }

    @PostMapping
    public ResponseEntity<CartItemResponseDTO> createCartItem(@Valid @RequestBody CartItemRequestDTO cartItemRequestDTO){
        CartItemResponseDTO cartItemResponseDTO = cartItemService.createCartItem(cartItemRequestDTO);

        return ResponseEntity.ok().body(cartItemResponseDTO);
    }

    @PutMapping("/userId/{userId}/productId/{productId}")
    public ResponseEntity<CartItemResponseDTO> updateCartItem(@PathVariable UUID userId, @PathVariable UUID productId, @Validated({Default.class}) @RequestBody CartItemRequestDTO cartItemRequestDTO){
        CartItemId id = new CartItemId(userId, productId);
        CartItemResponseDTO cartItemResponseDTO = cartItemService.updateCartItem(id, cartItemRequestDTO);

        return ResponseEntity.ok().body(cartItemResponseDTO);
    }

    @DeleteMapping("/userId/{userId}/productId/{productId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable UUID userId, @PathVariable UUID productId){
        CartItemId id = new CartItemId(userId, productId);
        cartItemService.deleteCartItem(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/userId/{userId}")
    public ResponseEntity<List<CartItemResponseDTO>> getCartItemByUserId(@PathVariable UUID userId){

        List<CartItemResponseDTO> cartItemResponseDTOs = cartItemService.getCartItemsByUserId(userId);
        return ResponseEntity.ok().body(cartItemResponseDTOs);
    }

    @DeleteMapping("/userId/{userId}")
    public ResponseEntity<Void> deleteCartItemsByUserId(@PathVariable UUID userId){
        cartItemService.deleteCartItemsByUserId(userId);
        return ResponseEntity.noContent().build();
    }

}
