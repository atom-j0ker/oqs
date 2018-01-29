package com.oqs.controllers;

import com.oqs.dao.MasterDao;
import com.oqs.dao.ScheduleDao;
import com.oqs.dto.BusinessSchedule;
import com.oqs.model.Schedule;
import com.oqs.util.DateFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ScheduleController {

    private final MasterDao masterDao;
    private final ScheduleDao scheduleDao;

    @Inject
    public ScheduleController(MasterDao masterDao, ScheduleDao scheduleDao) {
        this.masterDao = masterDao;
        this.scheduleDao = scheduleDao;
    }


    @RequestMapping(value = "/organization/{organizationId}/schedule", method = RequestMethod.GET)
    public ModelAndView schedulePage(@PathVariable("organizationId") long organizationId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("schedule");
        modelAndView.addObject("masters", masterDao.getMasterListByOrganization(organizationId));
        modelAndView.addObject("schedule", scheduleDao.getScheduleListByBusiness(organizationId));
        return modelAndView;
    }

    @RequestMapping(value = "/fillScheduleTable", method = RequestMethod.GET)
    public @ResponseBody
    List<BusinessSchedule> scheduleByDateAndMaster(@RequestParam("date") String dateString,
                                                   @RequestParam("masterId") String masterId) throws ParseException {
        Date sqlDate = null;
        if (!dateString.isEmpty())
            sqlDate = DateFormatter.format(dateString);
        List<Schedule> scheduleList = scheduleDao.getScheduleListByDateAndMaster(sqlDate, masterId);
        List<BusinessSchedule> businessScheduleList = new ArrayList<>();
        for (Schedule s : scheduleList)
            businessScheduleList.add(new BusinessSchedule(s));
        return businessScheduleList;
    }

    @RequestMapping(value = "/changeStatus", method = RequestMethod.GET)
    public @ResponseBody
    void changeStatus(@RequestParam("bookingId") long bookingId,
                      @RequestParam("statusId") String status) {
        Schedule booking = scheduleDao.get(bookingId);
        booking.setStatus(status.toUpperCase());
        scheduleDao.saveOrUpdate(booking);
    }
}
