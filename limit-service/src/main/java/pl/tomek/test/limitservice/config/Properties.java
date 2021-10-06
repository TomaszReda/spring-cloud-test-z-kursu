package pl.tomek.test.limitservice.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "limit-service")
@Data
public class Properties {


    private int minimum;
    private int maximum;
}
