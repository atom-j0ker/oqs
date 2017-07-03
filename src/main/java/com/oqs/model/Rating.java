package com.oqs.model;

import javax.persistence.*;

@Entity
@Table(name = "rating")
public class Rating {
    @Id
    @GeneratedValue
    @Column(name = "rating_id")
    private long id;

    @Column(name = "rating_rating")
    private short rating;

    @ManyToOne(optional = false)
    @JoinColumn(name = "rating_business")
    private Business business;

    @ManyToOne(optional = false)
    @JoinColumn(name = "rating_user")
    private User user;

    public Rating() {
    }

    public Rating(short rating, Business business, User user) {
        this.rating = rating;
        this.business = business;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public short getRating() {
        return rating;
    }

    public void setRating(short rating) {
        this.rating = rating;
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
        return "Rating{" +
                "id=" + id +
                ", rating=" + rating +
                ", business=" + business +
                ", user=" + user +
                '}';
    }
}
