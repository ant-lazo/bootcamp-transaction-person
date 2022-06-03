package com.nttdata.bootcamp;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.nttdata.bootcamp.models.products.PersonalCredit;

@SpringBootTest
class BootcampServiceTransactionPersonApplicationTests {

	@Test
	void consumption() {
		PersonalCredit pc = new PersonalCredit();
		pc.setId("b1");
		pc.setIdCustomerPerson("c1");
		pc.setAccountingBalance("700");
		pc.setAvailableBalance("700");
		pc.setDebt("0");
		pc.setNumMovement(3);
		
		BigDecimal mount = new BigDecimal("2");
		pc.consumption(mount);
		assertEquals("694", pc.getAvailableBalance());
		assertEquals("6", pc.getDebt());
		assertEquals(4, pc.getNumMovement());
	}
	
	@Test
	void payment() {
		PersonalCredit pc = new PersonalCredit();
		pc.setId("b1");
		pc.setIdCustomerPerson("c1");
		pc.setAccountingBalance("700");
		pc.setAvailableBalance("650");
		pc.setDebt("50");
		pc.setNumMovement(3);
		
		BigDecimal mount = new BigDecimal("24");
		pc.payment(mount);
		assertEquals("674", pc.getAvailableBalance());
		assertEquals("26", pc.getDebt());
		assertEquals(4, pc.getNumMovement());
	}

}
