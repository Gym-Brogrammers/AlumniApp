package com.example.ubathletics;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.support.annotation.NonNull;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout menu;                      //Navigation menu main access
    private FragmentTransaction trans;              //Used to switch fragments, or pages to the user

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);    //Ties main screen to layout

        Toolbar toolbar = findViewById(R.id.toolbar);       //Adds custom toolbar to top of the app
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        trans = getSupportFragmentManager().beginTransaction();
        SharedPreferences favoriteScreenPref = this.getSharedPreferences(getString(R.string.favorite_screen_id),MODE_PRIVATE);
        String favoriteScreen = favoriteScreenPref.getString(getString(R.string.favorite_screen_id),getString(R.string.favorite_screen_default));

        if(favoriteScreen.equals("AlumniGymFragment")){
            trans.add(R.id.content_frame,new AlumniGymFragment());
        }
        else if(favoriteScreen.equals("MapFragment")){
            trans.add(R.id.content_frame,new MapFragment());
        }
        else if(favoriteScreen.equals("ContactUsFragment")){
            trans.add(R.id.content_frame,new ContactUsFragment());
        }
        else{
            trans.add(R.id.content_frame,new MapFragment());
        }
        trans.commit();


        menu = findViewById(R.id.drawer_layout);                //Adds listener to menu, needed to
        menu.addDrawerListener(                                 //Let menu open
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                        // TODO:Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(@NonNull View drawerView) {
                        // TODO:Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(@NonNull View drawerView) {
                        // TODO:Respond when the drawer is closed
                    }
                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // TODO:Respond when the drawer motion state changes
                    }
                }
        );

        NavigationView navigationView = findViewById(R.id.nav_view);     //Listens for item clicks
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        menuItem.setChecked(true);              //Darkens selected menu
                        menu.closeDrawers();                    //Closes drawer
                        Fragment currFrag = new Fragment();     //Creates new fragment
                        switch (menuItem.getItemId()){          //Looks to see what is clicked
                            case R.id.nav_gym:
                                currFrag = new AlumniGymFragment(); //Set fragment as Alumni
                                break;
                            case R.id.nav_map:
                                currFrag=new MapFragment();     //Set fragment as Map
                                break;
                            case R.id.nav_contact:
                                currFrag=new ContactUsFragment();
                                break;
                        }
                        trans = getSupportFragmentManager().beginTransaction();     //Replaces current
                        trans.replace(R.id.content_frame,currFrag);                 //Fragment with new
                        trans.addToBackStack(null);
                        trans.commitAllowingStateLoss();
                        return true;
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {           //Opens menu when clicked
        switch (item.getItemId()) {
            case android.R.id.home:
                menu.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
