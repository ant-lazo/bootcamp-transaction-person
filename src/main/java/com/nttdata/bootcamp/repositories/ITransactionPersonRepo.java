package com.nttdata.bootcamp.repositories;

import java.util.Date;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.nttdata.bootcamp.models.TransactionPerson;

import reactor.core.publisher.Flux;

public interface ITransactionPersonRepo extends ReactiveMongoRepository<TransactionPerson, String>{

	Flux<TransactionPerson> findByIdCustomerPersonAndProductName(String idCustomerPerson, String productName);
	
	Flux<TransactionPerson> findByProductNameAndCreatedAtBetween(String productName, Date from, Date to);

}
