package com.physiotherapy.mcgill.patientmonitoring;

import android.app.DatePickerDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

            RadioGroup rg;
            int idButton;
            View radioButton;
            int radioId;
            RadioButton btn;
            DatePicker datePicker;
            int day, month, year;
            EditText editText;
            TextView textView;
            String firstName, lastName, hospitalId, admissionDate, discharged, dischargeDate, patientAge, patientGender, 
                    strokeType, firstStroke, legionSide, hemiplegiaSide, 
                    consciousness, orientation, language, visualImpairment, hearingAid, hearingAssessed, aphasia, 
                    peg, ng, foley, fallRisk, limitation, motivation, otherHistory, cognition, 
                    dateFirstOT, totalOT, dateFirstSwallow, dateFirstPT, totalPT, dateFirstSLT, totalSLT;

            editText = (EditText) findViewById(R.id.edit_first_name);
            firstName = editText.getText().toString();

            editText = (EditText) findViewById(R.id.edit_last_name);
            lastName = editText.getText().toString();

            editText = (EditText) findViewById(R.id.edit_hospital_ID);
            hospitalId = editText.getText().toString();

            datePicker = (DatePicker) findViewById(R.id.date_picker);
            day = datePicker.getDayOfMonth();
            month = datePicker.getMonth();
            year = datePicker.getYear();
            admissionDate = year + "-" + (month+1) + "-" + day;

            editText = (EditText) findViewById(R.id.edit_patient_age);
            patientAge = editText.getText().toString();

            rg=(RadioGroup)findViewById(R.id.rgGender);
            idButton = rg.getCheckedRadioButtonId();
            if (idButton!=-1) {
                radioButton = rg.findViewById(idButton);
                radioId = rg.indexOfChild(radioButton);
                btn = (RadioButton) rg.getChildAt(radioId);
                patientGender = (String) btn.getText();
            } else{
                patientGender = "";
            }

            editText = (EditText) findViewById(R.id.edit_stroke_type);
            strokeType = editText.getText().toString();

            rg=(RadioGroup)findViewById(R.id.rgFirstStroke);
            idButton = rg.getCheckedRadioButtonId();
            if (idButton!=-1) {
                radioButton = rg.findViewById(idButton);
                radioId = rg.indexOfChild(radioButton);
                btn = (RadioButton) rg.getChildAt(radioId);
                firstStroke = (String) btn.getText();
            } else{
                firstStroke = "";
            }

            rg=(RadioGroup)findViewById(R.id.rgLegionSide);
            idButton = rg.getCheckedRadioButtonId();
            if (idButton!=-1) {
                radioButton = rg.findViewById(idButton);
                radioId = rg.indexOfChild(radioButton);
                btn = (RadioButton) rg.getChildAt(radioId);
                legionSide = (String) btn.getText();
            } else{
                legionSide = "";
            }

            rg=(RadioGroup)findViewById(R.id.rgHemiplegiaSide);
            idButton = rg.getCheckedRadioButtonId();
            if (idButton!=-1) {
                radioButton = rg.findViewById(idButton);
                radioId = rg.indexOfChild(radioButton);
                btn = (RadioButton) rg.getChildAt(radioId);
                hemiplegiaSide = (String) btn.getText();
            } else{
                hemiplegiaSide = "";
            }

            editText = (EditText) findViewById(R.id.edit_consciousness);
            consciousness = editText.getText().toString();

            rg=(RadioGroup)findViewById(R.id.rgOrientation);
            idButton = rg.getCheckedRadioButtonId();
            if (idButton!=-1) {
                radioButton = rg.findViewById(idButton);
                radioId = rg.indexOfChild(radioButton);
                btn = (RadioButton) rg.getChildAt(radioId);
                orientation = (String) btn.getText();
            } else{
                orientation = "";
            }

            editText = (EditText) findViewById(R.id.edit_language);
            language = editText.getText().toString();

            rg=(RadioGroup)findViewById(R.id.rgVisual);
            idButton = rg.getCheckedRadioButtonId();
            if (idButton!=-1) {
                radioButton = rg.findViewById(idButton);
                radioId = rg.indexOfChild(radioButton);
                btn = (RadioButton) rg.getChildAt(radioId);
                visualImpairment = (String) btn.getText();
            } else{
                visualImpairment = "";
            }

            rg=(RadioGroup)findViewById(R.id.rgHearingAid);
            idButton = rg.getCheckedRadioButtonId();
            if (idButton!=-1) {
                radioButton = rg.findViewById(idButton);
                radioId = rg.indexOfChild(radioButton);
                btn = (RadioButton) rg.getChildAt(radioId);
                hearingAid = (String) btn.getText();
            } else{
                hearingAid = "";
            }

            rg=(RadioGroup)findViewById(R.id.rgHearingAssessed);
            idButton = rg.getCheckedRadioButtonId();
            if (idButton!=-1) {
                radioButton = rg.findViewById(idButton);
                radioId = rg.indexOfChild(radioButton);
                btn = (RadioButton) rg.getChildAt(radioId);
                hearingAssessed = (String) btn.getText();
            } else{
                hearingAssessed = "";
            }

            editText = (EditText) findViewById(R.id.edit_aphasia);
            aphasia = editText.getText().toString();

            rg=(RadioGroup)findViewById(R.id.rgPegAdmit);
            idButton = rg.getCheckedRadioButtonId();
            if (idButton!=-1) {
                radioButton = rg.findViewById(idButton);
                radioId = rg.indexOfChild(radioButton);
                btn = (RadioButton) rg.getChildAt(radioId);
                peg = (String) btn.getText();
            } else{
                peg = "";
            }

            rg=(RadioGroup)findViewById(R.id.rgNgAdmit);
            idButton = rg.getCheckedRadioButtonId();
            if (idButton!=-1) {
                radioButton = rg.findViewById(idButton);
                radioId = rg.indexOfChild(radioButton);
                btn = (RadioButton) rg.getChildAt(radioId);
                ng = (String) btn.getText();
            } else{
                ng = "";
            }

            rg=(RadioGroup)findViewById(R.id.rgFoleyAdmit);
            idButton = rg.getCheckedRadioButtonId();
            if (idButton!=-1) {
                radioButton = rg.findViewById(idButton);
                radioId = rg.indexOfChild(radioButton);
                btn = (RadioButton) rg.getChildAt(radioId);
                foley = (String) btn.getText();
            } else{
                foley = "";
            }

            rg=(RadioGroup)findViewById(R.id.rgFallRisk);
            idButton = rg.getCheckedRadioButtonId();
            if (idButton!=-1) {
                radioButton = rg.findViewById(idButton);
                radioId = rg.indexOfChild(radioButton);
                btn = (RadioButton) rg.getChildAt(radioId);
                fallRisk = (String) btn.getText();
            } else{
                fallRisk = "";
            }

            rg=(RadioGroup)findViewById(R.id.rgLimitation);
            idButton = rg.getCheckedRadioButtonId();
            if (idButton!=-1) {
                radioButton = rg.findViewById(idButton);
                radioId = rg.indexOfChild(radioButton);
                btn = (RadioButton) rg.getChildAt(radioId);
                limitation = (String) btn.getText();
            } else{
                limitation = "";
            }

            rg=(RadioGroup)findViewById(R.id.rgMotivationAdmit);
            idButton = rg.getCheckedRadioButtonId();
            if (idButton!=-1) {
                radioButton = rg.findViewById(idButton);
                radioId = rg.indexOfChild(radioButton);
                btn = (RadioButton) rg.getChildAt(radioId);
                motivation = (String) btn.getText();
            } else{
                motivation = "";
            }

            editText = (EditText) findViewById(R.id.edit_other);
            otherHistory = editText.getText().toString();

            editText = (EditText) findViewById(R.id.edit_cognition);
            cognition = editText.getText().toString();
            
            textView = (TextView) findViewById(R.id.textDateFirstOT);
            dateFirstOT = textView.getText().toString();

            textView = (TextView) findViewById(R.id.textDateFirstSwallow);
            dateFirstSwallow = textView.getText().toString();

            textView = (TextView) findViewById(R.id.textDateFirstPT);
            dateFirstPT = textView.getText().toString();

            textView = (TextView) findViewById(R.id.textDateFirstSLT);
            dateFirstSLT = textView.getText().toString();
            
            
            discharged = "No";
            dischargeDate = "";
            totalOT = "";
            totalPT = "";
            totalSLT = "";


            long newID = MainActivity.myDb.insertRowPatient(firstName, lastName, hospitalId, admissionDate, discharged, dischargeDate, patientAge, patientGender,
                    strokeType, firstStroke, legionSide, hemiplegiaSide, consciousness, orientation, language, visualImpairment, hearingAid, hearingAssessed, aphasia,
                    peg, ng, foley, fallRisk, limitation, motivation, otherHistory, cognition,
                    dateFirstOT, totalOT, dateFirstSwallow, dateFirstPT, totalPT, dateFirstSLT, totalSLT);




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
