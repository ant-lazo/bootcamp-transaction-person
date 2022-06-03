package com.nttdata.bootcamp.services.impl;
import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import com.nttdata.bootcamp.exceptions.TypeTransactionException;
import com.nttdata.bootcamp.utils.Constants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.nttdata.bootcamp.models.TransactionPerson;
import com.nttdata.bootcamp.models.products.PersonalCredit;
import com.nttdata.bootcamp.models.products.SavingAccount;
import com.nttdata.bootcamp.repositories.ITransactionPersonRepo;
import com.nttdata.bootcamp.services.ITransactionPersonService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionPersonImpl implements ITransactionPersonService{
    
    private final ITransactionPersonRepo tprepo;
    
    @Autowired
    private WebClient.Builder webClientBuilder;
    
    private Function<Mono<SavingAccount>, Mono<SavingAccount>>
            FUpdateSavingAccount = (objeto) -> webClientBuilder
            .baseUrl("http://service-product-savingaccount")
            .build()
            .patch()
            .uri("/savingAccount/update")
            .body(objeto, SavingAccount.class)
            .retrieve()
            .bodyToMono(SavingAccount.class);
            
    private Function<Mono<PersonalCredit>, Mono<PersonalCredit>>
            FUpdatePersonalCredit = (objeto) -> webClientBuilder
            .baseUrl("http://service-product-personalcredit")
            .build()
            .patch()
            .uri("/personalCredit/update")
            .body(objeto, PersonalCredit.class)
            .retrieve()
            .bodyToMono(PersonalCredit.class);
    
    @Override
    public Flux<TransactionPerson> findAll() {
        return tprepo.findAll();
    }
    @Override
    public Mono<TransactionPerson> findById(String id) {
        return tprepo.findById(id);
    }
    @Override
	public Mono<TransactionPerson> saveCurrentAccount(TransactionPerson transactionPerson) {
		return null;
	}
	@Override
	public Mono<TransactionPerson> saveFixedTermAccount(TransactionPerson transactionPerson) {
		return null;
	}
	@Override
	public Mono<TransactionPerson> savePersonalCredit(TransactionPerson transactionPerson) {
		Mono<PersonalCredit> personalCredit = webClientBuilder
                .baseUrl("http://service-product-personalcredit")
                .build()
                .get()
                .uri("/personalCredit/findById/"+transactionPerson.getIdProduct())
                .retrieve()
                .bodyToMono(PersonalCredit.class)
                .flatMap(p->{
                    switch (transactionPerson.getTypeTransaction()){
                        case Constants.CONSUMPTION:
                            p.consumption(new BigDecimal(transactionPerson.getAmount()));
                            break;
                        case Constants.PAYMENT:
                            p.payment(new BigDecimal(transactionPerson.getAmount()));
                            break;
                        default:
                            return Mono.error( new TypeTransactionException("The type transaction is incorret"));
                    }
                    return Mono.just(p);
                });
		
		Mono<PersonalCredit> updatePersonalCredit = FUpdatePersonalCredit.apply(personalCredit);
	       
        return updatePersonalCredit.flatMap(p->{
    		log.info("a TransactionPerson was created");
            return tprepo.save(transactionPerson);
        });
	}
    @Override
    public Mono<TransactionPerson> saveSavingAccount(TransactionPerson transactionPerson){
        Mono<SavingAccount> savingAccount = webClientBuilder
                .baseUrl("http://service-product-savingaccount")
                .build()
                .get()
                .uri("/savingAccount/findById/"+transactionPerson.getIdProduct())
                .retrieve()
                .bodyToMono(SavingAccount.class)
                .flatMap(p->{
                    switch (transactionPerson.getTypeTransaction()){
                        case Constants.WITHDRAWAL:
                            p.withdrawal(new BigDecimal(transactionPerson.getAmount()));
                            break;
                        case Constants.DEPOSIT:
                            p.deposit(new BigDecimal(transactionPerson.getAmount()));
                            break;
                        default:
                            return Mono.error( new TypeTransactionException("The type transaction is incorret"));
                    }
                    return Mono.just(p);
                });
            
        Mono<SavingAccount> updateSavingAccount = FUpdateSavingAccount.apply(savingAccount);
       
        return updateSavingAccount.flatMap(p->{
    		log.info("a TransactionPerson was created");
            return tprepo.save(transactionPerson);
        });
    }
    @Override
    public Mono<Void> delete(TransactionPerson transactionPerson) {
        return tprepo.delete(transactionPerson);
    }
	@Override
	public Flux<TransactionPerson> findByIdCustomerPersonAndProductName(String idCustomerPerson, String productName){
		if(idCustomerPerson.equals("xxxx")&&productName.equals("xxxx")) {
			throw new IllegalStateException("illegal product name TransactionPerson");
		}
		return tprepo.findByIdCustomerPersonAndProductName(idCustomerPerson, productName);
	}
	@Override
	public Flux<TransactionPerson> findByProductNameAndCreatedAtBetween(String productName, Date from, Date to) {
		return tprepo.findByProductNameAndCreatedAtBetween(productName, from, to);
	}

}
