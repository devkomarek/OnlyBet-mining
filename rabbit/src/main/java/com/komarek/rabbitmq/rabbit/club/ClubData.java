package com.komarek.rabbitmq.rabbit.club;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ClubData {
    @Id
    private String id;
}
