package com.jakubfilo.schoolservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jakubfilo.schoolservice.client.api.StudentsControllerApi;
import com.jakubfilo.schoolservice.client.invoker.ApiClient;

@Configuration
public class ClientConfig {

	@Bean
	public StudentsControllerApi studentsControllerApi(ApiClient apiClient) {
		return new StudentsControllerApi(apiClient);
	}

	@Bean
	public ApiClient apiClient() {
		return new ApiClient().setBasePath("http://localhost:7000");
	}
}
