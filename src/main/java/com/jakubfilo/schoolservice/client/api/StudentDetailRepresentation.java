package com.jakubfilo.schoolservice.client.api;

import java.util.Set;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class StudentDetailRepresentation {

	String id;
	String name;
	String email;
	PhoneNumber phoneNumber;
	float gpa;
	Set<String> courses;
}
