package com.sep6.backend.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path="api/v1/users")
public class UserController {

    @PostMapping( "/login")
    public String login(){
        return "user";
    }
}
