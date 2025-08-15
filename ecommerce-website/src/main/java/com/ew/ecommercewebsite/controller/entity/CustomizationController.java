package com.ew.ecommercewebsite.controller.entity;

import com.ew.ecommercewebsite.dto.entity.CustomizationRequestDTO;
import com.ew.ecommercewebsite.dto.entity.CustomizationResponseDTO;
import com.ew.ecommercewebsite.service.entity.CustomizationService;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for managing Customization operations.
 * Provides endpoints for CRUD operations on customizations.
 */
@RestController
@RequestMapping("/customizations")
public class CustomizationController {

    /**
     * Service layer for handling customization business logic
     */
    private final CustomizationService customizationService;

    /**
     * Constructs a new CustomizationController with the specified service
     *
     * @param customizationService the service to handle customization operations
     */
    public CustomizationController(CustomizationService customizationService) {
        this.customizationService = customizationService;
    }

    /**
     * Retrieves all customizations
     *
     * @return ResponseEntity containing a list of all customizations
     */
    @GetMapping
    public ResponseEntity<List<CustomizationResponseDTO>> getCustomizations(){
        List<CustomizationResponseDTO> customizations = customizationService.getCustomizations();
        return ResponseEntity.ok().body(customizations);
    }

    /**
     * Creates a new customization
     *
     * @param customizationRequestDTO the customization data to create
     * @return ResponseEntity containing the created customization
     */
    @PostMapping
    public ResponseEntity<CustomizationResponseDTO> createCustomization(@Valid @RequestBody CustomizationRequestDTO customizationRequestDTO){
        CustomizationResponseDTO customizationResponseDTO = customizationService.createCustomization(customizationRequestDTO);

        return ResponseEntity.ok().body(customizationResponseDTO);
    }

    /**
     * Updates an existing customization
     *
     * @param id                      the ID of the customization to update
     * @param customizationRequestDTO the updated customization data
     * @return ResponseEntity containing the updated customization
     */
    @PutMapping("/{id}")
    public ResponseEntity<CustomizationResponseDTO> updateCustomization(@PathVariable UUID id, @Validated({Default.class}) @RequestBody CustomizationRequestDTO customizationRequestDTO){
        CustomizationResponseDTO customizationResponseDTO = customizationService.updateCustomization(id, customizationRequestDTO);

        return ResponseEntity.ok().body(customizationResponseDTO);
    }

    /**
     * Deletes a customization by its ID
     *
     * @param id the ID of the customization to delete
     * @return ResponseEntity with no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomization(@PathVariable UUID id){
        customizationService.deleteCustomization(id);
        return ResponseEntity.noContent().build();
    }
}
