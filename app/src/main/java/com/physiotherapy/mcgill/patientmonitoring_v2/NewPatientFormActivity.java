package com.physiotherapy.mcgill.patientmonitoring_v2;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;


public class NewPatientFormActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_patient_form);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_patient_form, menu);
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

        if (id == R.id.patient_save) {
            EditText editText_first = (EditText) findViewById(R.id.edit_first_name);
            String firstName = editText_first.getText().toString();

            EditText editText_last = (EditText) findViewById(R.id.edit_last_name);
            String lastName = editText_last.getText().toString();

            EditText editText_ID = (EditText) findViewById(R.id.edit_hospital_ID);
            String hospitalId = editText_ID.getText().toString();

            long newID = MainActivity.myDb.insertRowPatient(firstName, lastName, hospitalId);

            //MainActivity.updatePatientList();
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
