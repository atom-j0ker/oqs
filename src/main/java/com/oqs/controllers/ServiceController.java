package com.oqs.controllers;

import com.oqs.crud.BusinessDAO;
import com.oqs.crud.MasterDAO;
import com.oqs.crud.ServiceDAO;
import com.oqs.model.Master;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ServiceController {
    @Autowired
    BusinessDAO businessDAO;
    @Autowired
    MasterDAO masterDAO;
    @Autowired
    ServiceDAO serviceDAO;

    @RequestMapping(value = "/organization/{organizationId}/service/{serviceId}", method = RequestMethod.GET)
    public ModelAndView service(@PathVariable("organizationId") long organizationId,
                                @PathVariable("serviceId") long serviceId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("booking");
        modelAndView.addObject("organization", businessDAO.get(organizationId));
        modelAndView.addObject("service", serviceDAO.get(serviceId));
        modelAndView.addObject("masters", masterDAO.getMasterListByServiceAndOrganization(serviceId, organizationId));
        return modelAndView;
    }
}
