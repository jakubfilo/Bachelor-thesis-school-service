package com.jakubfilo.schoolservice.rest.exception;

import java.util.Set;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class InvalidCourseIdsException extends ServiceException {

	private static final int INVALID_COURSE_IDS_STATUS_CODE = 404;
	private static final String ERROR = "INVALID_COURSE_IDS_EXCEPTION";

	private final Set<String> courseIds;

	@Override
	public int getStatusCode() {
		return INVALID_COURSE_IDS_STATUS_CODE;
	}

	@Override
	public String getMessage() {
		return "Courses with ids " + courseIds + " do not exist";
	}

	@Override
	public String getError() {
		return ERROR;
	}
}
