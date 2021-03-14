package com.example.dictionary_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dictionary_assignment.WordModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;

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
    static int base = 256;
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
        }
    }


}

