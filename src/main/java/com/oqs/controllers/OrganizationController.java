package com.oqs.controllers;

import com.oqs.dto.ServiceTable;
import com.oqs.util.Pair;
import com.oqs.dao.*;
import com.oqs.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OrganizationController {

    private final BusinessDao businessDao;
    private final CategoryDao categoryDao;
    private final RatingDao ratingDao;
    private final ServiceDao serviceDao;
    private final UserDao userDao;
    @Value("${directory}")
    private String directory;

    @Inject
    public OrganizationController(BusinessDao businessDao, CategoryDao categoryDao, RatingDao ratingDao, ServiceDao serviceDao, UserDao userDao) {
        this.businessDao = businessDao;
        this.categoryDao = categoryDao;
        this.ratingDao = ratingDao;
        this.serviceDao = serviceDao;
        this.userDao = userDao;
    }

    @RequestMapping(value = "/user/{userId}/createBusiness", method = RequestMethod.GET)
    public ModelAndView createBusinessPage(@PathVariable("userId") long userId) {
        return new ModelAndView("createBusiness", "user", userDao.get(userId));
    }

    @RequestMapping(value = "/user/{userId}/createBusiness", method = RequestMethod.POST)
    public String createBusiness(Business business, @PathVariable("userId") long userId, BindingResult result) {
        User user = userDao.get(userId);
        user.setBusiness(business);
        userDao.saveOrUpdate(user);
        return "redirect:/";
    }

    @RequestMapping(value = "/organizations", method = RequestMethod.GET)
    public ModelAndView organizationsPage(@ModelAttribute("categoryId") String categoryId,
                                          @ModelAttribute("categoryName") String categoryName) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("organizations");
        modelAndView.addObject("organizations", businessDao.getBsnList());
        modelAndView.addObject("categories", categoryDao.getCategories());
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
        return businessDao.getBsnListByCategory(Long.valueOf(categoryId),
                Integer.valueOf(page), Integer.valueOf(rowsOnPage));
    }

    @RequestMapping(value = "/fillServiceTable", method = RequestMethod.GET)
    public @ResponseBody
    Pair<Integer, List<ServiceTable>> servicesSortBy(@RequestParam("string") String string,
                                                     @RequestParam("categoryId") String categoryId,
                                                     @RequestParam("sortBy") String sortBy,
                                                     @RequestParam("page") String page,
                                                     @RequestParam("rowsOnPage") String rowsOnPage) {
        Pair<Integer, List<Service>> services = serviceDao.getServiceListByParams(
                string, categoryId, sortBy, Integer.valueOf(page), Integer.valueOf(rowsOnPage));
        Pair<Integer, List<ServiceTable>> pair =
                new Pair<>(services.getFirst(), null);
        List<ServiceTable> serviceList = new ArrayList<>();
        for (Service s : services.getSecond())
            serviceList.add(new ServiceTable(s));
        pair.setSecond(serviceList);
        return pair;
    }

    @RequestMapping(value = "/organization/{organizationId}", method = RequestMethod.GET)
    public ModelAndView organization(@PathVariable("organizationId") long organizationId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Business business = businessDao.get(organizationId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("organization");
        modelAndView.addObject("organization", business);
        if (business.getPhoto() != null)
            modelAndView.addObject("photo", directory + business.getPhoto().getPhoto());
        modelAndView.addObject("services", serviceDao.getServiceListByOrganization(organizationId));
        modelAndView.addObject("categories", categoryDao.getCategories());
        modelAndView.addObject("rating", ratingDao.getRating(organizationId));
        if (auth.isAuthenticated()) {
            String username = auth.getName();
            if (!username.equals("anonymousUser"))
                modelAndView.addObject("user", userDao.get(username));
        }
        return modelAndView;
    }

    @RequestMapping(value = "/fillSubcategories", method = RequestMethod.GET)
    public @ResponseBody
    List<Category> fillCategories(@RequestParam("categoryId") String categoryId) {
        return categoryDao.getSubcategories(Long.valueOf(categoryId));
    }
}
