package com.oqs.config;

import com.oqs.crud.*;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
//import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
//import org.springframework.jdbc.datasource.init.DataSourceInitializer;
//import org.springframework.jdbc.datasource.init.DatabasePopulator;
//import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.quartz.*;
//import org.springframework.ui.velocity.VelocityEngineFactoryBean;
//import org.springframework.web.client.RestTemplate;
//import ru.javastudy.mvcHtml5Angular.mvc.quartz.CronQuartzTask;
//import ru.javastudy.mvcHtml5Angular.mvc.quartz.QuartzTask;

//import java.util.Properties;


@Configuration
//@PropertySource(value = "classpath:util.properties") //<context:property-placeholder location=".." />
@ComponentScan(basePackages = "com.oqs.controllers")
@EnableTransactionManagement
public class ApplicationConfig {

    @Bean(name = "dataSource")
    public DriverManagerDataSource getDriverManagerDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/oqs2");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return dataSource;
    }

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean getLocalContainerEntityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(getDriverManagerDataSource());
        return em;
    }

    @Bean(name = "jpaTransactionManager")
    public JpaTransactionManager getJpaTransactionManager() {
        JpaTransactionManager jpa = new JpaTransactionManager();
        jpa.setEntityManagerFactory(getLocalContainerEntityManagerFactoryBean().getObject());
        return jpa;
    }

    @Bean(name = "businessDAO")
    public BusinessDAO getBusinessDAO() {
        BusinessDAO businessDAO = new BusinessDAO();
        return businessDAO;
    }

    @Bean(name = "categoryDAO")
    public CategoryDAO getCategoryDAO() {
        CategoryDAO categoryDAO = new CategoryDAO();
        return categoryDAO;
    }

    @Bean(name = "masterDAO")
    public MasterDAO getMasterDAO() {
        MasterDAO masterDAO = new MasterDAO();
        return masterDAO;
    }

    @Bean(name = "roleDAO")
    public RoleDAO getRoleDAO() {
        RoleDAO roleDAO = new RoleDAO();
        return roleDAO;
    }

    @Bean(name = "ratingDAO")
    public RatingDAO getRatingDAO() {
        RatingDAO ratingDAO = new RatingDAO();
        return ratingDAO;
    }

    @Bean(name = "serviceDAO")
    public ServiceDAO getServiceDAO() {
        ServiceDAO serviceDAO = new ServiceDAO();
        return serviceDAO;
    }

    @Bean(name = "scheduleDAO")
    public ScheduleDAO getScheduleDAO() {
        ScheduleDAO scheduleDAO = new ScheduleDAO();
        return scheduleDAO;
    }

    @Bean(name = "statusDAO")
    public StatusDAO getStatusDAO() {
        StatusDAO statusDAO = new StatusDAO();
        return statusDAO;
    }

    @Bean(name = "userDAO")
    public UserDAO getUserDAO() {
        UserDAO userDAO = new UserDAO();
        return userDAO;
    }

}