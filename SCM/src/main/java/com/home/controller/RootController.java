package com.home.controller;

import java.util.logging.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.home.Helpers.Helper;
import com.home.entities.User;
import com.home.services.UserService;

@ControllerAdvice
public class RootController {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
   
    @Autowired
    private UserService userService; 
   
    @ModelAttribute
    public void addLoggedInUserInformation(Model model, Authentication authentication){
        if (authentication == null) {
            return;
        }
        System.out.println("adding logdin user info in model");
        String usename = Helper.getEmailOfLoggedInUser(authentication);
        logger.info("User logger in: {}",usename);
        // we can fetch the data from database
        User user = userService.getUserByEmail(usename);
        System.out.println(user);
        System.out.println(user.getName());
        System.out.println(user.getEmail());
        model.addAttribute("loggedInUser", user);
   }
}
