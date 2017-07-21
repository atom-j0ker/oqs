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

    @RequestMapping(value = "/masterPopup", method = RequestMethod.GET)
    public @ResponseBody
    Master.MasterInfo scheduleByMaster(@RequestParam("masterId") long masterId) {
        Master master = masterDAO.get(masterId);
        Master.MasterInfo masterInfo = master.getMasterInfo(master);
        if (masterInfo.getPhoto() != null)
            masterInfo.setPhoto(directory + masterInfo.getPhoto());
        return masterInfo;
    }
}
