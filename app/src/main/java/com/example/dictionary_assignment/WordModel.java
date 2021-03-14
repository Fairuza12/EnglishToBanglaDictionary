package com.example.dictionary_assignment;

public class WordModel {
    String English;
    String Bangla;
    int primaryHash,secondaryHash;
    public WordModel(String English, String Bangla){
        this.English = English;
        this.Bangla = Bangla;
    }
    public String getEnglish(){
        return this.English;
    }
    public String getBangla(){
        return this.Bangla;
    }
    public void setEnglish(String en){
        this.English = en;
    }
    public void setBangla(String bn){
        this.Bangla = bn;
    }
    public int getPrimaryHash() {
        return primaryHash;
    }

    public void setPrimaryHash(int primaryHash) {
        this.primaryHash = primaryHash;
    }

    public int getSecondaryHash() {
        return secondaryHash;
    }

    public void setSecondaryHash(int secondaryHash) {
        this.secondaryHash = secondaryHash;
    }
}
