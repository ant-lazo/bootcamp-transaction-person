package com.nttdata.bootcamp.models.products;

import java.math.BigDecimal;

import com.nttdata.bootcamp.exceptions.InsufficientAmountExcepcion;
import com.nttdata.bootcamp.utils.Constants;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonalCredit {
	private String id;
	private String idCustomerPerson;
	private String accountingBalance;
	private String availableBalance;
	private String debt;
	private Integer numMovement;
	
	public void consumption(BigDecimal amount) {
		
		BigDecimal avbBigDec = new BigDecimal(this.availableBalance);
		BigDecimal debtBigDec = new BigDecimal(this.debt);
		if(avbBigDec.compareTo(amount)>=0) {
			BigDecimal newAmountDebt;
			BigDecimal newAmountAvailable;
			Integer limit = this.numMovement+1;
			if(limit % Constants.LIMITMOVEMENTTOMAINTENANCE == 0) {
				BigDecimal maintenance = new BigDecimal(Constants.MAINTENANCE);
				BigDecimal newAmount = amount.add(maintenance);
				newAmountDebt = debtBigDec.add(newAmount);
				newAmountAvailable = avbBigDec.subtract(newAmount);
				log.info("the maintenance was charged");
			}else {
				newAmountDebt = debtBigDec.add(amount);
				newAmountAvailable = avbBigDec.subtract(amount);
			}
			this.debt = newAmountDebt.toPlainString();
			this.availableBalance =newAmountAvailable.toPlainString();
			this.numMovement++;
		}else {
			throw new InsufficientAmountExcepcion("The amount can't be greater than available balance");
		}
	}
	
	public void payment(BigDecimal amount) {
		BigDecimal avbBigDec = new BigDecimal(this.availableBalance);
		BigDecimal debtBigDec = new BigDecimal(this.debt);
		if(debtBigDec.compareTo(amount)>=0) {
			BigDecimal newAmountDebt;
			BigDecimal newAmountAvailable;
			Integer limit = this.numMovement+1;
			if(limit % Constants.LIMITMOVEMENTTOMAINTENANCE == 0) {
				BigDecimal maintenance = new BigDecimal(Constants.MAINTENANCE);
				BigDecimal newAmount = amount.add(maintenance);
				newAmountDebt = debtBigDec.subtract(newAmount);
				newAmountAvailable = avbBigDec.add(newAmount);
				log.info("the maintenance was charged");
			}else {
				newAmountDebt = debtBigDec.subtract(amount);
				newAmountAvailable = avbBigDec.add(amount);
			}
			this.debt =newAmountDebt.toPlainString();
			this.availableBalance =newAmountAvailable.toPlainString();
			this.numMovement++;
		}else {
			throw new InsufficientAmountExcepcion("The amount can't be greater than debt");
		}
	}
}
