package com.oqs.controllers;

import com.oqs.crud.*;
import com.oqs.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class OrganizationController {

    @Autowired
    private BusinessDAO businessDAO;
    @Autowired
    private CategoryDAO categoryDAO;
    @Autowired
    private RatingDAO ratingDAO;
    @Autowired
    private ServiceDAO serviceDAO;
    @Autowired
    private UserDAO userDAO;

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
    public ModelAndView organizationsPage(@ModelAttribute("categoryId") String categoryId, @ModelAttribute("categoryName") String categoryName) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("organizations");
        modelAndView.addObject("organizations", businessDAO.getBsnList());
        modelAndView.addObject("categories", categoryDAO.getCategories());
        if (!categoryId.isEmpty()) {
            modelAndView.addObject("categoryId", categoryId);
            modelAndView.addObject("categoryName", categoryName);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/organizations-sort-by", method = RequestMethod.POST)
    public String organizationsSortBy(@RequestParam("categoryId") String categoryId,
                                      @RequestParam("categoryName") String categoryName,
                                      RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("categoryId", categoryId);
        redirectAttributes.addFlashAttribute("categoryName", categoryName);
        return "redirect:/organizations";
    }

    @RequestMapping(value = "/search-service", method = RequestMethod.GET)
    @ResponseBody
    public List<Service.ServiceTable> searchServices(@RequestParam("string") String string) {
        List<Service> services = serviceDAO.getServiceListByString(string);
        List<Service.ServiceTable> serviceList = new ArrayList<Service.ServiceTable>();
        for (Service s : services) {
            serviceList.add(s.getServiceTable(s));
        }
        return serviceList;
    }

    @RequestMapping(value = "/create-organization-table", method = RequestMethod.GET)
    @ResponseBody
    public List<Business> organizationsBySort(@RequestParam("categoryId") String categoryId) {
        List<Business> organizations = businessDAO.getBsnListByCategory(Long.valueOf(categoryId));
        return organizations;
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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("organization");
        modelAndView.addObject("organization", businessDAO.get(organizationId));
        modelAndView.addObject("services", serviceDAO.getServiceListByOrganization(organizationId));
        modelAndView.addObject("categories", categoryDAO.getCategories());
        modelAndView.addObject("rating", ratingDAO.getRating(organizationId));
        if (auth.isAuthenticated()) {
            String username = auth.getName();
            if (!username.equals("anonymousUser"))
                modelAndView.addObject("user", userDAO.get(username));
        }
        return modelAndView;
    }

    @RequestMapping(value = "/fill-subcategories", method = RequestMethod.GET)
    @ResponseBody
    public List<Category> fillCategories(@RequestParam("categoryId") String categoryId) {
        List<Category> categories = categoryDAO.getSubcategories(Long.valueOf(categoryId));
        return categories;
    }
}
