package com.oqs.controllers;

import com.oqs.crud.MasterDAO;
import com.oqs.crud.ScheduleDAO;
import com.oqs.dto.BusinessSchedule;
import com.oqs.model.Schedule;
import com.oqs.util.DateFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ScheduleController {

    private final MasterDAO masterDAO;
    private final ScheduleDAO scheduleDAO;

    @Inject
    public ScheduleController(MasterDAO masterDAO, ScheduleDAO scheduleDAO) {
        this.masterDAO = masterDAO;
        this.scheduleDAO = scheduleDAO;
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
    List<BusinessSchedule> scheduleByDateAndMaster(@RequestParam("date") String dateString,
                                                   @RequestParam("masterId") String masterId) throws ParseException {
        Date sqlDate = null;
        if (!dateString.isEmpty())
            sqlDate = DateFormatter.format(dateString);
        List<Schedule> scheduleList = scheduleDAO.getScheduleListByDateAndMaster(sqlDate, masterId);
        List<BusinessSchedule> businessScheduleList = new ArrayList<>();
        for (Schedule s : scheduleList)
            businessScheduleList.add(new BusinessSchedule(s));
        return businessScheduleList;
    }

    @RequestMapping(value = "/changeStatus", method = RequestMethod.GET)
    public @ResponseBody
    void changeStatus(@RequestParam("bookingId") long bookingId,
                      @RequestParam("statusId") String status) {
        Schedule booking = scheduleDAO.get(bookingId);
        booking.setStatus(status.toUpperCase());
        scheduleDAO.saveOrUpdate(booking);
    }
}
