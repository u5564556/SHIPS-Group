package com.example.ships.myapplication.EMDR;

/**
 * Lalit Prasad 2017
 * EMDR settings activity
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.ships.myapplication.R;

public class EMDRActivitySettings extends AppCompatActivity {

    //emdr movement type, to be selected by user
    String emdrMovementType = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emdrsettings);

        //set and populate spinner with emdr movement types
        Spinner emdr_movement_type_spinner = (Spinner) findViewById(R.id.emdr_movement_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.emdr_movements_array, android.R.layout.simple_spinner_dropdown_item);
        emdr_movement_type_spinner.setAdapter(adapter);

        emdrMovementType = emdr_movement_type_spinner.getSelectedItem().toString();

        //set movement type upon (re)selection
        emdr_movement_type_spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Object item = adapterView.getItemAtPosition(i);
                        emdrMovementType = item.toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                }
        );

        //launch emdr activity on button click
        Button emdrStartButton = (Button) findViewById(R.id.emdrstartbutton);
        emdrStartButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                sendMessage(view);
            }
        });
    }

    public void sendMessage(View view) {
        Intent EMDRintent = new Intent(this, EMDRActivity.class);
        //tell emdr activity which movement type to run
        EMDRintent.putExtra("emdr_Movement_Type", emdrMovementType);
        startActivity(EMDRintent);
    }
}
