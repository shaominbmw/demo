package com.demo;

import com.demo.service.StaffService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ServerConsumerApplicationTests {


	@Autowired
	StaffService staffService;
	@Test
	void contextLoads() {
staffService.add(2);
	}

}
