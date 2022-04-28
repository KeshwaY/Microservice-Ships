package com.github.ships.ships.shot;

import lombok.Data;

import java.util.List;

@Data
public class ShotResultDTO {

   private ShotLegality shotLegality;
   private int cellIDofLegalShot;
   private StatusOfLegalShot statusOfLegalShot;
   private List<Integer> shipSunk;
   private List<Integer> adjWaterOfShipSunk;

}
