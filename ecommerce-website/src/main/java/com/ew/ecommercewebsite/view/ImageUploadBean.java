package com.ew.ecommercewebsite.view;

import com.ew.ecommercewebsite.dto.entity.ProductResponseDTO;
import com.ew.ecommercewebsite.utils.CustomField;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import java.io.*;
import java.util.List;

@Named
@ViewScoped
public class ImageUploadBean implements Serializable {

    @Setter
    @Getter
    private UploadedFile file;

    @Inject
    private ProductPageBean productPageBean;

    public void handleFileUpload(FileUploadEvent event) {
        UploadedFile file = event.getFile();
        if (file == null || file.getFileName() == null) {
            System.out.println("File is empty");
            return;
        }

        // Upload directory on disk (not in resources)
        String uploadDirPath = "/app/assets/img/";
        System.out.println("uploadDirPath: " + uploadDirPath);
        File uploadDir = new File(uploadDirPath);
        if (!uploadDir.exists()) {
            System.out.println("uploadDir does not exist");
            uploadDir.mkdirs();
            System.out.println("uploadDir has been created");
        }
        System.out.println("uploadDir: " + uploadDir.getAbsolutePath());

        // Determine file extension
        String originalFilename = file.getFileName();
        String extension = "";
        int dotIndex = originalFilename.lastIndexOf('.');
        if (dotIndex > 0) {
            extension = originalFilename.substring(dotIndex);
        }

        // Find the next available product#.ext
        int nextIndex = getNextImageIndex(uploadDir);
        String newFileName = "product" + nextIndex + extension;

        // Save file
        File savedFile = new File(uploadDir, newFileName);
        try (OutputStream out = new FileOutputStream(savedFile)) {
            out.write(file.getContent());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Image saved as: " + savedFile.getAbsolutePath());

        String relativePath = "/assets/img/" + newFileName;

        List<CustomField> customFieldItemList = productPageBean.getCustomFieldItemList();
        for (CustomField field : customFieldItemList){
            System.out.println("customfield: " + field.getName());
            if(field.getName().equalsIgnoreCase("Image")){
                System.out.println("Image found");
                field.setValue(relativePath);
            }
        }
        productPageBean.setCustomFieldItemList(customFieldItemList);
    }

    private int getNextImageIndex(File uploadDir) {
        int maxIndex = 0;

        File[] files = uploadDir.listFiles((dir, name) ->
                name.matches("product\\d+\\.(png|jpg|jpeg|gif|webp)")
        );

        if (files != null) {
            for (File file : files) {
                String name = file.getName();
                int start = "product".length();
                int end = name.lastIndexOf('.');
                if (end > start) {
                    try {
                        int num = Integer.parseInt(name.substring(start, end));
                        maxIndex = Math.max(maxIndex, num);
                    } catch (NumberFormatException ignored) {}
                }
            }
        }

        return maxIndex + 1;
    }
}
