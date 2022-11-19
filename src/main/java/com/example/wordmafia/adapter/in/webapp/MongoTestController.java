package com.example.wordmafia.adapter.in.webapp;

import com.example.wordmafia.adapter.out.TestEntityAdapter;
import com.example.wordmafia.model.TestEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mongo/test")
@RequiredArgsConstructor
public class MongoTestController {
    private final TestEntityAdapter testEntityAdapter;

    @GetMapping
    public void test() {
        testEntityAdapter.save(new TestEntity("test", "repo init test message"));
    }
}
