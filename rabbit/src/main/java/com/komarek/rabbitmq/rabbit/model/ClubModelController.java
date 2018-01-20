package com.komarek.rabbitmq.rabbit.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller(value = "clubsmodels")
public class ClubModelController {

    @Autowired
    private ClubModelService service;

//    @GetMapping(value = "{id}")
//    public ClubModel getClubModel(@PathVariable String id){
//        return service.getClubModel(id);
//    }
//
//    @PostMapping
//    public void addClubModel(@RequestBody ClubModel model){
//        service.addClubModel(model);
//    }
//
//    @PutMapping(value = "{id}")
//    public void updateClubModel(@RequestBody ClubModel model){
//        service.updateClubModel(model);
//    }

}
