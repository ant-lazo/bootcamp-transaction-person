package com.nttdata.bootcamp.services;

import java.util.Date;

import com.nttdata.bootcamp.models.TransactionPerson;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ITransactionPersonService {
	public Flux<TransactionPerson> findAll();
	public Mono<TransactionPerson> findById(String id);
	public Mono<TransactionPerson> saveCurrentAccount(TransactionPerson transactionPerson);
	public Mono<TransactionPerson> saveFixedTermAccount(TransactionPerson transactionPerson);
	public Mono<TransactionPerson> savePersonalCredit(TransactionPerson transactionPerson);
	public Mono<TransactionPerson> saveSavingAccount(TransactionPerson transactionPerson);
	public Mono<Void> delete(TransactionPerson transactionPerson);
	public Flux<TransactionPerson> findByIdCustomerPersonAndProductName(String idCustomerPerson, String productName);
	public Flux<TransactionPerson> findByProductNameAndCreatedAtBetween(String productName, Date from, Date to);
}
