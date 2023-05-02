package com.example.lutemon;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class TrainingListAdapter extends RecyclerView.Adapter<LutemonViewHolder> {
    private Context context;
    int imageId;
    private HashMap<String, Lutemon> lutemons;
    private HashMap<String, Lutemon> lutemonsFull;
    Storage s = Storage.getInstance();

    public TrainingListAdapter(Context context, HashMap<String, Lutemon> lutemons) {
        this.context = context;
        if (lutemons != null) {
            this.lutemons = lutemons;
        } else {
            this.lutemons = new HashMap<>();
        }
    }
    @NonNull
    @Override
    public LutemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LutemonViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LutemonViewHolder holder, int position) {

        this.lutemons = s.getTrainingLutemons();
        this.lutemonsFull = s.getLutemons();

        imageId = context.getResources().getIdentifier(String.valueOf(R.drawable.ic_home), "drawable", context.getPackageName());

        String key = (String) lutemons.keySet().toArray()[position];
        Lutemon lutemon = lutemons.get(key);

        holder.name.setText(lutemon.getName());
        holder.health.setText("Elämä: " + lutemon.getHealth() + "/" + lutemon.getMaxHealth());
        holder.attack.setText("Hyökkäys: " + lutemon.getAttack());
        holder.defense.setText("Puolustus: " + lutemon.getDefense());
        holder.experience.setText("Kokemus: " + lutemon.getExperience());
        System.out.println(lutemon.getExperience());

        holder.icon.setImageResource(lutemon.getImage());

        holder.record.setVisibility(View.GONE);
        holder.edit.setVisibility(View.GONE);
        holder.delete.setVisibility(View.GONE);

        holder.battle.setImageResource(imageId);
        holder.battle.setOnClickListener(view -> {
            s.moveFromTraining(key);
            lutemons.remove(key);
            notifyItemRemoved(holder.getLayoutPosition());
            try {
                ObjectOutputStream lutemonWriter = new ObjectOutputStream(context.openFileOutput("lutemons.data", Context.MODE_PRIVATE));
                lutemonWriter.writeObject(lutemonsFull);
                lutemonWriter.close();
            } catch (IOException e) {
                System.out.println("Lutemonien tallentaminen ei onnistunut");
            }

            Toast.makeText(view.getContext(), lutemon.getName()+" siirretty kotiin.", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return lutemons.size();
    }
}

