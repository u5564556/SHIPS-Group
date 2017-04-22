package com.example.ships.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SuggestedModules extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_modules);
    }

    public void viewProgram(View view) {
        Button button = (Button) view;
        String selection = button.getText().toString();
        Log.d("selected as", selection);
        switch (selection){
            case "Long Term":
                //save result as long_term;
                Log.d("User selected ", selection);
            case "Mid Term":
                //save result as mid_term;
                Log.d("User selected ", selection);
            case "Short Term":
                //save result as short_term;
                Log.d("User selected ", selection);
        }
        startActivity(new Intent(this, ProgramList.class));
    }

    public void goBack(View view) {
        super.onBackPressed();
    }
}
