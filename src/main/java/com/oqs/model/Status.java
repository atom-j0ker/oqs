package com.oqs.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "status")
public class Status {
    @Id
    @GeneratedValue
    @Column(name = "status_id")
    private long id;

    @Column(name = "status_name")
    private String name;

    @OneToMany(mappedBy = "status", cascade = CascadeType.ALL)
    private Collection<Schedule> schedules = new ArrayList<Schedule>();

    public Status() {
    }

    public Status(String name, Collection<Schedule> schedules) {
        this.name = name;
        this.schedules = schedules;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(Collection<Schedule> schedules) {
        this.schedules = schedules;
    }

    @Override
    public String toString() {
        return "Status{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", schedules=" + schedules +
                '}';
    }
}
