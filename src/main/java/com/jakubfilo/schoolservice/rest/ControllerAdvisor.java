package com.jakubfilo.schoolservice.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jakubfilo.schoolservice.rest.exception.ServiceErrorRepresentation;
import com.jakubfilo.schoolservice.rest.exception.ServiceException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ControllerAdvisor {

	@ExceptionHandler(ServiceException.class)
	public ResponseEntity<ServiceErrorRepresentation> handleServiceException(ServiceException e) {
		var representation = ServiceErrorRepresentation
				.builder(e)
				.build();

		return ResponseEntity.status(e.getStatusCode()).body(representation);
	}
}
