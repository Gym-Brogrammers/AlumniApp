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

    /*First method called when Fragment is attached to an activiy
     *
     * Input: context, the context(activity in our case) the fragment attaches too
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.graphview, container, false);
    }

    /*Method called the first time the view is created. Sets up graph parameters that will be constant throughout
     *
     * Inputs: view; the view containing the fragment, savedInstanceStates; bundle containing any
     * arguments from parent/siblings.
     */
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
    /*
     * Wipes any previous series attached to the graph, and adds new series
     */
    public void updateGraph(){
        graph.removeAllSeries();
        dataFromParent = getArguments().getIntArray("data");        //Gets all data from argument
        //Sets up new series for the data, uses index as x val and value at index as y
        series = new LineGraphSeries<>();
        for(int i=0;i<25;i++) {
            series.appendData(new DataPoint(i, dataFromParent[i]), true, 24);
        }
        //adds series to graph
        graph.addSeries(series);
    }
}