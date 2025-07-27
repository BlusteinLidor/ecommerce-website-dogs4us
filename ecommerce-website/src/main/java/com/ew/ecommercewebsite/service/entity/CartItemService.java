package com.ew.ecommercewebsite.service.entity;

import com.ew.ecommercewebsite.dto.entity.CartItemRequestDTO;
import com.ew.ecommercewebsite.dto.entity.CartItemResponseDTO;
import com.ew.ecommercewebsite.exception.CartItemIdAlreadyExistsException;
import com.ew.ecommercewebsite.exception.CartItemNotFoundException;
import com.ew.ecommercewebsite.exception.ProductNotFoundException;
import com.ew.ecommercewebsite.exception.ProductOutOfStockException;
import com.ew.ecommercewebsite.mapper.CartItemMapper;
import com.ew.ecommercewebsite.model.CartItem;
import com.ew.ecommercewebsite.model.Product;
import com.ew.ecommercewebsite.repository.CartItemRepository;
import com.ew.ecommercewebsite.repository.ProductRepository;
import com.ew.ecommercewebsite.utils.CartItemId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public CartItemService(CartItemRepository cartItemRepository, ProductRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    public List<CartItemResponseDTO> getCartItems(){
        List<CartItem> cartItems = cartItemRepository.findAll();

        List<CartItemResponseDTO> cartItemResponseDTOs = cartItems.stream()
                .map(cartItem -> CartItemMapper.toDTO(cartItem)).toList();

        return cartItemResponseDTOs;
    }

    public CartItemResponseDTO createCartItem(CartItemRequestDTO cartItemRequestDTO){
        CartItem cartItem = CartItemMapper.toModel(cartItemRequestDTO);
        Product product = productRepository.findById(UUID.fromString(cartItemRequestDTO.getProductId())).orElse(null);
        if (product == null){
            throw new ProductNotFoundException("Product with this ID does not exist "
            + cartItemRequestDTO.getProductId());
        }
        if (cartItemRepository.existsById(cartItem.getId())){
            throw new CartItemIdAlreadyExistsException("Cart item with this ID already exists "
                    + cartItemRequestDTO.getUserId() + " " + cartItemRequestDTO.getProductId());
        }
        if (product.getStockQuantity() < cartItem.getQuantity()){
            throw new ProductOutOfStockException("Product with this ID does not have enough stock "
            + product.getId());
        }
        CartItem newCartItem = cartItemRepository.save(cartItem);

        return CartItemMapper.toDTO(newCartItem);
    }

    public CartItemResponseDTO updateCartItem(CartItemId id, CartItemRequestDTO cartItemRequestDTO){
        CartItem cartItem = cartItemRepository.findById(id).orElseThrow(
                () -> new CartItemNotFoundException("Cart item not found with ID: " + id));

        Product product = productRepository.findById(UUID.fromString(cartItemRequestDTO.getProductId())).orElse(null);
        int productQuantityLeft = product.getStockQuantity();
        int cartItemQuantity = Integer.parseInt(cartItemRequestDTO.getQuantity());
        if (productQuantityLeft < cartItemQuantity){
            throw new ProductOutOfStockException("Product with this ID does not have enough stock "
            + product.getId());
        }

        cartItem.setQuantity(cartItemQuantity);
        cartItem.setCustomizationPreview(cartItemRequestDTO.getCustomizationPreview());

        CartItem updatedCartItem = cartItemRepository.save(cartItem);

        return CartItemMapper.toDTO(updatedCartItem);
    }

    @Transactional
    public void deleteCartItem(CartItemId id){
        CartItem cartItem = cartItemRepository.findById(id).orElseThrow(
                () -> new CartItemNotFoundException("Cart item not found with ID: " + id));

        cartItemRepository.delete(cartItem);
    }

    public List<CartItemResponseDTO> getCartItemsByUserId(UUID userId){
        List<CartItem> cartItems = cartItemRepository.findByIdUserId(userId);
        if (cartItems.isEmpty()){
            throw new CartItemNotFoundException("Cart item not found with user ID: " + userId);
        }
        List<CartItemResponseDTO> cartItemResponseDTOs = cartItems.stream()
                .map(cartItem -> CartItemMapper.toDTO(cartItem)).toList();

        return cartItemResponseDTOs;
    }

    @Transactional
    public void deleteCartItemsByUserId(UUID userId){
        List<CartItem> cartItems = cartItemRepository.findByIdUserId(userId);
        if (cartItems.isEmpty()){
            throw new CartItemNotFoundException("Cart item not found with user ID: " + userId);
        }
        cartItemRepository.deleteAll(cartItems);
    }

}
