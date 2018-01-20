package com.komarek.rabbitmq.rabbit.domain;

import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.util.Date;

public class Bookmark
{
    private String id;
    private String name;
    private Date dateFrom;
    private Date dateTo;
    private Date created;

    public Bookmark(String id, String name, Date dateFrom, Date dateTo, Date created) {
        this.id = id;
        this.name = name;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.created = created;
    }

    public Bookmark() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
