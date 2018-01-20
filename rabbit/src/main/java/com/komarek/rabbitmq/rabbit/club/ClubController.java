package com.komarek.rabbitmq.rabbit.club;

import com.komarek.rabbitmq.rabbit.model.ClubModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController(value = "clubs")
public class ClubController {
    @Autowired
    private ClubService clubService;

//
//    @GetMapping
//    public List<Club> getAllClubs(){
//        return clubService.getAllClubs();
//    }
//
//    @GetMapping("{id}")
//    public Club getClub(@PathVariable String id){
//        return clubService.getClub(id);
//    }
//
//    @PostMapping
//    public void addClub(@RequestBody Club club){
//        clubService.addClub(club);
//    }
//
//    @PostMapping(value = "/{id}/data")
//    public void addClubData(@PathVariable String id,@RequestBody ClubData data){
//        clubService.addClubData(id,data);
//    }

//    @GetMapping(value = "/find/{name}")
//    public List<Club> findClub(@PathVariable String name){
//        Set<Club> clubs = clubService.getAllClubsByName(name);
//        return new ArrayList<>(clubs);
//    }
}
