package com.example.demo.controller;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class DemoController {
    @MessageMapping("/demo")
    @SendTo("/topic/demo")
    public String demo(String message) throws Exception {
        Thread.sleep(1000);
        return "Hello from web socket!";
    }
}
