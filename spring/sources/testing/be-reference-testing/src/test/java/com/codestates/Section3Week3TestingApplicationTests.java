package com.codestates;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationContextFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

@SpringBootTest
class Section3Week3TestingApplicationTests {
	@Autowired
	private ApplicationContext applicationContext;

	@Test
	void contextLoads() {
		if (applicationContext != null) {
			String[] beans = applicationContext.getBeanDefinitionNames();
			Arrays.stream(beans)
					.forEach(System.out::println);
		}
	}

}
