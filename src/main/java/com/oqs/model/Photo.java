package com.oqs.model;

import javax.persistence.*;
import java.sql.Blob;

@Entity
@Table(name = "photo")
public class Photo {
    @Id
    @GeneratedValue
    @Column(name = "photo_id")
    private long id;

    @Column(name = "photo_photo")
    private String photo;

    @OneToOne(mappedBy = "photo")
    private Business business;

    @OneToOne(mappedBy = "photo")
    private User user;

    public Photo() {
    }

    public Photo(String photo, Business business, User user) {
        this.photo = photo;
        this.business = business;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + id +
                ", photo=" + photo +
                ", business=" + business +
                ", user=" + user +
                '}';
    }
}
