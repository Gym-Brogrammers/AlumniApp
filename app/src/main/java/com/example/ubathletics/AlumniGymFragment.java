package com.example.ubathletics;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AlumniGymFragment extends Fragment{            //Sets up super basic fragment
    @Override                                               //TODO: Make do something
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.alumni_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceStates){
        FragmentTransaction trans;
        trans = getChildFragmentManager().beginTransaction(); //Sets first fragment, will be favorite page eventually
        trans.add(R.id.alumni_graph_frame,new GraphFragment());
        trans.commit();
    }

}
