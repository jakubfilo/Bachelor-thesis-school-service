package com.jakubfilo.schoolservice.client.api;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PhoneNumber {

	String number;
	PhoneNumberCountryCode country;
}
