package com.home.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.home.Helpers.Message;
import com.home.Helpers.MessageType;
import com.home.entities.User;
import com.home.forms.UserForm;
import com.home.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
public class PageController {

	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String index() {
		return "redirect:/home";
	}
	

   @RequestMapping("/home")
	public String home(Model model) {
		System.out.println("Home page handler...!!");
		
//		sending data to view
		model.addAttribute("name", "Substring technologies");
		model.addAttribute("tutorial", "Learn code with durgesh");
		model.addAttribute("GithubReposetory", "https://github.com/BhupeshDhakate");
		return "home";
	}

	// About Page Rout
	@RequestMapping("/about")
	public String aboutPage(Model model){
		model.addAttribute("isLogin", false);
		System.out.println("About Page Loading");
		return "about";
	}

	// Services Page Rout
	@RequestMapping("/services")
	public String servicesPage(){
		System.out.println("Services Page Loading");
		return "services";
	}

	// login Page Rout
	@GetMapping("/contact")
	public String contactPage(){
		System.out.println("contact Page Loading");
		return "contact";
	}

	// login Page Rout
	@GetMapping("/login")
	public String loginPage(){
		return new String("login");
	}

	// Register Page Rout
	@GetMapping("/register")
	public String registerPage(Model model){
		UserForm userForm = new UserForm();
		//we can put defout data also
		// userForm.setName("Ramesh");
		model.addAttribute("userForm", userForm);
		return "register";
	}

	//proccessing register
	@RequestMapping(value="/do-register", method=RequestMethod.POST)
	public String proccessRegister(@Valid @ModelAttribute UserForm userForm,BindingResult rBindingResult, HttpSession session){
		System.out.println("this is do-register page");
		//fetch the form data
		//uaserForm
		System.out.println(userForm);
		//validate form data
		if (rBindingResult.hasErrors()) {
			return "register";
		}
		
		// TODO::Validation userForm

		//save to database
		//userservice- contains all method which executes business logic
		//userForm --> User
		// User user = User.builder()
		// 	.name(userForm.getName())
		// 	.email(userForm.getEmail())
		// 	.password(userForm.getPassword())
		// 	.about(userForm.getAbout())
		// 	.phoneNumber(userForm.getPhoneNumber())
		// 	.profilePic("https://img.freepik.com/free-vector/blue-circle-with-white-user_78370-4707.jpg?ga=GA1.1.1319275440.1744381065&semt=ais_hybrid&w=740")
		// 	.build();
		User user = new User();
			user.setName(userForm.getName());
			user.setEmail(userForm.getEmail());
			user.setPassword(userForm.getPassword());
			user.setAbout(userForm.getAbout());
			user.setPhoneNumber(userForm.getPhoneNumber());
			user.setEnabled(false);
			user.setProfilePic("https://img.freepik.com/free-vector/blue-circle-with-white-user_78370-4707.jpg?ga=GA1.1.1319275440.1744381065&semt=ais_hybrid&w=740");

		User sevedUser = userService.saveUser(user);
		System.out.println("user saved :");
		//massage = "Registration Successull"
		//add the massege
		Message message = Message.builder().content("Registration Successful").type(MessageType.green).build();
		session.setAttribute("message", message);
		//redirect to login page
		return "redirect:/register";
	}

}
