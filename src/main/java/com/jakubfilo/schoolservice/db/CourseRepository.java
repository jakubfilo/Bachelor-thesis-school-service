package com.jakubfilo.schoolservice.db;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.jakubfilo.schoolservice.db.dbo.CourseDbo;

@Repository
public interface CourseRepository extends MongoRepository<CourseDbo, String> {

	default List<String> getAllIds() {
		return findAll()
				.stream()
				.map(CourseDbo::getId)
				.toList();
	}
}
