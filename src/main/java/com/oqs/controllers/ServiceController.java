package com.oqs.controllers;

import com.oqs.crud.*;
import com.oqs.model.Master;
import com.oqs.model.Price;
import com.oqs.model.Schedule;
import com.oqs.model.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ServiceController {

    @Autowired
    BusinessDAO businessDAO;
    @Autowired
    CategoryDAO categoryDAO;
    @Autowired
    ServiceDAO serviceDAO;

    @RequestMapping(value = "/add-service/{organizationId}", method = RequestMethod.GET)
    @ResponseBody
    public Service serviceAdd(@PathVariable("organizationId") long organizationId,
                              @RequestParam("subcategoryId") String subcategoryId,
                              @RequestParam("serviceName") String serviceName,
                              @RequestParam("priceValue") String priceValue,
                              @RequestParam("duration") String duration) {
        Service service = new Service();
        Price price = new Price();
        service.setName(serviceName);
        service.setBusiness(businessDAO.get(organizationId));
        service.setCategory(categoryDAO.get(Long.valueOf(subcategoryId)));
        price.setPrice(priceValue);
        service.setPrice(price);
        service.setDuration(Short.valueOf(duration));

        service = serviceDAO.get(serviceDAO.saveOrUpdate(service));
        return service;
    }

}
