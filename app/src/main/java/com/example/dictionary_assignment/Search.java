package com.example.dictionary_assignment;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dictionary_assignment.Models.WordModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class Search extends AppCompatActivity {
    Button button;
    TextView textView;
    ArrayList<String> wordList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        textView = findViewById(R.id.Engtext);
        button = (Button)findViewById(R.id.Whatsnow);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readJSON();
            }
        });
    }
    public void readJSON(){
        String json;
        try {
            InputStream is = getAssets().open("E2Bdatabase.json");
            int size = is.available();
            byte[] Buffer = new byte[size];
            is.read();
            is.close();
            json = new String(Buffer,"UTF-8");
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject object = jsonArray.getJSONObject(i);
                if (object.getString("wordList").equals("en"))
                {
                    wordList.add(object.getString("bn"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

}
