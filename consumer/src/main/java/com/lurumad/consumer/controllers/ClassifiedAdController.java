package com.lurumad.consumer.controllers;

import com.lurumad.consumer.clients.ClassifiedAdServiceClient;
import com.lurumad.consumer.models.ClassifiedAdCollection;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ClassifiedAdController {

    private ClassifiedAdServiceClient client;

    public ClassifiedAdController(ClassifiedAdServiceClient client) {
        this.client = client;
    }

    @GetMapping("/collection")
    public String collection(Model model) {
        var collection = new ClassifiedAdCollection("Default", client.fetchClassifiedAds().getClassifiedAds());
        model.addAttribute("collection", collection);
        return "collection";
    }

    @GetMapping("/collection/{id}")
    public String collection(@PathVariable("id") String id, Model model) {
        model.addAttribute("classifiedAd", client.getClassifiedAdById(id));
        return "details";
    }
}
