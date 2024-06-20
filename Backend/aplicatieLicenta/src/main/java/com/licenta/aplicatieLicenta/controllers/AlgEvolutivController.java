package com.licenta.aplicatieLicenta.controllers;

import com.licenta.aplicatieLicenta.service.AlgEvolutivService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RequestMapping("/algEvolutiv")
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class AlgEvolutivController {

    @Autowired
    private AlgEvolutivService algEvolutivService;

    @PostMapping("/run")
    public Map<String, Object> runAlgorithm(@RequestParam int n, @RequestParam int populationSize,
                                            @RequestParam int maxGenerations, @RequestParam double lambda) {
        return algEvolutivService.run(n, populationSize, maxGenerations, lambda);
    }
}
