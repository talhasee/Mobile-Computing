package com.example.odysseyreview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class Activity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        EditText Name = findViewById(R.id.NameEditText);
        EditText RoleName = findViewById((R.id.RoleName));
        AppCompatButton NextButton = findViewById(R.id.NextButton);
        NextButton.setOnClickListener(new View.OnClickListener(){
            int flag = 0; //Name Empty = 1, Role Empty = 2 , Role case sensitivity = 3 else 0
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(Name.getText().toString())){
//                    Toast.makeText(Activity2.this, "Empty Field Not Allowed!!", Toast.LENGTH_SHORT).show();
                    flag = 1;
                }
                if(TextUtils.isEmpty(RoleName.getText().toString())){
//                    Toast.makeText(Activity2.this, "Empty Role Field Not Allowed!!", Toast.LENGTH_SHORT).show();
                    flag = 2;
                }
                String Role = RoleName.getText().toString().trim().toLowerCase();
//                String temp = Role+"HI"+Role.equals("audience");
//                Toast.makeText(Activity2.this, temp, Toast.LENGTH_SHORT).show();
                if(!Role.equals("audience") && !Role.equals("participant")) {
//                    Toast.makeText(Activity2.this, "Wrong input in Role!!...Try Again!!", Toast.LENGTH_SHORT).show();
                    flag = 3;
                }
                String[] arr = {"Proceed", "Empty Field Not Allowed!!", "Empty Role Field Not Allowed!!", "Wrong input in Role!!...Try Again!!"};
                Toast.makeText(Activity2.this, arr[flag], Toast.LENGTH_SHORT).show();
                if(flag == 0){
                    openNewActivity();
                }
                flag = 0;
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        Log.i("OnStart()", "Activity2 started!");
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.i("OnPause()", "Activity2 Pause!");
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.i("OnResume()", "Activity2 Resumed!");
    }
    public void openNewActivity(){
        Intent intent = new Intent(Activity2.this, Activity3.class);
        EditText name = (EditText)findViewById(R.id.NameEditText);
        intent.putExtra("Name",name.getText() );
        startActivity(intent);
    }
}