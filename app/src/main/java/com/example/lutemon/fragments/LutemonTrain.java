package com.example.lutemon.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lutemon.Lutemon;
import com.example.lutemon.LutemonListAdapter;
import com.example.lutemon.R;
import com.example.lutemon.Storage;
import com.example.lutemon.TrainingListAdapter;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class LutemonTrain extends Fragment {

    private RecyclerView recyclerView;
    Storage s = Storage.getInstance();
    private HashMap<String, Lutemon> lutemons = new HashMap();
    private HashMap<String, Lutemon> lutemonsTrain = new HashMap();

    private Button button;
    RecyclerView.Adapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lutemon_train, container, false);

        lutemonsTrain = s.getTrainingLutemons();
        lutemons = s.getLutemons();
        button = view.findViewById(R.id.btnTrain);

        recyclerView = view.findViewById(R.id.rvTrainList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new TrainingListAdapter(getContext().getApplicationContext(), lutemonsTrain);
        recyclerView.setAdapter(adapter);

        button.setOnClickListener(view1 -> {

            for (Lutemon lutemon : lutemonsTrain.values()) {
                lutemon.train();
                lutemons.put(lutemon.getId(), lutemon);
            }
            try {
                ObjectOutputStream lutemonWriter = new ObjectOutputStream(getContext().openFileOutput("lutemons.data", Context.MODE_PRIVATE));
                lutemonWriter.writeObject(lutemons);
                lutemonWriter.close();
                s.loadLutemons(getContext());
            } catch (IOException e) {
                System.out.println("Lutemonien tallentaminen ei onnistunut");
            }
            adapter.notifyDataSetChanged();
            recyclerView.invalidate();
            System.out.println("Toimii");
        });
        return view;
    }

    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
        recyclerView.invalidate();
    }

}