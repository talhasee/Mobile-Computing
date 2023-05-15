package com.example.dictionary;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText inputWord;
    private Button searchButton;

    private String baseURL = "https://api.dictionaryapi.dev/api/v2/entries/en/";
    private String word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputWord = findViewById(R.id.EnterWord);
        searchButton = findViewById(R.id.SearchBtn);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                word = inputWord.getText().toString().trim();
                if (word.matches("[a-zA-Z]+")) {
                    new DictionaryAsyncTask().execute(baseURL + word);
                } else {
                    Toast.makeText(MainActivity.this, "Invalid input. Only characters are allowed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class DictionaryAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line = bufferedReader.readLine();

                while (line != null) {
                    result += line;
                    line = bufferedReader.readLine();
                }
//                Toast.makeText(MainActivity.this, "Link Opened Successfully", Toast.LENGTH_SHORT).show();
//                Log.i("Success", "Link Opened Successfully");

            } catch (MalformedURLException e) {
//                Toast.makeText(MainActivity.this, "Error in Link", Toast.LENGTH_SHORT).show();
//                Log.i("Error", "Error in Link");
                e.printStackTrace();
            } catch (IOException e) {
//                Toast.makeText(MainActivity.this, "Error in Link", Toast.LENGTH_SHORT).show();
//                Log.i("Error", "Error in Link");
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ArrayList<String> AudioLinks = new ArrayList<String>();
            try {
                JSONArray jsonArray = new JSONArray(s);
                JSONObject jsonObject = jsonArray.getJSONObject(0);

                String word = jsonObject.getString("word");
                String phonetics = jsonObject.getJSONArray("phonetics").getJSONObject(0).getString("text");
                String audioURL = jsonObject.getJSONArray("phonetics").getJSONObject(0).getString("audio");
                JSONArray Phonetics = jsonObject.getJSONArray("phonetics");

                for(int k = 0; k < Phonetics.length(); k++){
                    JSONObject temp = new JSONObject();
                    temp = Phonetics.getJSONObject(k);
                    AudioLinks.add(temp.getString("audio"));
                    if(!temp.getString("audio").equals("")) {
                        audioURL = temp.getString("audio");
                        break;
                    }
                }
                Log.i("POST", AudioLinks.toString());

                JSONArray meaningsArray = jsonObject.getJSONArray("meanings");
                Intent intent = new Intent(MainActivity.this, WordActivity.class);
                intent.putExtra("word", word);
                intent.putExtra("phonetics", phonetics);
                intent.putExtra("audioURL", audioURL);
                intent.putExtra("meaningsArray", meaningsArray.toString());

                startActivity(intent);
                Toast.makeText(MainActivity.this, "Word retrieved Successfully", Toast.LENGTH_SHORT).show();
                Log.i("Success", "Word retrieved Successfully");

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "Word not found in dictionary.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
