package com.example.demo;

import com.example.demo.config.MapperUtil;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
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


