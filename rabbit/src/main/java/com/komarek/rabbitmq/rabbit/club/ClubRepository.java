package com.komarek.rabbitmq.rabbit.club;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Set;

public interface ClubRepository extends MongoRepository<Club,String> {
    Club findById(String id);

    List<Club> findAllByName(String name);
}
