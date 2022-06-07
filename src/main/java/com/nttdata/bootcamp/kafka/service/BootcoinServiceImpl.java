package com.nttdata.bootcamp.kafka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.bootcamp.models.RequestTransactBootcoin;
import com.nttdata.bootcamp.models.ResponseTransactBootcoin;

import reactor.core.publisher.Mono;

import com.nttdata.bootcamp.producer.KafkaProducer;

@Service
public class BootcoinServiceImpl implements IBootcoinService {
	
	@Autowired
	private KafkaProducer kafkaProducer;

	@Override
	public Mono<ResponseTransactBootcoin> processTransferBootcoin(RequestTransactBootcoin transaction) {
		// TODO Auto-generated method stub
		return null;
	}

}
