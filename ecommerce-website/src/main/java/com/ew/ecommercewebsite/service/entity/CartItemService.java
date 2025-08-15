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

/**
 * Service class for managing cart items in the e-commerce system.
 * Handles CRUD operations for cart items and their relationships with products.
 */
@Service
public class CartItemService {
    /**
     * Repository for cart item data access operations
     */
    private final CartItemRepository cartItemRepository;
    /**
     * Repository for product data access operations
     */
    private final ProductRepository productRepository;

    /**
     * Constructs a new CartItemService with required repositories.
     *
     * @param cartItemRepository Repository for cart item operations
     * @param productRepository  Repository for product operations
     */
    public CartItemService(CartItemRepository cartItemRepository, ProductRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    /**
     * Retrieves all cart items in the system.
     *
     * @return List of cart items as DTOs
     */
    public List<CartItemResponseDTO> getCartItems(){
        List<CartItem> cartItems = cartItemRepository.findAll();

        List<CartItemResponseDTO> cartItemResponseDTOs = cartItems.stream()
                .map(cartItem -> CartItemMapper.toDTO(cartItem)).toList();

        return cartItemResponseDTOs;
    }

    /**
     * Creates a new cart item.
     *
     * @param cartItemRequestDTO The cart item data to create
     * @return The created cart item as DTO
     * @throws ProductNotFoundException         if the product doesn't exist
     * @throws CartItemIdAlreadyExistsException if the cart item ID already exists
     * @throws ProductOutOfStockException       if the product is out of stock
     */
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

    /**
     * Updates an existing cart item.
     *
     * @param id                 The ID of the cart item to update
     * @param cartItemRequestDTO The updated cart item data
     * @return The updated cart item as DTO
     * @throws CartItemNotFoundException  if the cart item is not found
     * @throws ProductOutOfStockException if the product is out of stock
     */
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

    /**
     * Deletes a cart item.
     *
     * @param id The ID of the cart item to delete
     * @throws CartItemNotFoundException if the cart item is not found
     */
    @Transactional
    public void deleteCartItem(CartItemId id){
        CartItem cartItem = cartItemRepository.findById(id).orElseThrow(
                () -> new CartItemNotFoundException("Cart item not found with ID: " + id));

        cartItemRepository.delete(cartItem);
    }

    /**
     * Retrieves all cart items for a specific user.
     *
     * @param userId The ID of the user
     * @return List of cart items as DTOs
     * @throws CartItemNotFoundException if no cart items are found for the user
     */
    public List<CartItemResponseDTO> getCartItemsByUserId(UUID userId){
        List<CartItem> cartItems = cartItemRepository.findByIdUserId(userId);
        if (cartItems.isEmpty()){
            throw new CartItemNotFoundException("Cart item not found with user ID: " + userId);
        }
        List<CartItemResponseDTO> cartItemResponseDTOs = cartItems.stream()
                .map(cartItem -> CartItemMapper.toDTO(cartItem)).toList();

        return cartItemResponseDTOs;
    }

    /**
     * Deletes all cart items for a specific user.
     *
     * @param userId The ID of the user
     * @throws CartItemNotFoundException if no cart items are found for the user
     */
    @Transactional
    public void deleteCartItemsByUserId(UUID userId){
        List<CartItem> cartItems = cartItemRepository.findByIdUserId(userId);
        if (cartItems.isEmpty()){
            throw new CartItemNotFoundException("Cart item not found with user ID: " + userId);
        }
        cartItemRepository.deleteAll(cartItems);
    }

}
