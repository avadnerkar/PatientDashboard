package com.physiotherapy.mcgill.patientmonitoring;

import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;


public class DischargePatientForm extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discharge_patient_form);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_discharge_patient_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.discharge_save) {

            DatePicker datePicker;
            int day, month, year;
            EditText editText;
            String dischargeDate, totalOT, totalPT, totalSLT, mocaScore, customScore, customMax;

            editText = (EditText) findViewById(R.id.mocaScore);
            mocaScore = editText.getText().toString();

            editText = (EditText) findViewById(R.id.customScore);
            customScore = editText.getText().toString();

            editText = (EditText) findViewById(R.id.customMax);
            customMax = editText.getText().toString();

            datePicker = (DatePicker) findViewById(R.id.discharge_date_picker);
            day = datePicker.getDayOfMonth();
            month = datePicker.getMonth();
            year = datePicker.getYear();
            dischargeDate = year + "-" + (month+1) + "-" + day;

            editText = (EditText) findViewById(R.id.edit_totalOT);
            totalOT = editText.getText().toString();

            editText = (EditText) findViewById(R.id.edit_totalPT);
            totalPT = editText.getText().toString();

            editText = (EditText) findViewById(R.id.edit_totalSLT);
            totalSLT = editText.getText().toString();

            MainActivity.myDb.dischargeRowPatient(MainActivity.currentPatientId, dischargeDate, mocaScore, customScore, customMax, totalOT, totalPT, totalSLT);

            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
