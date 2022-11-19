package com.example.wordmafia.model;

public class Const {
    public static class Collections {
        public static final String TEST_ENTITY = "testEntity";
    }

    public static class RabbiMq {
        public static final String EXCHANGE_NAME = "sample.exchange";
        public static final String QUEUE_NAME = "sample.queue";
        public static final String ROUTING_KEY = "sample.test.#";

    }

    public static class MessageType {
        public static final String HI_MESSAGE = "Hi";
        public static final String HELLO_MESSAGE = "Hello";
    }
}
