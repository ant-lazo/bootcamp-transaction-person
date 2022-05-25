package com.nttdata.bootcamp.services;

import com.nttdata.bootcamp.models.TransactionPerson;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ITransactionPersonService {
	public Flux<TransactionPerson> findAll();
	public Mono<TransactionPerson> findById(String id);
	public Mono<TransactionPerson> save(TransactionPerson transactionPerson);
	public Mono<Void> delete(TransactionPerson transactionPerson);
	public Flux<TransactionPerson> findByIdCustomerPersonAndProductName(String idCustomerPerson, String productName);
}
