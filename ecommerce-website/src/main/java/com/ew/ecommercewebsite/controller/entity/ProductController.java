package com.ew.ecommercewebsite.controller.entity;


import com.ew.ecommercewebsite.dto.entity.ProductRequestDTO;
import com.ew.ecommercewebsite.dto.entity.ProductResponseDTO;
import com.ew.ecommercewebsite.service.entity.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for managing Product-related operations.
 * Provides endpoints for CRUD operations on products.
 */
@RestController
@RequestMapping("/products")
public class ProductController {


    /**
     * The service responsible for handling product-related business logic.
     */
    private final ProductService productService;

    /**
     * Constructs a new ProductController with the specified ProductService.
     *
     * @param productService the service to handle product operations
     */
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Retrieves all products.
     *
     * @return ResponseEntity containing a list of all products
     */
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getProducts() {
        List<ProductResponseDTO> products = productService.getProducts();
        return ResponseEntity.ok().body(products);
    }

    /**
     * Creates a new product.
     *
     * @param productRequestDTO the product data to create
     * @return ResponseEntity containing the created product
     */
    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductRequestDTO productRequestDTO) {
        ProductResponseDTO productResponseDTO = productService.createProduct(productRequestDTO);

        return ResponseEntity.ok().body(productResponseDTO);
    }

    /**
     * Updates an existing product.
     *
     * @param id                the ID of the product to update
     * @param productRequestDTO the updated product data
     * @return ResponseEntity containing the updated product
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable UUID id, @Validated({Default.class}) @RequestBody ProductRequestDTO productRequestDTO) {
        ProductResponseDTO productResponseDTO = productService.updateProduct(id, productRequestDTO);

        return ResponseEntity.ok().body(productResponseDTO);
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id the ID of the product to delete
     * @return ResponseEntity with no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param id the ID of the product to retrieve
     * @return ResponseEntity containing the requested product
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable UUID id){
        ProductResponseDTO productResponseDTO = productService.getProductById(id);

        return ResponseEntity.ok().body(productResponseDTO);
    }

}
