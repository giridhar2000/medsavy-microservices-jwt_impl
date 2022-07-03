package com.medsavy.authservice.configs;

import com.medsavy.authservice.calls.UserDto;
import io.jsonwebtoken.Claims;
import java.util.List;
import java.util.function.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RefreshScope
@Slf4j
public class AuthFilter implements GatewayFilter {

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private MyUserDetailService userDetailService;

  @Autowired
  private RouterValidator routerValidator;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    ServerHttpRequest request = (ServerHttpRequest) exchange.getRequest();
    // HttpHeaders headers = request.getHeaders();

    // final List<String> apiEndpoints = List.of("/api/v1/user/register",
    //     "/api/v1/user/login",
    //     "/api/v1/user/customer/role");

    // Predicate<ServerHttpRequest> isApiSecured = r -> apiEndpoints.stream()
    //     .noneMatch(uri -> r.getURI().getPath().contains(uri));

    //    if (routerValidator.isSecured.test(request)) {
//      if (this.isAuthMissing(request))
//        return this.onError(exchange, "Authorization header is missing in request", HttpStatus.UNAUTHORIZED);

    if (routerValidator.isSecured.test(request)) {
      if (!request.getHeaders().containsKey("Authorization")) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);

        return response.setComplete();
      }

      String token = request.getHeaders().getOrEmpty("Authorization").get(0);
      UserDto userDto = new UserDto();
      try {
        token = token.substring(7);
        String tokenUser = jwtUtil.extractUsername(token);
        Claims claims = jwtUtil.extractAllClaims(token);
        String userRole =(String) claims.get("role");
        // List<String> user = headers.get("user");
        // userDto = userDetailService.fetchUserData(user.get(0));


      } catch (Exception e) {
        // e.printStackTrace();
        // log.info("Logs:"+e.getMessage());
//        ServerHttpResponse response = exchange.getResponse();
//        response.setStatusCode(HttpStatus.UNAUTHORIZED);
//
//        return response.setComplete();
      }
    }

    return chain.filter(exchange);
  }
}
