package com.example.dictionary_assignment;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dictionary_assignment.Models.WordModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Search extends AppCompatActivity {
    ArrayList<WordModel> wordList = new ArrayList<>();
    //String json;
    final int slotNo = 104035;
    final int prime = (int) (1e9+7);
    int n;
    String hashKey,hashValue;
    int hashFunction1;
    int hashFunction2;
    int[][] hashArray = new int[slotNo][3];
    int[] wordOfASlot = new int[slotNo];
    int[] canWord = new int[slotNo];
    WordModel[][] SecondTry;
    String str_data = "";
    String str_line = "";
    JSONObject object;
    int collides=0;
    EditText show;
    TextView textView;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        final EditText text = findViewById(R.id.searchText);
        Button search = findViewById(R.id.btnSearch);
        textView = (TextView)findViewById(R.id.showText);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            /*take word inputs*/
            public void onClick(View v) {
                String word = text.getText().toString();
                word = word.toLowerCase();
                try {
                    lookForBanglaMeaning(word);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
            /*initialize array*/
        Arrays.fill(wordOfASlot,0);
            /*hash function initialization*/
        hashFunction1 = (int)(Math.random()*7)+1;
        hashFunction2 = (int)(Math.random()*7);
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getAssets().open("E2Bdatabase.json")));
            while (str_line!=null) {
                str_data += str_line;
                str_data = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i=0;i<slotNo;i++){
            WordModel wordModel = new WordModel(null,null);
            wordList.add(wordModel);
        }
        Log.d("slotNO", "onCreate: " + slotNo);
        try{
            object = new JSONObject(str_data);
            Iterator key = object.keys();
            while (key.hasNext()){
                hashKey = (String)key.next();
                //hashValue = null;
                hashValue = object.getString(hashKey);
                int convertedNumber = stringToNumber(hashKey);
                int number = primaryHash(convertedNumber);
                WordModel wordModel = wordList.get(number);
                wordOfASlot[number]++;
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        canWord = wordOfASlot;
        Arrays.sort(canWord);
        n = canWord[slotNo-1];
        n = n*n;
        SecondTry = new WordModel[slotNo][n];
        for(int i=0;i<slotNo;i++){
            int primary_hash_value = (int)(Math.random()*(n-1))+1;
            int secondary_hash_value = (int)(Math.random()*(n-1));
            int len = wordOfASlot[i]*wordOfASlot[i];
            hashArray[i][0] = len;
            hashArray[i][1] = primary_hash_value;
            hashArray[i][2] = secondary_hash_value;
        }
        hashFunction();
    }

    //Toast.makeText(getApplicationContext(),wordList.toString(),Toast.LENGTH_LONG).show();


    /*InputStream is = getAssets().open("E2Bdatabase.json");
     int size = is.available();
     byte[] Buffer = new byte[size];
     is.read();
     is.close();
     json = new String(Buffer,"UTF-8");
     JSONArray jsonArray = new JSONArray(json);

     slotNo = jsonArray.length();

     for(int i=0;i<jsonArray.length();i++){
        JSONObject obj = jsonArray.getJSONObject(i);
        if(obj.getString("en").equals(word)){
         wordList.add(obj.getString("bn"));
     }
            }*/
    //Toast.makeText(getApplicationContext(),wordList.toString(),Toast.LENGTH_LONG).show();
}
