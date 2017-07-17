package com.oqs.controllers;

import com.oqs.pair.Pair;
import com.oqs.crud.*;
import com.oqs.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
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
    @Value("${directory}")
    private String directory;

    @RequestMapping(value = "/user/{userId}/createBusiness", method = RequestMethod.GET)
    public ModelAndView createBusinessPage(@PathVariable("userId") long userId) {
        return new ModelAndView("createBusiness", "user", userDAO.get(userId));
    }

    @RequestMapping(value = "/user/{userId}/createBusiness", method = RequestMethod.POST)
    public String createBusiness(Business business, @PathVariable("userId") long userId, BindingResult result) {
        User user = userDAO.get(userId);
        user.setBusiness(business);
        userDAO.saveOrUpdate(user);
        return "redirect:/";
    }

    @RequestMapping(value = "/organizations", method = RequestMethod.GET)
    public ModelAndView organizationsPage(@ModelAttribute("categoryId") String categoryId,
                                          @ModelAttribute("categoryName") String categoryName) {
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

    @RequestMapping(value = "/organizationsSortByCategory", method = RequestMethod.POST)
    public String organizationsSortByCategory(@RequestParam("categoryId") String categoryId,
                                              @RequestParam("categoryName") String categoryName,
                                              RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("categoryId", categoryId);
        redirectAttributes.addFlashAttribute("categoryName", categoryName);
        return "redirect:/organizations";
    }

    @RequestMapping(value = "/fillOrganizationTable", method = RequestMethod.GET)
    public @ResponseBody
    Pair<Integer, List<Business>> organizationsByCategorySort(@RequestParam("categoryId") String categoryId,
                                               @RequestParam("page") String page,
                                               @RequestParam("rowsOnPage") String rowsOnPage) {
        return businessDAO.getBsnListByCategory(Long.valueOf(categoryId),
                Integer.valueOf(page), Integer.valueOf(rowsOnPage));
    }

    @RequestMapping(value = "/fillServiceTable", method = RequestMethod.GET)
    public @ResponseBody
    Pair<Integer, List<Service.ServiceTable>> servicesSortBy(@RequestParam("string") String string,
                                                             @RequestParam("categoryId") String categoryId,
                                                             @RequestParam("sortBy") String sortBy,
                                                             @RequestParam("page") String page,
                                                             @RequestParam("rowsOnPage") String rowsOnPage) {
        Pair<Integer, List<Service>> services = serviceDAO.getServiceListByParams(
                string, categoryId, sortBy, Integer.valueOf(page), Integer.valueOf(rowsOnPage));
        Pair<Integer, List<Service.ServiceTable>> pair =
                new Pair<Integer, List<Service.ServiceTable>>(services.getFirst(), null);
        List<Service.ServiceTable> serviceList = new ArrayList<Service.ServiceTable>();
        for (Service s : services.getSecond())
            serviceList.add(s.getServiceTable(s));
        pair.setSecond(serviceList);
        return pair;
    }

    @RequestMapping(value = "/organization/{organizationId}", method = RequestMethod.GET)
    public ModelAndView organization(@PathVariable("organizationId") long organizationId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Business business = businessDAO.get(organizationId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("organization");
        modelAndView.addObject("organization", business);
        if (business.getPhoto() != null)
            modelAndView.addObject("photo", directory + business.getPhoto().getPhoto());
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

    @RequestMapping(value = "/fillSubcategories", method = RequestMethod.GET)
    public @ResponseBody
    List<Category> fillCategories(@RequestParam("categoryId") String categoryId) {
        return categoryDAO.getSubcategories(Long.valueOf(categoryId));
    }
}
