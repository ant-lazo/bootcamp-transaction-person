package com.nttdata.bootcamp.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestTransactBootcoin {
	private String idCustomer;
    private String typeTransaction;
    private String amount;
}
