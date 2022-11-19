package com.example.wordmafia.adapter.out;

import com.example.wordmafia.adapter.out.repository.MongoDBTestRepository;
import com.example.wordmafia.model.TestEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestEntityAdapter {
    private final MongoDBTestRepository mongoDBTestRepository;

    public TestEntity save(TestEntity testEntity) {
        return mongoDBTestRepository.save(testEntity);
    }
}
