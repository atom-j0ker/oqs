package com.oqs.controllers;

import com.oqs.crud.*;
import com.oqs.model.Price;
import com.oqs.model.Service;
import com.oqs.pair.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ServiceController {

    @Autowired
    private BusinessDAO businessDAO;
    @Autowired
    private CategoryDAO categoryDAO;
    @Autowired
    private ServiceDAO serviceDAO;

    @RequestMapping(value = "/organization/{organizationId}/serviceAdd", method = RequestMethod.GET)
    public ModelAndView serviceAddPage(@PathVariable("organizationId") long organizationId) {
        return new ModelAndView("serviceAdd", "categories", categoryDAO.getCategories());
    }

    @RequestMapping(value = "/organization/{organizationId}/serviceAdd", method = RequestMethod.POST)
    public String serviceAdd(Service service, BindingResult result,
                             @PathVariable("organizationId") long organizationId,
                             HttpServletRequest request) {
        Price price = new Price();
        service.setBusiness(businessDAO.get(organizationId));
        service.setCategory(categoryDAO.get(Long.valueOf(request.getParameter("subcategory"))));
        price.setPrice(Integer.valueOf(request.getParameter(("price"))));
        service.setPrice(price);
        service = serviceDAO.get(serviceDAO.saveOrUpdate(service));
        return "redirect:/organization/" + organizationId;
    }

    @RequestMapping(value = "/organization/updateService/{serviceId}", method = RequestMethod.GET)
    public @ResponseBody
    void serviceUpdate(@PathVariable("serviceId") long serviceId,
                       @RequestParam("newName") String newName,
                       @RequestParam("newPrice") String newPrice,
                       @RequestParam("newDuration") String newDuration) {
        Price price = new Price();
        Service service = serviceDAO.get(serviceId);
        service.setName(newName);
        price.setPrice(Integer.valueOf(newPrice));
        service.setPrice(price);
        service.setDuration(Short.valueOf(newDuration));
        serviceDAO.saveOrUpdate(service);
    }

    @RequestMapping(value = "/organization/cancelService/{serviceId}", method = RequestMethod.GET)
    public @ResponseBody
    Service.ServiceTable serviceCancel(@PathVariable("serviceId") long serviceId) {
        Service service = serviceDAO.get(serviceId);
        return service.getServiceTable(service);
    }

    @RequestMapping(value = "/organization/deleteService", method = RequestMethod.GET)
    public @ResponseBody
    void serviceDelete(@RequestParam("serviceId") String serviceId) {
        serviceDAO.delete(Long.valueOf(serviceId));
    }

    @RequestMapping(value = "/serviceCheckBoxes/{organizationId}/{masterId}", method = RequestMethod.GET)
    public @ResponseBody
    Pair<List<Service>, List<Service>> serviceCheckBoxes(@PathVariable("organizationId") long organizationId,
                                                         @PathVariable("masterId") long masterId) {
        List<Service> serviceListByOrganization = serviceDAO.getServiceListByOrganization(organizationId);
        List<Service> serviceListByMaster = serviceDAO.getServiceListByMaster(masterId);
        return new Pair<>(serviceListByOrganization, serviceListByMaster);
    }


}
