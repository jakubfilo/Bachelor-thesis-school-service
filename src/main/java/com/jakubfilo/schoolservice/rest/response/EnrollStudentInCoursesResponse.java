package com.jakubfilo.schoolservice.rest.response;

import java.util.Set;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class EnrollStudentInCoursesResponse {

	@NotNull
	String studentId;
	@Builder.Default
	Set<String> enrolledCourses = Set.of();
}
