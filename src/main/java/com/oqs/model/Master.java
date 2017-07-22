package com.oqs.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "master")
public class Master {
    @Id
    @GeneratedValue
    @Column(name = "master_id")
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "master_business")
    private Business business;

    @Column(name = "master_starttime")
    private short starttime;

    @Column(name = "master_description")
    private String description;

    @Column(name = "master_experience")
    private short experience;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "service_master",
            joinColumns = @JoinColumn(name = "sm_master"),
            inverseJoinColumns = @JoinColumn(name = "sm_service"))
    private Set<Service> services = new HashSet<Service>();

    @OneToOne(mappedBy = "master")
    private User user;

    public Master() {
    }

    public Master(Business business, short starttime, String description, short experience, Set<Service> services, User user) {
        this.business = business;
        this.starttime = starttime;
        this.description = description;
        this.experience = experience;
        this.services = services;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
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

    public Set<Service> getServices() {
        return services;
    }

    public void setServices(Set<Service> services) {
        this.services = services;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Master{" +
                "id=" + id +
                ", business=" + business +
                ", starttime=" + starttime +
                ", description='" + description + '\'' +
                ", experience=" + experience +
                ", services=" + services +
                ", user=" + user +
                '}';
    }

    public class MasterInfo {
        private String business;
        private short starttime;
        private String description;
        private short experience;
        private String firstname;
        private String lastname;
        private String phone;
        private String photo;

        MasterInfo(Master master) {
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

    public MasterInfo getMasterInfo(Master master) {
        return new MasterInfo(master);
    }
}
