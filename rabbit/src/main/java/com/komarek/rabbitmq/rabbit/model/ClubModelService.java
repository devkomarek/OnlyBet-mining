package com.komarek.rabbitmq.rabbit.model;

import com.komarek.rabbitmq.rabbit.club.Club;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClubModelService {
    @Autowired
    private ClubModelRepository repository;

    public ClubModel getClubModel(String id) {
        return repository.findById(id);
    }

    public void addClubModel(ClubModel model) {
        repository.save(model);
    }

    public void updateClubModel(ClubModel model){
        repository.save(model);

    }
}
