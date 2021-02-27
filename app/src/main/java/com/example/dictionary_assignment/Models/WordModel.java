package com.example.dictionary_assignment.Models;

public class WordModel {
    private String English;
    private String Bangla;
    public WordModel(String English, String Bangla){
        this.English = English;
        this.Bangla = Bangla;
    }
    public String getEnglish(){
        return English;
    }
    public String getBangla(){
        return Bangla;
    }
    public void setEnglish(String english){
        English = english;
    }
    public void setBangla(String bangla){
        Bangla = bangla;
    }
}
