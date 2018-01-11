/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.komarek.rabbitmq.rabbit;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JavaTypeMapper;

@Component
public class ScrapingResultHandler
{

    @RabbitListener(queues = "result.queue")
    public void handleMessage(ScrapingResultMessage scrapingResultMessage)
    {
        ObjectMapper mapper = new ObjectMapper(); 
        Club value;
        try {
            value = mapper.readValue(scrapingResultMessage.getJson(), Club.class);
        } catch (IOException ex) {
            Logger.getLogger(ScrapingResultHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Received summary: " + scrapingResultMessage.getJson());
    }
}