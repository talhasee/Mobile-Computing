package com.example.dictionary.Adapter2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictionary.Adapter2.RecyclerViewAdapter2;
import com.example.dictionary.R;

import java.util.ArrayList;
import java.util.HashMap;

public class RecyclerViewAdapter2 extends RecyclerView.Adapter<RecyclerViewAdapter2.ViewHolder> {
    HashMap<String, ArrayList<ArrayList<String>>> map;

    private Context context;
    String AudioURl;
    String partofSpeech;
    ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
    public RecyclerViewAdapter2(Context context, HashMap<String, ArrayList<ArrayList<String>>> map, String AudioURL, String partofSpeech){
        this.context = context;
        this.map = map;
        this.AudioURl = AudioURL;
        this.partofSpeech = partofSpeech;
        this.data = map.get(partofSpeech);
    }
    @NonNull
    @Override
    public RecyclerViewAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate((R.layout.dictionary_row), parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter2.ViewHolder holder, int position) {
        ArrayList<String> temp = new ArrayList<String>();
        temp = data.get(position);
        holder.definition.setText(temp.get(0));
        if(!temp.get(1).equals("[]"))
            holder.synonym.setText(temp.get(1));
        else
            holder.synonym.setText("");
        if(!temp.get(2).equals("[]"))
            holder.antonym.setText(temp.get(2));
        else
            holder.antonym.setText("");

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView definition, synonym, antonym;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            definition = itemView.findViewById(R.id.definition);
            synonym = itemView.findViewById(R.id.synonym);
            antonym = itemView.findViewById(R.id.antonym);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
