package com.codekaro.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codekaro.beans.CurrencyExchange;

public interface CurrencyExchangeRepo extends JpaRepository<CurrencyExchange, Long> {

	CurrencyExchange findByFromAndTo(String from, String to);
	
	

}
