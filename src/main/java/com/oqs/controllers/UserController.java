package com.oqs.controllers;

import com.oqs.crud.PhotoDAO;
import com.oqs.crud.RoleDAO;
import com.oqs.crud.ScheduleDAO;
import com.oqs.crud.UserDAO;
import com.oqs.model.Master;
import com.oqs.model.Photo;
import com.oqs.model.Role;
import com.oqs.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

@Controller
public class UserController {

    @Autowired
    PhotoDAO photoDAO;
    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    private ScheduleDAO scheduleDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Value("${directory}")
    private String directory;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String startPage() {
        return "index";
    }

    @RequestMapping(value = "/authorization", method = RequestMethod.GET)
    public String authorizationPage() {
        return "authorization";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registrationPage() {
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String createNewUser(User user, BindingResult result, HttpServletRequest request) {
        String ROLE_MASTER = "ROLE_MASTER";
        Role role = roleDAO.get(roleDAO.getId(request.getParameter("role")));

        user.setPassword(encoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<Role>();
        roles.add(role);
        user.setRoles(roles);
        user.setPhoto(photoDAO.get(9)); //no photo

        if(role.getRole().equals(ROLE_MASTER)) {
            Master master = new Master();
            user.setMaster(master);
        }
        userDAO.saveOrUpdate(user);

        return "redirect:/";
    }

    @RequestMapping(value = "/my-profile", method = RequestMethod.POST)
    public String myProfilePage(HttpServletRequest request) {
        long userId = userDAO.getId(request.getParameter("username"));
        return "redirect:/user/" + userId;
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public ModelAndView myProfilePage(@PathVariable("userId") long userId) {
        User user = userDAO.get(userId);
        String photo = null;
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("my-profile");
        modelAndView.addObject("user", user);
        modelAndView.addObject("schedule", scheduleDAO.getScheduleListByUser(userId));
            photo = directory + user.getPhoto().getPhoto();
        modelAndView.addObject("photo", photo);
        return modelAndView;
    }
}
