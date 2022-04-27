package com.github.ships.ships;

import java.util.List;

/**
 * @author Anna Ovcharenko
 */
public class ShotResultDTO {

   private ShotStatus status;
   private int id;
   private List<Integer> shipSunk;
   private List<Integer> adjWaterOfSunkShip;

   public ShotStatus getStatus() {
      return status;
   }

   public void setStatus(ShotStatus status) {
      this.status = status;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public List<Integer> getShipSunk() {
      return shipSunk;
   }

   public void setShipSunk(List<Integer> shipSunk) {
      this.shipSunk = shipSunk;
   }

   public List<Integer> getAdjWaterOfSunkShip() {
      return adjWaterOfSunkShip;
   }

   public void setAdjWaterOfSunkShip(List<Integer> adjWaterOfSunkShip) {
      this.adjWaterOfSunkShip = adjWaterOfSunkShip;
   }
}
