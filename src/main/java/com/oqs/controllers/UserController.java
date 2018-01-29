package com.oqs.controllers;

import com.oqs.dao.PhotoDao;
import com.oqs.dao.RoleDao;
import com.oqs.dao.ScheduleDao;
import com.oqs.dao.UserDao;
import com.oqs.email.GoogleMail;
import com.oqs.model.Master;
import com.oqs.model.Role;
import com.oqs.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

@Controller
public class UserController {

    private final PhotoDao photoDao;
    private final RoleDao roleDao;
    private final ScheduleDao scheduleDao;
    private final UserDao userDao;
    private final BCryptPasswordEncoder encoder;
    @Value("${directory}")
    private String directory;

    @Inject
    public UserController(PhotoDao photoDao, RoleDao roleDao, ScheduleDao scheduleDao, UserDao userDao, BCryptPasswordEncoder encoder) {
        this.photoDao = photoDao;
        this.roleDao = roleDao;
        this.scheduleDao = scheduleDao;
        this.userDao = userDao;
        this.encoder = encoder;
    }

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
        Role role = roleDao.get(roleDao.getId(request.getParameter("role")));

        user.setPassword(encoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<Role>();
        roles.add(role);
        user.setRoles(roles);
        user.setPhoto(photoDao.get(9)); //no photo

        if (role.getRole().equals(ROLE_MASTER)) {
            Master master = new Master();
            user.setMaster(master);
        }
        userDao.saveOrUpdate(user);

        return "redirect:/";
    }

    @RequestMapping(value = "/my-profile", method = RequestMethod.POST)
    public String myProfilePage(HttpServletRequest request) {
        long userId = userDao.getId(request.getParameter("username"));
        return "redirect:/user/" + userId;
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public ModelAndView myProfilePage(@PathVariable("userId") long userId) {
        User user = userDao.get(userId);
        String photo = null;
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("my-profile");
        modelAndView.addObject("user", user);
        modelAndView.addObject("schedule", scheduleDao.getScheduleListByUser(userId));
        photo = directory + user.getPhoto().getPhoto();
        modelAndView.addObject("photo", photo);
        return modelAndView;
    }

    @RequestMapping(value = "/send-message-to-us", method = RequestMethod.POST)
    @ResponseBody
    public void sendMessageToUs(@RequestParam("name") String name,
                                @RequestParam("email") String email,
                                @RequestParam("phone") String phone,
                                @RequestParam("message") String message) throws MessagingException {
        GoogleMail.Send("online.queue.system", "Password1234567890", "online.queue.system@gmail.com",
                "from " + email + " to online.queue.system@gmail.com",
                "name: " + name + "\nphone: " + phone + "\n" + message);
    }
}
