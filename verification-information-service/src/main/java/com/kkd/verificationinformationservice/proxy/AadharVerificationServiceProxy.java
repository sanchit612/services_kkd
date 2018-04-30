package com.kkd.verificationinformationservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name="aadhar-verification-service",url="http://localhost:8002")
public interface AadharVerificationServiceProxy {

	@GetMapping("/user/new/{aadharNo}")
	public String addAadhar(@PathVariable("aadharNo") String aadharNo);
	
}
