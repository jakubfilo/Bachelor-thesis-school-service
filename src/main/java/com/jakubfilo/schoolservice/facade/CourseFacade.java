package com.jakubfilo.schoolservice.facade;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.jakubfilo.schoolservice.db.CourseRepository;
import com.jakubfilo.schoolservice.rest.response.CourseDetailRepresentation;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component // TODO check whether this is sufficient or do I need Configuration classes
public class CourseFacade {

	private final CourseRepository courseRepository;

	public List<String> getAllCourseIds() {
		return courseRepository.getAllIds();
	}

	public Optional<CourseDetailRepresentation> getCourseDetail(String courseId) {
		var courseDetailDbo = courseRepository.findById(courseId);
		return courseDetailDbo
				.map(CourseDetailRepresentation::from);
	}
}
