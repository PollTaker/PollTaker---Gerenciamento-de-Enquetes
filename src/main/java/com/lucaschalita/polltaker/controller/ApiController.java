package com.lucaschalita.polltaker.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Status")
public class ApiController {

    @GetMapping
    @Operation(summary = "Verifica se a API está funcionando")
    public String status() {
        return "API PollTaker funcionando";
    }
}
