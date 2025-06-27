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

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getProducts() {
        List<ProductResponseDTO> products = productService.getProducts();
        return ResponseEntity.ok().body(products);
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductRequestDTO productRequestDTO) {
        ProductResponseDTO productResponseDTO = productService.createProduct(productRequestDTO);

        return ResponseEntity.ok().body(productResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable UUID id, @Validated({Default.class}) @RequestBody ProductRequestDTO productRequestDTO) {
        ProductResponseDTO productResponseDTO = productService.updateProduct(id, productRequestDTO);

        return ResponseEntity.ok().body(productResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}
