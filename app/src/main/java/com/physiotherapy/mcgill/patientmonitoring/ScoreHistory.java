package com.physiotherapy.mcgill.patientmonitoring;

import android.database.Cursor;
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

    //Test for git
    //Test number 2


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
        int score = 0;

        Cursor cursor = MainActivity.myDb.getRowData(MainActivity.currentPatientId, scoreDay);

        if (cursor.moveToFirst()){
            RadioGroup rg=(RadioGroup)findViewById(R.id.rgScoreType);
            int id = rg.getCheckedRadioButtonId();
            View radioButton = rg.findViewById(id);
            int radioId = rg.indexOfChild(radioButton);
            RadioButton btn = (RadioButton) rg.getChildAt(radioId);
            String selectedScoreType = (String) btn.getText();

            if (selectedScoreType.equals("Barthel")){
                String feedingString = cursor.getString(MainActivity.myDb.COL_FEEDING);
                String dressingString = cursor.getString(MainActivity.myDb.COL_DRESSING);
                String sitStandString = cursor.getString(MainActivity.myDb.COL_SITSTAND);
                String walkingString = cursor.getString(MainActivity.myDb.COL_WALKING);
                String bladderString = cursor.getString(MainActivity.myDb.COL_BLADDER);
                String liftsAffectedString = cursor.getString(MainActivity.myDb.COL_LIFTSAFFECTED);
                String liftsUnaffectedString = cursor.getString(MainActivity.myDb.COL_LIFTSUNAFFECTED);
                int feedingInt = 0;
                int dressingInt = 0;
                int sitStandInt = 0;
                int walkingInt = 0;
                int bladderInt = 0;
                int liftsAffectedInt = 0;
                int liftsUnaffectedInt = 0;
                boolean scoreToggle = true;


                if (feedingString.equals("None")){
                    feedingInt = 0;
                } else if (feedingString.equals("Partial")){
                    feedingInt = 1;
                } else if (feedingString.equals("Full")){
                    feedingInt = 2;
                } else {
                    scoreToggle = false;
                }

                if (dressingString.equals("None")){
                    dressingInt = 0;
                } else if (dressingString.equals("Partial")){
                    dressingInt = 1;
                } else if (dressingString.equals("Full")){
                    dressingInt = 2;
                } else {
                    scoreToggle = false;
                }

                if (sitStandString.equals("None")){
                    sitStandInt = 0;
                } else if (sitStandString.equals("Partial")){
                    sitStandInt = 1;
                } else if (sitStandString.equals("Full")){
                    sitStandInt = 2;
                } else {
                    scoreToggle = false;
                }

                if (walkingString.equals("None")){
                    walkingInt = 0;
                } else if (walkingString.equals("Partial")){
                    walkingInt = 1;
                } else if (walkingString.equals("Full")){
                    walkingInt = 2;
                } else {
                    scoreToggle = false;
                }

                if (bladderString.equals("Foley")){
                    bladderInt = 0;
                } else if (bladderString.equals("Diaper")){
                    bladderInt = 0;
                } else if (bladderString.equals("Bedpan")){
                    bladderInt = 1;
                } else if (bladderString.equals("Toilet")){
                    bladderInt = 2;
                } else {
                    scoreToggle = false;
                }

                if (liftsAffectedString.equals("None")){
                    liftsAffectedInt = 0;
                } else if (liftsAffectedString.equals("Partial")){
                    liftsAffectedInt = 1;
                } else if (liftsAffectedString.equals("Full")){
                    liftsAffectedInt = 2;
                } else {
                    scoreToggle = false;
                }

                if (liftsUnaffectedString.equals("None")){
                    liftsUnaffectedInt = 0;
                } else if (liftsUnaffectedString.equals("Partial")){
                    liftsUnaffectedInt = 1;
                } else if (liftsUnaffectedString.equals("Full")){
                    liftsUnaffectedInt = 2;
                } else {
                    scoreToggle = false;
                }

                if (scoreToggle){
                    int barthel1 = 5*feedingInt;
                    int barthel2 = Math.max(0,5*feedingInt-5);
                    int barthel3 = Math.max(0,5*feedingInt-5);
                    int barthel4 = 5*dressingInt;
                    int barthel5 = Math.min(sitStandInt,walkingInt)*5;
                    int barthel6 = 5*bladderInt;
                    int barthel7 = 5*bladderInt;
                    int barthel8 = 5*sitStandInt;
                    int barthel9 = 5*walkingInt;
                    int barthel10 = Math.min(liftsAffectedInt,liftsUnaffectedInt)*5;
                    score = barthel1 + barthel2 + barthel3 + barthel4 + barthel5 + barthel6 + barthel7 + barthel8 + barthel9 + barthel10;

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
                } else{
                    clearReportFields();
                }



            } else{
                //Berg score
                String liftsAffectedString = cursor.getString(MainActivity.myDb.COL_LIFTSAFFECTED);
                String liftsUnaffectedString = cursor.getString(MainActivity.myDb.COL_LIFTSUNAFFECTED);
                String sitStandString = cursor.getString(MainActivity.myDb.COL_SITSTAND);
                String standString = cursor.getString(MainActivity.myDb.COL_STAND);
                String sittingString = cursor.getString(MainActivity.myDb.COL_SITTING);

                int liftsAffectedInt = 0;
                int liftsUnaffectedInt = 0;
                int sitStandInt = 0;
                int standInt = 0;
                int sittingInt = 0;
                boolean scoreToggle = true;

                if (liftsAffectedString.equals("None")){
                    liftsAffectedInt = 0;
                } else if (liftsAffectedString.equals("Partial")){
                    liftsAffectedInt = 1;
                } else if (liftsAffectedString.equals("Full")){
                    liftsAffectedInt = 2;
                } else {
                    scoreToggle = false;
                }

                if (liftsUnaffectedString.equals("None")){
                    liftsUnaffectedInt = 0;
                } else if (liftsUnaffectedString.equals("Partial")){
                    liftsUnaffectedInt = 1;
                } else if (liftsUnaffectedString.equals("Full")){
                    liftsUnaffectedInt = 2;
                } else {
                    scoreToggle = false;
                }

                if (sitStandString.equals("None")){
                    sitStandInt = 0;
                } else if (sitStandString.equals("Partial")){
                    sitStandInt = 1;
                } else if (sitStandString.equals("Full")){
                    sitStandInt = 2;
                } else {
                    scoreToggle = false;
                }

                if (standString.equals("None")){
                    standInt = 0;
                } else if (standString.equals("Partial")){
                    standInt = 1;
                } else if (standString.equals("Full")){
                    standInt = 2;
                } else {
                    scoreToggle = false;
                }

                if (sittingString.equals("None")){
                    sittingInt = 0;
                } else if (sittingString.equals("Partial")){
                    sittingInt = 1;
                } else if (sittingString.equals("Full")){
                    sittingInt = 2;
                } else {
                    scoreToggle = false;
                }

                if (scoreToggle){
                    if(liftsAffectedInt*liftsUnaffectedInt==4){
                        score = 51;
                    } else if (liftsAffectedInt*liftsUnaffectedInt==2){
                        score = 44;
                    } else if (sitStandInt==2){
                        score = 20;
                    } else if (sitStandInt==1){
                        score = 18;
                    } else if (standInt==2){
                        score = 8;
                    } else if (standInt==1){
                        score = 6;
                    } else if (sittingInt==2){
                        score = 4;
                    } else if (sittingInt==1){
                        score = 2;
                    } else {
                        score = 0;
                    }

                    text_total.setText(score + "/56");

                } else {
                    clearReportFields();

                }

            }
        } else{
            clearReportFields();
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
