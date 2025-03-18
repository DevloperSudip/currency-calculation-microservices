package com.currencyconversion.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.currencyconversion.beans.CurrencyConversion;
import com.currencyconversion.interfaceproxy.CurrencyExchangeProxy;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class CurrencyConversionController {
	
	@Autowired
	private CurrencyExchangeProxy proxy;
	
	@GetMapping("/Currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculationCurrencyConversion(@PathVariable String from,@PathVariable String to,
	@PathVariable BigDecimal quantity) {
		HashMap<String , String>uriVariable=new HashMap<>();
		uriVariable.put("from", from);
		uriVariable.put("to", to);
		ResponseEntity<CurrencyConversion> reponseEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
				CurrencyConversion.class,uriVariable);
		CurrencyConversion currencyConversion = reponseEntity.getBody();
		return new CurrencyConversion(currencyConversion.getId(),from,to,quantity,currencyConversion.getConversionMultiple(),
				quantity.multiply(currencyConversion.getConversionMultiple()),currencyConversion.getEnvironment());
	}
	
	
	@GetMapping("/Currency-conversion-feighn/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculationCurrencyConversionFeign(@PathVariable String from,@PathVariable String to,
	@PathVariable BigDecimal quantity) {
		CurrencyConversion currencyConversion = 	proxy.retriveExchangeValue(from, to);
		return new CurrencyConversion(currencyConversion.getId(),from,to,quantity,currencyConversion.getConversionMultiple(),
				quantity.multiply(currencyConversion.getConversionMultiple()),currencyConversion.getEnvironment());
	}
	

}
