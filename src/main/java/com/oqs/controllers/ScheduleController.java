package com.oqs.controllers;

import com.oqs.crud.ScheduleDAO;
import com.oqs.crud.StatusDAO;
import com.oqs.model.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ScheduleController {

    @Autowired
    private ScheduleDAO scheduleDAO;
    @Autowired
    private StatusDAO statusDAO;

    @RequestMapping(value = "/organization/{organizationId}/mastersSchedule", method = RequestMethod.GET)
    public String mastersSchedulePage(@PathVariable("organizationId") long organizationId) {
        ModelAndView modelAndView = new ModelAndView();

        return "mastersSchedule";
    }

    @RequestMapping(value = "/organization/{organizationId}/schedule", method = RequestMethod.GET)
    public ModelAndView schedulePage(@PathVariable("organizationId") long organizationId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("schedule");
        modelAndView.addObject("schedule", scheduleDAO.getScheduleListByBusiness(organizationId));
        return modelAndView;
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
