package com.nttdata.bootcamp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.bootcamp.models.TransactionPerson;
import com.nttdata.bootcamp.services.ITransactionPersonService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/transactionPerson")
public class TransactionPersonController {
	
	@Autowired
	private ITransactionPersonService tarepo;
	
	@GetMapping("/findAll")
	public Flux<TransactionPerson> findAll(){
		return tarepo.findAll();
	}
	
	@PutMapping("/findById/{id}")
	public Mono<TransactionPerson> findById(@PathVariable String id){
		log.info("a TransactionPerson was consulted");
		return tarepo.findById(id);
	}
	
	@PostMapping("/save")
	public Mono<TransactionPerson> save(@RequestBody TransactionPerson transactionPerson){
		log.info("a TransactionPerson was created");
		return tarepo.save(transactionPerson);
	}
	
	@DeleteMapping("/delete")
	public Mono<Void> delete(@RequestBody TransactionPerson transactionPerson){
		return tarepo.delete(transactionPerson);
	}
	
	@GetMapping("/findByIdCustomerPersonAndProductName/{idCustomerPerson}/{productName}")
	public Flux<TransactionPerson> findByIdCustomerPersonAndProductName(@PathVariable String idCustomerPerson, @PathVariable String productName){
		return tarepo.findByIdCustomerPersonAndProductName(idCustomerPerson, productName);
	}

}
