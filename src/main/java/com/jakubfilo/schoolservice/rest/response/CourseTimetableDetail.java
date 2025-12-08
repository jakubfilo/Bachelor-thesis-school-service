package com.jakubfilo.schoolservice.rest.response;

import com.jakubfilo.schoolservice.domain.CourseTime;

import lombok.Builder;

@Builder
public record CourseTimetableDetail(String courseId, CourseTime courseTime, String roomId) {

}
