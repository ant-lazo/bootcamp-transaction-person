package com.nttdata.bootcamp.web.expose;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.bootcamp.kafka.service.IBootcoinService;
import com.nttdata.bootcamp.models.RequestTransactBootcoin;
import com.nttdata.bootcamp.models.ResponseTransactBootcoin;
import com.nttdata.bootcamp.producer.KafkaProducer;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/customer")
public class TrasanctionController {
	@Autowired
	private KafkaProducer kafkaProducer;
	
	@Autowired
	private IBootcoinService bootservice;
	
	@PostMapping(path="/transaction", produces = MediaType.APPLICATION_JSON_VALUE,
			consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> requestTransaction(@RequestBody String transaction){
		kafkaProducer.publishMessage(transaction);
		return ResponseEntity.ok(transaction);
		//return bootservice.processTransferBootcoin(transaction);
	}
	
	/*@PostMapping(path="/transactionBootcoin", produces = MediaType.APPLICATION_JSON_VALUE,
			consumes= MediaType.APPLICATION_JSON_VALUE)
	public Mono<ResponseTransactBootcoin> requestTransactionBootcoin(@RequestBody RequestTransactBootcoin request){
		kafkaProducer.publishMessageTransaction(request);
		return null;
	}*/
}
