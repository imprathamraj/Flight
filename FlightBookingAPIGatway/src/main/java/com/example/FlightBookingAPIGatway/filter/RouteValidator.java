package com.example.FlightBookingAPIGatway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String>openAPIEndpoints= List.of(
            "/auth/register",
            "/auth/login",
            "/auth/validate"
    );
    public Predicate<ServerHttpRequest> isSecured=
            request -> openAPIEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
