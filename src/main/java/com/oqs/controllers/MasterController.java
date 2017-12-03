package com.oqs.controllers;

import com.oqs.crud.BusinessDAO;
import com.oqs.crud.MasterDAO;
import com.oqs.crud.ServiceDAO;
import com.oqs.model.Master;
import com.oqs.model.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

@Controller
public class MasterController {

    final BusinessDAO businessDAO;
    final MasterDAO masterDAO;
    final ServiceDAO serviceDAO;
    @Value("${directory}")
    private String directory;

    @Inject
    public MasterController(BusinessDAO businessDAO, MasterDAO masterDAO, ServiceDAO serviceDAO) {
        this.businessDAO = businessDAO;
        this.masterDAO = masterDAO;
        this.serviceDAO = serviceDAO;
    }

    @RequestMapping(value = "/organization/{organizationId}/mastersSettings", method = RequestMethod.GET)
    public ModelAndView mastersSettings(@PathVariable("organizationId") long organizationId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("mastersSettings");
        modelAndView.addObject("organizationId", organizationId);
        modelAndView.addObject("masters", masterDAO.getMasterListByOrganization(organizationId));
        modelAndView.addObject("mastersFree", masterDAO.getFreeMasters());
        return modelAndView;
    }

    @RequestMapping(value = "/addFreeMaster/{organizationId}/{masterFreeId}", method = RequestMethod.GET)
    public @ResponseBody
    void addFreeMaster(@PathVariable("organizationId") long organizationId,
                       @PathVariable("masterFreeId") long masterId) {
        Master master = masterDAO.get(masterId);
        master.setBusiness(businessDAO.get(organizationId));
        masterDAO.saveOrUpdate(master);
    }

    @RequestMapping(value = "/mastersDataChange/{masterId}", method = RequestMethod.GET)
    public @ResponseBody
    void mastersDataChange(@PathVariable("masterId") long masterId,
                           @RequestParam("starttime") String starttime,
                           @RequestParam("experience") String experience,
                           @RequestParam("description") String description) {
        Master master = masterDAO.get(masterId);
        master.setStarttime(Short.valueOf(starttime));
        master.setExperience(Short.valueOf(experience));
        master.setDescription(description);
        masterDAO.saveOrUpdate(master);
    }

    @RequestMapping(value = "/changeMasterService/{masterId}", method = RequestMethod.GET)
    @ResponseBody
    public void changeMasterService(@PathVariable("masterId") long masterId,
                                    @RequestParam("selected") long[] selected,
                                    @RequestParam("nonSelected") long[] nonSelected) {
        Master master = masterDAO.get(masterId);
        Set<Service> services = new HashSet<>();
        for (long serviceId : selected)
            services.add(serviceDAO.get(serviceId));
        master.setServices(services);
        masterDAO.saveOrUpdate(master);
    }

    @RequestMapping(value = "/masterPopup", method = RequestMethod.GET)
    public @ResponseBody
    Master.MasterInfo scheduleByMaster(@RequestParam("masterId") long masterId) {
        Master master = masterDAO.get(masterId);
        String photo = null;
        Master.MasterInfo masterInfo = master.getMasterInfo(master);
        masterInfo.setPhoto(directory + masterInfo.getPhoto());
        return masterInfo;
    }
}
