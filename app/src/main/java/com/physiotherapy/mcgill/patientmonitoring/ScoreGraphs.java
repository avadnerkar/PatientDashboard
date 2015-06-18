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

        Cursor cursor = MainActivity.myDb.getAllPatientData(MainActivity.currentPatientId);
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
            viewport.setMinY(0);
            viewport.setMaxY(100);
            int barthel;
            if (cursor.moveToFirst()) {
                do {

                    if (!cursor.getString(MainActivity.myDb.COL_BARTHEL).equals("-1")){
                        day = Integer.parseInt(cursor.getString(MainActivity.myDb.COL_DAY));
                        barthel = Integer.parseInt(cursor.getString(MainActivity.myDb.COL_BARTHEL));
                        dataPoint = new DataPoint(day,barthel);
                        series.appendData(dataPoint,true,1000);
                    }

                } while(cursor.moveToNext());
            }
        } else if (selectedScoreType.equals("Berg")){
            renderer.setVerticalAxisTitle("Berg");
            viewport.setMinY(0);
            viewport.setMaxY(60);
            int berg;
            if (cursor.moveToFirst()) {
                do {

                    if (!cursor.getString(MainActivity.myDb.COL_BERG).equals("-1")){
                        day = Integer.parseInt(cursor.getString(MainActivity.myDb.COL_DAY));
                        berg = Integer.parseInt(cursor.getString(MainActivity.myDb.COL_BERG));
                        dataPoint = new DataPoint(day,berg);
                        series.appendData(dataPoint,true,1000);
                    }

                } while(cursor.moveToNext());
            }
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
