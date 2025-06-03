package com.home.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.home.entities.Contact;
import com.home.entities.User;

public interface ContactService {
    //save contacts
    Contact save(Contact contact);

    //update contact
    Contact update(Contact contact);

    //get contact
    List<Contact> getAll();

    //get contact by id
    Contact getById(String id);

    //delete contact
    void delete(String id);

    //search contact
    Page<Contact> searchByName(String nameKeyword, int size, int page, String sortBy, String order, User user);
    Page<Contact> searchByEmail(String emailKeyword, int size, int page, String sortBy, String order, User user);
    Page<Contact> searchByPhoneNumber(String phoneNumberKeyword, int size, int page, String sortBy, String order, User user);
 
    //get contact by userId
    List<Contact> getByUserId(String userId);

    //get contact by user
    Page<Contact> getByUser(User user, int page, int size , String sortField, String sortDirection);

    long countByUser(User user);

    long countByUserAndFavoriteTrue(User user);

    List<Contact> findTop5ByUserOrderByIdDesc(User user); // for recently added
}
