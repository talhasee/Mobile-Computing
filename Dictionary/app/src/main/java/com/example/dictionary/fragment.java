package com.example.dictionary;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dictionary.Adapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class fragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    public fragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        HashMap<String, ArrayList<ArrayList<String>>> map = (HashMap<String, ArrayList<ArrayList<String>>>) getArguments().getSerializable("map");
        String Word = getArguments().getString("Word");
        String AudioURL = getArguments().getString("Audio");
        recyclerViewAdapter = new RecyclerViewAdapter(getContext(), map, Word, AudioURL);
        recyclerView.setAdapter(recyclerViewAdapter);

        return view;
    }

}