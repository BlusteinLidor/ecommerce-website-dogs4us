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

/**
 * Bean class responsible for handling image upload operations in the application.
 * This class manages the file upload process and stores images in a designated directory.
 */
@Named
@ViewScoped
public class ImageUploadBean implements Serializable {

    /**
     * The uploaded file instance from the user's request.
     */
    @Setter
    @Getter
    private UploadedFile file;

    /**
     * Reference to the ProductPageBean for updating product image information.
     */
    @Inject
    private ProductPageBean productPageBean;

    /**
     * Handles the file upload event, processes the uploaded image, and saves it to the designated directory.
     * The method generates a unique filename and updates the product's image path in the ProductPageBean.
     *
     * @param event The FileUploadEvent containing the uploaded file information
     * @throws RuntimeException if there are IO errors during file saving
     */
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

    /**
     * Determines the next available index for naming product images.
     * Scans the upload directory for existing product images and returns the next available number.
     *
     * @param uploadDir The directory containing product images
     * @return The next available index for naming product images
     */
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
