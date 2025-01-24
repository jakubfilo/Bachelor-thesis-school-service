package com.jakubfilo.schoolservice.rest.response;

import com.jakubfilo.schoolservice.domain.CourseTime;

public record CourseTimetableDetail(String courseId, CourseTime courseTime, String roomId) {

}
