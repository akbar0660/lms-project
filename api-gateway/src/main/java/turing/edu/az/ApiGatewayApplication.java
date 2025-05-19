package turing.edu.az;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import turing.edu.az.filter.JwtAuthenticationFilter;

import java.util.Collections;

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
                        .filters(f -> f.filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config())))
                        .uri("lb://MS-STUDENT"))
                .route("ms-notification", r -> r
                        .path("/api/v1/notification/**")
                        .uri("lb://MS-NOTIFICATION"))
                //for swagger
                .route("swagger-ms-auth", r -> r
                        .path("/ms-auth/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://MS-AUTH"))
                .route("swagger-ms-student", r -> r
                        .path("/ms-student/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://MS-STUDENT"))
                .build();
    }

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(Collections.singletonList("*"));
        corsConfig.setMaxAge(3600L);
        corsConfig.addAllowedMethod("*");
        corsConfig.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }
}