package com.oqs.model;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    @GeneratedValue
    @Column(name = "schedule_id")
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "schedule_user")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "schedule_service")
    private Service service;

    @ManyToOne(optional = false)
    @JoinColumn(name = "schedule_master")
    private Master master;

    @Column(name = "schedule_date")
    private Date date;

    @Column(name = "schedule_time")
    private Time time;

    @Column(name = "schedule_comment")
    private String comment;

    @ManyToOne(optional = false)
    @JoinColumn(name = "schedule_status")
    private Status status;

    public Schedule() {
    }

    public Schedule(User user, Service service, Master master, Date date, Time time, String comment, Status status) {
        this.user = user;
        this.service = service;
        this.master = master;
        this.date = date;
        this.time = time;
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

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", user=" + user +
                ", service=" + service +
                ", master=" + master +
                ", date=" + date +
                ", time=" + time +
                ", comment='" + comment + '\'' +
                ", status=" + status +
                '}';
    }
}
