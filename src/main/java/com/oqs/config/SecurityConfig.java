package com.oqs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.inject.Inject;
import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = {"com.oqs.config"})
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;

    @Inject
    public SecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .passwordEncoder(passwordEncoder())
                .dataSource(dataSource)
                .usersByUsernameQuery("SELECT USER_EMAIL, USER_PASSWORD, TRUE FROM USER WHERE USER_EMAIL = ?")
                .authoritiesByUsernameQuery("SELECT U.USER_EMAIL, R.ROLE_NAME FROM ROLE R, USER U, USER_ROLE UR " +
                        "WHERE U.USER_ID = UR.UR_USER AND UR.UR_ROLE = R.ROLE_ID AND U.USER_EMAIL = ?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .and()
                .formLogin().loginPage("/authorization").permitAll().usernameParameter("email")
                .passwordParameter("password").loginProcessingUrl("/login").defaultSuccessUrl("/")
                .and()
                .authorizeRequests()
                .antMatchers("/**/schedule", "/**/mastersSettings").hasRole("BUSINESS")
                .antMatchers("/user/**").hasAnyRole("USER", "BUSINESS", "MASTER")
                .antMatchers("/**").permitAll()
                .and()
                .logout().logoutSuccessUrl("/")
                .and()
                .csrf().disable();
    }

    @Bean(name = "encoder")//TODO use component annotation
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}