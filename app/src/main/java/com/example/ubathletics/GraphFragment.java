package com.example.ubathletics;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class GraphFragment extends Fragment {
    LineGraphSeries<DataPoint> series;
    int[]dataFromParent;
    GraphView graph;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.graphview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        graph = view.findViewById(R.id.graph);
        //graph.setScrollY(100);
        graph.setTitle("Projected Business Levels");
        //graph.getViewport().setScalable(true);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(25);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(80);

        updateGraph();
    }
    public void updateGraph(){
        graph.removeAllSeries();
        dataFromParent = getArguments().getIntArray("data");
        series = new LineGraphSeries<>();
        for(int i=0;i<25;i++) {
            series.appendData(new DataPoint(i, dataFromParent[i]), true, 24);
        }
        graph.addSeries(series);
    }
}