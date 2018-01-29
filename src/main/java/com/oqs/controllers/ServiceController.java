package com.oqs.controllers;

import com.oqs.dao.*;
import com.oqs.dto.ServiceTable;
import com.oqs.model.Price;
import com.oqs.model.Service;
import com.oqs.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ServiceController {

    private final BusinessDao businessDao;
    private final CategoryDao categoryDao;
    private final ServiceDao serviceDao;

    @Inject
    public ServiceController(BusinessDao businessDao, CategoryDao categoryDao, ServiceDao serviceDao) {
        this.businessDao = businessDao;
        this.categoryDao = categoryDao;
        this.serviceDao = serviceDao;
    }

    @RequestMapping(value = "/organization/{organizationId}/serviceAdd", method = RequestMethod.GET)
    public ModelAndView serviceAddPage(@PathVariable("organizationId") long organizationId) {
        return new ModelAndView("serviceAdd", "categories", categoryDao.getCategories());
    }

    @RequestMapping(value = "/organization/{organizationId}/serviceAdd", method = RequestMethod.POST)
    public String serviceAdd(Service service, BindingResult result,
                             @PathVariable("organizationId") long organizationId,
                             HttpServletRequest request) {
        Price price = new Price();
        service.setBusiness(businessDao.get(organizationId));
        service.setCategory(categoryDao.get(Long.valueOf(request.getParameter("subcategory"))));
        price.setPrice(Integer.valueOf(request.getParameter(("price"))));
        service.setPrice(price);
        service = serviceDao.get(serviceDao.saveOrUpdate(service));
        return "redirect:/organization/" + organizationId;
    }

    @RequestMapping(value = "/organization/updateService/{serviceId}", method = RequestMethod.GET)
    public @ResponseBody
    void serviceUpdate(@PathVariable("serviceId") long serviceId,
                       @RequestParam("newName") String newName,
                       @RequestParam("newPrice") String newPrice,
                       @RequestParam("newDuration") String newDuration) {
        Price price = new Price();
        Service service = serviceDao.get(serviceId);
        service.setName(newName);
        price.setPrice(Integer.valueOf(newPrice));
        service.setPrice(price);
        service.setDuration(Short.valueOf(newDuration));
        serviceDao.saveOrUpdate(service);
    }

    @RequestMapping(value = "/organization/cancelService/{serviceId}", method = RequestMethod.GET)
    public @ResponseBody
    ServiceTable serviceCancel(@PathVariable("serviceId") long serviceId) {
        Service service = serviceDao.get(serviceId);
        return new ServiceTable(service);
    }

    @RequestMapping(value = "/organization/deleteService", method = RequestMethod.GET)
    public @ResponseBody
    void serviceDelete(@RequestParam("serviceId") String serviceId) {
        serviceDao.delete(Long.valueOf(serviceId));
    }

    @RequestMapping(value = "/serviceCheckBoxes/{organizationId}/{masterId}", method = RequestMethod.GET)
    public @ResponseBody
    Pair<List<Service>, List<Service>> serviceCheckBoxes(@PathVariable("organizationId") long organizationId,
                                                         @PathVariable("masterId") long masterId) {
        List<Service> serviceListByOrganization = serviceDao.getServiceListByOrganization(organizationId);
        List<Service> serviceListByMaster = serviceDao.getServiceListByMaster(masterId);
        return new Pair<>(serviceListByOrganization, serviceListByMaster);
    }


}
