package com.kkd.aadharverificationservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kkd.aadharverificationservice.model.AadharBean;

public interface AadharVerificationRepository extends MongoRepository<AadharBean, String> {
	
	

}
