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

    public Rating() {
    }

    public Rating(short rating, Business business) {
        this.rating = rating;
        this.business = business;
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

    @Override
    public String toString() {
        return "Rating{" +
                "id=" + id +
                ", rating=" + rating +
                ", business=" + business +
                '}';
    }
}
