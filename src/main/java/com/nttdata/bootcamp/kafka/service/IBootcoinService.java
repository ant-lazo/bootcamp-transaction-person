package com.nttdata.bootcamp.kafka.service;

import com.nttdata.bootcamp.models.RequestTransactBootcoin;
import com.nttdata.bootcamp.models.ResponseTransactBootcoin;

import reactor.core.publisher.Mono;

public interface IBootcoinService {
	public Mono<ResponseTransactBootcoin> processTransferBootcoin (RequestTransactBootcoin transaction);
}
