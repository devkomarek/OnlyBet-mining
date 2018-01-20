/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.komarek.rabbitmq.rabbit;

import com.komarek.rabbitmq.rabbit.club.Club;
import org.springframework.stereotype.Component;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Component
public class ScrapingResultHandler
{

    @RabbitListener(queues = "result.queue")
    public void handleMessage(List<Club> scrapingJson)
    {
        System.out.println(scrapingJson.get(0).getName());
//        if(scrapingJson.getJson() == null) return;
//        ObjectMapper mapper = new ObjectMapper();
//        Club value = null;
//        try {
//            value = mapper.readValue(scrapingJson.getJson(), Club.class);
//        } catch (IOException ex) {
//            Logger.getLogger(ScrapingResultHandler.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        System.out.println("Received summary: " + value.getId());
    }
}