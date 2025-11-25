package com.jakubfilo.schoolservice.client;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.jakubfilo.schoolservice.client.api.MultipleStudentsDetailRepresentation;
import com.jakubfilo.schoolservice.client.api.StudentApiMapper;
import com.jakubfilo.schoolservice.client.api.StudentsControllerApi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class PeopleServiceClient {

	private final StudentsControllerApi studentsControllerApi;
	private final StudentApiMapper studentApiMapper = StudentApiMapper.INSTANCE;

	public MultipleStudentsDetailRepresentation getStudentsDetailBatchLookup(Set<String> studentIds) {
		var apiResponse = studentsControllerApi.getStudentDetailsBatchLookupWithHttpInfo(studentIds);
		if (apiResponse.getStatusCode() == HttpStatus.OK.value()) {
			return studentApiMapper.map(apiResponse.getData());
		} else {
			LOGGER.info("Exception during getStudentsDetailBatchLookup('{}'), response code '{}', returning empty", studentIds, apiResponse.getStatusCode());
			return MultipleStudentsDetailRepresentation.empty();
		}
	}


}
