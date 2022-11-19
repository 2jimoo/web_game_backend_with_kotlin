package com.example.wordmafia.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.Collections;

@Configuration
public class MongoConfig {

    @Bean
    public MongoTransactionManager transactionManager(MongoDatabaseFactory mongoDatabaseFactory) {
        return new MongoTransactionManager(mongoDatabaseFactory);
    }

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(Collections.emptyList());
    }

    @ConditionalOnBean(value = MongoProperties.class)
    @Bean
    public MongoDatabaseFactory mongoDatabaseFactory(MongoProperties mongoProperties) {
        return new SimpleMongoClientDatabaseFactory(mongoProperties.getUri());
    }

    @Bean
    public MongoTemplate mongoTemplate(
            MongoDatabaseFactory mongoDatabaseFactory, MongoCustomConversions mongoCustomConversions) {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDatabaseFactory);
        MappingMongoConverter mongoMappingConverter =
                (MappingMongoConverter) mongoTemplate.getConverter();
        mongoMappingConverter.setCustomConversions(mongoCustomConversions);
        mongoMappingConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
        mongoMappingConverter.afterPropertiesSet();
        return mongoTemplate;
    }

}
