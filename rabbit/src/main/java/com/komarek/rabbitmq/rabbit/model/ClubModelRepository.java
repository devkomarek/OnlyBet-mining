package com.komarek.rabbitmq.rabbit.model;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClubModelRepository extends MongoRepository<ClubModel,String>{
    ClubModel findById(String id);
}
