package com.jakubfilo.schoolservice.rest;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.jakubfilo.schoolservice.api.ExternalCourseControllerApi;
import com.jakubfilo.schoolservice.api.model.CourseDetailApi;
import com.jakubfilo.schoolservice.api.model.EnrollStudentInCoursesResponseApi;
import com.jakubfilo.schoolservice.facade.CourseFacade;
import com.jakubfilo.schoolservice.mapper.CourseMapper;
import com.jakubfilo.schoolservice.rest.response.EnrollStudentInCoursesResponse;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ExternalCourseController implements ExternalCourseControllerApi { // TODO potentially better name

	private static final CourseMapper COURSE_MAPPER = CourseMapper.INSTANCE;
	private final CourseFacade courseFacade;

	@Override
	public ResponseEntity<EnrollStudentInCoursesResponseApi> enrollStudentInCourses(String studentId, Set<String> courseIds) {
		var enrolledCoursesIds = courseFacade.enrollStudentInCourses(studentId, courseIds);
		var enrollStudentResponse = EnrollStudentInCoursesResponse.builder()
				.studentId(studentId)
				.enrolledCourses(enrolledCoursesIds)
				.build();

		var response = COURSE_MAPPER.map(enrollStudentResponse);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(response);
	}

	@Override
	public ResponseEntity<Set<CourseDetailApi>> getCourseDetailsBatch(Set<String> courseIds) {
		var courseDetails = courseFacade.getCourseDetailsBatch(courseIds);
		var courseDetailsApi = courseDetails.stream()
				.map(COURSE_MAPPER::map)
				.collect(Collectors.toSet());

		if (courseDetails.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		if (courseDetails.size() != courseIds.size()) {
			return ResponseEntity
					.status(CustomStatusCodes.BATCH_LOOKUP_INCOMPLETE.getStatusCode())
					.body(courseDetailsApi);
		}

		return ResponseEntity.ok(courseDetailsApi);
	}

	@RequiredArgsConstructor
	@Getter
	private enum CustomStatusCodes {
		BATCH_LOOKUP_INCOMPLETE(206);

		private final int statusCode;
	}
}
