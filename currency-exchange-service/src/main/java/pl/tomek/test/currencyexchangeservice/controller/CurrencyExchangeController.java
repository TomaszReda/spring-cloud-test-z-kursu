package pl.tomek.test.currencyexchangeservice.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.tomek.test.currencyexchangeservice.model.ExchangeValue;
import pl.tomek.test.currencyexchangeservice.repository.ExchangeValueRepository;

import java.util.Objects;

@RestController
@Data
@Slf4j
@AllArgsConstructor
public class CurrencyExchangeController {
    private final Environment environment;
    private final ExchangeValueRepository repository;


    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public ExchangeValue retrieveExchangeValue
            (@PathVariable String from, @PathVariable String to) throws Exception {
        log.info("retrieveExchangeValue called with {} to {}", from, to);

        ExchangeValue exchangeValue =
               repository.findByFromAndTo(from, to).orElseThrow(() -> new RuntimeException("Unable to find data from" + from + " to" + to));

        exchangeValue.setEnvironment(
                Integer.parseInt(Objects.requireNonNull(environment.getProperty("local.server.port"))));

        log.info("{}", exchangeValue);

        return exchangeValue;
    }
}
