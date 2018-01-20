package com.komarek.rabbitmq.rabbit.domain;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

public interface BookmarkRepository extends MongoRepository<Bookmark,String>
{
    List<Bookmark> findBookmarkByName();
//    @RestResource(path="url")
//    List<Bookmark> findByUrl(@Param("text") String url);
}
