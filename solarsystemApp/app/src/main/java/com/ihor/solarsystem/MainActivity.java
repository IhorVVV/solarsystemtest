package com.ihor.solarsystem;

import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.ihor.solarsystem.screens.listplanet.ListPlanetFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, ListPlanetFragment.newInstance(), ListPlanetFragment.class.getSimpleName());
            transaction.commit();
        }
    }
}
