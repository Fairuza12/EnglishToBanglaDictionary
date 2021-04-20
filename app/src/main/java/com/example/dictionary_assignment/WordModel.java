package com.example.dictionary_assignment;

public class WordModel {
    String English;
    String Bangla;
    long primaryHash,secondaryHash;
    public WordModel(String English, String Bangla){
        this.English = English;
        this.Bangla = Bangla;
    }
    public String getEnglish(){ return this.English; }
    public String getBangla(){
        return this.Bangla;
    }
    public void setEnglish(String en){
        this.English = en;
    }
    public void setBangla(String bn){
        this.Bangla = bn;
    }
    public long getPrimaryHash() {
        return primaryHash;
    }

    public void setPrimaryHash(int primaryHash) {
        this.primaryHash = primaryHash;
    }

    public long getSecondaryHash() {
        return secondaryHash;
    }

    public void setSecondaryHash(long secondaryHash) {
        this.secondaryHash = secondaryHash;
    }
}
