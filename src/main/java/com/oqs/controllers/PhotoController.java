package com.oqs.controllers;

import com.oqs.crud.BusinessDAO;
import com.oqs.crud.PhotoDAO;
import com.oqs.file.FileUpload;
import com.oqs.model.Business;
import com.oqs.model.Photo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class PhotoController {

    @Autowired
    private BusinessDAO businessDAO;
    @Autowired
    private FileUpload fileUpload;
    @Autowired
    private PhotoDAO photoDAO;

    @RequestMapping(value = "/organization/{organizationId}/change-photo", method = RequestMethod.POST)
    public String changeOrganizationPhoto(@PathVariable("organizationId") long organizationId, @RequestParam("file") MultipartFile file) {
        fileUpload.fileUpload(file, organizationId);
        Business business = businessDAO.get(organizationId);
        Photo photo = new Photo();
        photo.setPhoto("/resources/photos/organizations/bsn-" + organizationId + ".jpg");
        long photoId = photoDAO.saveOrUpdate(photo);
        business.setPhoto(photoDAO.get(photoId));
        businessDAO.saveOrUpdate(business);

        return "redirect:/organization/{organizationId}";
    }
}
