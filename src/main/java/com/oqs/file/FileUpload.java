package com.oqs.file;

import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class FileUpload {
    public void fileUpload(MultipartFile file, long bsnId) {
        String fileName = null;
        String msg = "";
        File directory = new File("/Users/Sanek/Documents/IdeaProjects/OQS/src/main/webapp/resources/img/organizations/");
        if (!directory.exists())
            directory.mkdir();
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream buffStream =
                        new BufferedOutputStream(new FileOutputStream(new File(directory + "/bsn-" + bsnId + ".jpg")));
                System.out.println(directory);
                buffStream.write(bytes);
                buffStream.close();
            } catch (Exception e) {
                System.out.println("You failed to upload " + file.getOriginalFilename() + ": " + e.getMessage());
            }

        }
    }
}
