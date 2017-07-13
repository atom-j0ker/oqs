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
    private int price;

    @OneToOne(mappedBy = "price")
    private Service service;

    public Price() {
    }

    public Price(int price, Service service) {
        this.price = price;
        this.service = service;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
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
                ", price=" + price +
                ", service=" + service +
                '}';
    }
}
