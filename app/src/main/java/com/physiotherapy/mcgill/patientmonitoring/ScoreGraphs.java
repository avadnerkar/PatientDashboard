package com.physiotherapy.mcgill.patientmonitoring;

import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


public class ScoreGraphs extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_graphs);

        plotGraphs();


    }

    public void plotGraphs(){
        GraphView graphView = (GraphView) findViewById(R.id.graph);
        graphView.removeAllSeries();
        graphView.setTitle("Patient score");
        Viewport viewport = graphView.getViewport();
        GridLabelRenderer renderer = graphView.getGridLabelRenderer();
        renderer.setHorizontalAxisTitle("Days since admission");
        renderer.setPadding(50);

        Cursor cursor = MainActivity.myDb.getAllPatientDataSorted(MainActivity.currentPatientId);
        LineGraphSeries series = new LineGraphSeries();
        DataPoint dataPoint;
        RadioGroup rg=(RadioGroup)findViewById(R.id.rgScoreTypeGraphs);
        int id = rg.getCheckedRadioButtonId();
        View radioButton = rg.findViewById(id);
        int radioId = rg.indexOfChild(radioButton);
        RadioButton btn = (RadioButton) rg.getChildAt(radioId);
        String selectedScoreType = (String) btn.getText();
        int day;
        if (selectedScoreType.equals("Barthel")){
            renderer.setVerticalAxisTitle("Barthel");

            float barthel;
            if (cursor.moveToFirst()) {
                do {
                    barthel = ScoreCalculators.barthelScore(cursor)[0];
                    if (barthel != -1){
                        day = cursor.getInt(cursor.getColumnIndex(DBAdapter.dataMap.get("KEY_DAY")));
                        dataPoint = new DataPoint(day,barthel);
                        series.appendData(dataPoint,true,1000);
                    }

                } while(cursor.moveToNext());
            }
            viewport.setMinY(0);
            viewport.setMaxY(100);
        } else if (selectedScoreType.equals("Berg")){
            renderer.setVerticalAxisTitle("Berg");

            float berg;
            if (cursor.moveToFirst()) {
                do {
                    berg = ScoreCalculators.bergScore(cursor)[0];
                    if (berg != -1){
                        day = cursor.getInt(cursor.getColumnIndex(DBAdapter.dataMap.get("KEY_DAY")));
                        dataPoint = new DataPoint(day,berg);
                        series.appendData(dataPoint,true,1000);
                    }

                } while(cursor.moveToNext());
            }
            viewport.setMinY(0);
            viewport.setMaxY(60);
        } else if (selectedScoreType.equals("CNS")){
            renderer.setVerticalAxisTitle("CNS");

            float cns;
            if (cursor.moveToFirst()) {
                do {
                    cns = ScoreCalculators.cnsScore(cursor)[0];
                    if (cns != -1){
                        day = cursor.getInt(cursor.getColumnIndex(DBAdapter.dataMap.get("KEY_DAY")));
                        dataPoint = new DataPoint(day,cns);
                        series.appendData(dataPoint,true,1000);
                    }

                } while(cursor.moveToNext());
            }
            viewport.setMinY(0);
            viewport.setMaxY(11.5);
        } else if (selectedScoreType.equals("NIHSS")){
            renderer.setVerticalAxisTitle("NIHSS");

            float nihss;
            if (cursor.moveToFirst()) {
                do {
                    nihss = ScoreCalculators.nihssScore(cursor)[0];
                    if (nihss != -1){
                        day = cursor.getInt(cursor.getColumnIndex(DBAdapter.dataMap.get("KEY_DAY")));
                        dataPoint = new DataPoint(day,nihss);
                        series.appendData(dataPoint,true,1000);
                    }

                } while(cursor.moveToNext());
            }
            viewport.setMinY(0);
            viewport.setMaxY(23);
        }



        cursor.close();

        graphView.addSeries(series);
    }

    public void selectBarthel(View view){
        plotGraphs();
    }

    public void selectBerg(View view){
        plotGraphs();
    }

    public void selectCns(View view){
        plotGraphs();
    }

    public void selectNihss(View view){
        plotGraphs();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_score_graphs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }
}
