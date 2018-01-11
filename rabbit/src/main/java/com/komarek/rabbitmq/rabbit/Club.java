/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.komarek.rabbitmq.rabbit;

/**
 *
 * @author komarekzm
 */
public class Club {
    private String name;
    private String league;

    public Club(String name, String league) {
        this.name = name;
        this.league = league;
    }

    public Club() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }
    
}
