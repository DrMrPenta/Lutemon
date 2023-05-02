package com.example.lutemon;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LutemonViewHolder extends RecyclerView.ViewHolder {

    ImageButton delete, edit, battle;
    ImageView icon;
    TextView name, health, attack, defense, experience, record;
    EditText editName;


    public LutemonViewHolder(@NonNull View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.textName);
        health = itemView.findViewById(R.id.textHealth);
        attack = itemView.findViewById(R.id.textAttack);
        defense = itemView.findViewById(R.id.textDefense);
        experience = itemView.findViewById(R.id.textExperience);
        record = itemView.findViewById(R.id.txtRecord);
        icon = itemView.findViewById(R.id.imgPriority);
        delete = itemView.findViewById(R.id.imgDelete);
        edit = itemView.findViewById(R.id.imgEdit);
        battle = itemView.findViewById(R.id.imgBattle);
    }
}
