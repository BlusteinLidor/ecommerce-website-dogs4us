package com.ew.ecommercewebsite.controller;


import com.ew.ecommercewebsite.dto.ProductResponseDTO;
import com.ew.ecommercewebsite.dto.UserRequestDTO;
import com.ew.ecommercewebsite.dto.UserResponseDTO;
import com.ew.ecommercewebsite.service.ProductService;
import com.ew.ecommercewebsite.service.UserService;
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

}
