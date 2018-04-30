package com.kkd.verificationinformationservice.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Document(collection="OTPBeanDoc")
@ApiModel(description = "All details about the User rating.")
public class OTPBean {
	
	@Id
	@ApiModelProperty(notes = "${message-not-empty}")
	@NotNull(message = "${message-not-empty}")
	String mobileNo;
	
	@ApiModelProperty(notes = "${message-not-empty}")
	@NotNull(message = "${message-not-empty}")
	@Size(min=1000,max=9999, message="${message-range}")
	int otp;
	
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public int getOtp() {
		return otp;
	}
	public void setOtp(int otp) {
		this.otp = otp;
	}
	public OTPBean(String mobileNo, int otp) {
		this.mobileNo = mobileNo;
		this.otp = otp;
	}
	
	@Override
	public String toString() {
		return "OTPBean [mobileNo=" + mobileNo + ", otp=" + otp + "]";
	}
	public OTPBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	

}
