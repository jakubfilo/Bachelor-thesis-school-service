package com.jakubfilo.schoolservice.client.pact;

import static au.com.dius.pact.consumer.dsl.LambdaDsl.newJsonArrayUnordered;
import static au.com.dius.pact.consumer.dsl.LambdaDsl.newJsonBody;
import static com.jakubfilo.schoolservice.client.pact.PactConstants.PEOPLE_SERVICE_COMPONENT_NAME;
import static com.jakubfilo.schoolservice.client.pact.PactConstants.SCHOOL_SERVICE_COMPONENT_NAME;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.jakubfilo.schoolservice.client.PeopleServiceClient;
import com.jakubfilo.schoolservice.client.api.PhoneNumber;
import com.jakubfilo.schoolservice.client.api.StudentDetailRepresentation;
import com.jakubfilo.schoolservice.config.ClientConfig;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit.MockServerConfig;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.provider.junitsupport.Consumer;
import jakarta.inject.Inject;

@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.NONE,
		classes = {
				ClientConfig.class, PeopleServiceClient.class
		}
)
@Consumer(SCHOOL_SERVICE_COMPONENT_NAME)
@MockServerConfig(port = "7000")
@ExtendWith({ MockitoExtension.class, PactConsumerTestExt.class })
public class PeopleServiceClientPactTest {

	@Inject
	private PeopleServiceClient peopleServiceClient;

	@Pact(consumer = SCHOOL_SERVICE_COMPONENT_NAME, provider = PEOPLE_SERVICE_COMPONENT_NAME)
	V4Pact getStudentDetailBatchLookup(PactDslWithProvider builder) {
		return builder
				.given("People exist with provided IDs")
				.uponReceiving("A request for getting student details by a batch of person IDs")
				.path("/students/detail/batch-lookup")
				.matchQuery("ids", "[A-z0-9]+(,[A-z0-9]+)*", "STUDENT1,STUDENT2")
				.method("GET")
				.willRespondWith()
				.body(studentsDetailBatchLookupResponseBody())
				.status(HttpStatus.OK.value())
				.toPact(V4Pact.class);
	}

	@Test
	@PactTestFor(pactMethod = "getStudentDetailBatchLookup", pactVersion = PactSpecVersion.V4)
	void getStudentDetailBatchLookupPactTest() {
		var response = peopleServiceClient.getStudentsDetailBatchLookup(Set.of("STUDENT1", "STUDENT2"));

		var expectedStudent1 = StudentDetailRepresentation.builder()
				.id("STUDENT1")
				.name("Maria Perez Fernandez Muntez")
				.email("student1@muni.cz")
				.gpa(3.75f)
				.phoneNumber(new PhoneNumber("777123456", "+420"))
				.courses(Set.of("COURSE1", "COURSE2"))
				.build();
		var expectedStudent2 = StudentDetailRepresentation.builder()
				.id("STUDENT2")
				.name("Mario Rodrigo")
				.email("student2@gmail.com")
				.gpa(2.2f)
				.phoneNumber(new PhoneNumber("944035978", "+421"))
				.courses(Set.of("COURSE1", "COURSE42"))
				.build();

		// here the values must match the ones provided in the pact DSL, but in the provider, it only checks their type and regex if there is one

		assertThat(response).isNotNull();
		assertThat(response.studentsCount()).isEqualTo(2);
		assertThat(response.students()).hasSize(2);
		assertThat(response.students()).containsAll(Set.of(expectedStudent2, expectedStudent1));
	}

	private static DslPart studentsDetailBatchLookupResponseBody() {
		return newJsonBody(response -> {
			response.unorderedArray("students", studentsArray -> {
				studentsArray.object(student -> {
					student.stringMatcher("id", "^[A-Za-z0-9]+$", "STUDENT1");
					student.stringMatcher("name", "^[A-Za-z]+( [A-Za-z]+)+$", "Maria Perez Fernandez Muntez");
					student.stringMatcher("email", "^[^@\\s]+@[^@\\s]+\\.[A-z]+$", "student1@muni.cz");
					student.object("phoneNumber", phone -> {
						phone.stringMatcher("countryCode", "^\\+?[0-9]{1,3}$", "+420");
						phone.stringMatcher("number", "^[0-9]{3,15}$", "777123456");
					});
					student.numberType("gpa", 3.75);
					student.unorderedArray("courses", courses -> {
						courses.stringType("COURSE1");
						courses.stringType("COURSE2");
					});
				});
				studentsArray.object(student -> {
					student.stringMatcher("id", "^[A-Za-z0-9]+$", "STUDENT2");
					student.stringMatcher("name", "^[A-Za-z]+( [A-Za-z]+)+$", "Mario Rodrigo");
					student.stringMatcher("email", "^[^@\\s]+@[^@\\s]+\\.[A-z]+$", "student2@gmail.com");
					student.object("phoneNumber", phone -> {
						phone.stringMatcher("countryCode", "^\\+?[0-9]{1,3}$", "+421");
						phone.stringMatcher("number", "^[0-9]{3,15}$", "944035978");
					});
					student.numberType("gpa", 2.2);
					student.unorderedArray("courses", courses -> {
						courses.stringType("COURSE1");
						courses.stringType("COURSE42");
					});
				});
			});
			response.integerType("studentsCount", 2);
		}).build();
	}
}
