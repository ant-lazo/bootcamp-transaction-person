package com.nttdata.bootcamp.models.products;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.nttdata.bootcamp.exceptions.InsufficientAmountExcepcion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurrentAccount {
	private String id;
	private String idCustomer;
	private String typeCustomer;
	private String accountingBalance;
	private String maintenance;
	private String profile;
	
	public void withdrawal(BigDecimal amount) {
		BigDecimal accBigDec = new BigDecimal(this.accountingBalance);
		BigDecimal newAmount = accBigDec.subtract(amount);
		if(newAmount.compareTo(BigDecimal.ZERO)<0) {
			throw new InsufficientAmountExcepcion("The account balance can't be less than zero");
		}
		this.accountingBalance =newAmount.toPlainString();
	}
	
	public void deposit(BigDecimal amount) {
		BigDecimal accBigDec = new BigDecimal(this.accountingBalance);
		BigDecimal result = accBigDec.add(amount);
		this.accountingBalance=result.toPlainString();
	}
}
