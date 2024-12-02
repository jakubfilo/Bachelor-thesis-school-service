package com.jakubfilo.schoolservice.facade;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.jakubfilo.schoolservice.client.PeopleServiceClient;
import com.jakubfilo.schoolservice.client.api.MultipleStudentsDetailRepresentation;
import com.jakubfilo.schoolservice.db.CourseRepository;
import com.jakubfilo.schoolservice.rest.exception.ObjectNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class StudentFacade {

	private final PeopleServiceClient peopleServiceClient;
	private final CourseRepository courseRepository;

	public MultipleStudentsDetailRepresentation getStudentsInCourse(String courseId) {
		var course = courseRepository.findById(courseId);
		if (course.isEmpty()) {
			throw new ObjectNotFoundException(); // TODO once exception is imolemented add more details like course not found
		}

		return getStudentDetailsBatchLookup(course.get().getEnrolledStudentsIds());
	}

	private MultipleStudentsDetailRepresentation getStudentDetailsBatchLookup(Set<String> studentIds) {
		return peopleServiceClient.getStudentsDetailBatchLookup(studentIds);
	}
}
