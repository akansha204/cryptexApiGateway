package com.cryptex.apigateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtHeaderFilter implements GlobalFilter{

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        return exchange.getPrincipal()
                .filter(principal -> principal instanceof JwtAuthenticationToken)
                .cast(JwtAuthenticationToken.class)
                .flatMap(jwtAuth -> {
//                    System.out.println("JWT SUBJECT: " + jwtAuth.getToken().getSubject());
//                    System.out.println("JWT CLAIMS: " + jwtAuth.getToken().getClaims());
                    String userId = jwtAuth.getToken().getSubject();
                    String email  = jwtAuth.getToken().getClaimAsString("email");

                    ServerHttpRequest mutatedRequest = exchange.getRequest()
                            .mutate()
                            .header("X-User-Id", userId)
                            .header("X-User-Email", email)
                            .header("X-Gateway-Source", "cryptex-gateway")
                            .build();

                    ServerWebExchange mutatedExchange =
                            exchange.mutate().request(mutatedRequest).build();

                    return chain.filter(mutatedExchange);
                })
                .switchIfEmpty(chain.filter(exchange));
    }
}
