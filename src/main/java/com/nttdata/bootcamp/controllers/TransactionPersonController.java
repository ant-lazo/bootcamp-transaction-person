package com.nttdata.bootcamp.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.bootcamp.exceptions.InsufficientAmountExcepcion;
import com.nttdata.bootcamp.exceptions.ProductNameNotFoundExcepcion;
import com.nttdata.bootcamp.exceptions.TypeTransactionException;
import com.nttdata.bootcamp.models.RequestFindByDate;
import com.nttdata.bootcamp.models.TransactionPerson;
import com.nttdata.bootcamp.repositories.ITransactionPersonRepo;
import com.nttdata.bootcamp.services.ITransactionPersonService;
import com.nttdata.bootcamp.utils.Constants;
import com.nttdata.bootcamp.utils.GetDate;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/transactionPerson")
public class TransactionPersonController {
	
	@Autowired
	private ITransactionPersonService tarepo;
	
	@Autowired
	private ITransactionPersonRepo repo;
	
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
		
		transactionPerson.setCreatedAt(new Date());
		
		log.info("a TransactionPerson was created");
		switch (transactionPerson.getProductName()) {
			case Constants.CURRENTACCOUNT:
				return tarepo.saveCurrentAccount(transactionPerson);
			case Constants.FIXEDTERMACCOUNT:
				return tarepo.saveFixedTermAccount(transactionPerson);
			case Constants.PERSONALCREDIT:
				return tarepo.savePersonalCredit(transactionPerson);
			case Constants.SAVINGACCOUNT:
				return tarepo.saveSavingAccount(transactionPerson);
		}
		return Mono.error( new ProductNameNotFoundExcepcion("Product Name not is valid"));  
	}
	
	@DeleteMapping("/delete")
	public Mono<Void> delete(@RequestBody TransactionPerson transactionPerson){
		return tarepo.delete(transactionPerson);
	}
	
	@GetMapping("/findByIdCustomerPersonAndProductName/{idCustomerPerson}/{productName}")
	public Flux<TransactionPerson> findByIdCustomerPersonAndProductName(@PathVariable String idCustomerPerson, @PathVariable String productName){
		return tarepo.findByIdCustomerPersonAndProductName(idCustomerPerson, productName);
	}
	
	@GetMapping("/findByProductNameAndCreatedAtBetween")
	public Flux<TransactionPerson> findByProductNameAndCreatedAtBetween(@RequestBody RequestFindByDate requestFindByDate) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy"); 
	    Date from = format.parse(requestFindByDate.getFrom());
	    Date to = format.parse(requestFindByDate.getTo());
	    
	    Calendar c = Calendar.getInstance();
        c.setTime(from);
        c.add(Calendar.DATE, 1);
        from = c.getTime();
        
        Calendar d = Calendar.getInstance();
        d.setTime(to);
        d.add(Calendar.DATE, 1);
        to = d.getTime();
        
	    return repo.findByProductNameAndCreatedAtBetween(requestFindByDate.getProductName(),from,to);
		 
	}

}
