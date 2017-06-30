package com.oqs.controllers;

import com.oqs.crud.*;
import com.oqs.model.Master;
import com.oqs.model.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ServiceController {
    @Autowired
    BusinessDAO businessDAO;
    @Autowired
    MasterDAO masterDAO;
    @Autowired
    ServiceDAO serviceDAO;
    @Autowired
    ScheduleDAO scheduleDAO;
    @Autowired
    StatusDAO statusDAO;
    @Autowired
    UserDAO userDAO;

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

    @RequestMapping(value = "/booking-add/{organizationId}/{username}/{serviceId}", method = RequestMethod.POST)
    public String book(@PathVariable("organizationId") long organizationId,
                     @PathVariable("username") String username,
                     @PathVariable("serviceId") long serviceId,
                     HttpServletRequest request, Schedule schedule) throws ParseException {
        final String OLD_FORMAT = "dd-MM-yyyy";
        final String NEW_FORMAT = "yyyy-MM-dd";

        String dateString = request.getParameter("dateName");

        SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
        java.util.Date date = sdf.parse(dateString);
        sdf.applyPattern(NEW_FORMAT);
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        schedule.setUser(userDAO.get(userDAO.getId(username)));
        schedule.setService(serviceDAO.get(serviceId));
        schedule.setMaster(masterDAO.get(Long.valueOf(request.getParameter("mastersListName"))));
        schedule.setDate(sqlDate);
        schedule.setTime(Time.valueOf(request.getParameter("timeListName")));
        schedule.setComment(request.getParameter("bookingComment"));
        schedule.setStatus(statusDAO.get(1));
        scheduleDAO.saveOrUpdate(schedule);

        return "redirect:/user/" + userDAO.getId(username);
    }

    @RequestMapping(value = "/fill-time-list", method = RequestMethod.GET)
    @ResponseBody
    public List<List<Time>> scheduleByMaster(@RequestParam("masterId") long masterId,
                                             @RequestParam("date") String dateString) throws ParseException {
        final String OLD_FORMAT = "dd-MM-yyyy";
        final String NEW_FORMAT = "yyyy-MM-dd";

        SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
        java.util.Date date = sdf.parse(dateString);
        sdf.applyPattern(NEW_FORMAT);
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        List<Time> timeList = new ArrayList<Time>();

        for (int i = 8; i < 20; i++) {
            timeList.add(java.sql.Time.valueOf(i + ":00:00"));
            timeList.add(java.sql.Time.valueOf(i + ":30:00"));
        }

//        List<Time> timeListByMasterAndDate = scheduleDAO.getTimeListByMasterAndDate(masterId, sqlDate);

        List<List<Time>> listOfTimeLists = new ArrayList<List<Time>>();
        listOfTimeLists.add(timeList);
//        listOfTimeLists.add(timeListByMasterAndDate);

        return listOfTimeLists;
    }
}
