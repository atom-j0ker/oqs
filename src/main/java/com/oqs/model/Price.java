package com.oqs.model;

import javax.persistence.*;

@Entity
@Table(name = "price")
public class Price {
    @Id
    @GeneratedValue
    @Column(name = "price_id")
    private long id;

    @Column(name = "price_price")
    private String price;

    @OneToOne(mappedBy = "price")
    private Service service;

    public Price() {
    }

    public Price(String price, Service service) {
        this.price = price;
        this.service = service;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    @Override
    public String toString() {
        return "Price{" +
                "id=" + id +
                ", price='" + price + '\'' +
                ", service=" + service +
                '}';
    }
}
