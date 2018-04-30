package com.kkd.aadharverificationservice.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class AadharBean {
	
	@Id
	private String id;
	private String aadharNo;
	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth;
	private String gender;
	private String fatherName;
	private String photoUrl;
	private AddressBean permanentAddress;
	private String mobileNo;

	public AadharBean() {
		super();
	}

	public AadharBean(String id,String aadhaarNo, String firstName, String lastName, LocalDate dateOfBirth, String gender,
			String fatherName, String photoUrl, AddressBean permanentAddress, String mobileNo) {
		super();
		this.id = id;
		this.aadharNo = aadhaarNo;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.fatherName = fatherName;
		this.photoUrl = photoUrl;
		this.permanentAddress = permanentAddress;
		this.mobileNo = mobileNo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getAadharNo() {
		return aadharNo;
	}

	public void setAadharNo(String aadharNo) {
		this.aadharNo = aadharNo;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public AddressBean getPermanentAddress() {
		return permanentAddress;
	}

	public void setPermanentAddress(AddressBean permanentAddress) {
		this.permanentAddress = permanentAddress;
	}

	@Override
	public String toString() {
		return "AadharBean [aadharNo=" + aadharNo + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", dateOfBirth=" + dateOfBirth + ", gender=" + gender + ", fatherName=" + fatherName + ", photoUrl="
				+ photoUrl + ", permanentAddress=" + permanentAddress + ", mobileNo=" + mobileNo + "]";
	}

}
