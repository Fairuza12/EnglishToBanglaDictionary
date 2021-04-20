package com.example.dictionary_assignment;

public class HashTable {
    long a;
    long b;
    int tableLength;
    HashTable(int tableLength)
    {
        this.tableLength = tableLength;
    }
    /*public int getTableLength(){
        return tableLength;
    }
    public void setLength(int tableLength){
        this.tableLength = tableLength;
    }*/
   public long getA(){
        return a;
    }
    public void setA(long a){
        this.a = a;
    }
    public long getB(){
        return b;
    }
    public void setB(long b){
        this.b = b;
    }
}
