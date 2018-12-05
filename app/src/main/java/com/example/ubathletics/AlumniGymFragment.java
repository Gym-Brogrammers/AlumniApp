package com.example.ubathletics;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AlumniGymFragment extends Fragment {            //Sets up fragment for Alumni Gym
    View inflatedView = null;
    ImageButton favoriteButton = null;
    EditText dateView = null;
    Spinner predictType = null;
    boolean _isProper=false;
    Activity activity;
    GraphFragment alumniGraph;

    /*First method called when Fragment is attached to an activiy
     * sets up local variables
     *
     * Input: context, the context(activity in our case) the fragment attaches too
     */
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        this.activity = (Activity) context;     //saves context for use later
    }

    /*First method called when view is created. Contains code for a cohesive favorite button, and for
     * a date field which takes in a proper date from a user, and then changes graph to reflect date
     *
     * Input: inflater; the layout for the page, container; the container the fragment lives in,
     * savedInstanceStates; a bundle containing any arguments sent to the fragment
     * Output: View, the current view, modified from the input view
     */
    @Override                                               //TODO: Make do something
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflatedView = inflater.inflate(R.layout.alumni_fragment_layout, container, false);

        //Changes header to alumni gym
        activity.setTitle(R.string.alumni_header);

        //Sets up favorite button backend, checks to see what current text of favorite_screen argument is
        //and changes is to an empty string if null, or turns favorite button yellow is alumni is the favorite screen
        favoriteButton = inflatedView.findViewById(R.id.favoriteButton);
        SharedPreferences pref = inflatedView.getContext().getSharedPreferences(getString(R.string.favorite_screen_id),Context.MODE_PRIVATE);
        String test = pref.getString(getString(R.string.favorite_screen_id),null);
        if(test==null){
            test = " ";
        }
        if(test.equals("AlumniGymFragment")){
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
                //If alumni page is not favorited, favorite it. If alumni page is favorited, turn the button off,
                //Setting favorite to the default value
                if(!favorite.equals("AlumniGymFragment")){
                    favoriteButton.setImageResource(R.drawable.ic_favorite_on);
                    favorite = "AlumniGymFragment";
                }
                else{
                    favoriteButton.setImageResource((R.drawable.ic_favorite_off));
                    favorite = "";
                }
                editor.putString(getString(R.string.favorite_screen_id),favorite);
                editor.apply();
            }
        });
        //Sets up spinner to select prediction method
        predictType = inflatedView.findViewById(R.id.spinner);
        //predictType.setOnItemSelectedListener(this);

        List<String> predictionModes = new ArrayList<String>();
        predictionModes.add("Predict by Day of Week");
        predictionModes.add("Predict by Day of Month");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, predictionModes);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        predictType.setAdapter(arrayAdapter);

        //Sets up Text editor field for date entry
        dateView = inflatedView.findViewById(R.id.date_field);
        //Text listener for dateView
        dateView.addTextChangedListener(new TextWatcher() {
            int beforeChange;
            int afterChange;
            @Override
            //Both before and on text changed methods save a value to see if user is backspacing or going forward
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                afterChange=after;
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                beforeChange=before;
            }

            @Override
            public void afterTextChanged(Editable s) {
                //If the user is backspacing, remove the char before the cursor
                if(((s.length()==3)||s.length()==6)&&beforeChange>afterChange){
                    s.delete(s.length()-1,s.length());
                }
                //If the length is 11 or larger, delete the end of the sequence, as the date can only be 10 chars
                else if(s.length()>=11){
                    s.delete(s.length()-1,s.length());
                }
                //Insert slashes for the user if they do not desire to add their own
                if((s.length()>=3)&&(s.charAt(2)!='/')){
                    s.insert(2,"/");
                }
                else if((s.length()>=6)&&(s.charAt(5)!='/')){
                    s.insert(5,"/");
                }

                //Disallow periods from being used, notify user
                if(s.length()>0) {
                    if (s.charAt(s.length() - 1) == '.') {
                        s.delete(s.length() - 1, s.length());
                        Toast toast = Toast.makeText(inflatedView.getContext(), "Periods are not allowed", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, -340);
                        toast.show();
                    }
                }

                //If the user is in the year field, ensure that year entered is between this year and next,
                //If the date is proper, change _isProper variable to reflect
                if(s.length()==10){
                    if((s.charAt(6)!='2')&&(s.charAt(7)!='0')&&(s.charAt(8)!='1')&&((s.charAt(9)<'7')||(s.charAt(9)>'9'))){
                        Toast toast = Toast.makeText(inflatedView.getContext(),"Please enter date between last year and next year",Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL,0,-340);
                        toast.show();
                    }
                    else{_isProper=true;}
                }
                //If the user is in the day field, make sure the proper number of days is allowed based on month entered, stop user if not.
                //Very long conditional because of the strange nature of our calendar
                else if(s.length()>=6) {
                    if (((Integer.parseInt(s.toString().substring(0,2))==2)&&(Integer.parseInt(s.toString().substring(3,5))>28))||
                            ((Integer.parseInt(s.toString().substring(3,5))>30)&&((Integer.parseInt(s.toString().substring(0,2))==4)||
                            (Integer.parseInt(s.toString().substring(0,2))==6)||(Integer.parseInt(s.toString().substring(0,2))==9)||(Integer.parseInt(s.toString().substring(0,2))==11)))
                            ||((Integer.parseInt(s.toString().substring(3,5))>31)))
                    {
                        s.delete(5,6);
                        Toast toast = Toast.makeText(inflatedView.getContext(),"Please enter a proper date",Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL,0,-340);
                        toast.show();
                    }
                }
                //If the user is in the month field, only allow user to enter a valid month, if not stop user and notify
                else if(s.length()>=3) {
                    if((Integer.parseInt(s.toString().substring(0,2))>12)||(Integer.parseInt(s.toString().substring(0,2))<1)) {
                        s.delete(2,3);
                        Toast toast = Toast.makeText(inflatedView.getContext(),"Please enter a proper date",Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL,0,-340);
                        toast.show();
                    }
                }

            }
        });

        //Listener for when the user hits enter in dateView field
        dateView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId==EditorInfo.IME_ACTION_DONE) {
                    //Get data from date given
                    if(dateView.getText().length()==10) {
                        String dateString = String.valueOf(dateView.getText());
                        int month = Integer.parseInt(dateString.substring(0, 2));
                        int day = Integer.parseInt(dateString.substring(3, 5));
                        int year = Integer.parseInt(dateString.substring(6, 10));
                    }

                    //Populate data array with provided data, currently random numbers just to test
                    int[] data = new int[25];
                    Random rand = new Random();
                    for(int i=0;i<25;i++){
                        data[i]= rand.nextInt(80);
                    }
                    //Update the graph and return true
                    updateGraph(data);
                    return true;
                }
                return false;
            }
        });

        return inflatedView;
    }

    /*Method called the first time the view is created. Populates the graph with initial data and
     * creates the graph.
     *
     * Inputs: view; the view containing the fragment, savedInstanceStates; bundle containing any
     * arguments from parent/siblings.
     */
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Create initial data
        int[] data = new int[25];
        data[4]=6;

        //Initialize the graph and associated structures, populates the graph with initial data
        FragmentTransaction trans;
        alumniGraph = new GraphFragment();
        Bundle args = new Bundle();
        args.putIntArray("data",data);
        alumniGraph.setArguments(args);
        trans = getChildFragmentManager().beginTransaction();
        trans.add(R.id.alumni_graph_frame, alumniGraph);
        trans.commit();
    }

    /* Updates graph with current data.
     *
     * Input: data, a 25 integer array of data for the graph
     */
    public void updateGraph(int[] data){
        //Update the graph with data optained from argument
        Bundle args = new Bundle();
        args.putIntArray("data",data);
        alumniGraph.setArguments(args);
        alumniGraph.updateGraph();
    }

}