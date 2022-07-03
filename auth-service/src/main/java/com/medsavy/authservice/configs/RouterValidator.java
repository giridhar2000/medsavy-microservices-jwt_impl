package com.medsavy.authservice.configs;

import java.util.List;
import java.util.function.Predicate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class RouterValidator {

  public static final List<String> openApiEndpoints= List.of(
      "/api/v1/user/register",
      "/api/v1/user/login",
      "/api/v1/user/customer/role"
  );

  
  public Predicate<ServerHttpRequest> isSecured =
      request -> openApiEndpoints
          .stream()
          .noneMatch(uri -> request.getURI().getPath().contains(uri));

}
