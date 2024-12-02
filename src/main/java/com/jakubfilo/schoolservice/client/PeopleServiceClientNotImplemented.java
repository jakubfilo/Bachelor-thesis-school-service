package com.jakubfilo.schoolservice.client;

import static com.jakubfilo.schoolservice.client.PeopleServiceClientNotImplemented.CLIENT_NAME;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.jakubfilo.schoolservice.client.api.MultipleStudentsDetailRepresentation;

@FeignClient(
		name = CLIENT_NAME,
		url = "${clientsConfiguration.peopleService.host}")
public interface PeopleServiceClientNotImplemented {

	String CLIENT_NAME = "people-service-not-implemented";
	Logger LOGGER = LoggerFactory.getLogger(PeopleServiceClientNotImplemented.class);

	String STUDENTS_DETAIL_BATCH_LOOKUP_RESOURCE_NOT_IMPLEMENTED = "/students/detail/not-implemented";

	default MultipleStudentsDetailRepresentation getStudentsDetailBatchLookupNotImplemented(Set<String> studentIds) {
		var responseEntity = getStudentsDetailBatchLookupNotImplementedResponse(studentIds);

		if (responseEntity.getStatusCode() == HttpStatus.NOT_IMPLEMENTED) {
			LOGGER.warn("Resource in People service is not implemented yet, returning fallback - empty student details");
			return MultipleStudentsDetailRepresentation.empty();
		}

		return responseEntity.getBody();
	}

	@GetMapping(STUDENTS_DETAIL_BATCH_LOOKUP_RESOURCE_NOT_IMPLEMENTED)
	ResponseEntity<MultipleStudentsDetailRepresentation> getStudentsDetailBatchLookupNotImplementedResponse(Set<String> studentIds);

}
