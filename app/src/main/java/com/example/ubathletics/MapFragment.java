package com.example.ubathletics;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class MapFragment extends Fragment{          //Sets up super basic fragment
    View inflatedView = null;
    ImageButton favoriteButton = null;
    private Activity activity;
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        this.activity = (Activity) context;
    }
    @Override                                       //TODO: Make do something
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inflatedView = inflater.inflate(R.layout.map_fragment_layout, container, false);

        favoriteButton = inflatedView.findViewById(R.id.favoriteButton);

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(favoriteButton.getTag().equals("Off")){
                    favoriteButton.setImageResource(R.drawable.ic_favorite_on);
                    favoriteButton.setTag("On");
                }
                else if(favoriteButton.getTag().equals("On")){
                    favoriteButton.setImageResource((R.drawable.ic_favorite_off));
                    favoriteButton.setTag("Off");
                }

            }
        });
        return inflatedView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        activity.setTitle(R.string.map_header);
    }
}
