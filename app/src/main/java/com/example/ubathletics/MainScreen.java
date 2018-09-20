package com.example.ubathletics;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainScreen extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
    }

    public void gotoMapScreen(View view){
        Intent intent = new Intent(this, MapScreen.class);
        startActivity(intent);
    }

    public void gotoContactScreen(View view){
        Intent intent = new Intent(this, MapScreen.class);
        startActivity(intent);
    }

    public void gotoAlumniGymScreen(View view){
        Intent intent = new Intent(this, AlumniGymScreen.class);
        startActivity(intent);
    }
}
