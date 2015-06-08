package com.physiotherapy.mcgill.patientmonitoring;

import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class ScoreHistory extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_history);

        generateScoreReport();

    }

    public void generateScoreReport(){
        TextView textView = (TextView) findViewById(R.id.dayNumberScore);
        textView.setText("Day " + MainActivity.currentDay);
        int score = 0;

        Cursor cursor = MainActivity.myDb.getRowData(MainActivity.currentPatientId, MainActivity.currentDay);

        if (cursor.moveToFirst()){
            RadioGroup rg=(RadioGroup)findViewById(R.id.rgStand);
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

                if (feedingString.equals("None")){
                    feedingInt = 0;
                } else if (feedingString.equals("Partial")){
                    feedingInt = 1;
                } else if (feedingString.equals("Full")){
                    feedingInt = 2;
                }

                if (dressingString.equals("None")){
                    dressingInt = 0;
                } else if (dressingString.equals("Partial")){
                    dressingInt = 1;
                } else if (dressingString.equals("Full")){
                    dressingInt = 2;
                }

                if (sitStandString.equals("None")){
                    sitStandInt = 0;
                } else if (sitStandString.equals("Partial")){
                    sitStandInt = 1;
                } else if (sitStandString.equals("Full")){
                    sitStandInt = 2;
                }

                if (walkingString.equals("None")){
                    walkingInt = 0;
                } else if (walkingString.equals("Partial")){
                    walkingInt = 1;
                } else if (walkingString.equals("Full")){
                    walkingInt = 2;
                }

                if (bladderString.equals("Foley")){
                    bladderInt = 0;
                } else if (bladderString.equals("Diaper")){
                    bladderInt = 0;
                } else if (bladderString.equals("Bedpan")){
                    bladderInt = 1;
                } else if (bladderString.equals("Toilet")){
                    bladderInt = 2;
                }

                if (liftsAffectedString.equals("None")){
                    liftsAffectedInt = 0;
                } else if (liftsAffectedString.equals("Partial")){
                    liftsAffectedInt = 1;
                } else if (liftsAffectedString.equals("Full")){
                    liftsAffectedInt = 2;
                }

                if (liftsUnaffectedString.equals("None")){
                    liftsUnaffectedInt = 0;
                } else if (liftsUnaffectedString.equals("Partial")){
                    liftsUnaffectedInt = 1;
                } else if (liftsUnaffectedString.equals("Full")){
                    liftsUnaffectedInt = 2;
                }

                int barthel1 = 5*feedingInt;
                int barthel2 = Math.max(0,5*feedingInt-5);
                int barthel3 = Math.max(0,5*feedingInt-5);
                int barthel4 = 5*dressingInt;

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
