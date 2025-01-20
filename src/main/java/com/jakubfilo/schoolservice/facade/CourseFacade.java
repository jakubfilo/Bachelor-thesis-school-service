package com.jakubfilo.schoolservice.facade;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.jakubfilo.schoolservice.client.PeopleServiceClient;
import com.jakubfilo.schoolservice.db.CourseRepository;
import com.jakubfilo.schoolservice.db.dbo.CourseDbo;
import com.jakubfilo.schoolservice.rest.response.CourseDetailRepresentation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
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

	public Set<String> enrollStudentInCourses(String studentId, Set<String> courseIds) {
		var enrolledCoursesIds = courseIds.stream()
				.map(courseId -> enrollStudentInCourse(studentId, courseId))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.map(CourseDbo::getId)
				.collect(Collectors.toSet());

		LOGGER.info("Enrolled student {} in courses {}", studentId, enrolledCoursesIds);
		return enrolledCoursesIds;
	}

	private Optional<CourseDbo> enrollStudentInCourse(String studentId, String courseId) {
		return courseRepository.findById(courseId)
				.map(courseDbo -> {
					var enrolledStudentsIds = courseDbo.getEnrolledStudentsIds();
					enrolledStudentsIds.add(studentId);

					courseDbo.toBuilder()
							.enrolledStudentsIds(enrolledStudentsIds)
							.enrolledStudentsCount(courseDbo.getEnrolledStudentsCount() + 1)
							.build();
					return Optional.of(courseRepository.save(courseDbo));
				})
				.orElseGet(Optional::empty);
	}
}
