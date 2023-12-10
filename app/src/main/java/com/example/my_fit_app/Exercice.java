package com.example.my_fit_app;

public class Exercice {

   private  String nomExercice;
   private String key;
   private String imageURLEx;
   private int nbserie,nbrepetition;
   private String musclecible;

   public Exercice() {
   }

   public Exercice(String nomExercice, String imageURLEx, int nbserie, int nbrepetition, String musclecible) {
      this.nomExercice = nomExercice;
      this.imageURLEx = imageURLEx;
      this.nbserie = nbserie;
      this.nbrepetition = nbrepetition;
      this.musclecible = musclecible;
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
}
