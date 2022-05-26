package com.nttdata.bootcamp.services.impl;
import java.math.BigDecimal;
import java.util.function.Function;
import com.nttdata.bootcamp.exceptions.TypeTransactionException;
import com.nttdata.bootcamp.utils.Constants;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.nttdata.bootcamp.models.TransactionPerson;
import com.nttdata.bootcamp.models.products.SavingAccount;
import com.nttdata.bootcamp.repositories.ITransactionPersonRepo;
import com.nttdata.bootcamp.services.ITransactionPersonService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class TransactionPersonImpl implements ITransactionPersonService{
    @Autowired
    ITransactionPersonRepo tprepo;
    /*private WebClient customerServiceClient = WebClient.builder()
              .baseUrl("http://localhost:8024")
              .build();*/
    @Autowired
    private WebClient.Builder webClientBuilder;
    /*private Function<Mono<SavingAccount>, Mono<SavingAccount>>
            updateSavingAccount = (objeto) -> customerServiceClient
            .patch()
            .uri("/savingAccount/update")
            .body(objeto, SavingAccount.class)
            .retrieve()
            .bodyToMono(SavingAccount.class);*/
    @Override
    public Flux<TransactionPerson> findAll() {
        return tprepo.findAll();
    }
    @Override
    public Mono<TransactionPerson> findById(String id) {
        return tprepo.findById(id);
    }
    @Override
    public Mono<TransactionPerson> save(TransactionPerson transactionPerson){
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
        
            Mono<SavingAccount> updateSavingAccount = webClientBuilder
                    .baseUrl("http://service-product-savingaccount")
                    .build()
                    .patch()
                    .uri("/savingAccount/update")
                    .accept(MediaType.APPLICATION_JSON)
                    .body(savingAccount, SavingAccount.class)
                    .retrieve()
                    .bodyToMono(SavingAccount.class);
            
                
        return updateSavingAccount.flatMap(p->{
            return tprepo.save(transactionPerson);
        });
    }
    @Override
    public Mono<Void> delete(TransactionPerson transactionPerson) {
        return tprepo.delete(transactionPerson);
    }
	@Override
	public Flux<TransactionPerson> findByIdCustomerPersonAndProductName(String idCustomerPerson, String productName) {
		// TODO Auto-generated method stub
		return null;
	}

}
