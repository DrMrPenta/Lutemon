package com.example.lutemon;

import android.content.Context;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;

public class Storage {
    private static Storage storage = null;

    private HashMap<String, Lutemon> lutemons = new HashMap();
    private HashMap<String, Lutemon> homeLutemons = new HashMap();
    private HashMap<String, Lutemon> trainingLutemons = new HashMap();
    private HashMap<String, Lutemon> arenaLutemons = new HashMap();

    private Storage() {
    }

    public static Storage getInstance() {
        if(storage == null) {
            storage = new Storage();
        }
        return(storage);
    }
    public void addLutemon(String name, String color, int image, int attack, int defense, int maxHealth, Context context) {

        int randomId = (int) (Math.random() * 900000);
        String id = String.format(Locale.ENGLISH,"%06d", randomId);
        Lutemon lutemon = new Lutemon(name, color, id, image, attack, defense, maxHealth);
        lutemons.put(id, lutemon);
        homeLutemons.put(id, lutemon);
        saveLutemons(context);
    }
    public void saveLutemons(Context context) {
        try {
            ObjectOutputStream lutemonWriter = new ObjectOutputStream(context.openFileOutput("lutemons.data", Context.MODE_PRIVATE));
            lutemonWriter.writeObject(lutemons);
            lutemonWriter.close();
        } catch (IOException e) {
            System.out.println("Lutemonien tallentaminen ei onnistunut");
        }
    }
    @SuppressWarnings("unchecked")
    public void loadLutemons(Context context) {

        try {
            ObjectInputStream lutemonReader = new ObjectInputStream(context.openFileInput("lutemons.data"));
            lutemons = (HashMap<String, Lutemon>) lutemonReader.readObject();
            lutemonReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Lutemonien lukeminen ei onnistunut.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Lutemonien lukeminen ei onnistunut.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Lutemonien lukeminen ei onnistunut.");
            e.printStackTrace();
        }
    }

    public void loadLutemonsHome(Context context) {
        try {
            ObjectInputStream lutemonReader = new ObjectInputStream(context.openFileInput("lutemons.data"));
            lutemons = (HashMap<String, Lutemon>) lutemonReader.readObject();
            lutemonReader.close();
            homeLutemons = lutemons;
        } catch (FileNotFoundException e) {
            System.out.println("Lutemonien lukeminen ei onnistunut.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Lutemonien lukeminen ei onnistunut.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Lutemonien lukeminen ei onnistunut.");
            e.printStackTrace();
        }
    }

    public void moveToTraining(String id) {
        trainingLutemons.put(id, getLutemonById(id));
        homeLutemons.remove(id);
    }
    public void moveFromTraining(String id) {
        trainingLutemons.remove(id);
        getLutemonById(id).setHealth(getLutemonById(id).getMaxHealth());
        lutemons.put(id, getLutemonById(id));
        homeLutemons.put(id, getLutemonById(id));
    }
    public void moveToArena(String id, Context context) {
        if (arenaLutemons.size() < 2) {
            arenaLutemons.put(id, getLutemonById(id));
            homeLutemons.remove(id);
        } else {
            Toast toast = Toast.makeText(context, "Areena on täynnä.", Toast.LENGTH_LONG);
            toast.show();
        }
    }
    public void removeFromArena(String id) {
        if (arenaLutemons.containsKey(id)) {
            arenaLutemons.remove(id);
            getLutemonById(id).setHealth(getLutemonById(id).getMaxHealth());
            lutemons.put(id, getLutemonById(id));
            homeLutemons.put(id, getLutemonById(id));
        }
    }

    public void removeLutemon(String id) {
        lutemons.remove(id);
        arenaLutemons.remove(id);
        homeLutemons.remove(id);
        trainingLutemons.remove(id);
    }

    public HashMap<String, Lutemon> getTrainingLutemons() { return trainingLutemons; }

    public HashMap<String, Lutemon> getArenaLutemons() {return arenaLutemons;}

    public HashMap<String, Lutemon> getHomeLutemons() {return homeLutemons;}

    public Lutemon getLutemonById(String id) {
        return lutemons.get(id);
    }
    public HashMap<String, Lutemon> getLutemons() {
        return(lutemons);
    }

}
