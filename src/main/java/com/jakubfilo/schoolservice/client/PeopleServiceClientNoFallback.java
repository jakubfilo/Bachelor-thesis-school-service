package com.jakubfilo.schoolservice.client;

import static com.jakubfilo.schoolservice.client.PeopleServiceClientNoFallback.CLIENT_NAME;

import java.util.Set;

import org.springframework.cloud.openfeign.FeignClient;

import com.jakubfilo.schoolservice.client.api.MultipleStudentsDetailRepresentation;

@FeignClient(
		name = CLIENT_NAME,
		url = "${clientsConfiguration.peopleService.host}")
public interface PeopleServiceClientNoFallback {

	String CLIENT_NAME = "people-service-no-fallback";

	String STUDENTS_DETAIL_BATCH_LOOKUP_RESOURCE_WRONG_PATH = "/students/detail/wrong-path";

	MultipleStudentsDetailRepresentation getStudentsDetailBatchLookupWrongPath(Set<String> studentIds);

}
