package com.jakubfilo.schoolservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.jakubfilo.schoolservice.api.model.StudentsInCourseApi;
import com.jakubfilo.schoolservice.client.api.MultipleStudentsDetailRepresentation;

@Mapper
public interface StudentMapper {

	public static final StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

	StudentsInCourseApi map(MultipleStudentsDetailRepresentation multipleStudentsDetailRepresentation);
}
