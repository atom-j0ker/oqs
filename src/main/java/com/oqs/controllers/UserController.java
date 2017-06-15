package com.oqs.controllers;

import com.oqs.crud.RoleDAO;
import com.oqs.crud.UserDAO;
import com.oqs.model.Role;
import com.oqs.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashSet;
import java.util.Set;

@Controller
public class UserController {

    @Autowired
    RoleDAO roleDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    Md5PasswordEncoder encoder;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String startPage() {
        return "index";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registrationPage() {
        return "registration";
    }

    @RequestMapping(value = "/authorization", method = RequestMethod.GET)
    public String authorizationPage() {
        return "authorization";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String createNewUser(User user, BindingResult result) {
        user.setPassword(encoder.encodePassword(user.getPassword(), "salt"));
        System.out.println(user.getPassword());
//        Set<Role> roles = new HashSet<Role>();
//        roles.add(roleDAO.get(1));
//        user.setRoles(roles);
        userDAO.saveOrUpdate(user);
        return "index";
    }
}
