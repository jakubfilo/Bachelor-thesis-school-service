package com.jakubfilo.schoolservice.domain;

import java.time.DayOfWeek;
import java.time.LocalTime;

import com.jakubfilo.schoolservice.db.dbo.CourseDbo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CourseTime {

	private DayOfWeek dayOfWeek;
	private int startHour;
	private int startMinute;
	private int endHour;
	private int endMinute;

	public CourseTime(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
		this.dayOfWeek = dayOfWeek;
		this.startHour = startTime.getHour();
		this.startMinute = startTime.getMinute();
		this.endHour = endTime.getHour();
		this.endMinute = endTime.getMinute();
	}

	public static CourseTime courseTimeForCourse(CourseDbo courseDbo) {
		return new CourseTime(courseDbo.getDayOfWeek(), courseDbo.getStartTime(), courseDbo.getEndTime());
	}
}
