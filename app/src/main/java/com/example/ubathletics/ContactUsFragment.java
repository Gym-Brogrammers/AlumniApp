package com.example.ubathletics;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class ContactUsFragment extends Fragment {            //Sets up fragment with information to contact UB/ us, the devs
    View inflatedView = null;
    ImageButton favoriteButton = null;
    Activity activity;

    /*First method called when Fragment is attached to an activiy
     * sets up local variables
     *
     * Input: context, the context(activity in our case) the fragment attaches too
     */
    @Override
    public void onAttach(Context context){

        super.onAttach(context);
        this.activity = (Activity) context;    //saves context for use later
    }

    /*First method called when view is created. Contains code for a cohesive favorite button.
     *
     * Input: inflater; the layout for the page, container; the container the fragment lives in,
     * savedInstanceStates; a bundle containing any arguments sent to the fragment
     * Output: View, the current view, modified from the input view
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflatedView = inflater.inflate(R.layout.contactus_layout, container, false);

        //Changes header to Contact Us
        activity.setTitle(R.string.contactus_header);

        //Sets up favorite button backend, checks to see what current text of favorite_screen argument is
        //and changes is to an empty string if null, or turns favorite button yellow is alumni is the favorite screen
        favoriteButton = inflatedView.findViewById(R.id.favoriteButton);
        SharedPreferences pref = inflatedView.getContext().getSharedPreferences(getString(R.string.favorite_screen_id),Context.MODE_PRIVATE);
        String test = pref.getString(getString(R.string.favorite_screen_id),null);
        if(test==null){
            test = " ";
        }
        if(test.equals("ContactUsFragment")){
            favoriteButton.setImageResource(R.drawable.ic_favorite_on);
        }

        //Listener for favorite button
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sets up argument describing which button is the favorite, and editor to change the argument if needed
                SharedPreferences pref = v.getContext().getSharedPreferences(getString(R.string.favorite_screen_id),Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                String favorite = pref.getString(getString(R.string.favorite_screen_id),null);
                if(favorite==null){
                    favorite=" ";
                }
                //If contact us page is not favorited, favorite it. If contact us page is favorited, turn the button off,
                //Setting favorite to the default value
                if(!favorite.equals("ContactUsFragment")){
                    favoriteButton.setImageResource(R.drawable.ic_favorite_on);
                    favorite = "ContactUsFragment";
                }
                else{
                    favoriteButton.setImageResource((R.drawable.ic_favorite_off));
                    favorite = "";
                }
                editor.putString(getString(R.string.favorite_screen_id),favorite);
                editor.apply();
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
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
    }
}