package pl.tomek.test.currencyconversionservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import pl.tomek.test.currencyconversionservice.model.CurrencyConversion;
import pl.tomek.test.currencyconversionservice.proxy.CurrencyExchangeProxy;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@RestController
public class CurrencyConversionController {

    private final CurrencyExchangeProxy currencyExchangeProxy;

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversion(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity

    ) {

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CurrencyConversion> responseEntity = restTemplate.getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class,
                uriVariables);
        CurrencyConversion currencyConversion = responseEntity.getBody();

        BigDecimal conversionMultiple = currencyConversion.getConversionMultiple();
        String environment = currencyConversion.getEnvironment();
        Long id = currencyConversion.getId();
        CurrencyConversion result = new CurrencyConversion(id, from, to, conversionMultiple, quantity, quantity.multiply(conversionMultiple), environment);

        return result;
    }


    @GetMapping("/currency-conversion-foreign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversionForeign(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity

    ) {


        CurrencyConversion currencyConversion = currencyExchangeProxy.retrieveExchangeValue(from,to);
        BigDecimal conversionMultiple = currencyConversion.getConversionMultiple();
        String environment = currencyConversion.getEnvironment()+" foreign";
        Long id = currencyConversion.getId();
        CurrencyConversion result = new CurrencyConversion(id, from, to, conversionMultiple, quantity, quantity.multiply(conversionMultiple), environment);

        return result;
    }
}
