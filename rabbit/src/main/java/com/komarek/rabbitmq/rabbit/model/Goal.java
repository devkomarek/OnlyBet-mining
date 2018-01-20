package com.komarek.rabbitmq.rabbit.model;

public class Goal {
    private String count;

    public Goal(String count) {
        this.count = count;
    }

    public Goal() {
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
