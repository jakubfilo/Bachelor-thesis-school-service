package com.jakubfilo.schoolservice.db;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.jakubfilo.schoolservice.db.dbo.DepartmentDbo;

@Repository
public interface DepartmentRepository extends MongoRepository<DepartmentDbo, String> {
}
