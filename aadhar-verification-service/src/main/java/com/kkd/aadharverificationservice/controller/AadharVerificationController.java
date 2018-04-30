package com.kkd.aadharverificationservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kkd.aadharverificationservice.model.AadharBean;
import com.kkd.aadharverificationservice.repository.AadharVerificationRepository;

@RestController
@RequestMapping("/user")
public class AadharVerificationController {
	
	//creating instance of aadharVerificationRepository
	@Autowired
	private AadharVerificationRepository aadharVerification;
	
	private com.kkd.aadharverificationservice.proxy.VerificationInformationServiceProxy proxy;
	
	
	@PostMapping("/new/add")
	public String newUser(@RequestBody AadharBean aadharBean)
	{
		aadharVerification.save(aadharBean);
		return "Successfully added aadhar.";
	}
	
	@GetMapping("/new/{aadharNo}")
	public ResponseEntity<AadharBean> addAadhar(@PathVariable String aadharNo) {
		
		System.out.println("*****//////////////////////"+aadharNo);
		AadharBean aad = aadharVerification.findById(aadharNo).orElse(null);
		
		System.out.println("*********************************"+aad);
		
		if(aad.getAadharNo().equals(aadharNo)) {
			String mobileNo = aad.getMobileNo();
			System.out.println("+++++++++++++++++++++++++++++++++++++++"+mobileNo);
			try {
			proxy.generateOtp(mobileNo);
			}
			catch(Exception e) {
				System.out.println("############################################"+e);
			}
			
			AadharBean bean = aad;
			return ResponseEntity.status(HttpStatus.CREATED).body(bean);
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		
	}
	
	

}