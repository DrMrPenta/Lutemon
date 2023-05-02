package com.example.lutemon.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.Layout;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lutemon.Lutemon;
import com.example.lutemon.R;
import com.example.lutemon.Storage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Random;

public class LutemonArena extends Fragment {

    View lutemonView1, lutemonView2;
    String id, id2;
    int imageId;
    MediaPlayer mediaPlayer;

    ImageView icon, icon2, delete, edit, battle, delete2, edit2, battle2;
    TextView name, health, attack, defense, experience, record, name2, health2, attack2, defense2, experience2, record2;
    Button button;

    TextView battleLogTextView;
    StringBuilder battleLog = new StringBuilder();

    ValueAnimator animator, animator2;
    SpringAnimation springAnim, springAnim2;
    int damage;
    Layout layout;
    Random r = new Random();

    Storage s = Storage.getInstance();
    private HashMap<String, Lutemon> lutemons = new HashMap();
    private HashMap<String, Lutemon> arenaLutemons = new HashMap();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lutemon_arena, container, false);

        arenaLutemons = s.getArenaLutemons();
        lutemons = s.getLutemons();
        imageId = getContext().getResources().getIdentifier(String.valueOf(R.drawable.ic_home), "drawable", getContext().getPackageName());

        button = view.findViewById(R.id.btnBattle);
        battleLogTextView = view.findViewById(R.id.txtLog);
        battleLogTextView.setMovementMethod(new ScrollingMovementMethod());

        lutemonView1 = view.findViewById(R.id.lutemon1);
        name = lutemonView1.findViewById(R.id.textName);
        attack = lutemonView1.findViewById(R.id.textAttack);
        health = lutemonView1.findViewById(R.id.textHealth);
        defense = lutemonView1.findViewById(R.id.textDefense);
        experience = lutemonView1.findViewById(R.id.textExperience);
        record = lutemonView1.findViewById(R.id.txtRecord);
        icon = lutemonView1.findViewById(R.id.imgPriority);
        delete = lutemonView1.findViewById(R.id.imgDelete);
        edit = lutemonView1.findViewById(R.id.imgEdit);
        battle = lutemonView1.findViewById(R.id.imgBattle);

        lutemonView2 = view.findViewById(R.id.lutemon2);
        name2 = lutemonView2.findViewById(R.id.textName);
        attack2 = lutemonView2.findViewById(R.id.textAttack);
        health2 = lutemonView2.findViewById(R.id.textHealth);
        defense2 = lutemonView2.findViewById(R.id.textDefense);
        record2 = lutemonView2.findViewById(R.id.txtRecord);
        experience2 = lutemonView2.findViewById(R.id.textExperience);
        icon2 = lutemonView2.findViewById(R.id.imgPriority);
        delete2 = lutemonView2.findViewById(R.id.imgDelete);
        edit2 = lutemonView2.findViewById(R.id.imgEdit);
        battle2 = lutemonView2.findViewById(R.id.imgBattle);

        mediaPlayer = MediaPlayer.create(getContext(), R.raw.punch);

        animator = ValueAnimator.ofFloat(0f, -2f);
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                lutemonView1.setTranslationY(value * 60);
            }
        });

        springAnim = new SpringAnimation(lutemonView1, DynamicAnimation.TRANSLATION_Y, 0);
        springAnim.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY);
        springAnim.getSpring().setStiffness(SpringForce.STIFFNESS_HIGH);

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mediaPlayer.start();
                springAnim.start();
            }
        });

        animator2 = ValueAnimator.ofFloat(0f, 2f);
        animator2.setDuration(1000);
        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                lutemonView2.setTranslationY(value * 60);
            }
        });

        springAnim2 = new SpringAnimation(lutemonView2, DynamicAnimation.TRANSLATION_Y, 0);
        springAnim2.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY);
        springAnim2.getSpring().setStiffness(SpringForce.STIFFNESS_HIGH);

        animator2.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mediaPlayer.start();
                springAnim2.start();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickBattle();
            }
        });
        return view;
    }

    public void lutemonAttack1() {
        animator.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                damage = s.getLutemonById(id).getAttack()-s.getLutemonById(id2).getDefense()+r.nextInt((2-(-2))+1)-2;
                s.getLutemonById(id2).setHealth(s.getLutemonById(id2).getHealth()-damage);
                if (s.getLutemonById(id2).getHealth()<=0) {
                    battleLog.append(s.getLutemonById(id).getName()+" teki "+damage+" vahinkoa!").append("\n");
                    battleLog.append(s.getLutemonById(id2).getName()+" hävisi! Lutemon selvisi, mutta heikentyi tasolle 0!").append("\n");
                    layout = battleLogTextView.getLayout();
                    if(layout != null){
                        int scrollDelta = layout.getLineBottom(battleLogTextView.getLineCount() - 1)
                                - battleLogTextView.getScrollY() - battleLogTextView.getHeight();
                        if(scrollDelta > 0)
                            battleLogTextView.scrollBy(0, scrollDelta);
                    }
                    battleLogTextView.setText(battleLog.toString());
                    button.setText("Aloita taistelu!");
                    s.getLutemonById(id2).addLoss();
                    s.getLutemonById(id).addWin();
                    s.saveLutemons(getContext());
                } else {
                    battleLog.append(s.getLutemonById(id).getName()+" teki "+damage+" vahinkoa!").append("\n");
                    battleLogTextView.setText(battleLog.toString());
                    layout = battleLogTextView.getLayout();
                    if(layout != null){
                        int scrollDelta = layout.getLineBottom(battleLogTextView.getLineCount() - 1)
                                - battleLogTextView.getScrollY() - battleLogTextView.getHeight();
                        if(scrollDelta > 0)
                            battleLogTextView.scrollBy(0, scrollDelta);
                    }
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            lutemonAttack2();
                        }
                    }, 1000);
                }
                loadLutemon1();
                loadLutemon2();
            }
        }, 1000);
    }
    public void lutemonAttack2() {
        animator2.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                damage = s.getLutemonById(id2).getAttack()-s.getLutemonById(id).getDefense()+r.nextInt((2-(-2))+1)-2;
                s.getLutemonById(id).setHealth(s.getLutemonById(id).getHealth()-damage);
                if (s.getLutemonById(id).getHealth()<=0) {
                    battleLog.append(s.getLutemonById(id2).getName()+" teki "+damage+" vahinkoa!").append("\n");
                    battleLog.append(s.getLutemonById(id).getName()+" hävisi! Lutemon selvisi, mutta heikentyi tasolle 0!").append("\n");
                    battleLogTextView.setText(battleLog.toString());
                    layout = battleLogTextView.getLayout();
                    if(layout != null){
                        int scrollDelta = layout.getLineBottom(battleLogTextView.getLineCount() - 1)
                                - battleLogTextView.getScrollY() - battleLogTextView.getHeight();
                        if(scrollDelta > 0)
                            battleLogTextView.scrollBy(0, scrollDelta);
                    }
                    button.setText("Aloita taistelu!");
                    s.getLutemonById(id).addLoss();
                    s.getLutemonById(id2).addWin();
                    s.saveLutemons(getContext());
                } else {
                    battleLog.append(s.getLutemonById(id2).getName()+" teki "+damage+" vahinkoa!").append("\n");
                    battleLogTextView.setText(battleLog.toString());
                    layout = battleLogTextView.getLayout();
                    if(layout != null){
                        int scrollDelta = layout.getLineBottom(battleLogTextView.getLineCount() - 1)
                                - battleLogTextView.getScrollY() - battleLogTextView.getHeight();
                        if(scrollDelta > 0)
                            battleLogTextView.scrollBy(0, scrollDelta);
                    }
                }
                loadLutemon1();
                loadLutemon2();
            }
        }, 1000);
    }

    @SuppressLint("SetTextI18n")
    public void loadLutemon1() {

        if(arenaLutemons.isEmpty()) {
            lutemonView1.setVisibility(View.GONE);
        } else {
            lutemonView1.setVisibility(View.VISIBLE);
            id = arenaLutemons.keySet().toArray()[0].toString();
            Lutemon lutemon = s.getLutemonById(id);

            name.setText(lutemon.getName());
            health.setText("Elämä: " + lutemon.getHealth() + "/" + lutemon.getMaxHealth());
            attack.setText("Hyökkäus: " + lutemon.getAttack());
            defense.setText("Puolustus: " + lutemon.getDefense());
            experience.setText("Kokemus: " + lutemon.getExperience());
            icon.setImageResource(lutemon.getImage());
            record.setVisibility(View.GONE);
            delete.setVisibility(View.GONE);
            edit.setVisibility(View.GONE);
            battle.setImageResource(imageId);

            battle.setOnClickListener(view -> {
                s.removeFromArena(id);
                s.saveLutemons(getContext());
                loadLutemon1();
                loadLutemon2();
            });
        }
    }
    @SuppressLint("SetTextI18n")
    public void loadLutemon2() {
        if(arenaLutemons.size() > 1) {
            lutemonView2.setVisibility(View.VISIBLE);
            id2 = arenaLutemons.keySet().toArray()[1].toString();
            Lutemon lutemon2 = s.getLutemonById(id2);

            name2.setText(lutemon2.getName());
            health2.setText("Elämä: " + lutemon2.getHealth() + "/" + lutemon2.getMaxHealth());
            attack2.setText("Hyökkäus: " + lutemon2.getAttack());
            defense2.setText("Puolustus: " + lutemon2.getDefense());
            experience2.setText("Kokemus: " + lutemon2.getExperience());
            icon2.setImageResource(lutemon2.getImage());
            record2.setVisibility(View.GONE);
            delete2.setVisibility(View.GONE);
            edit2.setVisibility(View.GONE);
            battle2.setImageResource(imageId);

            battle2.setOnClickListener(view -> {
                s.removeFromArena(id2);
                s.saveLutemons(getContext());
                loadLutemon1();
                loadLutemon2();
            });
        } else {
            lutemonView2.setVisibility(View.GONE);
        }
    }

    public void clickBattle() {
        lutemonAttack1();
        button.setText("Seuraava vuoro!");
    }

    public void onResume() {
        super.onResume();
        loadLutemon1();
        loadLutemon2();
        battleLog.setLength(0);
        battleLogTextView.setText("");
    }
}