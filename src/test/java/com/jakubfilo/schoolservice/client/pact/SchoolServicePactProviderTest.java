package com.jakubfilo.schoolservice.client.pact;

import static org.mockito.Mockito.when;

import java.time.DayOfWeek;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.jakubfilo.schoolservice.domain.CourseTime;
import com.jakubfilo.schoolservice.domain.DepartmentType;
import com.jakubfilo.schoolservice.domain.Term;
import com.jakubfilo.schoolservice.facade.CourseFacade;
import com.jakubfilo.schoolservice.facade.StudentFacade;
import com.jakubfilo.schoolservice.rest.CourseController;
import com.jakubfilo.schoolservice.rest.ExternalCourseController;
import com.jakubfilo.schoolservice.rest.response.CourseDetailRepresentation;
import com.jakubfilo.schoolservice.rest.response.CourseTimetableDetail;

import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junitsupport.Consumer;
import au.com.dius.pact.provider.junitsupport.IgnoreMissingStateChange;
import au.com.dius.pact.provider.junitsupport.IgnoreNoPactsToVerify;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.PactBrokerAuth;
import au.com.dius.pact.provider.spring.junit5.MockMvcTestTarget;
import au.com.dius.pact.provider.spring.junit5.PactVerificationSpringProvider;
import jakarta.inject.Inject;

@Provider(PactConstants.SCHOOL_SERVICE_COMPONENT_NAME)
@Consumer(PactConstants.PEOPLE_SERVICE_COMPONENT_NAME)
@IgnoreNoPactsToVerify
//@IgnoreMissingStateChange
@PactBroker(url = "${pactbroker.url:https://pact-broker-app.gentlefield-825c3ee5.polandcentral.azurecontainerapps.io}",
		authentication = @PactBrokerAuth(username = "${pactbroker.auth.username}", password = "${pactbroker.auth.password}"))
@WebMvcTest(
		controllers = {
				CourseController.class,
				ExternalCourseController.class
		}
)
@AutoConfigureMockMvc(addFilters = false)
/*
 you need to add environemnt variables into junit configuration

 In IntelliJ, right-click your test class → Run 'PeopleServicePactProviderTest'
 It will create a temporary run configuration.
 Open Run → Edit Configurations…
 Find your JUnit test configuration
 Add environment variables:
 */
public class SchoolServicePactProviderTest {

	@Inject
	private MockMvc mockMvc;

	@MockitoBean
	private CourseFacade courseFacade;
	@MockitoBean
	private StudentFacade studentFacade;

	@BeforeEach
	void setUp(PactVerificationContext context) {
		if (context != null) {
			context.setTarget(new MockMvcTestTarget(mockMvc));
		}
	}

	@TestTemplate
	@ExtendWith(PactVerificationSpringProvider.class)
	void verifyPact(PactVerificationContext context) {
		if (context != null) {
			context.verifyInteraction();
		}
	}

	@State("Courses with details exist")
	void getCoursesWithDetails() {
		when(courseFacade.getCourseDetailsBatch(Set.of("COURSE1", "COURSE2"))).thenReturn(
				Set.of(
						CourseDetailRepresentation.builder()
								.id("COURSE1")
								.courseName("Mathematics")
								.courseCode("MATH101")
								.description("An introductory course to Mathematics.")
								.departmentId("DEPT1")
								.departmentType(DepartmentType.MATHEMATICS)
								.credits(5)
								.teacherId("TEACHER1")
								.term(Term.WINTER)
								.startYear(2024)
								.enrolledStudentsIds(Set.of("STUDENT1"))
								.enrolledStudentsCount(1)
								.courseTime(new CourseTime(DayOfWeek.MONDAY, 8, 30, 10, 0))
								.roomId("ROOM101")
								.build(),
						CourseDetailRepresentation.builder()
								.id("COURSE2")
								.courseName("Engineering")
								.courseCode("ENG101")
								.description("An introductory course to Engineering.")
								.departmentId("DEPT2")
								.departmentType(DepartmentType.MECHANICAL_ENGINEERING)
								.credits(10)
								.teacherId("TEACHER42")
								.term(Term.SUMMER)
								.startYear(2024)
								.enrolledStudentsIds(Set.of("STUDENT31"))
								.enrolledStudentsCount(1)
								.courseTime(new CourseTime(DayOfWeek.MONDAY, 8, 30, 10, 0))
								.roomId("ROOM101")
								.build()
				)
		);
	}

	@State("Courses with details do not exist")
	void getCoursesDetailBatchLookupEmptyResponse() {
		when(courseFacade.getCourseDetailsBatch(Set.of("COURSE1", "COURSE2")))
				.thenReturn(Collections.emptySet());
	}

	@State("Not all courses with details exist")
	void getCoursesDetailBatchLookupIncompletePact() {
		when(courseFacade.getCourseDetailsBatch(Set.of("COURSE1", "COURSE2")))
				.thenReturn(
						Set.of(
								CourseDetailRepresentation.builder()
										.id("COURSE1")
										.courseName("Mathematics")
										.courseCode("MATH101")
										.description("An introductory course to Mathematics.")
										.departmentId("DEPT1")
										.departmentType(DepartmentType.MATHEMATICS)
										.credits(5)
										.teacherId("TEACHER1")
										.term(Term.WINTER)
										.startYear(2024)
										.enrolledStudentsIds(Set.of("STUDENT1"))
										.enrolledStudentsCount(1)
										.courseTime(new CourseTime(DayOfWeek.MONDAY, 8, 30, 10, 0))
										.roomId("ROOM101")
										.build()
						)
				);
	}

	@State("Course timetables exist")
	void getCourseTimetablesBatchTest() {
		var expectedCourseTimeDetail = CourseTimetableDetail.builder()
				.courseId("COURSE1")
				.courseTime(new CourseTime(DayOfWeek.MONDAY, 8, 30, 10, 0))
				.roomId("ROOM101")
				.build();

		when(courseFacade.getCourseTimeDetail("COURSE1"))
				.thenReturn(Optional.of(expectedCourseTimeDetail));
		when(courseFacade.getCourseTimesBatch(Set.of("COURSE1")))
				.thenReturn(Set.of(expectedCourseTimeDetail));
	}

	@State("All selected courses exist")
	void enrollStudentToCoursesTest() {
		when(courseFacade.enrollStudentInCourses("STUDENT1", Set.of("COURSE1", "COURSE2")))
				.thenReturn(Set.of("COURSE1", "COURSE2"));
	}

	@State("Some of selected courses do not exist")
	void enrollStudentToCoursesWhichSomeDoNotExistTest() {
		when(courseFacade.enrollStudentInCourses("STUDENT1", Set.of("COURSE1", "COURSE2")))
				.thenReturn(Set.of("COURSE1"));
	}

	@State("None of selected courses exists")
	void enrollStudentToCoursesWhichAllDoNotExistTest() {
		when(courseFacade.enrollStudentInCourses("STUDENT1", Set.of("COURSE1", "COURSE2")))
				.thenReturn(Collections.emptySet());
	}



}
