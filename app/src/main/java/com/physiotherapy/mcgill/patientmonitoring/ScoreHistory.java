package com.physiotherapy.mcgill.patientmonitoring;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class ScoreHistory extends ActionBarActivity {

    public static int scoreDay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_history);
        scoreDay = MainActivity.currentDay;
        generateScoreReport();

    }

    public void selectBarthel(View view){
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.scoreLinesLayout);
        linearLayout.setVisibility(View.VISIBLE);
        generateScoreReport();
    }

    public void selectBerg(View view){
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.scoreLinesLayout);
        linearLayout.setVisibility(View.INVISIBLE);
        generateScoreReport();
    }

    public void scoreDecrementDay(View view){
        scoreDay = Math.max(1, scoreDay - 1);
        generateScoreReport();
    }

    public void scoreIncrementDay(View view){
        scoreDay = Math.min(99, scoreDay + 1);
        generateScoreReport();
    }

    public void clearReportFields(){

        TextView text_total = (TextView) findViewById(R.id.score_total);
        TextView text_line1 = (TextView) findViewById(R.id.score_line1);
        TextView text_line2 = (TextView) findViewById(R.id.score_line2);
        TextView text_line3 = (TextView) findViewById(R.id.score_line3);
        TextView text_line4 = (TextView) findViewById(R.id.score_line4);
        TextView text_line5 = (TextView) findViewById(R.id.score_line5);
        TextView text_line6 = (TextView) findViewById(R.id.score_line6);
        TextView text_line7 = (TextView) findViewById(R.id.score_line7);
        TextView text_line8 = (TextView) findViewById(R.id.score_line8);
        TextView text_line9 = (TextView) findViewById(R.id.score_line9);
        TextView text_line10 = (TextView) findViewById(R.id.score_line10);
        text_total.setText("N/A");
        text_line1.setText("");
        text_line2.setText("");
        text_line3.setText("");
        text_line4.setText("");
        text_line5.setText("");
        text_line6.setText("");
        text_line7.setText("");
        text_line8.setText("");
        text_line9.setText("");
        text_line10.setText("");
    }

    public void generateScoreReport(){
        TextView textView = (TextView) findViewById(R.id.dayNumberScore);
        TextView text_total = (TextView) findViewById(R.id.score_total);
        TextView text_line1 = (TextView) findViewById(R.id.score_line1);
        TextView text_line2 = (TextView) findViewById(R.id.score_line2);
        TextView text_line3 = (TextView) findViewById(R.id.score_line3);
        TextView text_line4 = (TextView) findViewById(R.id.score_line4);
        TextView text_line5 = (TextView) findViewById(R.id.score_line5);
        TextView text_line6 = (TextView) findViewById(R.id.score_line6);
        TextView text_line7 = (TextView) findViewById(R.id.score_line7);
        TextView text_line8 = (TextView) findViewById(R.id.score_line8);
        TextView text_line9 = (TextView) findViewById(R.id.score_line9);
        TextView text_line10 = (TextView) findViewById(R.id.score_line10);
        textView.setText("Day " + scoreDay);

        RadioGroup rg=(RadioGroup)findViewById(R.id.rgScoreType);
        int id = rg.getCheckedRadioButtonId();
        View radioButton = rg.findViewById(id);
        int radioId = rg.indexOfChild(radioButton);
        RadioButton btn = (RadioButton) rg.getChildAt(radioId);
        String selectedScoreType = (String) btn.getText();

        float[] scoreArray = MainActivity.calculateScores(scoreDay, selectedScoreType);

        if (selectedScoreType.equals("Barthel")){
            if (scoreArray[0] == -1){
                clearReportFields();
            } else {
                float score = scoreArray[0];
                float barthel1 = scoreArray[1];
                float barthel2 = scoreArray[2];
                float barthel3 = scoreArray[3];
                float barthel4 = scoreArray[4];
                float barthel5 = scoreArray[5];
                float barthel6 = scoreArray[6];
                float barthel7 = scoreArray[7];
                float barthel8 = scoreArray[8];
                float barthel9 = scoreArray[9];
                float barthel10 = scoreArray[10];

                text_total.setText(score + "/100");
                text_line1.setText(String.valueOf(barthel1));
                text_line2.setText(String.valueOf(barthel2));
                text_line3.setText(String.valueOf(barthel3));
                text_line4.setText(String.valueOf(barthel4));
                text_line5.setText(String.valueOf(barthel5));
                text_line6.setText(String.valueOf(barthel6));
                text_line7.setText(String.valueOf(barthel7));
                text_line8.setText(String.valueOf(barthel8));
                text_line9.setText(String.valueOf(barthel9));
                text_line10.setText(String.valueOf(barthel10));
            }

        } else{
            if (scoreArray[0] == -1){
                clearReportFields();
            } else {
                float score = scoreArray[0];
                text_total.setText(score + "/56");
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_score_history, menu);
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
