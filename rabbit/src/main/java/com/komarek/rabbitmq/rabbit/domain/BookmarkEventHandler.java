package com.komarek.rabbitmq.rabbit.domain;

import com.komarek.rabbitmq.rabbit.TaskMessage;
import com.komarek.rabbitmq.rabbit.TaskProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RepositoryEventHandler(Bookmark.class)
public class BookmarkEventHandler
{
    private static final String PARAM = "PARAM";
    private static final String SPLASH_URL = "http://localhost:8050/render.html?url=https://s.flashscore.com/search/?q="+PARAM+"&l=1&s=1&f=1%3B1&pid=2&sid=1";

    @Autowired
    private TaskProducer taskProducer;

    @HandleBeforeCreate
    public void handleBookmarkCreate(Bookmark bookmark)
    {
        bookmark.setCreated(new Date());
    }

    @HandleAfterCreate
    public void handleAfterBookmarkCreate(Bookmark bookmark)
    {
        final TaskMessage taskMessage = new TaskMessage();
        taskMessage.setUrl(SPLASH_URL.replace(PARAM,bookmark.getName()));
        taskProducer.sendNewTask(taskMessage);
    }

}
