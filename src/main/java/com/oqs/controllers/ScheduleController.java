package com.oqs.controllers;

import com.oqs.crud.MasterDAO;
import com.oqs.crud.ScheduleDAO;
import com.oqs.crud.StatusDAO;
import com.oqs.model.Schedule;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ScheduleController {

    private final MasterDAO masterDAO;
    private final ScheduleDAO scheduleDAO;
    private final StatusDAO statusDAO;

    @Inject
    public ScheduleController(MasterDAO masterDAO, ScheduleDAO scheduleDAO, StatusDAO statusDAO) {
        this.masterDAO = masterDAO;
        this.scheduleDAO = scheduleDAO;
        this.statusDAO = statusDAO;
    }


    @RequestMapping(value = "/organization/{organizationId}/schedule", method = RequestMethod.GET)
    public ModelAndView schedulePage(@PathVariable("organizationId") long organizationId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("schedule");
        modelAndView.addObject("masters", masterDAO.getMasterListByOrganization(organizationId));
        modelAndView.addObject("schedule", scheduleDAO.getScheduleListByBusiness(organizationId));
        return modelAndView;
    }

    @RequestMapping(value = "/fillScheduleTable", method = RequestMethod.GET)
    public @ResponseBody
    List<Schedule.BusinessSchedule> scheduleByDateAndMaster(@RequestParam("date") String dateString,
                                                            @RequestParam("masterId") String masterId) throws ParseException {
        java.sql.Date sqlDate = null;
        if (!dateString.isEmpty()) {
            final String OLD_FORMAT = "dd-MM-yyyy";
            final String NEW_FORMAT = "yyyy-MM-dd";

            SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
            java.util.Date date = sdf.parse(dateString);
            sdf.applyPattern(NEW_FORMAT);
            sqlDate = new java.sql.Date(date.getTime());
        }
        List<Schedule> scheduleList = scheduleDAO.getScheduleListByDateAndMaster(sqlDate, masterId);
        List<Schedule.BusinessSchedule> businessScheduleList = new ArrayList<>();
        for (Schedule s : scheduleList)
            businessScheduleList.add(s.getBusinessSchedule(s));
        return businessScheduleList;
    }

    @RequestMapping(value = "/changeStatus", method = RequestMethod.GET)
    public @ResponseBody
    void changeStatus(@RequestParam("bookingId") long bookingId,
                      @RequestParam("statusId") long statusId) {
        Schedule booking = scheduleDAO.get(bookingId);
        booking.setStatus(statusDAO.get(statusId));
        scheduleDAO.saveOrUpdate(booking);
    }
}
