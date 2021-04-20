package com.example.dictionary_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;

class EnglishBangla
{
    int size;
    JSONArray jsonArray;
}


public class MainActivity extends AppCompatActivity {
    Button banglaTrans;
    EditText text;
    TextView meaning;
    static long prime =  999999999989L;
    int random_a = -1;
    int random_b = -1;
    EnglishBangla dictionary = new EnglishBangla();
    HashTable[] hashTables;
    WordModel[] wordModel;
    int[] collisionCount;
    int[][] newhashArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        banglaTrans = (Button)findViewById(R.id.button);
        text = (EditText)findViewById(R.id.text);
        meaning = (TextView)findViewById(R.id.result);
        final String[] input = new String[1]; //User input
        try{
            InputStream is = getAssets().open("E2Bdatabase.json");
            int size = is.available();
            byte[] Buffer = new byte[size];
            is.read(Buffer);
            is.close();
            String json = new String(Buffer,"UTF-8");
            dictionary.jsonArray = new JSONArray(json);
            dictionary.size = dictionary.jsonArray.length();
            wordModel = new WordModel[dictionary.size];
            for(int i=0;i<dictionary.size;i++)
            {
                String English = dictionary.jsonArray.getJSONObject(i).getString("en");
                String Bangla = dictionary.jsonArray.getJSONObject(i).getString("bn");
                wordModel[i] = new WordModel(English,Bangla);
            }
            generateHashTable(dictionary.size);
        }catch (IOException e){
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }
        banglaTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input[0] = text.getText().toString(); //input processed at text field
                input[0] = input[0].toLowerCase();
                String BanglaWord = findBanglaMeaning(input[0],dictionary.size);
                Toast.makeText(getApplicationContext(),BanglaWord,Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),BanglaWord,Toast.LENGTH_SHORT).show();
            }
        });
    }
    public String findBanglaMeaning(String englishWord,int dictionarySize)
    {
        String banglaWord = "";
        int inputFunction = getPrimaryHash(englishWord,dictionarySize);
        if(collisionCount[inputFunction]==0)
        {
            String str = "Meaning not found";
            banglaWord = str;
        }
        if(collisionCount[inputFunction]==1)
        {
            int key = newhashArray[inputFunction][0];
            String str = wordModel[key].English;
            if(str.equals(englishWord))
            {
                banglaWord = wordModel[key].Bangla;
            }
        }
        if(collisionCount[inputFunction]>1)
        {
            long a = hashTables[inputFunction].getA();
            long b = hashTables[inputFunction].getB();
            int m = hashTables[inputFunction].tableLength;
            int secondaryFunction = getSecondaryHash(a,b,m,englishWord);
            int key = newhashArray[inputFunction][secondaryFunction];
            String str = wordModel[key].English;
            if(str.equals(englishWord))
            {
                banglaWord = wordModel[key].Bangla;
            }
        }
        return banglaWord;
    }
    public void generateHashTable(int Size){
        int dictionarySize = Size;
        int possibleCollision = 0;
        collisionCount = new int[dictionarySize];
        for(int i=0;i<dictionarySize;i++)
        {
            collisionCount[i] = 0;
        }
        int [][] indexForCollision = new int[dictionarySize][500];
        hashTables = new HashTable[dictionarySize];
        for(int i=0;i<dictionarySize;i++)
        {
            wordModel[i].English = wordModel[i].English.toLowerCase();
            String str = wordModel[i].getEnglish();
            int primaryHash = getPrimaryHash(str, dictionarySize);
            int slot = collisionCount[primaryHash];
            indexForCollision[primaryHash][slot] = i; //ind[360][0] = index[cat] from json, ind[360][1] = index[bat] from json
            collisionCount[primaryHash]++; //collisionCount>1 means collision occured
            if (collisionCount[primaryHash]>possibleCollision)
                possibleCollision = collisionCount[primaryHash];
            wordModel[i].setPrimaryHash(primaryHash);
        }
        int newSize = possibleCollision*possibleCollision;
        newhashArray = new int[dictionarySize][newSize+100];
        for(int j=0;j<dictionarySize;j++){
            long a;
            long b;
            int length = collisionCount[j]*collisionCount[j]; //double size of prevailing words
            hashTables[j] = new HashTable(length);
            int m = length;
            if(collisionCount[j]>=1)
            {
                if(collisionCount[j]==1){   //Only one element in a slot
                    m = 1;
                    a = 0;
                    b = 0;
                    int index = indexForCollision[j][0];
                    int secondaryHash = getSecondaryHash(a,b,m,wordModel[index].English); //for future need
                    wordModel[index].setSecondaryHash(secondaryHash);
                    hashTables[j].setA(a);
                    hashTables[j].setB(b);
                }
                else
                {
                    int[] secondaryHashTable = new int[m]; //10 collisions, length = 100 in secondary hash table
                    for(int i=0;i<collisionCount[j];i++)
                    {
                        int index = indexForCollision[j][i]; //index stored for values collided in primary hash
                        a = (long)(1+Math.floor(Math.random()*(prime-1)));
                        b = (long) Math.floor(Math.random()*prime);
                        int secondaryHash = getSecondaryHash(a,b,m,wordModel[index].English);
                        if(secondaryHashTable[secondaryHash]==0) //if table empty value insert
                            wordModel[index].setSecondaryHash(secondaryHash);
                        else
                        {
                            for(int k=0;k<m;k++)
                            {
                                secondaryHashTable[k] = 0; //secondary hash table can't collide so whole table is 0
                            }
                            i = 0; //then restart newly generate
                            continue;
                        }
                        wordModel[index].setSecondaryHash(secondaryHash);
                        hashTables[j].setA(a);
                        hashTables[j].setB(b);
                    }
                }
            }
        }
        for(int i = 0;i<dictionarySize;i++)
        {
            int primaryHash = (int) wordModel[i].getPrimaryHash();
            int secondaryHash = (int) wordModel[i].getSecondaryHash();
            newhashArray[primaryHash][secondaryHash] = i; //iterates for every word in json file and index derived from json
        }
    }

    public int getPrimaryHash(String s,int m)
    {
        BigInteger firstKey = BigInteger.valueOf(this.getFirstKey(s));
        BigInteger slot = BigInteger.valueOf(m);
        BigInteger numb = firstKey.mod(slot);
        int primaryHash = numb.intValue();
        return primaryHash;
    }

    public long getFirstKey(String s)
    {
        int a = (int) (1 + Math.floor(Math.random() * (prime - 1)));
        int b = (int) Math.floor(Math.random()*prime);
        if(random_a==-1||random_b==-1)
        {
            this.random_a = a;
            this.random_b = b;
        }
        else                           //when random_a and random_b already initialized
        {
            a = this.random_a;
            b = this.random_b;
        }
        long key1 = this.AsciiEq(s);
        BigInteger a1 = BigInteger.valueOf(a);
        BigInteger b1 = BigInteger.valueOf(b);
        BigInteger k = BigInteger.valueOf(key1);
        BigInteger pr = BigInteger.valueOf(prime);
        long h1 = (((a1.multiply(k)).add(b1)).mod(pr)).longValue();
        return h1;
    }
    public long AsciiEq(String str)
    {
        long stringkey = 0;
        for(int i=0;i<str.length();i++)
        {
            stringkey = ((stringkey*256)%prime+str.charAt(i))%prime;
        }
        return stringkey;
    }
    public int getSecondaryHash(long a,long b,int m,String s)
    {
        BigInteger secondKey = BigInteger.valueOf(getSecondKey(a,b,m,s));
        BigInteger slot = BigInteger.valueOf(m);
        return secondKey.mod(slot).intValue();
    }
    public long getSecondKey(long a, long b, int m, String s)
    {
        long key1 = this.getFirstKey(s);
        BigInteger k = BigInteger.valueOf(key1);
        BigInteger a1 = BigInteger.valueOf(a);
        BigInteger b1 = BigInteger.valueOf(b);
        BigInteger pr = BigInteger.valueOf(prime);
        long key2 = (((a1.multiply(k)).add(b1)).mod(pr)).longValue();
        return key2;
    }
}


