package com.example.inicial1.controllers;

import com.example.inicial1.dtos.DnaDTO;
import com.example.inicial1.services.DnaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/mutant")
public class DnaController {

    private final DnaService dnaService;

    @PostMapping
    public ResponseEntity<?> isMutant(@RequestBody DnaDTO dnaDTO) {
        try {
            dnaService.isMutant(dnaDTO.getDna());
            return ResponseEntity.status(HttpStatus.OK).body("No es un mutante");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Los datos del ADN no son correctos: " + ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Es un mutante");
        }
    }
}