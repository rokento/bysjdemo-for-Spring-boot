package cn.bysj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class Applicantion extends SpringBootServletInitializer{

	public static void main(String[] args) {

		SpringApplication.run(Applicantion.class, args);

	}

	 @Override//Ϊ�˴��springboot��Ŀ
	    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
	        return builder.sources(this.getClass());
	    }

}