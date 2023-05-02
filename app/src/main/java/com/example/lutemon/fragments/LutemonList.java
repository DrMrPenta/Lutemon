package com.example.lutemon.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lutemon.LutemonListAdapter;
import com.example.lutemon.R;
import com.example.lutemon.Storage;

public class LutemonList extends Fragment {
    private RecyclerView recyclerView;
    Storage s = Storage.getInstance();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lutemon_list, container, false);

        s.loadLutemons(getContext());
        recyclerView = view.findViewById(R.id.rvItemView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new LutemonListAdapter(getContext().getApplicationContext(), s.getHomeLutemons());
        recyclerView.setAdapter(adapter);

        return view;
    }
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}