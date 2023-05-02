package com.example.lutemon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class LutemonListAdapter extends RecyclerView.Adapter<LutemonViewHolder> {
    private Context context;
    private HashMap<String, Lutemon> lutemons;
    private HashMap<String, Lutemon> homeLutemons;
    Storage s = Storage.getInstance();

    public LutemonListAdapter(Context context, HashMap<String, Lutemon> homeLutemons) {
        this.context = context;
        if (homeLutemons != null) {
            this.homeLutemons = homeLutemons;
        } else {
            this.homeLutemons = new HashMap<>();
        }
    }
    @NonNull
    @Override
    public LutemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LutemonViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LutemonViewHolder holder, int position) {

        this.lutemons = s.getLutemons();
        this.homeLutemons = s.getHomeLutemons();

        String key = (String) homeLutemons.keySet().toArray()[position];
        Lutemon lutemon = homeLutemons.get(key);

        holder.name.setText(lutemon.getName());
        holder.health.setText("Elämä: " + lutemon.getHealth() + "/" + lutemon.getMaxHealth());
        holder.attack.setText("Hyökkäys: " + lutemon.getAttack());
        holder.defense.setText("Puolustus: " + lutemon.getDefense());
        holder.experience.setText("Kokemus: " + lutemon.getExperience());
        holder.record.setText("O:"+lutemon.getBattles()+ " V:"+lutemon.getWins()+ " H:"+lutemon.getLosses());

        holder.icon.setImageResource(lutemon.getImage());

        holder.delete.setOnClickListener(view -> {
            s.removeLutemon(key);
            notifyItemRemoved(holder.getLayoutPosition());
            try {
                ObjectOutputStream lutemonWriter = new ObjectOutputStream(context.openFileOutput("lutemons.data", Context.MODE_PRIVATE));
                lutemonWriter.writeObject(lutemons);
                lutemonWriter.close();
            } catch (IOException e) {
                System.out.println("Lutemonien tallentaminen ei onnistunut");
            }
        });

        holder.edit.setOnClickListener(view -> {
            s.moveToTraining(lutemon.getId());
            notifyItemRemoved(holder.getLayoutPosition());
            Toast.makeText(view.getContext(), lutemon.getName()+" siirretty treenialueelle.", Toast.LENGTH_SHORT).show();
        });

        holder.battle.setOnClickListener(view -> {
            s.moveToArena(lutemon.getId(), context);
            notifyItemRemoved(holder.getLayoutPosition());
            Toast.makeText(view.getContext(), lutemon.getName()+" siirretty areenaan.", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return homeLutemons.size();
    }
}

