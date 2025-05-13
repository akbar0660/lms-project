package turing.edu.az;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import turing.edu.az.filter.JwtAuthenticationFilter;

@SpringBootApplication
@RequiredArgsConstructor
public class ApiGatewayApplication {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }


    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("ms-auth", r -> r
                            .path("/api/v1/auth/**")
                        .uri("lb://MS-AUTH"))
                .route("ms-student", r -> r
                        .path("/api/v1/student/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config()))) // This is where it might be causing the issue
                        .uri("lb://MS-STUDENT"))
                .build();
    }
}