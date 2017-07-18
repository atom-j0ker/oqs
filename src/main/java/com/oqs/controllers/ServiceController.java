package com.oqs.controllers;

import com.oqs.crud.*;
import com.oqs.model.Price;
import com.oqs.model.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ServiceController {

    @Autowired
    private BusinessDAO businessDAO;
    @Autowired
    private CategoryDAO categoryDAO;
    @Autowired
    private ServiceDAO serviceDAO;

    @RequestMapping(value = "/organization/{organizationId}/serviceAdd", method = RequestMethod.GET)
    public ModelAndView createBusinessPage(@PathVariable("organizationId") long organizationId) {
        return new ModelAndView("serviceAdd", "categories", categoryDAO.getCategories());
    }

    @RequestMapping(value = "/organization/{organizationId}/serviceAdd", method = RequestMethod.POST)
    public String createBusiness(Service service, BindingResult result,
                                 @PathVariable("organizationId") long organizationId,
                                 HttpServletRequest request) {
        Price price = new Price();
        service.setBusiness(businessDAO.get(organizationId));
        service.setCategory(categoryDAO.get(Long.valueOf(request.getParameter("subcategory"))));
        price.setPrice(Integer.valueOf(request.getParameter(("price"))));
        service.setPrice(price);
        service = serviceDAO.get(serviceDAO.saveOrUpdate(service));
        return "redirect:/";
    }
}
