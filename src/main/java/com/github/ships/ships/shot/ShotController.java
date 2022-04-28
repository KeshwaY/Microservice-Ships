package com.github.ships.ships.shot;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/shots")
public class ShotController {

   private final ShotService service;

   public ShotController(ShotService shotService) {
      this.service = shotService;
   }

   //TODO: Require all fields of the request to have body
   @PostMapping
   ResponseEntity<ShotResultDTO> placeShot(
           @Valid
           @RequestBody
           ShotPostDTO shotPostDTO) {
      ShotResultDTO shotResultDTO = service.placeShot(shotPostDTO);
      return ResponseEntity.ok(shotResultDTO);
   }
}
