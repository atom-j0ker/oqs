package com.oqs.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "service")
public class Service {
    @Id
    @GeneratedValue
    @Column(name = "service_id")
    private long id;

    @Column(name = "service_name")
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "service_business")
    private Business business;

    @ManyToOne(optional = false)
    @JoinColumn(name = "service_category")
    private Category category;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "service_price")
    private Price price;

    @Column(name = "service_duration")
    private short duration;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "service_master",
            joinColumns = @JoinColumn(name = "sm_service"),
            inverseJoinColumns = @JoinColumn(name = "sm_master"))
    private Set<Master> masters = new HashSet<Master>();

    public Service() {
    }

    public Service(String name, Business business, Category category, Price price, short duration, Set<Master> masters) {
        this.name = name;
        this.business = business;
        this.category = category;
        this.price = price;
        this.duration = duration;
        this.masters = masters;
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

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public short getDuration() {
        return duration;
    }

    public void setDuration(short duration) {
        this.duration = duration;
    }

    public Set<Master> getMasters() {
        return masters;
    }

    public void setMasters(Set<Master> masters) {
        this.masters = masters;
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", business=" + business +
                ", category=" + category +
                ", price=" + price +
                ", duration=" + duration +
                ", masters=" + masters +
                '}';
    }

    public class ServiceTable {

        private String serviceName;
        private int servicePrice;
        private long organizationId;
        private String organizationName;
        private String organizationAddress;
        private String organizationTelephone;

        public ServiceTable(Service service) {
            this.serviceName = service.getName();
            this.servicePrice = service.getPrice().getPrice();
            this.organizationId = service.getBusiness().getId();
            this.organizationName = service.getBusiness().getName();
            this.organizationAddress = service.getBusiness().getAddress();
            this.organizationTelephone = service.getBusiness().getPhone();
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public int getServicePrice() {
            return servicePrice;
        }

        public void setServicePrice(int servicePrice) {
            this.servicePrice = servicePrice;
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

    public ServiceTable getServiceTable(Service service) {
        return new ServiceTable(service);
    }
}
