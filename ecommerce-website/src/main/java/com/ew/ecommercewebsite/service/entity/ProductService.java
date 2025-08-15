package com.ew.ecommercewebsite.service.entity;

import com.ew.ecommercewebsite.dto.entity.ProductRequestDTO;
import com.ew.ecommercewebsite.dto.entity.ProductResponseDTO;
import com.ew.ecommercewebsite.exception.CategoryNotFoundException;
import com.ew.ecommercewebsite.exception.ProductNotFoundException;
import com.ew.ecommercewebsite.mapper.ProductMapper;
import com.ew.ecommercewebsite.model.Category;
import com.ew.ecommercewebsite.model.Product;
import com.ew.ecommercewebsite.repository.CartItemRepository;
import com.ew.ecommercewebsite.repository.CategoryRepository;
import com.ew.ecommercewebsite.repository.OrderItemRepository;
import com.ew.ecommercewebsite.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Service class for managing product-related operations in the e-commerce system.
 */
@Service
public class ProductService {
    /**
     * Repository for product data access
     */
    private final ProductRepository productRepository;
    /**
     * Repository for category data access
     */
    private final CategoryRepository categoryRepository;
    /**
     * Repository for cart item data access
     */
    private final CartItemRepository cartItemRepository;
    /**
     * Repository for order item data access
     */
    private final OrderItemRepository orderItemRepository;

    /**
     * Constructs a new ProductService with required repositories.
     *
     * @param productRepository   Repository for product operations
     * @param categoryRepository  Repository for category operations
     * @param cartItemRepository  Repository for cart item operations
     * @param orderItemRepository Repository for order item operations
     */
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository
    , CartItemRepository cartItemRepository, OrderItemRepository orderItemRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.cartItemRepository = cartItemRepository;
        this.orderItemRepository = orderItemRepository;
    }

    /**
     * Retrieves all products from the database.
     *
     * @return List of all products as DTOs
     */
    public List<ProductResponseDTO> getProducts() {
        List<Product> products = productRepository.findAll();

        List<ProductResponseDTO> productResponseDTOs = products.stream()
                .map(product -> ProductMapper.toDTO(product)).toList();

        return productResponseDTOs;
    }

    /**
     * Creates a new product in the database.
     *
     * @param productRequestDTO The product data to create
     * @return The created product as DTO
     * @throws CategoryNotFoundException if the specified category doesn't exist
     */
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        Category category = categoryRepository.findByName(productRequestDTO.getCategory())
                .orElseThrow(() -> new CategoryNotFoundException(
                        "Category not found with name: "
                                + productRequestDTO.getCategory()));

        Product product = ProductMapper.toModel(productRequestDTO);
        product.setCategory(category);
        Product newProduct = productRepository.save(product);

        return ProductMapper.toDTO(newProduct);

    }

    /**
     * Updates an existing product in the database.
     *
     * @param id                The ID of the product to update
     * @param productRequestDTO The updated product data
     * @return The updated product as DTO
     * @throws ProductNotFoundException  if the product doesn't exist
     * @throws CategoryNotFoundException if the specified category doesn't exist
     */
    public ProductResponseDTO updateProduct(UUID id, ProductRequestDTO productRequestDTO) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("Product not found with ID: " + id));

        Category category = categoryRepository.findByName(productRequestDTO.getCategory())
                .orElseThrow(() -> new CategoryNotFoundException(
                        "Category not found with name: "
                                + productRequestDTO.getCategory()));



        product.setName(productRequestDTO.getName());
        product.setDescription(productRequestDTO.getDescription());
        product.setImageURL(productRequestDTO.getImageURL());
        product.setPrice(Double.parseDouble(productRequestDTO.getPrice()));
        product.setStockQuantity(Integer.parseInt(productRequestDTO.getStockQuantity()));
        product.setCustomizableFields(productRequestDTO.getCustomizableFields());
        product.setCategory(category);

        Product updatedProduct = productRepository.save(product);

        return ProductMapper.toDTO(updatedProduct);
    }

    /**
     * Deletes a product and its related cart and order items from the database.
     *
     * @param id The ID of the product to delete
     * @throws ProductNotFoundException if the product doesn't exist
     */
    @Transactional
    public void deleteProduct(UUID id){
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("Product not found with ID: " + id));
        
        
        cartItemRepository.deleteByIdProductId(id);
        orderItemRepository.deleteByIdProductId(id);

        productRepository.delete(product);
    }

    /**
     * Retrieves a specific product by its ID.
     *
     * @param id The ID of the product to retrieve
     * @return The product as DTO
     * @throws ProductNotFoundException if the product doesn't exist
     */
    public ProductResponseDTO getProductById(UUID id){
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("Product not found with ID: " + id));

        return ProductMapper.toDTO(product);
    }
}
