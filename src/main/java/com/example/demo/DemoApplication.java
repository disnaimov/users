package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;



/*//@ComponentScan(basePackages={"<com.example>"})
//@EnableJpaRepositories(basePackages="<com.example.demo.dao.UserRepository>")
@EnableTransactionManagement
@EntityScan(basePackages="<entities>")*/
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
