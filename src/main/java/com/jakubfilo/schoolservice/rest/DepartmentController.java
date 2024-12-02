package com.jakubfilo.schoolservice.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.jakubfilo.schoolservice.api.DepartmentControllerApi;
import com.jakubfilo.schoolservice.api.model.DepartmentDetailApi;
import com.jakubfilo.schoolservice.api.model.DepartmentHeadDetailApi;
import com.jakubfilo.schoolservice.api.model.DepartmentListApi;
import com.jakubfilo.schoolservice.api.model.DepartmentTypeApi;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class DepartmentController implements DepartmentControllerApi {

	@Override
	public ResponseEntity<DepartmentDetailApi> getAllDepartmentDetails(DepartmentTypeApi department) {
		return null;
	}

	@Override
	public ResponseEntity<DepartmentListApi> getAllDepartments() {
		return null;
	}

	@Override
	public ResponseEntity<DepartmentHeadDetailApi> getDepartmentHead(DepartmentTypeApi department) {
		return null;
	}
}
