package com.komarek.rabbitmq.rabbit.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ClubModel {
    @Id
    private String id;
    private Goal goal;

    public ClubModel(String id, Goal goal) {
        this.id = id;
        this.goal = goal;
    }

    public ClubModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }
}
