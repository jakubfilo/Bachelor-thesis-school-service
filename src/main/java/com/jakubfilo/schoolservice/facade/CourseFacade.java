package com.jakubfilo.schoolservice.facade;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.jakubfilo.schoolservice.client.PeopleServiceClient;
import com.jakubfilo.schoolservice.db.CourseRepository;
import com.jakubfilo.schoolservice.rest.response.CourseDetailRepresentation;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component // TODO check whether this is sufficient or do I need Configuration classes
public class CourseFacade {

	private final CourseRepository courseRepository;
	private final PeopleServiceClient peopleServiceClient;

	public List<String> getAllCourseIds() {
		return courseRepository.getAllIds();
	}

	public Optional<CourseDetailRepresentation> getCourseDetail(String courseId) {
		var courseDetailDbo = courseRepository.findById(courseId);
		return courseDetailDbo
				.map(CourseDetailRepresentation::from);
	}

	public Set<CourseDetailRepresentation> getCourseDetailsBatch(Set<String> courseIds) {
		return courseIds.stream()
				.map(this::getCourseDetail)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toSet());
	}
}
