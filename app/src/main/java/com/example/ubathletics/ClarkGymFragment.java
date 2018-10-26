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
import android.widget.ImageButton;

public class ClarkGymFragment extends Fragment{
    View inflatedView = null;
    ImageButton favoriteButton = null;
    Activity activity;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        this.activity = (Activity) context;
    }

    @Override                                               //TODO: Make do something
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflatedView = inflater.inflate(R.layout.clark_fragment_layout, container, false);

        activity.setTitle(R.string.clark_header);


        favoriteButton = inflatedView.findViewById(R.id.favoriteButton);
        SharedPreferences pref = inflatedView.getContext().getSharedPreferences(getString(R.string.favorite_screen_id),Context.MODE_PRIVATE);
        String test = pref.getString(getString(R.string.favorite_screen_id),null);
        if(test.equals("ClarkGymFragment")){
            favoriteButton.setImageResource(R.drawable.ic_favorite_on);
        }

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = v.getContext().getSharedPreferences(getString(R.string.favorite_screen_id),Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                String favorite = pref.getString(getString(R.string.favorite_screen_id),null);
                if(!favorite.equals("ClarkGymFragment")){
                    favoriteButton.setImageResource(R.drawable.ic_favorite_on);
                    favorite = "ClarkGymFragment";
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

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentTransaction trans;
        GraphFragment clarkGraph = new GraphFragment();
        int[] data = new int[24];
        data[6]=4;

        Bundle args = new Bundle();
        args.putIntArray("data",data);
        clarkGraph.setArguments(args);
        trans = getChildFragmentManager().beginTransaction();
        trans.add(R.id.clark_graph_frame, clarkGraph);
        trans.commit();
    }

}
