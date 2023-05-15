package com.example.dictionary;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AudioDownloader extends AsyncTask<Void, Void, Void> {
    private Context context;
    private String audioUrl;

    public AudioDownloader(Context context, String audioUrl) {
        this.context = context;
        this.audioUrl = audioUrl;
        Log.i("Success", audioUrl);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL(audioUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            int responseCode = httpURLConnection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = httpURLConnection.getInputStream();
                //Code for downloadaing audio source file
                // Create a file to store the audio file
                File audioFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_MUSIC), "audio.mp3");
                FileOutputStream outputStream = new FileOutputStream(audioFile);

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                outputStream.flush();
                outputStream.close();
                inputStream.close();

                // Play the audio file
                MediaPlayer mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setDataSource(audioFile.getAbsolutePath());
                mediaPlayer.prepare();
                mediaPlayer.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        //Bug line
        Toast.makeText(context, "Audio download complete", Toast.LENGTH_SHORT).show();
    }
}
