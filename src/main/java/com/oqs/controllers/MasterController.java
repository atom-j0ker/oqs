package com.oqs.controllers;

import com.oqs.dao.BusinessDao;
import com.oqs.dao.MasterDao;
import com.oqs.dao.ServiceDao;
import com.oqs.dto.MasterInfo;
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

    private final BusinessDao businessDao;
    private final MasterDao masterDao;
    private final ServiceDao serviceDao;
    @Value("${directory}")
    private String directory;

    @Inject
    public MasterController(BusinessDao businessDao, MasterDao masterDao, ServiceDao serviceDao) {
        this.businessDao = businessDao;
        this.masterDao = masterDao;
        this.serviceDao = serviceDao;
    }

    @RequestMapping(value = "/organization/{organizationId}/mastersSettings", method = RequestMethod.GET)
    public ModelAndView mastersSettings(@PathVariable("organizationId") long organizationId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("mastersSettings");
        modelAndView.addObject("organizationId", organizationId);
        modelAndView.addObject("masters", masterDao.getMasterListByOrganization(organizationId));
        modelAndView.addObject("mastersFree", masterDao.getFreeMasters());
        return modelAndView;
    }

    @RequestMapping(value = "/addFreeMaster/{organizationId}/{masterFreeId}", method = RequestMethod.GET)
    public @ResponseBody
    void addFreeMaster(@PathVariable("organizationId") long organizationId,
                       @PathVariable("masterFreeId") long masterId) {
        Master master = masterDao.get(masterId);
        master.setBusiness(businessDao.get(organizationId));
        masterDao.saveOrUpdate(master);
    }

    @RequestMapping(value = "/mastersDataChange/{masterId}", method = RequestMethod.GET)
    public @ResponseBody
    void mastersDataChange(@PathVariable("masterId") long masterId,
                           @RequestParam("starttime") String starttime,
                           @RequestParam("experience") String experience,
                           @RequestParam("description") String description) {
        Master master = masterDao.get(masterId);
        master.setStarttime(Short.valueOf(starttime));
        master.setExperience(Short.valueOf(experience));
        master.setDescription(description);
        masterDao.saveOrUpdate(master);
    }

    @RequestMapping(value = "/changeMasterService/{masterId}", method = RequestMethod.GET)
    @ResponseBody
    public void changeMasterService(@PathVariable("masterId") long masterId,
                                    @RequestParam("selected") long[] selected,
                                    @RequestParam("nonSelected") long[] nonSelected) {
        Master master = masterDao.get(masterId);
        Set<Service> services = new HashSet<>();
        for (long serviceId : selected)
            services.add(serviceDao.get(serviceId));
        master.setServices(services);
        masterDao.saveOrUpdate(master);
    }

    @RequestMapping(value = "/masterPopup", method = RequestMethod.GET)
    public @ResponseBody
    MasterInfo scheduleByMaster(@RequestParam("masterId") long masterId) {
        Master master = masterDao.get(masterId);
        String photo = null;
        MasterInfo masterInfo = new MasterInfo(master);
        masterInfo.setPhoto(directory + masterInfo.getPhoto());
        return masterInfo;
    }
}
