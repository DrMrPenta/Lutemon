package com.example.lutemon;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.lutemon.fragments.LutemonAdd;
import com.example.lutemon.fragments.LutemonArena;
import com.example.lutemon.fragments.LutemonList;
import com.example.lutemon.fragments.LutemonTrain;

public class TabPagerAdapter extends FragmentStateAdapter {
    public TabPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new LutemonList();
            case 1:
                return new LutemonArena();
            case 2:
                return new LutemonAdd();
            case 3:
                return new LutemonTrain();
            default:
                return new LutemonList();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
