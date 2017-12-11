package com.oqs.model;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "service")
    private Service service;

    @ManyToOne(optional = false)
    @JoinColumn(name = "master")
    private Master master;

    @Column(name = "date")
    private Date date;

    @Column(name = "start_time")
    private Time startTime;

    @Column(name = "end_time")
    private Time endTime;

    @Column(name = "comment")
    private String comment;

    @Column(name = "status")
    private String status;

    public Schedule() {
    }

    public Schedule(User user,
                    Service service,
                    Master master,
                    Date date,
                    Time startTime,
                    Time endTime,
                    String comment,
                    String status) {
        this.user = user;
        this.service = service;
        this.master = master;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.comment = comment;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Master getMaster() {
        return master;
    }

    public void setMaster(Master master) {
        this.master = master;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Schedule setStartTime(Time startTime) {
        this.startTime = startTime;
        return this;
    }

    public Time getEndTime() {
        return endTime;
    }

    public Schedule setEndTime(Time endTime) {
        this.endTime = endTime;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public Schedule setStatus(String status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", user=" + user +
                ", service=" + service +
                ", master=" + master +
                ", date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", comment='" + comment + '\'' +
                ", status=" + status +
                '}';
    }
}
