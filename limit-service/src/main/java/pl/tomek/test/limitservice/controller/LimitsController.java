package pl.tomek.test.limitservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.tomek.test.limitservice.config.Properties;
import pl.tomek.test.limitservice.model.Limits;

@RestController
@AllArgsConstructor
public class LimitsController {

    private final Properties properties;

    @GetMapping("/limits")
    public Limits retreveLimits(){
        return new Limits(properties.getMinimum(),properties.getMaximum());
    }
}
