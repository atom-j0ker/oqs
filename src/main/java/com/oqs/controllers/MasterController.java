package com.oqs.controllers;

import com.oqs.crud.MasterDAO;
import com.oqs.model.Master;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MasterController {

    @Autowired
    MasterDAO masterDAO;
    @Value("${directory}")
    private String directory;

    @RequestMapping(value = "/organization/{organizationId}/mastersSettings", method = RequestMethod.GET)
    public ModelAndView mastersSettings(@PathVariable("organizationId") long organizationId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("mastersSettings");
        modelAndView.addObject("masters", masterDAO.getMasterListByOrganization(organizationId));
        return modelAndView;
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
