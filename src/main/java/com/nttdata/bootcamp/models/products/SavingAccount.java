package com.nttdata.bootcamp.models.products;

import java.math.BigDecimal;

import com.nttdata.bootcamp.exceptions.InsufficientAmountExcepcion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SavingAccount {
	private String id;
	private String idCustomerPerson;
	private String accountingBalance;
	private String maintenance;
	private String movementLimit;
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
