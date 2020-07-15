package com.techelevator;

import java.math.BigDecimal;

public class Quarter implements Coin {
	
	@Override
	public String getName() {
		return "Quarter";
	}
	
	@Override
	public BigDecimal getValue() {
		return new BigDecimal("25");
	}

}
