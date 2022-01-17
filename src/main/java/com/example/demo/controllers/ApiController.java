/*
 * SCRIPT: ApiController.java
 * AUTHOR: Wu (https://github.com/stupid-2020)
 * DATE  : 17-JAN-2022
 * NOTE  : 
 */
 package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.models.Author;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

	@GetMapping("/getAuthors")
	public @ResponseBody Iterable<Author> getInfo(
        @RequestParam(name="id", required=false, defaultValue="1")
        String id
    ) {
        List<Author> authors = new ArrayList<>();

        Author jane = new Author();
        Author joan = new Author();
        Author john = new Author();

        jane.setId(1);
        jane.setName("Jane Doe");
        jane.setInitials("JA");
        jane.setEmail("jane.doe@example.com");
        jane.setBooknames("Sensor and Sensibility, Price and Predict, Anna");

        joan.setId(2);
        joan.setName("Joan Doe");
        joan.setInitials("JK");
        joan.setEmail("joan.doe@example.com");
        joan.setBooknames("Hurry Potter, Fantastic Bees and Where to Find Them, Christmas Big");

        john.setId(3);
        john.setName("John Doe");
        john.setInitials("JR");
        john.setEmail("john.doe@example.com");
        john.setBooknames("The Hobby, The Lord of the Wings");

        authors.add(jane);
        authors.add(joan);
        authors.add(john);

        return authors;
    }

    @PostMapping("/testAuth")
	public String testAuth() {
        return "ok";
    }
}

