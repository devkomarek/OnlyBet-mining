package com.komarek.rabbitmq.rabbit.domain;

import javax.persistence.*;
import java.util.Date;

public class Bookmark
{
    private long id;
    private String url;
    private String summary;
    private Date created;

    private String note;

    public Date getCreated()
    {
        return created;
    }

    public void setCreated(Date created)
    {
        this.created = created;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getSummary()
    {
        return summary;
    }

    public void setSummary(String summary)
    {
        this.summary = summary;
    }

    public String getNote()
    {
        return note;
    }

    public void setNote(String note)
    {
        this.note = note;
    }
}
