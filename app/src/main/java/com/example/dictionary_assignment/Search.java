package com.example.dictionary_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Search extends AppCompatActivity {
    ArrayList<String> wordList = new ArrayList<>();
    String json;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        final EditText text = findViewById(R.id.searchText);
        Button search = findViewById(R.id.btnSearch);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word=text.getText().toString();

                get_JSON(word);
            }
        });
    }

    public void get_JSON(String word){
        try{
            InputStream is = getAssets().open("E2Bdatabase.json");
            int size = is.available();
            byte[] Buffer = new byte[size];
            is.read();
            is.close();
            json = new String(Buffer,"UTF-8");
            JSONArray jsonArray = new JSONArray(json);



            for(int i=0;i<jsonArray.length();i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                if(obj.getString("en").equals(word)){
                    wordList.add(obj.getString("bn"));
                }
            }
            Toast.makeText(getApplicationContext(),wordList.toString(),Toast.LENGTH_LONG).show();
        }catch (IOException e){
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
