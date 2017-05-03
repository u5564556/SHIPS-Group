package com.example.ships.myapplication.homepageAndRegistration;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ships.myapplication.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void back(View v)
    {
        startActivity(new Intent(this, TermsAndConditions.class));
    }

    public void finish(View v)
    {
        EditText email = (EditText) findViewById(R.id.emailInput);
        EditText pw = (EditText) findViewById(R.id.pwInput);
        EditText comfirmpw = (EditText) findViewById(R.id.pwConfirmInput);
        EditText firstName = (EditText) findViewById(R.id.firstNameInput);
        EditText lastName = (EditText) findViewById(R.id.lastNameInput);

        TextView emailtext = (TextView) findViewById(R.id.emailText);
        TextView pwtext = (TextView) findViewById(R.id.pwView);
        TextView comfirmpwtext = (TextView) findViewById(R.id.pwConfirmView);
        TextView firstNametext = (TextView) findViewById(R.id.firstNameView);
        TextView lastNametext = (TextView) findViewById(R.id.lastNameView);

        if (!isEmailValid(email.getText().toString()))
        {
            Context context = getApplicationContext();
            CharSequence text = "Please enter a valid e-mail";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            emailtext.setTextColor(Color.RED);
            pwtext.setTextColor(Color.BLACK);
            comfirmpwtext.setTextColor(Color.BLACK);
            firstNametext.setTextColor(Color.BLACK);
            lastNametext.setTextColor(Color.BLACK);
        }
        else if ((pw.getText().toString().length() == 0) || !pw.getText().toString().equals(comfirmpw.getText().toString()))
        {
            Context context = getApplicationContext();
            CharSequence text = "Please enter a password and make sure your password is consistent";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            emailtext.setTextColor(Color.BLACK);
            pwtext.setTextColor(Color.RED);
            comfirmpwtext.setTextColor(Color.RED);
            firstNametext.setTextColor(Color.BLACK);
            lastNametext.setTextColor(Color.BLACK);
        }
        else if (firstName.length() == 0 || lastName.length() == 0 )
        {
            Context context = getApplicationContext();
            CharSequence text = "You must enter your first and last name";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            emailtext.setTextColor(Color.BLACK);
            pwtext.setTextColor(Color.BLACK);
            comfirmpwtext.setTextColor(Color.BLACK);
            firstNametext.setTextColor(Color.BLACK);
            lastNametext.setTextColor(Color.BLACK);
            if (firstName.length() == 0)
            {
                firstNametext.setTextColor(Color.RED);
            }
            if (lastName.length() == 0)
            {
                lastNametext.setTextColor(Color.RED);
            }
        }
        else
        {
            startActivity(new Intent(this,RegisterSuccess.class));
        }
    }

    public boolean isEmailValid(String email)
    {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z0-9]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches())
            return true;
        else
            return false;
    }

}