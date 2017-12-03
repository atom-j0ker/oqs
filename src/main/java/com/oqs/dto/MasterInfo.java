package com.oqs.dto;

import com.oqs.model.Master;

public class MasterInfo {

    private String business;
    private short starttime;
    private String description;
    private short experience;
    private String firstname;
    private String lastname;
    private String phone;
    private String photo;

    public MasterInfo(Master master) {
        this.business = master.getBusiness().getName();
        this.starttime = master.getStarttime();
        this.description = master.getDescription();
        this.experience = master.getExperience();
        this.firstname = master.getUser().getFirstname();
        this.lastname = master.getUser().getLastname();
        this.phone = master.getUser().getPhone();
        this.photo = master.getUser().getPhoto().getPhoto();
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public short getStarttime() {
        return starttime;
    }

    public void setStarttime(short starttime) {
        this.starttime = starttime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public short getExperience() {
        return experience;
    }

    public void setExperience(short experience) {
        this.experience = experience;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

}

