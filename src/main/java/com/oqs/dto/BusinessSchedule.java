package com.oqs.dto;

import com.oqs.model.Schedule;

import java.sql.Date;
import java.sql.Time;

public class BusinessSchedule {

    private long id;
    private String clientFirstname;
    private String clientLastname;
    private String email;
    private String service;
    private String comment;
    private String masterFirstname;
    private String masterLastname;
    private Date date;
    private Time time;
    private long statusId;

    public BusinessSchedule(Schedule schedule) {
        this.id = schedule.getId();
        this.clientFirstname = schedule.getUser().getFirstname();
        this.clientLastname = schedule.getUser().getLastname();
        this.email = schedule.getUser().getEmail();
        this.service = schedule.getService().getName();
        this.comment = schedule.getComment();
        this.masterFirstname = schedule.getMaster().getUser().getFirstname();
        this.masterLastname = schedule.getMaster().getUser().getLastname();
        this.date = schedule.getDate();
        this.time = schedule.getTime();
        this.statusId = schedule.getStatus().getId();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getClientFirstname() {
        return clientFirstname;
    }

    public void setClientFirstname(String clientFirstname) {
        this.clientFirstname = clientFirstname;
    }

    public String getClientLastname() {
        return clientLastname;
    }

    public void setClientLastname(String clientLastname) {
        this.clientLastname = clientLastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getMasterFirstname() {
        return masterFirstname;
    }

    public void setMasterFirstname(String masterFirstname) {
        this.masterFirstname = masterFirstname;
    }

    public String getMasterLastname() {
        return masterLastname;
    }

    public void setMasterLastname(String masterLastname) {
        this.masterLastname = masterLastname;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public long getStatusId() {
        return statusId;
    }

    public void setStatusId(long statusId) {
        this.statusId = statusId;
    }
}

