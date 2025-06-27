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

@RestController
@RequestMapping("/customizations")
public class CustomizationController {

    private final CustomizationService customizationService;

    public CustomizationController(CustomizationService customizationService) {
        this.customizationService = customizationService;
    }

    @GetMapping
    public ResponseEntity<List<CustomizationResponseDTO>> getCustomizations(){
        List<CustomizationResponseDTO> customizations = customizationService.getCustomizations();
        return ResponseEntity.ok().body(customizations);
    }

    @PostMapping
    public ResponseEntity<CustomizationResponseDTO> createCustomization(@Valid @RequestBody CustomizationRequestDTO customizationRequestDTO){
        CustomizationResponseDTO customizationResponseDTO = customizationService.createCustomization(customizationRequestDTO);

        return ResponseEntity.ok().body(customizationResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomizationResponseDTO> updateCustomization(@PathVariable UUID id, @Validated({Default.class}) @RequestBody CustomizationRequestDTO customizationRequestDTO){
        CustomizationResponseDTO customizationResponseDTO = customizationService.updateCustomization(id, customizationRequestDTO);

        return ResponseEntity.ok().body(customizationResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomization(@PathVariable UUID id){
        customizationService.deleteCustomization(id);
        return ResponseEntity.noContent().build();
    }
}
