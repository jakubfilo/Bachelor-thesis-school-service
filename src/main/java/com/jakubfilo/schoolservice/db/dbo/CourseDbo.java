package com.jakubfilo.schoolservice.db.dbo;

import java.util.Set;

import org.springframework.data.annotation.Id;

import com.jakubfilo.schoolservice.domain.DepartmentType;
import com.jakubfilo.schoolservice.domain.Term;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder(toBuilder = true)
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
	Set<String> enrolledStudentsIds = Set.of();
	@Builder.Default
	int enrolledStudentsCount = 0;
	// TODO add more info, for example room, day of week, time
}
