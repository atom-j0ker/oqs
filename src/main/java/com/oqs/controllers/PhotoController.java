package com.oqs.controllers;

import com.oqs.crud.BusinessDAO;
import com.oqs.crud.PhotoDAO;
import com.oqs.crud.UserDAO;
import com.oqs.file.FileUpload;
import com.oqs.model.Business;
import com.oqs.model.Photo;
import com.oqs.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;

@Controller
public class PhotoController {

    private final BusinessDAO businessDAO;
    private final FileUpload fileUpload;
    private final PhotoDAO photoDAO;
    private final UserDAO userDAO;
    @Value("${directory}")
    private String directory;
    @Value("${business}")
    private String businessFolder;
    @Value("${userFolder}")
    private String userFolder;
    @Value("${format.jpg}")
    private String formatJPG;

    @Inject
    public PhotoController(BusinessDAO businessDAO, FileUpload fileUpload, PhotoDAO photoDAO, UserDAO userDAO) {
        this.businessDAO = businessDAO;
        this.fileUpload = fileUpload;
        this.photoDAO = photoDAO;
        this.userDAO = userDAO;
    }

    @RequestMapping(value = "/organization/{organizationId}/change-photo", method = RequestMethod.POST)
    public String changeOrganizationPhoto(@PathVariable("organizationId") long organizationId, @RequestParam("file") MultipartFile file) {
        String fileName = businessFolder + organizationId + formatJPG;
        fileUpload.fileUpload(file, fileName);
        Business business = businessDAO.get(organizationId);
        Photo photo = new Photo();
        photo.setPhoto(directory + fileName);
        long photoId = photoDAO.saveOrUpdate(photo);
        business.setPhoto(photoDAO.get(photoId));
        businessDAO.saveOrUpdate(business);
        return "redirect:/organization/{organizationId}";
    }

    @RequestMapping(value = "/user/{userId}/change-photo", method = RequestMethod.POST)
    public String changeUserPhoto(@PathVariable("userId") long userId, @RequestParam("file") MultipartFile file) {
        String fileName = userFolder + userId + formatJPG;
        fileUpload.fileUpload(file, fileName);
        User user = userDAO.get(userId);
        Photo photo = new Photo();
        photo.setPhoto(fileName);
        long photoId = photoDAO.saveOrUpdate(photo);
        user.setPhoto(photoDAO.get(photoId));
        userDAO.saveOrUpdate(user);
        return "redirect:/user/{userId}";
    }
}
