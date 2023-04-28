package com.lurumad.provider.controllers;

import com.lurumad.provider.models.ClassifiedAd;
import com.lurumad.provider.models.ClassifiedAdRepository;
import com.lurumad.provider.models.ClassifiedAdResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClassifiedAdsController {
    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "classified ad not found")
    public static class ClassifiedAdNotFoundException extends RuntimeException { }

    private final ClassifiedAdRepository classifiedAdRepository;

    public ClassifiedAdsController(ClassifiedAdRepository classifiedAdRepository) {
        this.classifiedAdRepository = classifiedAdRepository;
    }

    @GetMapping("/ads")
    public ClassifiedAdResponse collection() {
        return new ClassifiedAdResponse((List<ClassifiedAd>)classifiedAdRepository.findAll());
    }

    @GetMapping("/ads/{id}")
    public ClassifiedAd classifiedAdById(@PathVariable("id") String id) {
        return classifiedAdRepository.findById(id).orElseThrow(ClassifiedAdNotFoundException::new);
    }
}
