package com.oqs.controllers;

import com.oqs.dao.BusinessDao;
import com.oqs.dao.PhotoDao;
import com.oqs.dao.UserDao;
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

    private final BusinessDao businessDao;
    private final FileUpload fileUpload;
    private final PhotoDao photoDao;
    private final UserDao userDao;
    @Value("${directory}")
    private String directory;
    @Value("${business}")
    private String businessFolder;
    @Value("${userFolder}")
    private String userFolder;
    @Value("${format.jpg}")
    private String formatJPG;

    @Inject
    public PhotoController(BusinessDao businessDao, FileUpload fileUpload, PhotoDao photoDao, UserDao userDao) {
        this.businessDao = businessDao;
        this.fileUpload = fileUpload;
        this.photoDao = photoDao;
        this.userDao = userDao;
    }

    @RequestMapping(value = "/organization/{organizationId}/change-photo", method = RequestMethod.POST)
    public String changeOrganizationPhoto(@PathVariable("organizationId") long organizationId, @RequestParam("file") MultipartFile file) {
        String fileName = businessFolder + organizationId + formatJPG;
        fileUpload.fileUpload(file, fileName);
        Business business = businessDao.get(organizationId);
        Photo photo = new Photo();
        photo.setPhoto(directory + fileName);
        long photoId = photoDao.saveOrUpdate(photo);
        business.setPhoto(photoDao.get(photoId));
        businessDao.saveOrUpdate(business);
        return "redirect:/organization/{organizationId}";
    }

    @RequestMapping(value = "/user/{userId}/change-photo", method = RequestMethod.POST)
    public String changeUserPhoto(@PathVariable("userId") long userId, @RequestParam("file") MultipartFile file) {
        String fileName = userFolder + userId + formatJPG;
        fileUpload.fileUpload(file, fileName);
        User user = userDao.get(userId);
        Photo photo = new Photo();
        photo.setPhoto(fileName);
        long photoId = photoDao.saveOrUpdate(photo);
        user.setPhoto(photoDao.get(photoId));
        userDao.saveOrUpdate(user);
        return "redirect:/user/{userId}";
    }
}
