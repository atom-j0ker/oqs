package com.oqs.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "business")
public class Business {
    @Id
    @GeneratedValue
    @Column(name = "business_id")
    private long id;

    @Column(name = "business_name")
    private String name;

    @Column(name = "business_address")
    private String address;

    @Column(name = "business_description")
    private String description;

    @Column(name = "business_phone")
    private String phone;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "business_photo")
    private Photo photo;

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL)
    private Collection<Master> masters = new ArrayList<Master>();

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL)
    private Collection<Rating> ratings = new ArrayList<Rating>();

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL)
    private Collection<Service> services = new ArrayList<Service>();

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL)
    private Collection<User> users = new ArrayList<User>();

    public Business() {
    }

    public Business(String name, String address, String description, String phone, Photo photo, Collection<Master> masters, Collection<Rating> ratings, Collection<Service> services, Collection<User> users) {
        this.name = name;
        this.address = address;
        this.description = description;
        this.phone = phone;
        this.photo = photo;
        this.masters = masters;
        this.ratings = ratings;
        this.services = services;
        this.users = users;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public Collection<Master> getMasters() {
        return masters;
    }

    public void setMasters(Collection<Master> masters) {
        this.masters = masters;
    }

    public Collection<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(Collection<Rating> ratings) {
        this.ratings = ratings;
    }

    public Collection<Service> getServices() {
        return services;
    }

    public void setServices(Collection<Service> services) {
        this.services = services;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Business{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                ", phone='" + phone + '\'' +
                ", photo=" + photo +
                ", masters=" + masters +
                ", ratings=" + ratings +
                ", services=" + services +
                ", users=" + users +
                '}';
    }
}
