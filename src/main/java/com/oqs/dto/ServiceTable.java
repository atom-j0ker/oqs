package com.oqs.dto;

import com.oqs.model.Service;

public class ServiceTable {

    private long id;
    private String name;
    private int price;
    private short duration;
    private long organizationId;
    private String organizationName;
    private String organizationAddress;
    private String organizationTelephone;

    public ServiceTable(Service service) {
        this.id = service.getId();
        this.name = service.getName();
        this.price = service.getPrice().getPrice();
        this.duration = service.getDuration();
        this.organizationId = service.getBusiness().getId();
        this.organizationName = service.getBusiness().getName();
        this.organizationAddress = service.getBusiness().getAddress();
        this.organizationTelephone = service.getBusiness().getPhone();
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public short getDuration() {
        return duration;
    }

    public void setDuration(short duration) {
        this.duration = duration;
    }

    public long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(long organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationAddress() {
        return organizationAddress;
    }

    public void setOrganizationAddress(String organizationAddress) {
        this.organizationAddress = organizationAddress;
    }

    public String getOrganizationTelephone() {
        return organizationTelephone;
    }

    public void setOrganizationTelephone(String organizationTelephone) {
        this.organizationTelephone = organizationTelephone;
    }
}

