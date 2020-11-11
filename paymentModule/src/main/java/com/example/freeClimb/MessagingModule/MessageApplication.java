package com.example.freeClimb.MessagingModule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.vailsys.freeclimb.api.FreeClimbClient;
import com.vailsys.freeclimb.api.FreeClimbException;

@SpringBootApplication
public class MessageApplication {


   public static void main(String[] args) throws FreeClimbException {
        SpringApplication.run(MessageApplication.class, args);
    }

}
