package com.devkomarek.onlybet.mining.rest.springboot;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "ClubSearcher")
public class ClubSearcher {

    @RequestMapping("hello")
    public String getStudent(){
       return "Hello world";
    }
}
