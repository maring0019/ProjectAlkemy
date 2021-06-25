package com.alkemy.ong;

import com.alkemy.ong.service.Impl.EmailServiceImpl;

import java.io.IOException;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OngApplicationTests {
	@Autowired
	private EmailServiceImpl emailService;

	@Test
	void contextLoads() throws IOException {
	}

}
