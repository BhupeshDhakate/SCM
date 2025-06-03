package com.home.forms;

import org.springframework.web.multipart.MultipartFile;

import com.home.Validaters.ValidFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ContactForm {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid Email Address [contactmanager@manage.com]")
    private String email;

    @NotBlank(message = "Phone Number is required")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid Indian mobile number")
    private String phoneNumber;

    @NotBlank(message = "Adress not blank")
    private String address;
    private String description;
    private boolean favorite = false;
    private String websideLink;
    private String linkdInLink;

    //we create annotations to validate file
    //size
    //resolution
    @ValidFile(message = "invalid file")
    private MultipartFile contactImage;
    
    private String picture;

}
