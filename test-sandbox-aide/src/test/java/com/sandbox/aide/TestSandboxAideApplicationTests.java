package com.sandbox.aide;


import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TestSandboxAideApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void test1(){
		System.out.println(System.getProperties().getProperty("user.home"));
//		FileHnadle.copyFile("sandbox-structure.json","c:")
	}

}
