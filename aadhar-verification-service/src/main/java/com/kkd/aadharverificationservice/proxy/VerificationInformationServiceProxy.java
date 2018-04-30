package com.kkd.aadharverificationservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name="verification-information-service",url="http://localhost:8097")
public interface VerificationInformationServiceProxy {

	@GetMapping("/generateotp/{mobileNo}")
	public ResponseEntity<String> generateOtp(@PathVariable(value="mobileNo") String mobileNo);
	
}
