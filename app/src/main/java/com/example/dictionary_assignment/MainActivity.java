package com.example.dictionary_assignment;

import androidx.appcompat.app.AppCompatActivity;

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

class HashTable{
    int a;
    int b;
    int tableLength;
    HashTable(int tableLength)
    {
        this.tableLength = tableLength;
    }
    public int getTableLength(){
        return tableLength;
    }
    public void setLength(int tableLength){
        this.tableLength = tableLength;
    }
    public int getA(){
        return a;
    }
    public void setA(int a){
        this.a = a;
    }
    public int getB(){
        return b;
    }
    public void setB(int b){
        this.b = b;
    }
}

public class MainActivity extends AppCompatActivity {
    Button banglaTrans = (Button)findViewById(R.id.button);
    EditText text = (EditText)findViewById(R.id.text);
    TextView meaning = (TextView)findViewById(R.id.meaning);
    static int prime =  999999937;
    int random_a = -1;
    int random_b = -1;
    EnglishBangla dictionary = new EnglishBangla();
    WordModel[] wordModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String[] input = new String[1];
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
                input[0] = text.getText().toString();
                input[0] = input[0].toLowerCase();
                int key = AsciiEq(input[0]);
                int key1 = getFirstKey(input[0]);
                int primaryHashValue = getPrimaryHash(input[0],dictionary.size);
                String BanglaWord = findBanglaMeaning(input[0],dictionary.size);
                meaning.setText(BanglaWord);
                meaning.setVisibility(View.VISIBLE);
            }
        });
    }
    public void generateHashTable(int dictionarySize){
        int possibleCollision = 0;
        int[] collisionCount = new int[dictionarySize];
        for(int i=0;i<dictionarySize;i++)
        {
            collisionCount[i] = 0;
        }
        int [][] indexForCollision = new int[dictionarySize][1000];
        HashTable[] hashTables = new HashTable[dictionarySize];
        for(int i=0;i<dictionarySize;i++)
        {
            wordModel[i].English = wordModel[i].English.toLowerCase();
            String str = wordModel[i].getEnglish();
            int primaryHash = getPrimaryHash(str,dictionarySize);
            int slot = collisionCount[primaryHash];
            indexForCollision[primaryHash][slot] = i;
            collisionCount[primaryHash]++;
            if (collisionCount[primaryHash]>possibleCollision)
                possibleCollision = collisionCount[primaryHash];
            wordModel[i].setPrimaryHash(primaryHash);
        }
        Toast.makeText(getApplicationContext(),wordModel[1212].English,Toast.LENGTH_LONG).show();
        int newSize = possibleCollision*possibleCollision;
        int[][] newhashArray = new int[dictionarySize][newSize+1000];
        for(int j=0;j<dictionarySize;j++){
            int a;
            int b;
            int length = collisionCount[j]*collisionCount[j];
            hashTables[j] = new HashTable(length);
            int m = length;
            if(collisionCount[j]>=1)
            {
                if(collisionCount[j]==1){
                    m = 1;
                    a = 0;
                    b = 0;
                    int index = indexForCollision[j][0];
                    int secondaryHash = getSecondaryHash(a,b,m,wordModel[index].English);
                    wordModel[index].setSecondaryHash(secondaryHash);
                    hashTables[j].setA(a);
                    hashTables[j].setB(b);
                }
                else
                {
                    int[] secondaryHashTable = new int[m];
                    for(int i=0;i<collisionCount[j];i++)
                    {
                        int index = indexForCollision[j][i];
                        a = (int)(1+Math.floor(Math.random()*(prime-1)));
                        b = (int) Math.floor(Math.random()*prime);
                        int secondaryHash = getSecondaryHash(a,b,m,wordModel[index].English);
                        if(secondaryHashTable[secondaryHash]==0)
                            wordModel[index].setSecondaryHash(secondaryHash);
                        else
                        {
                            for(int k=0;k<m;k++)
                            {
                                secondaryHashTable[k] = 0;
                            }
                            i = 0;
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
            int primaryHash = wordModel[i].getPrimaryHash();
            int secondaryHash = wordModel[i].getSecondaryHash();
            indexForCollision[primaryHash][secondaryHash] = i;
        }
    }

    public int getPrimaryHash(String s,int m)
    {
        int key1 = this.getFirstKey(s);
        BigInteger BigKey1 = BigInteger.valueOf(key1);
        BigInteger dictionarySize = BigInteger.valueOf(m);
        BigInteger bigPrimaryHash = BigKey1.mod(dictionarySize);
        int primaryHash = bigPrimaryHash.intValue();
        return primaryHash;
    }

    public int getFirstKey(String s)
    {
        int a = (int) (1 + Math.floor(Math.random() * (prime - 1)));
        int b = (int) Math.floor(Math.random()*prime);
        if(random_a==-1||random_b==-1)
        {
            this.random_a = a;
            this.random_b = b;
        }
        else
        {
            a = this.random_a;
            b = this.random_b;
        }
        int key1 = this.AsciiEq(s);
        //BigInteger a1 = BigInteger.valueOf(a);
        //BigInteger b1 = BigInteger.valueOf(b);
        //BigInteger k = BigInteger.valueOf(key1);
        int h1 = (a*key1+b)%prime;
        return h1;
    }
    public int AsciiEq(String str)
    {
        int stringkey = 0;
        for(int i=0;i<str.length();i++)
        {
            stringkey = ((stringkey*256)%prime+str.charAt(i))%prime;
        }
        return stringkey;
    }
}

