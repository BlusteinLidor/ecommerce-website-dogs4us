package com.ew.ecommercewebsite.mapper;

import com.ew.ecommercewebsite.dto.entity.CustomizationRequestDTO;
import com.ew.ecommercewebsite.dto.entity.CustomizationResponseDTO;
import com.ew.ecommercewebsite.model.Customization;

/**
 * Mapper class responsible for converting between Customization entity and its DTO representations.
 * This class provides utility methods to map between domain models and data transfer objects.
 */
public class CustomizationMapper {

    /**
     * Converts a Customization entity to its DTO representation.
     *
     * @param customization The Customization entity to be converted
     * @return CustomizationResponseDTO containing the mapped data
     */
    public static CustomizationResponseDTO toDTO(Customization customization){
        CustomizationResponseDTO customizationDTO = new CustomizationResponseDTO();
        customizationDTO.setId(customization.getId().toString());
        customizationDTO.setProduct(customization.getProduct().getId().toString());
        customizationDTO.setOrder(customization.getOrder().getOrderId().toString());
        customizationDTO.setFieldName(customization.getFieldName());
        customizationDTO.setFieldValue(customization.getFieldValue());

        return customizationDTO;
    }

    /**
     * Converts a CustomizationRequestDTO to a Customization entity.
     *
     * @param customizationRequestDTO The DTO containing the customization request data
     * @return Customization entity with the mapped data
     */
    public static Customization toModel(CustomizationRequestDTO customizationRequestDTO){
        Customization customization = new Customization();
        customization.setFieldName(customizationRequestDTO.getFieldName());
        customization.setFieldValue(customizationRequestDTO.getFieldValue());

        return customization;
    }
}
