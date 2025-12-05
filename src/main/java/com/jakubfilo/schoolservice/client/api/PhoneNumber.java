package com.jakubfilo.schoolservice.client.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class PhoneNumber {

	String number;
	String countryCode;
}
