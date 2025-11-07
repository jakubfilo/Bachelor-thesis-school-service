package com.jakubfilo.schoolservice.client.api.notification;

import lombok.Data;

@Data
public class NotificationDto {

	private String studentId;
	private String courseId;
	private String type;
	private String message;
}
