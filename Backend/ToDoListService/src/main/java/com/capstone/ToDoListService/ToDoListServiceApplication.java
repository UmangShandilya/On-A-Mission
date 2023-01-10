package com.capstone.ToDoListService;

import com.capstone.ToDoListService.filter.JWTFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
@EnableEurekaClient
public class ToDoListServiceApplication {

	public static void main(String[] args)
	{
		SpringApplication.run(ToDoListServiceApplication.class, args);
	}
	@Bean
	public FilterRegistrationBean filteringJWT()
	{
		// Returns list of intercepted URLs with defined JWT Filter class
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new JWTFilter());
		filterRegistrationBean.addUrlPatterns(
				"/todo-list/{email}",
				"/user-name/{email}",
				"/user-name/update/{email}",
				"/update-task/{email}/{taskName}",
				"/delete/{email}/{taskName}",
				"/fetch-task/{email}",
				"/task-completed/{email}",
				"/task-unfinished/{email}",
				"/completed/{email}/{taskName}",
				"/priority/{email}/{choosePriority}"
		);

		return filterRegistrationBean;
	}

}
