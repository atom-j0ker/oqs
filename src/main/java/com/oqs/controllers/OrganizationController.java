package com.oqs.controllers;

import com.oqs.crud.BusinessDAO;
import com.oqs.crud.CategoryDAO;
import com.oqs.crud.ServiceDAO;
import com.oqs.crud.UserDAO;
import com.oqs.model.Business;
import com.oqs.model.Category;
import com.oqs.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class OrganizationController {

    @Autowired
    BusinessDAO businessDAO;
    @Autowired
    CategoryDAO categoryDAO;
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

    @RequestMapping(value = "/subcategories", method = RequestMethod.GET)
    @ResponseBody
    public List<Category> organizationsBySort(@RequestParam("categoryId") String categoryId) {
        List<Category> subcategories = categoryDAO.getSubcategories(Long.valueOf(categoryId));
        return subcategories;
    }

    @RequestMapping(value = "/organization/{organizationId}", method = RequestMethod.GET)
    public ModelAndView organization(@PathVariable("organizationId") long organizationId) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("organization");
        modelAndView.addObject("organization", businessDAO.get(organizationId));
        modelAndView.addObject("services", serviceDAO.getServiceListByOrganization(organizationId));
        return modelAndView;
    }
}
