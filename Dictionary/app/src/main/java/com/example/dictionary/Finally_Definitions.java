package com.example.dictionary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.dictionary.Adapter.RecyclerViewAdapter;
import com.example.dictionary.Adapter2.RecyclerViewAdapter2;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Finally_Definitions extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter2 recyclerViewAdapter2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finally_definitions);
        Intent intent = getIntent();
        AppCompatButton Download = findViewById(R.id.download);
        HashMap<String, ArrayList<ArrayList<String>>> map = new HashMap<String, ArrayList<ArrayList<String>>>();
        map =  (HashMap<String, ArrayList<ArrayList<String>>>)intent.getSerializableExtra("map");
        String AudioURL = intent.getStringExtra("AudioURL");
        String partOfSpeech = intent.getStringExtra("partOfSpeech");
        Context context = this;
        Download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioDownloader audioDownloader = new AudioDownloader(context, AudioURL);
                audioDownloader.execute();
            }
        });
        recyclerView = findViewById(R.id.RecyclerView_definition);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewAdapter2 = new RecyclerViewAdapter2(this, map, AudioURL, partOfSpeech);
        recyclerView.setAdapter(recyclerViewAdapter2);
    }
}