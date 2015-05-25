package com.physiotherapy.mcgill.patientmonitoring;

import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class ViewHistory extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);

        Cursor cursor = MainActivity.myDb.getAllPatientData(MainActivity.currentPatientId);

        String historyString = "";
        if (cursor.moveToFirst()) {
            do {
                // Process the data:


                historyString = historyString
                        + cursor.getInt(DBAdapter.COL_PARENTID) + " "
                        + cursor.getInt(DBAdapter.COL_DAY) + " "
                        + cursor.getString(DBAdapter.COL_PEG) + " "
                        + cursor.getString(DBAdapter.COL_NG) + " "
                        + cursor.getString(DBAdapter.COL_O2) + " \n";

            } while(cursor.moveToNext());
        }

        TextView textView = (TextView) findViewById(R.id.textview_history);
        textView.setText(historyString);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_history, menu);
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
