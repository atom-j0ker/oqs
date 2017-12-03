package com.oqs.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.logging.Logger;

@Component
public class FileUpload {

    @Value("${directory}")
    private String directory;
    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());


    public void fileUpload(MultipartFile file, String fileName) {
        if (!file.isEmpty()) {
            try (BufferedOutputStream buffStream = new BufferedOutputStream
                    (new FileOutputStream(new File(directory + fileName)))){
                byte[] bytes = file.getBytes();
                buffStream.write(bytes);
            } catch (Exception e) {
                LOGGER.warning("You failed to upload " + file.getOriginalFilename() + ": " + e.getMessage());
            }
        }
    }

}
