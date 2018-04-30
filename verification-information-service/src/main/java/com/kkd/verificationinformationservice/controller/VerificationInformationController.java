package com.kkd.verificationinformationservice.controller;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kkd.verificationinformationservice.model.OTPBean;
import com.kkd.verificationinformationservice.proxy.AadharVerificationServiceProxy;
import com.kkd.verificationinformationservice.repository.VerificationInformationRepository;
import com.kkd.verificationinformationservice.service.MessageSenderService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin
@RestController
@Api(tags = {"OTP Service"})
public class VerificationInformationController{
	
	@Autowired
	MessageSenderService messageSenderService;

	
	@Autowired
	private VerificationInformationRepository verification;
	
	//String phoneNo = proxy.addAadhar();
	//Function Generating random integers between 1000 and 9999
	public int getRandomIntegerBetweenRange(int min, int max){ 
		int x = (int)(Math.random()*((max-min)+1))+min; 
		return x;
	}
	

	@ApiOperation(value = "OTP generated and deleted after 2 minutes successfully.")
	
	@ApiResponses(value = { @ApiResponse(code = 201, message = "OTP generated and deleted after 2 minutes successfully."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource."),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden."),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found.") })
	
	/*@HystrixCommand(fallbackMethod="generateOtpFallback")*/
	
	@GetMapping("/generateotp/{mobileNo}")
	public ResponseEntity<String> generateOtp(@PathVariable String mobileNo) {
		//messageSenderService.produceMsg("OTP generated and deleted after 2 minutes successfully.");
	try {	
		System.out.println("=========================================");
		
		int otp = getRandomIntegerBetweenRange(1000,9999);
		
		OTPBean otpBean = new OTPBean();
		otpBean.setMobileNo(mobileNo);
		otpBean.setOtp(otp);
		
		verification.save(otpBean);
		
		System.out.println(otpBean.toString());
		
		System.out.println("\n\n\n\n\nOTP generated successfully. It is valid for 120 seconds");
		//we are assuming that otp has been sent to person on text
		deleteOTP();
		String var = "Your OTP expired";
		return ResponseEntity.status(HttpStatus.CREATED).body(var);
	}
	catch(Exception e) {
		System.out.println(e);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error");
	}
	}
	//Function to delete the OTP after 2 minutes.
		public String deleteOTP() {
			
				try {
					Thread.sleep(12000000);
					System.out.println("Yup");
				} catch (Exception e) {
					System.out.println("Nope");
				}	
			
			return ("Deleted successfully");
			
		}
		
	
	//Fallback method for the above method.
		public ResponseEntity<String> generateOtpFallback(@PathVariable String mobileNo) {
			String var = "Error in generating or deleting the OTP";
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(var);
		}
	
	
	@ApiOperation(value = "Fetching all the OTPs and the mobile numbers that they are corresponding to.")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Fetching all the OTPs and the mobile numbers that they are corresponding to."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource."),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden."),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found.") })
	@HystrixCommand(fallbackMethod="getAllActiveOtpFallback")
	@GetMapping("/otp/allData")
	public ResponseEntity<List<OTPBean>> getAllActiveOtp(){
		List<OTPBean> otpBean = verification.findAll();
		return ResponseEntity.status(HttpStatus.CREATED).body(otpBean);
	}
	
	//Fallback method for the above method.
	public ResponseEntity<List<OTPBean>> getAllActiveOtpFallback(){
		List<OTPBean> otpBean=Arrays.asList(new OTPBean("0000000000",0));
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(otpBean);
	}
	@ApiOperation(value = "Verifying the OTP.")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Verifying the OTP."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource."),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden."),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found.") })
	@HystrixCommand(fallbackMethod="verifyOtpFallback")
	@PostMapping("/verifyotp")
	public ResponseEntity<Boolean> verifyOtp(@RequestBody OTPBean dataInBody){
		Optional<OTPBean> otpBean = verification.findById(dataInBody.getMobileNo());
		OTPBean newBean = otpBean.orElse(new OTPBean());
		//if otpBean is null then no OTP request corresponding to your number
		if(otpBean.equals(null)) {
			Boolean bool = false; 
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(bool);
		}
		else
		{
			if(newBean.getOtp() == dataInBody.getOtp()) {
				Boolean bool = true; 
				return ResponseEntity.status(HttpStatus.CREATED).body(bool);
			}
			else {
			Boolean bool = false; 
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(bool);
			}
		}
	}
	
	//Fallback method for the above method.
	public ResponseEntity<Boolean> verifyOtpFallback(@RequestBody OTPBean dataInBody) {
		Boolean bool = false; 
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(bool);
	}


	@ApiOperation(value = "Got the OTP")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Got the OTP"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource."),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden."),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found.") })
	@GetMapping("getotp/{mobileNo}")
	public ResponseEntity<Integer> fetchOtp(@PathVariable String mobileNo){
	
		
		OTPBean Otp;
		int gotOtp = 0;
		Integer fetchedOtp = new Integer(gotOtp);
		List<OTPBean> otpBean = verification.findAll();
		Iterator i = otpBean.iterator();
		while(i.hasNext()) {
			
			Otp = (OTPBean)i.next();
			if(Otp.getMobileNo().equals(mobileNo))
			{
				System.out.println("asdjkfhasdjkfhasjkd");
				fetchedOtp=(Integer)Otp.getOtp();
				return ResponseEntity.status(HttpStatus.CREATED).body(fetchedOtp);
			}
			
			else {
			}
			
		}
		return null;
	}

}