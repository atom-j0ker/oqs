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

        BusinessSchedule(Schedule schedule) {
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

    public BusinessSchedule getBusinessSchedule(Schedule schedule) {
        return new BusinessSchedule(schedule);
    }
}
