package com.home.Helpers;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {

    public static String getEmailOfLoggedInUser(Authentication authentication){
        
        
        //if we logdin with userid and password so how we get email
        if (authentication instanceof OAuth2AuthenticationToken) {
        
           var aOAuth2AuthenticationToken = (OAuth2AuthenticationToken)authentication;
           var clientId = aOAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
           var oauth2User = (OAuth2User)authentication.getPrincipal();
           var usename = "";

           if (clientId.equalsIgnoreCase("google")) {
            //if sing with google
            System.out.println("Getting email from google");
            usename = oauth2User.getAttribute("email").toString();
           }
           else if(clientId.equalsIgnoreCase("github")){
            //if sing with github
            System.out.println("Getting email from GitHub");
            usename = oauth2User.getAttribute("email")!=null ? oauth2User.getAttribute("email").toString(): oauth2User.getAttribute("login").toString()+"@gmail.com";

           }
           return usename;
        
        //if sign with facebook
        }else{
            System.out.println("Getting data from local database");
            return authentication.getName();
        } 
    }

    public static String getLinkForEmailVerification(String emailToken) {
        
        String link = "http://localhost:8080/auth/verify-email?token="+emailToken;        
        return link;
        
    }
}
