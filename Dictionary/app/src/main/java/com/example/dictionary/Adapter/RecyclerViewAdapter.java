package com.example.dictionary.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictionary.Finally_Definitions;
import com.example.dictionary.MainActivity;
import com.example.dictionary.R;
import com.example.dictionary.WordActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
//    private ArrayList<String> Meanings;
    HashMap<String, ArrayList<ArrayList<String>>> map;
    ArrayList<String> PartofSpeech = new ArrayList<String>();
    String Word;
    String AudioURL;
    public RecyclerViewAdapter(Context context, HashMap<String, ArrayList<ArrayList<String>>> map, String word, String AudioURL) { //Change the parameter to Hashmap<String, Array<String>>
        this.context = context;
//        this.Meanings = meanings;
        this.map = map;
        this.Word = word;
        this.AudioURL = AudioURL;
        for(Map.Entry<String, ArrayList<ArrayList<String>>> entry : map.entrySet()){
            PartofSpeech.add(entry.getKey());
        }
    }
    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) { //change the Data assignment ...Assign partofSpeech
        String num = Integer.toString(position+1);
        String data = PartofSpeech.get(position);
//
        holder.MeanNum.setText(num);
        holder.means.setText(data);
    }

    @Override
    public int getItemCount() {
        return map.size();
//        return PartofSpeech.size();
//        return Meanings.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView means, MeanNum;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            means = itemView.findViewById(R.id.titleTextview);
            MeanNum = itemView.findViewById((R.id.dataTextView));
        }

        @Override
        public void onClick(View v) {
            int currentClickedposition = getAbsoluteAdapterPosition();
            String currentPartOfSpeech = PartofSpeech.get(currentClickedposition);

            Intent intent = new Intent(context, Finally_Definitions.class);
            intent.putExtra("partOfSpeech", currentPartOfSpeech);
            intent.putExtra("AudioURL", AudioURL);
            intent.putExtra("map", map);
            Toast.makeText(context, "Selected Part of Speech "+currentPartOfSpeech, Toast.LENGTH_SHORT).show();
            Log.i("Success", "Selected Part of Speech "+currentPartOfSpeech);
            context.startActivity(intent);
        }
    }
}
