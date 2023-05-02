package com.example.lutemon.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.lutemon.R;
import com.example.lutemon.Storage;

public class LutemonAdd extends Fragment{

    private EditText textName;
    private Button button;
    Context context;
    Storage s = Storage.getInstance();

    int attack, defense, maxHealth, imageId;
    String color, image;

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
        View view = inflater.inflate(R.layout.fragment_lutemon_add, container, false);

        context = getContext();

        textName = view.findViewById(R.id.editTextName);
        button = view.findViewById(R.id.btnAddLutemon);
        RadioGroup radioGroup = view.findViewById(R.id.radioGroup);

        button.setOnClickListener(view1 -> {

            int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
            if (selectedRadioButtonId == -1) {
                Toast toast = Toast.makeText(getContext(), "Valitse väri", Toast.LENGTH_LONG);
                toast.show();
            }
            else {
                RadioButton selectedRadioButton = view.findViewById(selectedRadioButtonId);
                color = selectedRadioButton.getText().toString();

                if (color.equals("Valkoinen")) {
                    attack = 5;
                    defense = 4;
                    maxHealth = 20;
                    image = "skull";
                } else if (color.equals("Vihreä")) {
                    attack = 6;
                    defense = 3;
                    maxHealth = 19;
                    image = "alien";
                } else if (color.equals("Pinkki")) {
                    attack = 7;
                    defense = 2;
                    maxHealth = 18;
                    image = "demon";
                } else if (color.equals("Oranssi")) {
                    attack = 8;
                    defense =1;
                    maxHealth = 17;
                    image = "monster";
                } else if (color.equals("Musta")) {
                    attack = 9;
                    defense = 0;
                    maxHealth = 16;
                    image = "ghost";
                }

                String name = textName.getText().toString();
                imageId = context.getResources().getIdentifier(image, "drawable", context.getPackageName());
                s.addLutemon(name, color, imageId, attack, defense, maxHealth, context);
            }
        });

        return view;
    }
}