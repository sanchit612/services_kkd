package com.kkd.verificationinformationservice;


import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import brave.sampler.Sampler;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableRabbit
@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
@EnableCircuitBreaker
@EnableHystrixDashboard
public class VerificationInformationServiceApplication{

	public static void main(String[] args) {
		SpringApplication.run(VerificationInformationServiceApplication.class, args);
	}
	// name of exchange to be created
			public static final String EXCHANGE_NAME = "appExchange";
			// name of generic queue to be created
			public static final String QUEUE_GENERIC_NAME = "appGenericQueue";
			// name of specific to be created
			public static final String QUEUE_SPECIFIC_NAME = "appSpecificQueue";
			// name of routing to be created
			public static final String ROUTING_KEY = "messages.key";
		
		
		//For Sleuth
		@Bean
		public Sampler defaultSampler()
		{
			return Sampler.ALWAYS_SAMPLE;
		}
		
		public static final Contact DEFAULT_CONTACT = new Contact("Loki/Sancz", "http://www.in28minutes.com",
				"lokesh09rishi@gmail.com");

		public static final ApiInfo DEFAULT_API_INFO = new ApiInfo("Spring Boot Rest API", "Spring Boot Rest API for KKD", "1.0",
				"Terms of Services", DEFAULT_CONTACT, null, null);

		private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<String>(
				Arrays.asList("application/json"));
		
		//For swagger to document the Service
	    @Bean
	    UiConfiguration uiConfig() {
	        return new UiConfiguration("validatorUrl", "list", "alpha", "schema",
	                UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS, false, true, 60000L);
	    }
	    @Bean
	    public Docket api() throws IOException, XmlPullParserException {
	        return new Docket(DocumentationType.SWAGGER_2).apiInfo(DEFAULT_API_INFO).produces(DEFAULT_PRODUCES_AND_CONSUMES)
					.consumes(DEFAULT_PRODUCES_AND_CONSUMES);
	}
	    
	 // creating exchange
	 	@Bean
	 	public TopicExchange appExchange() {
	 		return new TopicExchange(EXCHANGE_NAME);
	 	}

	 	// creating generic queue
	 	@Bean
	 	public Queue appQueueGeneric() {
	 		return new Queue(QUEUE_GENERIC_NAME);
	 	}

	 	// creating specific queue
	 	@Bean
	 	public Queue appQueueSpecific() {
	 		return new Queue(QUEUE_SPECIFIC_NAME);
	 	}

	 	// binding generic queue with exchange with a routing key
	 	@Bean
	 	public Binding declareBindingGeneric() {
	 		return BindingBuilder.bind(appQueueGeneric()).to(appExchange()).with(ROUTING_KEY);
	 	}

	 	// binding specific queue with exchange with a routing key
	 	@Bean
	 	public Binding declareBindingSpecific() {
	 		return BindingBuilder.bind(appQueueSpecific()).to(appExchange()).with(ROUTING_KEY);
	 	}

	}

