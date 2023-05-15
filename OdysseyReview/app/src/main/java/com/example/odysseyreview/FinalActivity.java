package com.example.odysseyreview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.internal.FastSafeIterableMap;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class FinalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        Intent intent = getIntent();
        Bundle extras = getIntent().getExtras();
        float[] arr = extras.getFloatArray("array");
        String name = intent.getStringExtra("Name");
        RatingBar DanceRating, MusicRating, FashionRating, FoodRating, PlayRating;
        DanceRating = findViewById(R.id.DanceRating);
        MusicRating  = findViewById(R.id.MusicRating);
        FashionRating = findViewById(R.id.FashionRating);
        FoodRating = findViewById(R.id.FoodRating);
        PlayRating = findViewById(R.id.PlayRating);
        DanceRating.setIsIndicator(true);
        MusicRating.setIsIndicator(true);
        FashionRating.setIsIndicator(true);
        FoodRating.setIsIndicator(true);
        PlayRating.setIsIndicator(true);
        for(int i = 0 ; i < 9; i+=2){
            if(arr[i] == 1 && i == 0){
//                System.out.println(arr[i+1]+" HERE ");
                Log.d("HERE WATHC ", String.valueOf(arr[i+1]));
                DanceRating.setRating(arr[i+1]);
            }
            if(arr[i] == 1 && i == 2){
//                System.out.println(arr[i+1]+" HERE ");
                Log.d("HERE WATHC ", String.valueOf(arr[i+1]));
                MusicRating.setRating(arr[i+1]);
            }
            if(arr[i] == 1 && i == 4){
//                System.out.println(arr[i+1]+" HERE ");
                Log.d("HERE WATHC ", String.valueOf(arr[i+1]));
                FashionRating.setRating(arr[i+1]);
            }
            if(arr[i] == 1 && i == 6){
//                System.out.println(arr[i+1]+" HERE ");
                Log.d("HERE WATHC ", String.valueOf(arr[i+1]));
                FoodRating.setRating(arr[i+1]);
            }
            if(arr[i] == 1 && i == 8){
//                System.out.println(arr[i+1]+" HERE ");
                    Log.d("HERE WATHC ", String.valueOf(arr[i+1]));
                PlayRating.setRating(arr[i+1]);
            }
        }
        Toast.makeText(FinalActivity.this, "Thank you for submitting", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onStart() {
        super.onStart();
        Log.i("OnStart()", "Final Activity started!");
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.i("OnPause()", "Final Activity Paused!");
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.i("OnResume()", "Final Activity Resumed!");
    }
}