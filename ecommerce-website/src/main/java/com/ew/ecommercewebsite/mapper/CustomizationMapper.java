package com.ew.ecommercewebsite.mapper;

import com.ew.ecommercewebsite.dto.CustomizationRequestDTO;
import com.ew.ecommercewebsite.dto.CustomizationResponseDTO;
import com.ew.ecommercewebsite.model.Customization;

public class CustomizationMapper {

    public static CustomizationResponseDTO toDTO(Customization customization){
        CustomizationResponseDTO customizationDTO = new CustomizationResponseDTO();
        customizationDTO.setId(customization.getId().toString());
        customizationDTO.setProduct(customization.getProduct().getId().toString());
        customizationDTO.setOrder(customization.getOrder().getOrderId().toString());
        customizationDTO.setFieldName(customization.getFieldName());
        customizationDTO.setFieldValue(customization.getFieldValue());

        return customizationDTO;
    }

    public static Customization toModel(CustomizationRequestDTO customizationRequestDTO){
        Customization customization = new Customization();
        customization.setFieldName(customizationRequestDTO.getFieldName());
        customization.setFieldValue(customizationRequestDTO.getFieldValue());

        return customization;
    }
}
