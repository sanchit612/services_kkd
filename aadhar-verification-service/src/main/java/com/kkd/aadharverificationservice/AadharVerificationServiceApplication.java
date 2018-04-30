package com.kkd.aadharverificationservice;

import java.io.IOException;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.xmlpull.v1.XmlPullParserException;

import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients("com.kkd.aadharverificationservice")
//@EnableSwagger2
//@EnableFeignClients("com.kkd.authenticationauthorisationserver")
public class AadharVerificationServiceApplication{

	public static void main(String[] args) {
		SpringApplication.run(AadharVerificationServiceApplication.class, args);
	}
	
	// For swagger to document the Service
	/*@Bean
	public Docket api() throws IOException,XmlPullParserException{
	       return new Docket(DocumentationType.SWAGGER_2);
	}*/

}
