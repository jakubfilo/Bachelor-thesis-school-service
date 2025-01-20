package com.jakubfilo.schoolservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.jakubfilo.schoolservice.api.model.CourseDetailApi;
import com.jakubfilo.schoolservice.api.model.EnrollStudentInCoursesResponseApi;
import com.jakubfilo.schoolservice.rest.response.CourseDetailRepresentation;
import com.jakubfilo.schoolservice.rest.response.EnrollStudentInCoursesResponse;

@Mapper
public interface CourseMapper {

	CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);

	CourseDetailApi map(CourseDetailRepresentation courseDetailRepresentation);

	EnrollStudentInCoursesResponseApi map(EnrollStudentInCoursesResponse enrollStudentResponse);
}
