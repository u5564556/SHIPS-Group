package com.example.ships.myapplication.OtherInterfaces;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.ships.myapplication.R;

public class ThereapyFactsheets extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thereapy_factsheets);
    }

    public void goBack(View view) {
        super.onBackPressed();
    }
}