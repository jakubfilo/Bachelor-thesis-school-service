package com.jakubfilo.schoolservice.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jakubfilo.schoolservice.client.PeopleServiceClientOld;
import com.jakubfilo.schoolservice.client.PeopleServiceClientOld.PeopleServiceClientApi;

@Configuration
@EnableFeignClients(basePackages = "com.jakubfilo.schoolservice.client")
public class FeignClientConfiguration {

	@Bean
	public PeopleServiceClientOld peopleServiceClient(PeopleServiceClientApi peopleServiceClientApi) {
		return new PeopleServiceClientOld(peopleServiceClientApi);
	}
}
