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
import android.widget.ListView;
import android.widget.Toast;

import com.example.dictionary_assignment.Models.WordModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    //ArrayList<String> numberList = new ArrayList<>();
    ArrayList<WordModel> wordList = new ArrayList<>();
    String json;
    final int slotNo = 104035;
    final int prime = (int) (1e9 + 7);
    int n;
    String hashKey, hashValue;
    int hashFunction1;
    int hashFunction2;
    int[][] hashArray = new int[slotNo][3];
    int[] wordOfASlot = new int[slotNo];
    int[] canWord = new int[slotNo];
    WordModel[][] SecondTry;
    String str_data = "";
    String str_line = "";
    JSONObject object;
    int collides = 0;
    EditText show;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        final EditText text = (EditText) findViewById(R.id.searchText);
        Button search = (Button) findViewById(R.id.btnSearch);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            /*take word inputs*/
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Search.class);
                startActivity(intent);
            }
        });
    }

}

