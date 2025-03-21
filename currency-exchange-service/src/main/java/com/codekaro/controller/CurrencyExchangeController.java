package com.codekaro.controller;

import org.springframework.web.bind.annotation.RestController;
import com.codekaro.beans.CurrencyExchange;
import com.codekaro.repo.CurrencyExchangeRepo;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class CurrencyExchangeController {
	
	@Autowired
	private CurrencyExchangeRepo repository;

	@Autowired
	private Environment environment;

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange retriveExchangeValue(@PathVariable String from, @PathVariable String to) {
		CurrencyExchange currencyExchange =	repository.findByFromAndTo(from,to);
		if (currencyExchange==null) {
			throw new RuntimeException("Unable to find data for "+from +"to"+ to);
		}
		String port = environment.getProperty("local.server.port");
		currencyExchange.setEnvironment(port);
		return currencyExchange;
	}
}
