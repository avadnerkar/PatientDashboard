package com.physiotherapy.mcgill.patientmonitoring;

import android.app.DatePickerDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;


public class NewPatientFormActivity extends ActionBarActivity {
    TextView txtDateOT;
    TextView txtDateSwallow;
    TextView txtDatePT;
    TextView txtDateSLT;
    public String dateFirstOTString = "";
    public String dateFirstSwallowString = "";
    public String dateFirstPTString = "";
    public String dateFirstSLTString = "";
    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_patient_form);
        txtDateOT = (TextView) findViewById(R.id.textDateFirstOT);
        txtDateSwallow = (TextView) findViewById(R.id.textDateFirstSwallow);
        txtDatePT = (TextView) findViewById(R.id.textDateFirstPT);
        txtDateSLT = (TextView) findViewById(R.id.textDateFirstSLT);
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

            DatePicker datePicker = (DatePicker) findViewById(R.id.date_picker);
            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth();
            int year = datePicker.getYear();
            String admissionDate = year + "-" + (month+1) + "-" + day;

            long newID = MainActivity.myDb.insertRowPatient(firstName, lastName, hospitalId, admissionDate);




            //MainActivity.updatePatientList();
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void selectDateOTFunc(View view){

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // Display Selected date in textbox
                        dateFirstOTString = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        txtDateOT.setText(dateFirstOTString);

                    }
                }, mYear, mMonth, mDay);
        dpd.show();
    }

    public void selectDateSwallowFunc(View view){

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {
                // Display Selected date in textbox
                dateFirstSwallowString = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                txtDateSwallow.setText(dateFirstSwallowString);

            }
        }, mYear, mMonth, mDay);
        dpd.show();
    }

    public void selectDatePTFunc(View view){

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {
                // Display Selected date in textbox
                dateFirstPTString = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                txtDatePT.setText(dateFirstPTString);

            }
        }, mYear, mMonth, mDay);
        dpd.show();
    }

    public void selectDateSLTFunc(View view){

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {
                // Display Selected date in textbox
                dateFirstSLTString = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                txtDateSLT.setText(dateFirstSLTString);

            }
        }, mYear, mMonth, mDay);
        dpd.show();
    }

}
