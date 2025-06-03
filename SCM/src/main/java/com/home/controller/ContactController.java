package com.home.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.home.Helpers.AppConstants;
import com.home.Helpers.Helper;
import com.home.Helpers.Message;
import com.home.Helpers.MessageType;
import com.home.entities.Contact;
import com.home.entities.User;
import com.home.forms.ContactForm;
import com.home.forms.ContactsSearchForm;
import com.home.services.ContactService;
import com.home.services.UserService;
import com.home.services.imageService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;




@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    Logger logger = LoggerFactory.getLogger(ContactController.class);
    @Autowired
    private ContactService contactService;

    @Autowired
    private imageService imageService;

    @Autowired
    private UserService userService;

    @RequestMapping("/add")
    //add contact page: handler
    public String addContactView(Model model){
        ContactForm contactForm = new ContactForm();
        contactForm.setFavorite(true);
        model.addAttribute("contactForm", contactForm);
        return "user/add_contact";
    }

    @RequestMapping(value = "/add", method=RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult result, Authentication authentication, HttpSession httpSession ){

        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> logger.info(error.toString()));
            httpSession.setAttribute("message", Message.builder()
            .content("Please correct the following errors")
            .type(MessageType.red)
            .build());
            return "user/add_contact";
        } 

        String username = Helper.getEmailOfLoggedInUser(authentication);
        
        User user =  userService.getUserByEmail(username);

        String filename = UUID.randomUUID().toString();
        // proccess the contact picture
        // image upload code
        String fileURL = imageService.uploadImage(contactForm.getContactImage(),filename);

        Contact contact = new Contact();
        contact.setName(contactForm.getName());
        contact.setFavorite(contactForm.isFavorite());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setUser(user);
        contact.setLinkdInLink(contactForm.getLinkdInLink());
        contact.setWebsideLink(contactForm.getWebsideLink());
        contact.setPicture(fileURL);
        contact.setCloudinaryImagePublicId(filename);
        //process the form data
        contactService.save(contact);
        System.out.println(contactForm);

        //set the contact picture url

        //set the msg for display on the view

        httpSession.setAttribute("message",Message.builder()
        .content("You Have Successfully Added a Contact")
        .type(MessageType.green)
        .build());
        //redirect to add contact page
        return "redirect:/user/contacts/add";

    }

    @RequestMapping
    public String viewControler(
        @RequestParam(value = "page",defaultValue = "0") int page, 
        @RequestParam(value = "size",defaultValue = AppConstants.PAGE_SIZE+"") int size, 
        @RequestParam(value = "sortBy",defaultValue = "name") String sortBy, 
        @RequestParam(value = "direction",defaultValue = "asc") String direction,
        Model model, Authentication authentication){
        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(username);
        Page<Contact> pageContacts =  contactService.getByUser(user,page,size,sortBy,direction);
        model.addAttribute("pageContacts", pageContacts);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);

        model.addAttribute("contactSearchForm", new ContactsSearchForm());
        return "user/contacts";
    }

    //search handler
    @RequestMapping("/search")
    public String searchHandler(
        // @RequestParam("field") String field,
        // @RequestParam("keyword") String value,
        @ModelAttribute ContactsSearchForm contactSearchForm,
        @RequestParam(value = "size",defaultValue = AppConstants.PAGE_SIZE+"") int size,
        @RequestParam(value = "page",defaultValue = "0") int page,
        @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
        @RequestParam(value = "direction", defaultValue = "asc") String direction, Model model, Authentication authentication){

        logger.info("field {} keyword {}",contactSearchForm.getField(),contactSearchForm.getValue());

        var user = userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));

        Page<Contact> pageContact = null;
        if (contactSearchForm.getField().equalsIgnoreCase("name")) {
            pageContact = contactService.searchByName(contactSearchForm.getValue(), size, page, sortBy, direction, user);
        }
        else if (contactSearchForm.getField().equalsIgnoreCase("email")){
            pageContact = contactService.searchByEmail(contactSearchForm.getValue(), size, page, sortBy, direction, user);
        }
        else if (contactSearchForm.getField().equalsIgnoreCase("phone")) {
            pageContact = contactService.searchByPhoneNumber(contactSearchForm.getValue(), size, page, sortBy, direction, user);
        }
        logger.info("pageContacts {}", pageContact);
        model.addAttribute("contactSearchForm", contactSearchForm);
        model.addAttribute("pageContact", pageContact);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        return "user/search";
    }

    // delete contact
    @RequestMapping("/delete/{contactId}")
    public String deleteContact(@PathVariable("contactId") String contactId, HttpSession session){
        contactService.delete(contactId);
        logger.info("contactId {} deleted",contactId);
        session.setAttribute("message", 
        Message.builder().content("Contact is Deleted Successfully !!").type(MessageType.green).build());
        return "redirect:/user/contacts";
    }

    //update contact form view 
    @GetMapping("/view/{contactId}")
    public String updateContactView(@PathVariable("contactId") String contactId, Model model){
        var contact = contactService.getById(contactId);
        ContactForm contactForm = new ContactForm();
        contactForm.setName(contact.getName());
        contactForm.setEmail(contact.getEmail());
        contactForm.setPhoneNumber(contact.getPhoneNumber());
        contactForm.setAddress(contact.getAddress());
        contactForm.setDescription(contact.getDescription());
        contactForm.setFavorite(contact.isFavorite());
        contactForm.setWebsideLink(contact.getWebsideLink());
        contactForm.setLinkdInLink(contact.getLinkdInLink());
        contactForm.setPicture( contact.getPicture());

        model.addAttribute("contactForm", contactForm);
        model.addAttribute("contactId", contactId);
        return "user/update_contact_view";
    }

    @RequestMapping(value="/update/{contactId}", method=RequestMethod.POST)
    public String updateContact(@PathVariable("contactId") String contactId,
    @Valid @ModelAttribute ContactForm contactForm,BindingResult bindingResult, 
    Model model){
        
        //update the contact
        if (bindingResult.hasErrors()) {
            return "user/update_contact_view";
        }
        var con = contactService.getById(contactId);
        con.setId(contactId);
        con.setName(contactForm.getName());
        con.setEmail(contactForm.getEmail());
        con.setPhoneNumber(contactForm.getPhoneNumber());
        con.setAddress(contactForm.getAddress());
        con.setDescription(contactForm.getDescription());
        con.setFavorite(contactForm.isFavorite());
        con.setWebsideLink(contactForm.getWebsideLink());
        con.setLinkdInLink(contactForm.getLinkdInLink());
        // con.setPicture( contactForm.getPicture());

        //process image:
        if (contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {
            logger.info("file is not empty");
            String fileName = UUID.randomUUID().toString();
            String imageUrl = imageService.uploadImage(contactForm.getContactImage(), fileName);
            con.setCloudinaryImagePublicId(fileName);
            con.setPicture(imageUrl);
            contactForm.setPicture(imageUrl);
        }else{
            logger.info("file is empty");
        }
        
        var updateCon = contactService.update(con);
        logger.info("update contact {}",updateCon);
        model.addAttribute("message", Message.builder().content("Contact Update").type(MessageType.green).build());

        return "redirect:/user/contacts/view/" + contactId;
    }
}

