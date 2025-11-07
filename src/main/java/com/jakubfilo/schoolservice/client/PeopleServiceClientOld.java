package com.jakubfilo.schoolservice.client;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jakubfilo.schoolservice.client.api.MultipleStudentsDetailRepresentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class PeopleServiceClientOld {

	public static final String CLIENT_NAME = "people-service";
	private final PeopleServiceClientApi peopleServiceClientApi;

	public MultipleStudentsDetailRepresentation getStudentsDetailBatchLookup(Set<String> studentIds) {
		try {
			return getStudentsDetailBatchLookupAsync(studentIds).get();
		} catch (Exception e) {
			LOGGER.info("Exception during getStudentsDetailBatchLookup('{}'), returning empty", studentIds, e);
			return MultipleStudentsDetailRepresentation.empty();
		}
	}

	private CompletableFuture<MultipleStudentsDetailRepresentation> getStudentsDetailBatchLookupAsync(Set<String> studentIds) {
		return CompletableFuture.supplyAsync(() -> peopleServiceClientApi.getStudentsDetailBatchLookup(studentIds));
	}

	@FeignClient(
			name = CLIENT_NAME,
			url = "${clientsConfiguration.peopleService.host}",
			dismiss404 = true
	)
	public interface PeopleServiceClientApi {

		String STUDENTS_DETAIL_BATCH_LOOKUP_RESOURCE = "/students/detail/batch-lookup";

		@GetMapping(STUDENTS_DETAIL_BATCH_LOOKUP_RESOURCE)
		MultipleStudentsDetailRepresentation getStudentsDetailBatchLookup(@RequestParam(name = "ids") Set<String> studentIds);
	}
}
