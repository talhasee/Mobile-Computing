package com.example.odysseyreview;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.util.HashMap;

public class Activity3 extends AppCompatActivity {
    RatingBar DanceRating, MusicRating, FashionRating, FoodRating, PlayRating;
    CheckBox DanceBar, MusicBar, FashionBar, FoodBar, PlayBar;
    HashMap<String, Integer> Map = new HashMap<String, Integer>();
    float[] arr = new float[10];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity3);
        AppCompatButton clearForm = (AppCompatButton) findViewById(R.id.ClearForm);
        AppCompatButton SubmitButton = (AppCompatButton) findViewById(R.id.SubmitButton);
        DanceRating = findViewById(R.id.DanceRating);
        MusicRating  = findViewById(R.id.MusicRating);
        FashionRating = findViewById(R.id.FashionRating);
        FoodRating = findViewById(R.id.FoodRating);
        PlayRating = findViewById(R.id.PlayRating);

        DanceBar = findViewById(R.id.DanceBar);
        MusicBar = findViewById(R.id.MusicBar);
        FashionBar = findViewById(R.id.FashionBar);
        FoodBar = findViewById(R.id.FoodBar);
        PlayBar = findViewById(R.id.PlayBar);

        DanceRating.setIsIndicator(true);
        MusicRating.setIsIndicator(true);
        FashionRating.setIsIndicator(true);
        FoodRating.setIsIndicator(true);
        PlayRating.setIsIndicator(true);

        clearForm.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        DanceBar.setChecked(false);
                        MusicBar.setChecked(false);
                        FashionBar.setChecked(false);
                        PlayBar.setChecked(false);
                        FoodBar.setChecked(false);

                        DanceRating.setIsIndicator(true);
                        MusicRating.setIsIndicator(true);
                        FashionRating.setIsIndicator(true);
                        FoodRating.setIsIndicator(true);
                        PlayRating.setIsIndicator(true);

                        DanceRating.setRating(0f);
                        MusicRating.setRating(0f);
                        FashionRating.setRating(0f);
                        FoodRating.setRating(0f);
                        PlayRating.setRating(0f);
                        arr[1] = 0;
                        arr[3] = 0;
                        arr[5] = 0;
                        arr[7] = 0;
                        arr[9] = 0;
                    }
                }
        );
        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Activity3.this, "Review Submitted!!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Activity3.this, FinalActivity.class);
                arr[1] = DanceRating.getRating(); arr[3] =MusicRating.getRating(); arr[5] = FashionRating.getRating(); arr[7] = FoodRating.getRating(); arr[9] = PlayRating.getRating();
                intent.putExtra("array", arr);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        Log.i("OnStart()", "Activity3 started!");
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.i("OnPause()", "Activity3 Pause!");
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.i("OnResume()", "Activity3 Resumed!");
    }
    public void onCheckboxListener(View view){
        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()){
            case R.id.DanceBar:
                if(checked) {
                   DanceRating.setIsIndicator(false);
                   Toast.makeText(Activity3.this, "Checked DanceBar", Toast.LENGTH_SHORT).show();
                    arr[0] = 1;
                    arr[1] = DanceRating.getRating();
                    Log.d("DAnce rating ", String.valueOf(DanceRating.getRating()));
                }
                else{
                    DanceRating.setRating(0f);
                    DanceRating.setIsIndicator(true);
                    arr[0] = 0;
                    arr[1] = 0;
//                    Toast.makeText(Activity3.this, "Checked DanceBar", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.MusicBar:
                if(checked){
                    MusicRating.setIsIndicator(false);
                    Toast.makeText(Activity3.this, "Checked MusicBar", Toast.LENGTH_SHORT).show();
                    arr[2] = 1;
                    arr[3] =MusicRating.getRating();
                }
                else{
                    MusicRating.setRating(0f);
                    MusicRating.setIsIndicator(true);
                    arr[2] = 0;
                    arr[3] = 0;
                }
                break;
            case R.id.FashionBar:
                if(checked){
                    FashionRating.setIsIndicator(false);
                    Toast.makeText(Activity3.this, "Checked FashionBar", Toast.LENGTH_SHORT).show();
                    arr[4] = 1;
                    arr[5] = FashionRating.getRating();
                }
                else{
                    FashionRating.setRating(0f);
                    FashionRating.setIsIndicator(true);
                    arr[4] = 0;
                    arr[5] = 0;
                }
                break;
            case R.id.FoodBar:
                if(checked){
                    FoodRating.setIsIndicator(false);
                    Toast.makeText(Activity3.this, "Checked FoodBar", Toast.LENGTH_SHORT).show();
                    arr[6] = 1;
                    arr[7] = FoodRating.getRating();
                }
                else{
                    FoodRating.setRating(0f);
                    FoodRating.setIsIndicator(true);
                    arr[6] = 0;
                    arr[7] = 0;
                }
                break;
            case R.id.PlayBar:
                if(checked){
                    PlayRating.setIsIndicator(false);
                    Toast.makeText(Activity3.this, "Checked PlayBar", Toast.LENGTH_SHORT).show();
                    arr[8] = 1;
                    arr[9] = PlayRating.getRating();

                }
                else {
                    PlayRating.setRating(0f);
                    PlayRating.setIsIndicator(true);
                    arr[8] = 0;
                    arr[9] = 0;
                }
                break;
            default:
                System.out.println("null");
                break;
        }
    }
}
