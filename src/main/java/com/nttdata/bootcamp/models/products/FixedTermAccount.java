package com.nttdata.bootcamp.models.products;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.nttdata.bootcamp.exceptions.DayNotCorrectException;
import com.nttdata.bootcamp.exceptions.InsufficientAmountExcepcion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FixedTermAccount {
	private String id;
	private String idCustomerPerson;
	private String accountingBalance;
	private String movementDay;
	
	public void withdrawal(BigDecimal amount) {
		SimpleDateFormat format = new SimpleDateFormat("dd");
		String today = format.format(new Date());
		if(this.movementDay.equals(today)) {
			BigDecimal accBigDec = new BigDecimal(this.accountingBalance);
			BigDecimal newAmount = accBigDec.subtract(amount);
			if(newAmount.compareTo(BigDecimal.ZERO)<0) {
				throw new InsufficientAmountExcepcion("The account balance can't be less than zero");
			}
			this.accountingBalance =newAmount.toPlainString();
		}else {
			throw new DayNotCorrectException("It is not the right day to make witdrawals");
		}
	}
	
	public void deposit(BigDecimal amount) {
		SimpleDateFormat format = new SimpleDateFormat("dd");
		String today = format.format(new Date());
		if(this.movementDay.equals(today)) {
			BigDecimal accBigDec = new BigDecimal(this.accountingBalance);
			BigDecimal result = accBigDec.add(amount);
			this.accountingBalance=result.toPlainString();
		}else {
			throw new DayNotCorrectException("It is not the right day to make deposits");
		}
	}
}
