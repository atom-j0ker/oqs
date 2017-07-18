package com.oqs.controllers;

import com.oqs.crud.MasterDAO;
import com.oqs.model.Master;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MasterController {

    @Autowired
    MasterDAO masterDAO;
    @Value("${directory}")
    private String directory;

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
