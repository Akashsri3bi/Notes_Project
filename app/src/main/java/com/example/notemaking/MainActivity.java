package com.example.notemaking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Connecting our Fragment
        FragmentManager fragmentManager = getSupportFragmentManager() ;
        FragmentTransaction ft = fragmentManager.beginTransaction() ;
        Fragment fragment = new RecyclerFragment() ;
        ft.add(R.id.fragment_container , fragment) ;
        ft.commit() ;
    }
}