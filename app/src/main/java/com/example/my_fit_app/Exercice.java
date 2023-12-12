package com.example.my_fit_app;

public class Exercice {

   private  String nomExercice;
   private String key;
   private String imageURLEx;
   private int nbserie,nbrepetition;
   private String musclecible;
   private String description;
   private String ytblink;

   public Exercice() {
   }

   public Exercice(String nomExercice, String imageURLEx, int nbserie, int nbrepetition, String musclecible,String description,String ytblink) {
      this.nomExercice = nomExercice;
      this.imageURLEx = imageURLEx;
      this.nbserie = nbserie;
      this.nbrepetition = nbrepetition;
      this.musclecible = musclecible;
      this.description = description;
      this.ytblink = ytblink;

   }

   public String getNomExercice() {
      return nomExercice;
   }

   public void setNomExercice(String nomExercice) {
      this.nomExercice = nomExercice;
   }

   public String getKey() {
      return key;
   }

   public void setKey(String key) {
      this.key = key;
   }

   public String getImageURLEx() {
      return imageURLEx;
   }

   public void setImageURLEx(String imageURLEx) {
      this.imageURLEx = imageURLEx;
   }

   public int getNbserie() {
      return nbserie;
   }

   public void setNbserie(int nbserie) {
      this.nbserie = nbserie;
   }

   public int getNbrepetition() {
      return nbrepetition;
   }

   public void setNbrepetition(int nbrepetition) {
      this.nbrepetition = nbrepetition;
   }

   public String getMusclecible() {
      return musclecible;
   }

   public void setMusclecible(String musclecible) {
      this.musclecible = musclecible;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getYtblink() {
      return ytblink;
   }

   public void setYtblink(String ytblink) {
      this.ytblink = ytblink;
   }

   @Override
   public String toString() {
      return "Exercice{" +
              "nomExercice='" + nomExercice + '\'' +
              ", key='" + key + '\'' +
              ", imageURLEx='" + imageURLEx + '\'' +
              ", nbserie=" + nbserie +
              ", nbrepetition=" + nbrepetition +
              ", musclecible='" + musclecible + '\'' +
              ", description='" + description + '\'' +
              ", ytblink='" + ytblink + '\'' +
              '}';
   }
}
