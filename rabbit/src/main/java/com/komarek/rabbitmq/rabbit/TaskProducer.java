package com.komarek.rabbitmq.rabbit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskProducer
{
    @Autowired
    private TaskProducerConfiguration taskProducerConfiguration;

    public void sendNewTask(TaskMessage taskMessage)
    {
        taskProducerConfiguration.rabbitTemplate().convertAndSend(taskProducerConfiguration.tasksQueue, taskMessage);
    }

}
