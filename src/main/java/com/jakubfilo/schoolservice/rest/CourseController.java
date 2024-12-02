package com.jakubfilo.schoolservice.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.jakubfilo.schoolservice.api.CourseControllerApi;
import com.jakubfilo.schoolservice.api.model.CourseDetailApi;
import com.jakubfilo.schoolservice.api.model.CourseDetailBriefApi;
import com.jakubfilo.schoolservice.api.model.DepartmentTypeApi;
import com.jakubfilo.schoolservice.api.model.MultipleCourseDetailApi;
import com.jakubfilo.schoolservice.api.model.MultipleCourseDetailBriefApi;
import com.jakubfilo.schoolservice.api.model.StudentsInCourseApi;
import com.jakubfilo.schoolservice.api.model.TermApi;
import com.jakubfilo.schoolservice.facade.CourseFacade;
import com.jakubfilo.schoolservice.facade.StudentFacade;
import com.jakubfilo.schoolservice.mapper.CourseMapper;
import com.jakubfilo.schoolservice.mapper.StudentMapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class CourseController implements CourseControllerApi {

	private static final CourseMapper COURSE_MAPPER = CourseMapper.INSTANCE;
	private static final StudentMapper STUDENT_MAPPER = StudentMapper.INSTANCE;

	private final CourseFacade courseFacade;
	private final StudentFacade studentFacade;

	@Override
	public ResponseEntity<List<String>> getAllCoursesIds() {
		var courseIdsList = courseFacade.getAllCourseIds();
		return ResponseEntity.ok(courseIdsList);
	}

	@Override
	public ResponseEntity<CourseDetailApi> getCourseDetail(String courseId) {
		var courseDetail = courseFacade.getCourseDetail(courseId);

		return courseDetail.isPresent()
				? ResponseEntity.ok(COURSE_MAPPER.map(courseDetail.get()))
				: ResponseEntity.notFound().build();
	}


	@Override
	public ResponseEntity<CourseDetailBriefApi> getCourseDetailBrief(String courseId) {
		return null;
	}

	@Override
	public ResponseEntity<StudentsInCourseApi> getCourseStudents(String courseId) {
		var studentDetails = studentFacade.getStudentsInCourse(courseId);
		return ResponseEntity.ok(STUDENT_MAPPER.map(studentDetails));
	}

	@Override
	public ResponseEntity<List<CourseDetailBriefApi>> getAllAvailableCourses(DepartmentTypeApi department, TermApi term, Integer year,
			Integer credits) {
		return null;
	}

	@Override
	public ResponseEntity<MultipleCourseDetailApi> getAllCoursesDetails() {
		return null;
	}

	@Override
	public ResponseEntity<MultipleCourseDetailBriefApi> getAllCoursesDetailsBrief() {
		return null;
	}
}
