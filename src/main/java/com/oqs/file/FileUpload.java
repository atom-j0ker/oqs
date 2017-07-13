package com.oqs.file;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

@Component
public class FileUpload {
    public void fileUpload(MultipartFile file, long bsnId) {
        String fileName = null;
        String msg = "";
        File directory = new File("/Users/Sanek/Documents/IdeaProjects/oqs2/src/main/webapp/resources/photos/organizations/");//TODO basedir + relative path
        if (!directory.exists())
            directory.mkdir();
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream buffStream =
                        new BufferedOutputStream(new FileOutputStream(new File(directory + "/bsn-" + bsnId + ".jpg")));//TODO file prefix to properties, resolve image format, move to constant
                buffStream.write(bytes);
                buffStream.close();//TODO use finally block
            } catch (Exception e) {
                System.out.println("You failed to upload " + file.getOriginalFilename() + ": " + e.getMessage());//TODO add logger
                //TODO rethrow own exception
            }

        }
    }

}
