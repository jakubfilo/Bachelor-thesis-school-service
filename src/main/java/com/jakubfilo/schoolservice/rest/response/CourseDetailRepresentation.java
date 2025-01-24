package com.jakubfilo.schoolservice.rest.response;

import java.util.Set;

import com.jakubfilo.schoolservice.db.dbo.CourseDbo;
import com.jakubfilo.schoolservice.domain.CourseTime;
import com.jakubfilo.schoolservice.domain.DepartmentType;
import com.jakubfilo.schoolservice.domain.Term;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CourseDetailRepresentation {

	private String id;
	private String courseName;
	private String code;
	private String description;
	private String departmentId;
	private DepartmentType departmentType;
	private String teacherId;
	private Term term;
	@Min(1993)
	@Max(2100)
	private int startYear;
	private Set<String> enrolledStudentsIds;
	private int enrolledStudentsCount;
	private CourseTime courseTime;
	private String roomId;

	public static CourseDetailRepresentation from(CourseDbo courseDbo) {
		return CourseDetailRepresentation.builder()
				.id(courseDbo.getId())
				.courseName(courseDbo.getCourseName())
				.code(courseDbo.getCode())
				.description(courseDbo.getDescription())
				.departmentId(courseDbo.getDepartmentId())
				.departmentType(courseDbo.getDepartmentType())
				.teacherId(courseDbo.getTeacherId())
				.term(courseDbo.getTerm())
				.startYear(courseDbo.getStartYear())
				.enrolledStudentsIds(courseDbo.getEnrolledStudentsIds())
				.enrolledStudentsCount(courseDbo.getEnrolledStudentsCount())
				.courseTime(CourseTime.courseTimeForCourse(courseDbo))
				.roomId(courseDbo.getRoomId())
				.build();
	}
}
