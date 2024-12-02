package com.jakubfilo.schoolservice.client.api;

import java.util.Set;

public record MultipleStudentsDetailRepresentation(
		Set<StudentDetailRepresentation> students,
		int studentsCount
) {

	public static MultipleStudentsDetailRepresentation empty() {
		return new MultipleStudentsDetailRepresentation(Set.of(), 0);
	}
}
