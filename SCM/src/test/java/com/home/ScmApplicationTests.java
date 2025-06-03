package com.home;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.home.services.EmailService;

@SpringBootTest
class ScmApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private EmailService service;

	@Test
	void sendEmailTest(){
		service.sendEmail("bhupeshdhakate01@gmail.com", "Just Testing email Service", "this is scm project working on email service");
	}
}
