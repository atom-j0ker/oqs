package com.oqs.controllers;

import com.oqs.crud.*;
import com.oqs.model.Schedule;
import com.oqs.pair.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BookingController {

    private final MasterDAO masterDAO;
    private final ServiceDAO serviceDAO;
    private final ScheduleDAO scheduleDAO;
    private final StatusDAO statusDAO;
    private final UserDAO userDAO;

    @Inject
    public BookingController(MasterDAO masterDAO, ServiceDAO serviceDAO, ScheduleDAO scheduleDAO, StatusDAO statusDAO, UserDAO userDAO) {
        this.masterDAO = masterDAO;
        this.serviceDAO = serviceDAO;
        this.scheduleDAO = scheduleDAO;
        this.statusDAO = statusDAO;
        this.userDAO = userDAO;
    }

    @RequestMapping(value = "/organization/{organizationId}/service/{serviceId}", method = RequestMethod.GET)
    public ModelAndView bookingPage(@PathVariable("organizationId") long organizationId,
                                    @PathVariable("serviceId") long serviceId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("booking");
        modelAndView.addObject("service", serviceDAO.get(serviceId));
        modelAndView.addObject("masters", masterDAO.getMasterListByServiceAndOrganization(serviceId, organizationId));
        return modelAndView;
    }

    @RequestMapping(value = "/booking-add/{organizationId}/{username}/{serviceId}", method = RequestMethod.POST)
    public String bookingAdd(@PathVariable("organizationId") long organizationId,
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

        schedule.setUser(userDAO.get(username));
        schedule.setService(serviceDAO.get(serviceId));
        schedule.setMaster(masterDAO.get(Long.valueOf(request.getParameter("mastersListName"))));
        schedule.setDate(sqlDate);
        schedule.setTime(Time.valueOf(request.getParameter("timeListName")));
        schedule.setComment(request.getParameter("bookingComment"));
        schedule.setStatus(statusDAO.get(1));
        scheduleDAO.saveOrUpdate(schedule);

        return "redirect:/user/" + userDAO.getId(username);
    }

    @RequestMapping(value = "/delete-booking/{scheduleId}", method = RequestMethod.GET)
    public @ResponseBody
    void bookingDelete(@PathVariable("scheduleId") long scheduleId) {
        scheduleDAO.delete(scheduleId);
    }


    @RequestMapping(value = "/fillTimeList", method = RequestMethod.GET)
    public @ResponseBody
    Pair<List<Time>, List<Time>> scheduleByMaster(@RequestParam("masterId") long masterId,
                                                  @RequestParam("date") String dateString) throws ParseException {
        final String OLD_FORMAT = "dd-MM-yyyy";
        final String NEW_FORMAT = "yyyy-MM-dd";

        SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
        java.util.Date date = sdf.parse(dateString);
        sdf.applyPattern(NEW_FORMAT);
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        List<Time> timeListFree = new ArrayList<>();
        for (int i = 8; i < 20; i++) {
            timeListFree.add(java.sql.Time.valueOf(i + ":00:00"));
            timeListFree.add(java.sql.Time.valueOf(i + ":30:00"));
        }
        List<Time> timeListBusy = scheduleDAO.getTimeListBusy(masterId, sqlDate);
        return new Pair<>(timeListFree, timeListBusy);
    }
}
