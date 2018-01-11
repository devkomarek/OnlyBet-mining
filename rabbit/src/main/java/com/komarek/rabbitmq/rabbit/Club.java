/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.komarek.rabbitmq.rabbit;

import org.springframework.data.annotation.Id;

/**
 *
 * @author komarekzm
 */
public class Club {
    @Id
    private String id;
    private String url;
    private String name;
    private String country;

    public Club(String id, String url, String name, String country) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.country = country;
    }

    public Club(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public Club() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    
}
