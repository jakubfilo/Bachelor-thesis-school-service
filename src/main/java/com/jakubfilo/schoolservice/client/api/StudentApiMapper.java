package com.jakubfilo.schoolservice.client.api;

import static org.mapstruct.factory.Mappers.*;

import org.mapstruct.Mapper;

@Mapper
public interface StudentApiMapper {

	StudentApiMapper INSTANCE = getMapper(StudentApiMapper.class);

	MultipleStudentsDetailRepresentation map(com.jakubfilo.schoolservice.client.model.MultipleStudentsDetailRepresentation
			multipleStudentsDetailRepresentation);
}
