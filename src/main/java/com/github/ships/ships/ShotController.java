package com.github.ships.ships;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Anna Ovcharenko
 */
@RestController
@RequestMapping("/api/v1/shots")
public class ShotController {

   private final ShotService service;

   public ShotController(ShotService shotService) {
      this.service = shotService;
   }

   @PostMapping
   ResponseEntity<ShotResultDTO> placeShot(
           @Valid @RequestBody ShotPostDTO shotPostDTO)
   {
      return ResponseEntity.ok(service.placeShot(shotPostDTO));
   }
}
