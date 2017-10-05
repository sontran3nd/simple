package com.example.admin.keyboard.Activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.admin.keyboard.Fragments.ViewFragment;
import com.example.admin.keyboard.R;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        setupViews();
    }

    FrameLayout frame;
    private void setupViews(){
        frame = (FrameLayout) findViewById(R.id.activity_second_frame);
        Fragment frg = new ViewFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activity_second_frame, frg);
        fragmentTransaction.commit();
    }
}
