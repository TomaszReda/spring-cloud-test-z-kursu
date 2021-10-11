package pl.tomek.test.currencyexchangeservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.tomek.test.currencyexchangeservice.model.ExchangeValue;

import java.util.Optional;

public interface ExchangeValueRepository extends 
		JpaRepository<ExchangeValue, Long>{

	Optional<ExchangeValue> findByFromAndTo(String from, String to);
}
