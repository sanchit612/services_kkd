package com.kkd.verificationinformationservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kkd.verificationinformationservice.model.OTPBean;

public interface VerificationInformationRepository extends MongoRepository<OTPBean, String> {
	

}
