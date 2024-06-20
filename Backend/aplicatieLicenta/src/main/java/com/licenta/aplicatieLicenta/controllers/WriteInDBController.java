package com.licenta.aplicatieLicenta.controllers;

import com.licenta.aplicatieLicenta.service.WriteInDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/writes")
@CrossOrigin(origins = "http://localhost:4200")
public class WriteInDBController {

    @Autowired
    private WriteInDBService writeInDBService;

    @PostMapping("/generate")
    public ResponseEntity<Map<String, String>> generateData(@RequestParam("nbJobs") Integer nbJobs,
                                                            @RequestParam("nbMchs") Integer nbMchs,
                                                            @RequestBody Map<String, Object> payload) {
        try {
            List<List<List<Integer>>> ops = (List<List<List<Integer>>>) payload.get("ops");
            List<List<Integer>> modes = (List<List<Integer>>) payload.get("modes");

            writeInDBService.generate(nbJobs, nbMchs, ops, modes);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Data generated and saved successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error generating and saving data");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Map<String, String>> deleteAllData() {
        try {
            writeInDBService.deleteAll();
            Map<String, String> response = new HashMap<>();
            response.put("message", "All data deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error deleting data");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


