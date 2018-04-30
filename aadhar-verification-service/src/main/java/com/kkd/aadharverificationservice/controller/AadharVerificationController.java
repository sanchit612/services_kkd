package com.kkd.aadharverificationservice.controller;


import javax.validation.Valid;

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
import com.kkd.aadharverificationservice.proxy.VerificationInformationServiceProxy;
import com.kkd.aadharverificationservice.repository.AadharVerificationRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/user")
public class AadharVerificationController extends Thread{
	
	//creating instance of aadharVerificationRepository
	@Autowired
	private AadharVerificationRepository aadharVerification;
	
	@Autowired
	public VerificationInformationServiceProxy proxy;
	
	
	@PostMapping("/new/add")
	@HystrixCommand(fallbackMethod = "newUserFallback")
	@ApiOperation("To add a new user's Aadhar details")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successfully added the details to the database"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<HttpStatus> newUser(@RequestBody AadharBean aadharBean)
	{
		
		try {
			aadharVerification.save(aadharBean);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
	}
	
	// Fallback method for the above mapping
		@ApiOperation("In case of newUser fallback")
		public ResponseEntity<HttpStatus> newUserFallback(@RequestBody AadharBean aadharBean)
		{
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	
	@GetMapping("/new/{aadharNo}")
	@HystrixCommand(fallbackMethod = "addAadharFallback")
	@ApiOperation("To return the mobile number corresponding to aadhar no")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successfully added the details to the database"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<AadharBean> addAadhar(@PathVariable String aadharNo) {
		
		AadharBean aad = aadharVerification.findById(aadharNo).orElse(null);
		
		if(aad.getAadharNo().equals(aadharNo)) {
			String mobileNo = aad.getMobileNo();
			try {
		
				Thread t1 = new Thread(new Runnable() {
					@Override
					public void run() {
						proxy.generateOtp(mobileNo);
					}
				});
				t1.start();
				
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			return ResponseEntity.status(HttpStatus.CREATED).body(aad);
			
		}
		else
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		
	}

}