package com.example.my_fit_app;

import java.util.Map;

public class Program {
    private String programName;
    private String ownerEmail;
    private Map<String, Exercice> exercises;

    public Program() {
    }
    public Program(String programName, String ownerEmail, Map<String, Exercice> exercises) {
        this.programName = programName;
        this.ownerEmail = ownerEmail;
        this.exercises = exercises;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public Map<String, Exercice> getExercises() {
        return exercises;
    }

    public void setExercises(Map<String, Exercice> exercises) {
        this.exercises = exercises;
    }

    @Override
    public String toString() {
        return "Program{" +
                "programName='" + programName + '\'' +
                ", ownerEmail='" + ownerEmail + '\'' +
                ", exercises=" + exercises +
                '}';
    }
}
