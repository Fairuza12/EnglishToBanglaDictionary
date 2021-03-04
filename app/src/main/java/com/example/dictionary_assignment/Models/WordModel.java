package com.example.dictionary_assignment.Models;

public class WordModel {
    private String en;
    private String bn;
    public WordModel(String English, String Bangla){
        this.en = English;
        this.bn = Bangla;
    }
    public String getEnglish(){
        return en;
    }
    public String getBangla(){
        return bn;
    }
    public void setEnglish(String en){
        this.en = en;
    }
    public void setBangla(String bn){
        this.bn = bn;
    }
}
