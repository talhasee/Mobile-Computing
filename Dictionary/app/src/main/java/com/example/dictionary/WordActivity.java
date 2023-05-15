package com.example.dictionary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictionary.Adapter.RecyclerViewAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WordActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<String> meanings;
    private ArrayAdapter<String> arrayAdapter;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_activity);

//        getSupportFragmentManager().beginTransaction().replace(R.id.MainContainer,new fragment()).commit();

//        recyclerView = findViewById(R.id.recyclerView);
//
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        String Word = intent.getStringExtra("word");
        String phonetics = intent.getStringExtra("phonetics");
        String AudioURL = intent.getStringExtra("audioURL");
        String meaningsArr = intent.getStringExtra("meaningsArray");
        JSONArray Meanings = new JSONArray();
        HashMap<String, ArrayList<ArrayList<String>>> map = new HashMap<String, ArrayList<ArrayList<String>>>();

        TextView Shabd = findViewById(R.id.ShowWord);

        Shabd.setText(Word);

        try {
            Meanings = new JSONArray(meaningsArr);
        } catch (JSONException e) {
            Log.d("DataCheck", "Error!!!");
            e.printStackTrace();
        }

        for(int i = 0; i < Meanings.length(); i++){
            JSONObject jsonobject = new JSONObject();
            String partOfSpeech = "";
            JSONArray definitions = new JSONArray();

            try {
                jsonobject = Meanings.getJSONObject(i);
                partOfSpeech = jsonobject.getString("partOfSpeech");
                definitions = jsonobject.getJSONArray("definitions");
                ArrayList<ArrayList<String>> array = new ArrayList<ArrayList<String>>();
                for(int j = 0 ; j < definitions.length(); j++){
                    JSONObject temp = new JSONObject();
                    temp = definitions.getJSONObject(j);
                    ArrayList<String> arr = new ArrayList<String>();
                    arr.add(temp.getString("definition"));
                    arr.add(temp.getJSONArray("synonyms").toString());
                    arr.add(temp.getJSONArray("antonyms").toString());
//                    map.put(partOfSpeech, arr);
                    array.add(arr);
                }
                map.put(partOfSpeech, array);

            } catch (JSONException e) {
                Log.d("DataCheck", "Error!!!");
                e.printStackTrace();
            }
            Log.d("DataCheck", partOfSpeech);
            for(Map.Entry<String, ArrayList<ArrayList<String>>> entry : map.entrySet()){
                Log.d("DataCheck", entry.getKey());
                Log.d("DataCheck", entry.getValue().toString());
            }
        }

        fragment frag = new fragment();
        Bundle args = new Bundle();
        args.putSerializable("map", map);
        args.putString("Word", Word);
        args.putString("Audio", AudioURL);
        frag.setArguments(args);

        frameLayout = (FrameLayout)findViewById(R.id.MainContainer);
        FragmentManager Mgr = getSupportFragmentManager();
        FragmentTransaction Ftrans = Mgr.beginTransaction();
        Ftrans.add(R.id.MainContainer, frag);
        Ftrans.commit();



        Log.d("Checking", Word);
        Log.d("Checking", phonetics);
        Log.d("Checking", AudioURL);
        Log.d("Checking", meaningsArr);


        DefinitionExtractor obj = new DefinitionExtractor();
        List<String> ls = obj.extractDefinitions(meaningsArr);
        meanings = new ArrayList<String>(ls);
//        recyclerViewAdapter = new RecyclerViewAdapter(WordActivity.this, map, Word);//Change the second argument such that in futute it sends HashMap
//        recyclerView.setAdapter(recyclerViewAdapter);

    }

    public class DefinitionExtractor {
        public List<String> extractDefinitions(String input) {
            List<String> definitions = new ArrayList<>();
            Pattern pattern = Pattern.compile("\\{\"definition\":\"(.*?)\"");
            Matcher matcher = pattern.matcher(input);
            while (matcher.find()) {
                String definition = matcher.group(1);
                definitions.add(definition);
            }
            return definitions;
        }
    }

}
