package com.jakubfilo.schoolservice.db.dbo;

import java.util.List;

import org.springframework.data.annotation.Id;

import com.jakubfilo.schoolservice.domain.DepartmentType;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class DepartmentDbo {

	@Id
	String id;
	String departmentName;
	DepartmentType departmentType;
	String departmentHeadId;
	@Builder.Default
	List<String> coursesIds = List.of();
	@Builder.Default
	List<String> employeesIds = List.of();
}
