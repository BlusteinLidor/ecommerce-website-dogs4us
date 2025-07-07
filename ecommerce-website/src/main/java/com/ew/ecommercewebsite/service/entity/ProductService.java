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

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderItemRepository orderItemRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository
    , CartItemRepository cartItemRepository, OrderItemRepository orderItemRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.cartItemRepository = cartItemRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public List<ProductResponseDTO> getProducts() {
        List<Product> products = productRepository.findAll();

        List<ProductResponseDTO> productResponseDTOs = products.stream()
                .map(product -> ProductMapper.toDTO(product)).toList();

        return productResponseDTOs;
    }

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

    @Transactional
    public void deleteProduct(UUID id){
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("Product not found with ID: " + id));
        
        
        cartItemRepository.deleteByIdProductId(id);
        orderItemRepository.deleteByIdProductId(id);

        productRepository.delete(product);
    }
}
