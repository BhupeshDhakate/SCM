package com.home.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.home.Helpers.Message;
import com.home.Helpers.MessageType;
import com.home.entities.User;
import com.home.repositories.UserRepo;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {
    
    // verify email

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/verify-email")
    public String verifyEmail(@RequestParam("token") String token, HttpSession session){

        User user = userRepo.findByEmailToken(token).orElse(null);
        if (user!=null) {
            //user is fetch
            if (user.getEmailToken().equals(token)) {
                user.setEmailVerified(true);
                user.setEnabled(true);
                userRepo.save(user);
                session.setAttribute("message", Message.builder()
                .type(MessageType.green)
                .content("Your Email is verified, now you can login.")
                .build());
        
                return "success_page";
            }
            return "error_page";
        }
        session.setAttribute("message", Message.builder()
        .type(MessageType.red)
        .content("Email not verified ! Token is not associated with user.")
        .build());
        return "error_page";
    }
}
