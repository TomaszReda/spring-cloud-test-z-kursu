package pl.test.tomek.cloud.apigateway.config;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.function.Function;

@Configuration
public class ApiGatewayConfiguration {


    private RestTemplate restTemplate;

    @Bean
    public RouteLocator gatewayROuter(RouteLocatorBuilder build) {
        Function<PredicateSpec, Buildable<Route>> routerFunction = p -> p.path("/get")
                .filters(f -> f.addRequestHeader("MyHeader", "My")
                        .addRequestParameter("param", "param"))
                .uri("http://httpbin.org:808");

        return build
                .routes()
                .route(routerFunction)
                .route(p->p.path("/currency-exchange/**")
                        .uri("lb://currency-exchange"))
                .route(p->p.path("/currency-conversion/**")
                        .uri("lb://currency-conversion"))
                .route(p->p.path("/currency-conversion-feign/**")
                        .uri("lb://currency-conversion"))
                .route(p->p.path("/currency-conversion-new/**")
                        .filters(f-> f.rewritePath(
                                "/currency-conversion-new/(?<segment>.*)",
                                "/currency-conversion-feign/${segment}"
                        ))
                        .uri("lb://currency-conversion"))
                .build();

    }
}
