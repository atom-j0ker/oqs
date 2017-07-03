package com.oqs.controllers;

import com.oqs.crud.*;
import com.oqs.model.Business;
import com.oqs.model.Category;
import com.oqs.model.Service;
import com.oqs.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class OrganizationController {

    @Autowired
    BusinessDAO businessDAO;
    @Autowired
    CategoryDAO categoryDAO;
    @Autowired
    RatingDAO ratingDAO;
    @Autowired
    ServiceDAO serviceDAO;
    @Autowired
    UserDAO userDAO;

    @RequestMapping(value = "/user/{userId}/create-business", method = RequestMethod.GET)
    public ModelAndView createBusinessPage(@PathVariable("userId") long userId) {
        return new ModelAndView("create-business", "user", userDAO.get(userId));
    }

    @RequestMapping(value = "/user/{userId}/create-business", method = RequestMethod.POST)
    public String createBusiness(Business business, @PathVariable("userId") long userId, BindingResult result) {
        User user = userDAO.get(userId);
        user.setBusiness(business);
        userDAO.saveOrUpdate(user);
        return "redirect:/";
    }

    @RequestMapping(value = "/organizations", method = RequestMethod.GET)
    public ModelAndView organizationsPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("organizations");
        modelAndView.addObject("organizations", businessDAO.getBsnList());
        modelAndView.addObject("categories", categoryDAO.getCategories());
        return modelAndView;
    }

    @RequestMapping(value = "/create-organization-table", method = RequestMethod.GET)
    @ResponseBody
    public List<Business> organizationsBySort(@RequestParam("categoryId") String categoryId) {
        List<Business> organizations = businessDAO.getBsnListByCategory(Long.valueOf(categoryId));
        return organizations;
    }

    @RequestMapping(value = "/fill-service", method = RequestMethod.GET)
    @ResponseBody
    public List<Service> findServices(@RequestParam("categoryId") String categoryId) {
        List<Service> services = serviceDAO.getServiceListByCategory(Long.valueOf(categoryId));
        return services;
    }

    @RequestMapping(value = "/fill-choosed-service-table", method = RequestMethod.GET)
    @ResponseBody
    public List<Service.ServiceTable> fillChoosedServiceTable(@RequestParam("serviceId") String serviceId) {
        Service service = serviceDAO.get(Long.valueOf(serviceId));
        List<Service.ServiceTable> serviceList = new ArrayList<Service.ServiceTable>();
        serviceList.add(service.getServiceTable(service));
        return serviceList;
    }

    @RequestMapping(value = "/fill-service-table", method = RequestMethod.GET)
    @ResponseBody
    public List<Service.ServiceTable> fillServiceTable(@RequestParam("categoryId") String categoryId) {
        List<Service> services = serviceDAO.getServiceListByCategory(Long.valueOf(categoryId));
        List<Service.ServiceTable> serviceList = new ArrayList<Service.ServiceTable>();
        for (Service s : services)
            serviceList.add(s.getServiceTable(s));
        return serviceList;
    }

    @RequestMapping(value = "/organization/{organizationId}", method = RequestMethod.GET)
    public ModelAndView organization(@PathVariable("organizationId") long organizationId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("organization");
        modelAndView.addObject("organization", businessDAO.get(organizationId));
        modelAndView.addObject("services", serviceDAO.getServiceListByOrganization(organizationId));
        modelAndView.addObject("rating", ratingDAO.getRating(organizationId));
        return modelAndView;
    }
}
