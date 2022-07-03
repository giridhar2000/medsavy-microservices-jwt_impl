package com.medsavy.authservice.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableHystrix
public class GatewayConfig {

  @Autowired
  AuthFilter filter;  // switch from AuthenticationFilter to AuthFilter if stuck problem remains

  @Bean
  public RouteLocator routes(RouteLocatorBuilder builder) {
    return builder.routes()
        .route("user-service", r -> r.path("/api/v1/user/**")
            .filters(f ->  f.filter(filter))
            .uri("lb://user-service"))
        .route("order-service", r -> r.path("/api/v1/orm/**")
            .filters(f -> f.filter(filter))
            .uri("lb://ORDER-SERVICE"))

        .route("inventory-management", r -> r.path("/api/v1/ivm/**")
            .filters(f -> f.filter(filter))
            .uri("lb://INVENTORY-MANAGEMENT"))

        .build();
  }

}
