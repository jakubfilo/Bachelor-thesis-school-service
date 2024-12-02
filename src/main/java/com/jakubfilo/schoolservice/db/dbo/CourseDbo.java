package com.jakubfilo.schoolservice.db.dbo;

import java.util.List;

import org.springframework.data.annotation.Id;

import com.jakubfilo.schoolservice.domain.DepartmentType;
import com.jakubfilo.schoolservice.domain.Term;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class CourseDbo {

	@Id
	String id;
	String courseName;
	String code;
	String description;
	String departmentId;
	DepartmentType departmentType;
	String teacherId;
	Term term;
	int startYear;
	@Builder.Default
	List<String> enrolledStudentsIds = List.of();
	private int enrolledStudentsCount;
	// TODO add more info, for example room, day of week, time
}
