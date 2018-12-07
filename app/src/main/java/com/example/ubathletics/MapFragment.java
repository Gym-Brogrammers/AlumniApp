package com.example.ubathletics;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ToggleButton;

import static android.content.Context.MODE_PRIVATE;

public class MapFragment extends Fragment{          //Sets up map fragment
    View inflatedView = null;
    ImageButton favoriteButton = null;
    private Activity activity;
    private SouthCampusFragment southFrag = new SouthCampusFragment();
    private NorthCampusFragment northFrag = new NorthCampusFragment();
    /*First method called when Fragment is attached to an activiy
     * sets up local variables
     *
     * Input: context, the context(activity in our case) the fragment attaches too
     */
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        this.activity = (Activity) context;
    }

    /*First method called when view is created. Contains code for a cohesive favorite button.
     *
     * Input: inflater; the layout for the page, container; the container the fragment lives in,
     * savedInstanceStates; a bundle containing any arguments sent to the fragment
     * Output: View, the current view, modified from the input view
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflatedView = inflater.inflate(R.layout.map_fragment_layout, container, false);

        //Changes header to Map
        activity.setTitle(R.string.map_header);

        //Sets up favorite button backend, checks to see what current text of favorite_screen argument is
        //and changes is to an empty string if null, or turns favorite button yellow is alumni is the favorite screen
        favoriteButton = inflatedView.findViewById(R.id.favoriteButton);
        SharedPreferences pref = inflatedView.getContext().getSharedPreferences(getString(R.string.favorite_screen_id),MODE_PRIVATE);
        String test = pref.getString(getString(R.string.favorite_screen_id),null);
        if(test==null){
            test = " ";
        }
        if(test.equals("MapFragment")){
            favoriteButton.setImageResource(R.drawable.ic_favorite_on);
        }

        //Listener for favorite button
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sets up argument describing which button is the favorite, and editor to change the argument if needed
                SharedPreferences pref = v.getContext().getSharedPreferences(getString(R.string.favorite_screen_id),MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                String favorite = pref.getString(getString(R.string.favorite_screen_id),null);
                if(favorite==null){
                    favorite=" ";
                }
                //If map page is not favorited, favorite it. If map page is favorited, turn the button off,
                //Setting favorite to the default value
                if(!favorite.equals("MapFragment")){
                    favoriteButton.setImageResource(R.drawable.ic_favorite_on);
                    favorite = "MapFragment";
                }
                else{
                    favoriteButton.setImageResource((R.drawable.ic_favorite_off));
                    favorite = "";
                }
                editor.putString(getString(R.string.favorite_screen_id),favorite);
                editor.apply();
            }
        });


        ToggleButton toggle =inflatedView.findViewById(R.id.mapToggle);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                FragmentTransaction trans=getChildFragmentManager().beginTransaction();
                if (isChecked) {
                    trans.replace(R.id.map_frame, southFrag);
                } else {
                    trans.replace(R.id.map_frame, northFrag);
                }
                trans.commit();
            }
        });

        return inflatedView;
    }

    /*Method called the first time the view is created.
     *
     * Inputs: view; the view containing the fragment, savedInstanceStates; bundle containing any
     * arguments from parent/siblings.
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);

        FragmentTransaction tran;
        tran = getChildFragmentManager().beginTransaction();
        tran.add(R.id.map_frame, northFrag);
        tran.commit();
    }

}
