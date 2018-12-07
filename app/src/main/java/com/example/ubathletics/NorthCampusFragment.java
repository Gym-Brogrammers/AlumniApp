package com.example.ubathletics;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Calendar;

public class NorthCampusFragment extends Fragment {
    private Activity activity;
    private View inflatedView;
    ArrayList<String[]> popCounts = null;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        this.activity = (Activity) context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflatedView = inflater.inflate(R.layout.north_map_layout, container, false);

        Calendar curr = Calendar.getInstance();
        popCounts = new DataHelper(getResources().openRawResource(R.raw.pseudo_data)).read();

        int year = curr.YEAR;
        int day = curr.DAY_OF_MONTH;
        int month = curr.MONTH;
        int hour = curr.HOUR;

        //Populate data array with zeros as a fail-safe
        int[] data = new int[25];
        for (int i = 0; i < 25; i++) {
            data[i] = 0;
        }

        //Match entered date
        for (int pcIndex = 0; pcIndex < popCounts.size(); pcIndex++) {
            if (month == Integer.parseInt(popCounts.get(pcIndex)[0]) && day == Integer.parseInt(popCounts.get(pcIndex)[1]) && year == Integer.parseInt(popCounts.get(pcIndex)[2])) {
                //If there is data, use it
                if (popCounts.get(pcIndex).length > 3) {
                    for (int i = 0; i < 24; i++) {
                        data[i] = Integer.parseInt(popCounts.get(pcIndex)[i + 3]);
                    }
                }
            }
        }

        if(data[hour]<=20){
            inflatedView.findViewById(R.id.alumniButton).setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green));
        }
        else if(data[hour]>20 && data[hour]<=40){
            inflatedView.findViewById(R.id.alumniButton).setBackgroundColor(ContextCompat.getColor(getContext(), R.color.yellow));
        }
        else if(data[hour]>40){
            inflatedView.findViewById(R.id.clarkButton).setBackgroundColor(ContextCompat.getColor(getContext(), R.color.red));
        }

        return inflatedView;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inflatedView.findViewById(R.id.alumniButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
                trans.replace(getActivity().findViewById(R.id.content_frame).getId(), new AlumniGymFragment());
                trans.commit();
            }
        });
    }

}
