package com.oqs.controllers;

import com.oqs.dao.*;
import com.oqs.model.Schedule;
import com.oqs.model.VisitStatus;
import com.oqs.util.DateFormatter;
import com.oqs.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BookingController {

    private final MasterDao masterDao;
    private final ServiceDao serviceDao;
    private final ScheduleDao scheduleDao;
    private final UserDao userDao;

    @Inject
    public BookingController(MasterDao masterDao, ServiceDao serviceDao, ScheduleDao scheduleDao, UserDao userDao) {
        this.masterDao = masterDao;
        this.serviceDao = serviceDao;
        this.scheduleDao = scheduleDao;
        this.userDao = userDao;
    }

    @RequestMapping(value = "/organization/{organizationId}/service/{serviceId}", method = RequestMethod.GET)
    public ModelAndView bookingPage(@PathVariable("organizationId") long organizationId,
                                    @PathVariable("serviceId") long serviceId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("booking");
        modelAndView.addObject("service", serviceDao.get(serviceId));
        modelAndView.addObject("masters", masterDao.getMasterListByServiceAndOrganization(serviceId, organizationId));
        return modelAndView;
    }

    @RequestMapping(value = "/booking-add/{organizationId}/{username}/{serviceId}", method = RequestMethod.POST)
    public String bookingAdd(@PathVariable("organizationId") long organizationId,
                             @PathVariable("username") String username,
                             @PathVariable("serviceId") long serviceId,
                             HttpServletRequest request, Schedule schedule) throws ParseException {
        String dateString = request.getParameter("dateName");
        Date sqlDate = DateFormatter.format(dateString);

        schedule.setUser(userDao.get(username));
        schedule.setService(serviceDao.get(serviceId));
        schedule.setMaster(masterDao.get(Long.valueOf(request.getParameter("mastersListName"))));
        schedule.setDate(sqlDate);
        schedule.setStartTime(Time.valueOf(request.getParameter("timeListName")));
        schedule.setComment(request.getParameter("bookingComment"));
        schedule.setStatus(VisitStatus.WAITING.getValue());
        scheduleDao.saveOrUpdate(schedule);

        return "redirect:/user/" + userDao.getId(username);
    }

    @RequestMapping(value = "/delete-booking/{scheduleId}", method = RequestMethod.GET)
    public @ResponseBody
    void bookingDelete(@PathVariable("scheduleId") long scheduleId) {
        scheduleDao.delete(scheduleId);
    }


    @RequestMapping(value = "/fillTimeList", method = RequestMethod.GET)
    public @ResponseBody
    Pair<List<Time>, List<Time>> scheduleByMaster(@RequestParam("masterId") long masterId,
                                                  @RequestParam("date") String dateString) throws ParseException {
        Date sqlDate = DateFormatter.format(dateString);

        List<Time> timeListFree = new ArrayList<>();
        for (int i = 8; i < 20; i++) {
            timeListFree.add(java.sql.Time.valueOf(i + ":00:00"));
            timeListFree.add(java.sql.Time.valueOf(i + ":30:00"));
        }
        List<Time> timeListBusy = scheduleDao.getTimeListBusy(masterId, sqlDate);
        return new Pair<>(timeListFree, timeListBusy);
    }
}
