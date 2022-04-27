package com.github.ships.ships.shot;

import java.util.List;

public class ShotResultDTO {

   private ShotLegality shotLegality;

   private StatusOfLegalShot statusOfLegalShot;

   private int cellIDofLegalShot;
   private List<Integer> shipSunk;
   private List<Integer> adjWaterOfShipSunk;

   public ShotLegality getShotLegality() {
      return shotLegality;
   }

   public void setShotLegality(ShotLegality shotLegality) {
      this.shotLegality = shotLegality;
   }

   public StatusOfLegalShot getLegalShotStatus() {
      return statusOfLegalShot;
   }

   public void setLegalShotStatus(StatusOfLegalShot legalStatusOfLegalShot) {
      this.statusOfLegalShot = legalStatusOfLegalShot;
   }

   public int getCellIDofLegalShot() {
      return cellIDofLegalShot;
   }

   public void setCellIDofLegalShot(int cellIDofLegalShot) {
      this.cellIDofLegalShot = cellIDofLegalShot;
   }

   public List<Integer> getShipSunk() {
      return shipSunk;
   }

   public void setShipSunk(List<Integer> shipSunk) {
      this.shipSunk = shipSunk;
   }

   public List<Integer> getAdjWaterOfShipSunk() {
      return adjWaterOfShipSunk;
   }

   public void setAdjWaterOfShipSunk(List<Integer> adjWaterOfShipSunk) {
      this.adjWaterOfShipSunk = adjWaterOfShipSunk;
   }
}
