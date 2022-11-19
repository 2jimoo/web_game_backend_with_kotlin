package com.example.wordmafia.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = Const.Collections.TEST_ENTITY)
@Data
@AllArgsConstructor
public class TestEntity {
    @Id
    String id; //sessionId
    String message;
}
