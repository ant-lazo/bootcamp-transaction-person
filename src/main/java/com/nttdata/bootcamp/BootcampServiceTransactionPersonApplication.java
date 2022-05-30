package com.nttdata.bootcamp;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.nttdata.bootcamp.models.TransactionPerson;
import com.nttdata.bootcamp.repositories.ITransactionPersonRepo;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@EnableEurekaClient
@Slf4j
@SpringBootApplication
public class BootcampServiceTransactionPersonApplication implements CommandLineRunner{
  
  @Autowired
  ITransactionPersonRepo tprepo;
  
  @Autowired
  ReactiveMongoTemplate mongoTemplate;

  public static void main(String[] args) {
    SpringApplication.run(BootcampServiceTransactionPersonApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
  mongoTemplate.dropCollection("transaction_persons").subscribe();
  
  //Date today= new Date();
    
  Flux.just(TransactionPerson.builder()
    .idCustomerPerson("b1")
    .idProduct("120")
    .productName("SavingAccount")
    .typeTransaction("paid")
    .amount("13")
    .createdAt(new Date())
    .build()).flatMap(bs->{
       return tprepo.save(bs);
  }).subscribe(s-> log.info("Se ingreso transactionPerson: "+s));
}

}
