package com.home.controller;

import java.security.Principal;
import java.util.List;
import java.util.logging.Handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.home.Helpers.Helper;
import com.home.entities.Contact;
import com.home.entities.User;
import com.home.services.ContactService;
import com.home.services.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;



@Controller
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);
    
    @Autowired
    private UserService userService;

    @Autowired
    private ContactService contactService;


    //user dashbord page
    @RequestMapping(value =  "/dashboard", method=RequestMethod.GET)
    // @GetMapping(value = "/dashboard")
    public String userDashboard(Model model, Authentication authentication) {
    String username = Helper.getEmailOfLoggedInUser(authentication);
    User user = userService.getUserByEmail(username);

    long totalContacts = contactService.countByUser(user);
    long starredContacts = contactService.countByUserAndFavoriteTrue(user);
    List<Contact> recentContacts = contactService.findTop5ByUserOrderByIdDesc(user);

    model.addAttribute("totalContacts", totalContacts);
    model.addAttribute("starredContacts", starredContacts);
    model.addAttribute("recentContacts", recentContacts);

    return "user/dashboard";
    }
    
    //user profile page
    @RequestMapping(value =  "/profile", method=RequestMethod.GET)
    // @GetMapping(value = "/profile")
    public String userProfile(Model model, Authentication authentication) {

        String usename = Helper.getEmailOfLoggedInUser(authentication);
        logger.info("User logger in: {}",usename);

        // we can fetch the data from database

        User user = userService.getUserByEmail(usename);

        System.out.println(user.getName());
        System.out.println(user.getEmail());
        model.addAttribute("loggedInUser", user);
        return "user/profile";
    }

    //user add contact page

    //user view contact page
    //user edit contact
    //user delete contact
    //user search contact

    
}
