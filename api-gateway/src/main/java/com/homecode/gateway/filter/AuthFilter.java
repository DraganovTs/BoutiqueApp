package com.homecode.gateway.filter;

import com.homecode.commons.exception.MissingAuthorizationException;
import com.homecode.commons.exception.UnauthorizedAccessException;
import com.homecode.gateway.util.JwtUtil;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    private final RouteValidator validator;
    private final JwtUtil jwtUtil;

    public AuthFilter(RouteValidator validator, JwtUtil jwtUtil) {
        super(Config.class);
        this.validator = validator;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (((exchange, chain) -> {
            ServerHttpRequest userInfo = null;
            if (validator.isSecured.test(exchange.getRequest())) {
                //header contains token or not
                if (!exchange.getRequest()
                        .getHeaders()
                        .containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new MissingAuthorizationException("Authorization header is missing");
                    //todo custom exception
                }
                String authHeaders = exchange
                        .getRequest()
                        .getHeaders()
                        .get(HttpHeaders.AUTHORIZATION)
                        .get(0);
                if (authHeaders != null && authHeaders.startsWith("Bearer ")) {
                    authHeaders = authHeaders.substring(7);
                }
                try {
                    //Rest call to AUTH service but less secured
                    jwtUtil.validateToken(authHeaders);

                    userInfo = exchange.getRequest()
                            .mutate()
                            .header("loggedInUser", jwtUtil.extractUsername(authHeaders))
                            .build();

                }catch (Exception e){
                    //todo Custom exception
                    throw new UnauthorizedAccessException("Not authorized access to application");
                }
            }

            return chain.filter(exchange.mutate().request(userInfo).build());
        }));
    }

    public static class Config {

    }

}
