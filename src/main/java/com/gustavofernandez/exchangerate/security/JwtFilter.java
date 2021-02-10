package com.gustavofernandez.exchangerate.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@Slf4j
public class JwtFilter implements WebFilter {

  private final RequestMappingHandlerMapping handlerMapping;
  private final JwtService jwtService;

  public JwtFilter(
      @Qualifier("requestMappingHandlerMapping") RequestMappingHandlerMapping handlerMapping,
      JwtService jwtService) {
    this.handlerMapping = handlerMapping;
    this.jwtService = jwtService;
  }

  @Override
  public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
    return handlerMapping.getHandler(serverWebExchange)
        .cast(HandlerMethod.class)
        .flatMap(handlerMethod -> {
          JwtAction jwtAction = handlerMethod.getMethodAnnotation(JwtAction.class);
          if (jwtAction == null || jwtAction.value() == null) {
            // Not secured endpoint
            return webFilterChain.filter(serverWebExchange);
          }
          HttpHeaders headers = serverWebExchange.getRequest().getHeaders();
          List<String> authorization = headers.get("Authorization");
          String jwt;
          if (authorization == null || authorization.isEmpty()
              || (jwt = extractJwt(authorization.get(0))) == null) {
            // Authorization header not found
            serverWebExchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            return serverWebExchange.getResponse().setComplete();
          }
          String jwtActionName = jwtAction.value();
          log.info("JwtActionName: '{}', Jwt: '{}'", jwtActionName, jwt);
          boolean isValid = jwtService.validateJwt(jwt, jwtActionName);
          if (!isValid) {
            // Invalid JWT
            serverWebExchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            return serverWebExchange.getResponse().setComplete();
          }
          return webFilterChain.filter(serverWebExchange);
        })
        .switchIfEmpty(Mono.defer(
            () -> serverWebExchange.getResponse().isCommitted()
                ? serverWebExchange.getResponse().setComplete()
                : webFilterChain.filter(serverWebExchange)));
  }

  private String extractJwt(String authorization) {
    if (authorization == null || !authorization.startsWith("Bearer ")) {
      return null;
    }
    return authorization.substring(7);
  }

}
