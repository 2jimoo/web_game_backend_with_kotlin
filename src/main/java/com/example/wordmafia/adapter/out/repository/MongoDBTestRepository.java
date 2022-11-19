package com.example.wordmafia.adapter.out.repository;

import com.example.wordmafia.model.TestEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoDBTestRepository extends MongoRepository<TestEntity, String> {
}
