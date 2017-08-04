package com.example.ships.myapplication.modules;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.example.ships.myapplication.R;
import com.example.ships.myapplication.homepageAndRegistration.DBManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ShortTermProgramList extends AppCompatActivity {
//refer to https://goo.gl/MrPdIi

//testing comment to prevent force push op

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, String> expandableListDetail;
    ExpandableListDataPump dataDump = new ExpandableListDataPump();
    String programTitle;
    String programDetail;
    private static String firstName;
    private static String lastName;
    private static String email;
    private static String uid;

    private void readIntent(){
        Bundle b = getIntent().getExtras();
        firstName = b.getString("firstName");
        lastName = b.getString("lastName");
        email = b.getString("email");
        uid = b.getString("uid");
    }
    private Bundle createBundle(){
        Bundle b = new Bundle();
        b.putString("firstName", firstName);
        b.putString("uid", uid);
        b.putString("lastName", lastName);
        b.putString("email", email);
        b.putString("typeOfTerm","long");
        return b;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_program_list);
        expandableListView = (ExpandableListView) findViewById(R.id.programList);
        readIntent();
        //generate program title and program detail to the list
        dataDump.setData("Self Assessment", "Self Assessment is a tool that helps you to " +
                "understand more about yourself on fear of flying.");
        dataDump.setData("EMDR", "EMDR is a kind of therapeutic tool that helps you " +
                "to distract from the plane by focusing on a moving ball");


        expandableListDetail = dataDump.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());


        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle,
                expandableListDetail, getIntent());
        expandableListView.setAdapter(expandableListAdapter);
    }

    public void onClick(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.add_to_my_program, popup.getMenu());
        popup.show();
    }

    public void goBack(View view) {
        //go back to the previous page
        Intent in = new Intent(this, ExpandableListDataPump.SuggestedModules.class);
        in.putExtras(createBundle());
        startActivity(in);
    }

    public void addToMyProgram(MenuItem item) {

    }
    public void addAll(View view) {
        SQLiteDatabase mySqlDB = DBManager.getInstance(this).getWritableDatabase();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        Cursor resTCat = mySqlDB.rawQuery("SELECT treatmentplan_category.TCID FROM treatmentplan_category WHERE treatmentplan_category.NAME = \"SHORT FLIGHT\"", null);
        resTCat.moveToFirst();
        String sqlInsertTP = "INSERT INTO treatmentplan (UID, TCID, date_added) VALUES(?,?,?)";
        SQLiteStatement stat = mySqlDB.compileStatement(sqlInsertTP);
        stat.bindString(1,uid);
        stat.bindLong(2,resTCat.getInt(0));
        stat.bindString(3, dateFormat.format(date));
        stat.executeInsert();
        Cursor resTID = mySqlDB.rawQuery("SELECT TID FROM treatmentplan WHERE date_added=? ;", new String[]{dateFormat.format(date)});
        resTID.moveToFirst();
        Cursor resSID = mySqlDB.rawQuery("SELECT status.SID FROM status WHERE status.name = \"NOT STARTED\"", null);
        resSID.moveToFirst();
        try {
            int index = 0;
            mySqlDB.beginTransaction();
            String sqlInsertUM = "INSERT INTO user_modules (TID, INDX, SID, MID, progress, last_updated) VALUES(?,?,?,?,0,?)";
            SQLiteStatement statement = mySqlDB.compileStatement(sqlInsertUM);
            for (String s: dataDump.getData().keySet()){
                if (!s.contains("Relax")){
                    if (s.contains("Self")){
                        s = "FAS";
                    }
                    Cursor resMID = mySqlDB.rawQuery("SELECT MID FROM modules WHERE name=?;", new String[]{s});
                    resMID.moveToFirst();
                    statement.clearBindings();
                    statement.bindLong(1,resTID.getInt(0));
                    statement.bindLong(2, index);
                    statement.bindLong(3, resSID.getInt(0));
                    statement.bindLong(4, resMID.getInt(0));
                    statement.bindString(5, dateFormat.format(date));
                    statement.executeInsert();
                    index++;
                }
            }
            mySqlDB.setTransactionSuccessful();
        } catch (Exception e) {
            System.out.println("Exception:"+ e);
        } finally {
            mySqlDB.endTransaction();
        }
    }
    public void goToMangement(View view) {
        startActivity(new Intent(this, MyShortTermProgram.class));
    }
}
