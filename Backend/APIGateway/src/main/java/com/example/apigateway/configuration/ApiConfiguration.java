package com.example.apigateway.configuration;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;

// @Configuration annotation tells Spring container that there is one or more beans that needs to be dealt with on runtime
@Configuration
public class ApiConfiguration
{
    // RouterLocator : obtains the route information | RouteLocatorBuilder : used to create routes
    @Bean
    public RouteLocator findRoutes(RouteLocatorBuilder builder)
    {
        return builder.routes().
                // Path : the rest end point pattern | URI: the uri at which service is running
                        route(look -> look.path("/auth/**").uri("http://localhost:9000")).
                route(look -> look.path("/todo-app/**").uri("http://localhost:65100")).
                route(look -> look.path("/notification/**").uri("http://localhost:65110")).
                build();
    }
}
