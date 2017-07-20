package com.oqs.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ScheduleController {

    @RequestMapping(value = "/organization/{organizationId}/mastersSchedule", method = RequestMethod.GET)
    public String mastersSchedulePage(@PathVariable("organizationId") long organizationId) {
        return "mastersSchedule";
    }

    @RequestMapping(value = "/organization/{organizationId}/schedule", method = RequestMethod.GET)
    public String schedulePage(@PathVariable("organizationId") long organizationId) {
        return "schedule";
    }
}
