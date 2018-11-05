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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class AlumniGymFragment extends Fragment {            //Sets up super basic fragment
    View inflatedView = null;
    ImageButton favoriteButton = null;
    EditText dateView = null;
    Activity activity;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        this.activity = (Activity) context;
    }

    @Override                                               //TODO: Make do something
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflatedView = inflater.inflate(R.layout.alumni_fragment_layout, container, false);

        activity.setTitle(R.string.alumni_header);

        favoriteButton = inflatedView.findViewById(R.id.favoriteButton);
        SharedPreferences pref = inflatedView.getContext().getSharedPreferences(getString(R.string.favorite_screen_id),Context.MODE_PRIVATE);
        String test = pref.getString(getString(R.string.favorite_screen_id),null);
        if(test==null){
            test = " ";
        }
        if(test.equals("AlumniGymFragment")){
            favoriteButton.setImageResource(R.drawable.ic_favorite_on);
        }
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = v.getContext().getSharedPreferences(getString(R.string.favorite_screen_id),Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                String favorite = pref.getString(getString(R.string.favorite_screen_id),null);
                if(favorite==null){
                    favorite=" ";
                }
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

        dateView = inflatedView.findViewById(R.id.date_field);
        dateView.addTextChangedListener(new TextWatcher() {
            int beforeChange;
            int afterChange;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                afterChange=after;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                beforeChange=before;
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(((s.length()==2)||(s.length()==5))&&(beforeChange<=afterChange)){
                    s.append("/");
                }
                else if(((s.length()==3)||s.length()==6)&&beforeChange>afterChange){
                    s.delete(s.length()-1,s.length());
                }
                else if(s.length()>=11){
                    s.delete(s.length()-1,s.length());
                }
                if(s.length()>=3) {
                    if((s.charAt(0) >= '2') || ((s.charAt(0) == '1') && (s.charAt(1) > '2'))) {
                        s.delete(2,3);
                        Toast toast = Toast.makeText(inflatedView.getContext(),"Please enter a proper date",Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL,0,-340);
                        toast.show();
                    }
                }
                else if(s.length()>=6) {
                    if ((s.charAt(3) >= '4') || ((s.charAt(3) == '3') && (s.charAt(4) >= '3'))) {
                        s.delete(5,6);
                        Toast toast = Toast.makeText(inflatedView.getContext(),"Please enter a proper date",Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL,0,-340);
                        toast.show();
                    }
                }
                else if(s.length()>=7){
                    if((s.charAt(6)!='2')&&(s.charAt(7)!='0')&&(s.charAt(8)!='1')&&((s.charAt(9)<'7')||(s.charAt(9)>'9'))){
                        Toast toast = Toast.makeText(inflatedView.getContext(),"Please enter date between last year and next year",Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL,0,-340);
                        toast.show();
                    }
                }
            }
        });

        return inflatedView;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentTransaction trans;
        GraphFragment alumniGraph = new GraphFragment();
        int[] data = new int[25];
        data[4]=6;

        Bundle args = new Bundle();
        args.putIntArray("data",data);
        alumniGraph.setArguments(args);
        trans = getChildFragmentManager().beginTransaction();
        trans.add(R.id.alumni_graph_frame, alumniGraph);
        trans.commit();
    }

}