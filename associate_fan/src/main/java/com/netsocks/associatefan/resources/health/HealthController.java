package com.netsocks.associatefan.resources.health;

import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
@Api(tags = "health")
class HealthController {
    @GetMapping
    public ResponseEntity check() {
        return ResponseEntity.noContent().build();
    }
}
