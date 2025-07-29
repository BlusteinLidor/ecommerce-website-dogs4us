package com.ew.ecommercewebsite.view;

import com.ew.ecommercewebsite.dto.entity.ProductRequestDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import org.springframework.util.StreamUtils;

import java.io.*;

@Named
@RequestScoped
public class FileUploadBean {
    @Setter
    @Getter
    private ProductRequestDTO product;
    @Setter
    @Getter
    private UploadedFile file;
    private final String imageDir = FacesContext.getCurrentInstance().getExternalContext().getRealPath("assets/img/");

    public void saveImage() {
        if (file == null || file.getFileName() == null) return;

        try {
            // Get file extension
            String originalName = file.getFileName();
            String extension = originalName.substring(originalName.lastIndexOf('.') + 1);

            // Count existing images
            File dir = new File("assets/img/");
            String[] existing = dir.list((d, name) -> name.matches("product\\d+\\..+"));
            int nextIndex = (existing != null) ? existing.length + 1 : 1;

            String filename = "product" + nextIndex + "." + extension;
            File target = new File(dir, filename);

            try (InputStream input = file.getInputStream();
                 OutputStream output = new FileOutputStream(target)) {
                StreamUtils.copy(input, output);
            }

            // Update product DTO
            product.setImageURL("assets/img/" + filename);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
