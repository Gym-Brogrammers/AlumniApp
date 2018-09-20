package com.example.ubathletics;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ContactScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_screen);
    }

    public void gotoMapScreen(View view){
        Intent intent = new Intent(this, MapScreen.class);
        startActivity(intent);
    }

    public void gotoMainScreen(View view){
        Intent intent = new Intent(this, MainScreen.class);
        startActivity(intent);
    }

    public void gotoAlumniGymScreen(View view){
        Intent intent = new Intent(this, AlumniGymScreen.class);
        startActivity(intent);
    }
}
