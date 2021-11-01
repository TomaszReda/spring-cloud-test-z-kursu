package pl.tomek.test.currencyexchangeservice.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CircuitBreakerController {

    @GetMapping("/sample-api")
//    @Retry(name = "sample-api", fallbackMethod = "hardcodeResponse")
    @CircuitBreaker(name = "default", fallbackMethod = "hardcodeResponse")
//    @RateLimiter(name = "default")
//    @Bulkhead(name = "default")
    public String sampleApi() {
        log.info("Logger");
//        ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080/some-dumny-url", String.class);
//        return forEntity.getBody();
        return "sample-api";
    }

    public String hardcodeResponse(Exception ex) {
        log.info("bad");
        return "fallback-response";
    }
}
