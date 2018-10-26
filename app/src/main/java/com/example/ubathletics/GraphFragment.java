package com.example.ubathletics;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class GraphFragment extends Fragment {
    LineGraphSeries<DataPoint> series;
    int[]dataFromParent;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle args = getArguments();
        dataFromParent = args.getIntArray("data");
        return inflater.inflate(R.layout.graphview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        GraphView graph = view.findViewById(R.id.graph);
        //graph.setScrollY(100);
        graph.setTitle("Projected Business Levels");
        //graph.getViewport().setScalable(true);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(24);
        graph.getGridLabelRenderer().setNumHorizontalLabels(6);
        series = new LineGraphSeries<>();
        for(int i=0;i<25;i++) {
            series.appendData(new DataPoint(i, dataFromParent[i]), true, 24);
        }
        graph.addSeries(series);
    }}