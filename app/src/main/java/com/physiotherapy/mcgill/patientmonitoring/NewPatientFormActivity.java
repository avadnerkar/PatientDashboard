package com.physiotherapy.mcgill.patientmonitoring;

import android.app.DatePickerDialog;
import android.database.Cursor;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


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
    public Boolean existingPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_patient_form);
        txtDateOT = (TextView) findViewById(R.id.textDateFirstOT);
        txtDateSwallow = (TextView) findViewById(R.id.textDateFirstSwallow);
        txtDatePT = (TextView) findViewById(R.id.textDateFirstPT);
        txtDateSLT = (TextView) findViewById(R.id.textDateFirstSLT);

        //Bundle extras = getIntent().getExtras();
        //existingPatient = extras.getBoolean("EXISTING_PATIENT");

        //if (existingPatient){
        Cursor cursor = MainActivity.myDb.getRowPatient(MainActivity.currentPatientId);

        if (cursor.moveToFirst()){
            existingPatient = true;
            EditText editText;
            String dateString;
            SimpleDateFormat form;
            Date date = null;
            Calendar calendar;
            DatePicker datePicker;
            int day, month, year;
            RadioGroup rg;
            TextView textView;


            editText = (EditText) findViewById(R.id.edit_first_name);
            editText.setText(cursor.getString(MainActivity.myDb.COL_FIRSTNAME));

            editText = (EditText) findViewById(R.id.edit_last_name);
            editText.setText(cursor.getString(MainActivity.myDb.COL_LASTNAME));

            editText = (EditText) findViewById(R.id.edit_hospital_ID);
            editText.setText(cursor.getString(MainActivity.myDb.COL_HOSPITALID));

            dateString = cursor.getString(MainActivity.myDb.COL_ADMISSIONDATE);
            form = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

            try{
                date = form.parse(dateString);
            } catch (java.text.ParseException e){
                e.printStackTrace();
            }
            calendar = Calendar.getInstance();
            calendar.setTime(date);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            month = calendar.get(Calendar.MONTH);
            year = calendar.get(Calendar.YEAR);

            datePicker = (DatePicker) findViewById(R.id.date_picker);
            datePicker.updateDate(year, month, day);

            editText = (EditText) findViewById(R.id.edit_patient_age);
            editText.setText(cursor.getString(MainActivity.myDb.COL_PATIENTAGE));

            rg=(RadioGroup)findViewById(R.id.rgGender);
            if (cursor.getString(MainActivity.myDb.COL_PATIENTGENDER).equals("Female")){
                rg.check(R.id.radio_GenderFemale);
            } else if(cursor.getString(MainActivity.myDb.COL_PATIENTGENDER).equals("Male")){
                rg.check(R.id.radio_GenderMale);
            } else{
                rg.clearCheck();
            }

            editText = (EditText) findViewById(R.id.edit_stroke_type);
            editText.setText(cursor.getString(MainActivity.myDb.COL_STROKETYPE));

            rg=(RadioGroup)findViewById(R.id.rgFirstStroke);
            if (cursor.getString(MainActivity.myDb.COL_FIRSTSTROKE).equals("Yes")){
                rg.check(R.id.radio_firstStrokeYes);
            } else if(cursor.getString(MainActivity.myDb.COL_FIRSTSTROKE).equals("No")){
                rg.check(R.id.radio_firstStrokeNo);
            } else{
                rg.clearCheck();
            }

            rg=(RadioGroup)findViewById(R.id.rgLegionSide);
            if (cursor.getString(MainActivity.myDb.COL_LEGIONSIDE).equals("Left")){
                rg.check(R.id.radio_LegionSideLeft);
            } else if(cursor.getString(MainActivity.myDb.COL_LEGIONSIDE).equals("Right")){
                rg.check(R.id.radio_LegionSideRight);
            } else{
                rg.clearCheck();
            }

            rg=(RadioGroup)findViewById(R.id.rgHemiplegiaSide);
            if (cursor.getString(MainActivity.myDb.COL_HEMIPLEGIASIDE).equals("Left")){
                rg.check(R.id.radio_HemiplegiaSideLeft);
            } else if(cursor.getString(MainActivity.myDb.COL_HEMIPLEGIASIDE).equals("Right")){
                rg.check(R.id.radio_HemiplegiaSideRight);
            } else if(cursor.getString(MainActivity.myDb.COL_HEMIPLEGIASIDE).equals("None")) {
                rg.check(R.id.radio_HemiplegiaSideNone);
            } else{
                rg.clearCheck();
            }

            editText = (EditText) findViewById(R.id.edit_consciousness);
            editText.setText(cursor.getString(MainActivity.myDb.COL_CONSCIOUSNESS));

            rg=(RadioGroup)findViewById(R.id.rgOrientation);
            if (cursor.getString(MainActivity.myDb.COL_ORIENTATION).equals("Oriented")){
                rg.check(R.id.radio_OrientationOriented);
            } else if(cursor.getString(MainActivity.myDb.COL_ORIENTATION).equals("Disoriented")){
                rg.check(R.id.radio_OrientationDisoriented);
            } else{
                rg.clearCheck();
            }

            editText = (EditText) findViewById(R.id.edit_language);
            editText.setText(cursor.getString(MainActivity.myDb.COL_LANGUAGE));

            rg=(RadioGroup)findViewById(R.id.rgVisual);
            if (cursor.getString(MainActivity.myDb.COL_VISUAL).equals("Yes")){
                rg.check(R.id.radio_VisualYes);
            } else if(cursor.getString(MainActivity.myDb.COL_VISUAL).equals("No")){
                rg.check(R.id.radio_VisualNo);
            } else{
                rg.clearCheck();
            }
            rg=(RadioGroup)findViewById(R.id.rgHearingAid);
            if (cursor.getString(MainActivity.myDb.COL_HEARINGAID).equals("Yes")){
                rg.check(R.id.radio_HearingAidYes);
            } else if(cursor.getString(MainActivity.myDb.COL_HEARINGAID).equals("No")){
                rg.check(R.id.radio_HearingAidNo);
            } else{
                rg.clearCheck();
            }
            rg=(RadioGroup)findViewById(R.id.rgHearingAssessed);
            if (cursor.getString(MainActivity.myDb.COL_HEARINGASSESSED).equals("Yes")){
                rg.check(R.id.radio_HearingAssessedYes);
            } else if(cursor.getString(MainActivity.myDb.COL_HEARINGASSESSED).equals("No")){
                rg.check(R.id.radio_HearingAssessedNo);
            } else{
                rg.clearCheck();
            }

            editText = (EditText) findViewById(R.id.edit_aphasia);
            editText.setText(cursor.getString(MainActivity.myDb.COL_APHASIA));

            rg=(RadioGroup)findViewById(R.id.rgPegAdmit);
            if (cursor.getString(MainActivity.myDb.COL_PEGADMIT).equals("Yes")){
                rg.check(R.id.radio_PegAdmitYes);
            } else if(cursor.getString(MainActivity.myDb.COL_PEGADMIT).equals("No")){
                rg.check(R.id.radio_PegAdmitNo);
            } else{
                rg.clearCheck();
            }
            rg=(RadioGroup)findViewById(R.id.rgNgAdmit);
            if (cursor.getString(MainActivity.myDb.COL_NGADMIT).equals("Yes")){
                rg.check(R.id.radio_NgAdmitYes);
            } else if(cursor.getString(MainActivity.myDb.COL_NGADMIT).equals("No")){
                rg.check(R.id.radio_NgAdmitNo);
            } else{
                rg.clearCheck();
            }
            rg=(RadioGroup)findViewById(R.id.rgFoleyAdmit);
            if (cursor.getString(MainActivity.myDb.COL_FOLEYADMIT).equals("Yes")){
                rg.check(R.id.radio_FoleyAdmitYes);
            } else if(cursor.getString(MainActivity.myDb.COL_FOLEYADMIT).equals("No")){
                rg.check(R.id.radio_FoleyAdmitNo);
            } else{
                rg.clearCheck();
            }
            rg=(RadioGroup)findViewById(R.id.rgFallRisk);
            if (cursor.getString(MainActivity.myDb.COL_FALLRISK).equals("Yes")){
                rg.check(R.id.radio_FallRiskYes);
            } else if(cursor.getString(MainActivity.myDb.COL_FALLRISK).equals("No")){
                rg.check(R.id.radio_FallRiskNo);
            } else{
                rg.clearCheck();
            }
            rg=(RadioGroup)findViewById(R.id.rgLimitation);
            if (cursor.getString(MainActivity.myDb.COL_LIMITATION).equals("Yes")){
                rg.check(R.id.radio_LimitationYes);
            } else if(cursor.getString(MainActivity.myDb.COL_LIMITATION).equals("No")){
                rg.check(R.id.radio_LimitationNo);
            } else{
                rg.clearCheck();
            }
            rg=(RadioGroup)findViewById(R.id.rgMotivationAdmit);
            if (cursor.getString(MainActivity.myDb.COL_MOTIVATIONADMIT).equals("Yes")){
                rg.check(R.id.radio_MotivationAdmitYes);
            } else if(cursor.getString(MainActivity.myDb.COL_MOTIVATIONADMIT).equals("No")){
                rg.check(R.id.radio_MotivationAdmitNo);
            } else{
                rg.clearCheck();
            }

            editText = (EditText) findViewById(R.id.edit_other);
            editText.setText(cursor.getString(MainActivity.myDb.COL_OTHER));

            editText = (EditText) findViewById(R.id.edit_cognition);
            editText.setText(cursor.getString(MainActivity.myDb.COL_COGNITION));

            dateString = cursor.getString(MainActivity.myDb.COL_FIRSTOT);
            textView = (TextView) findViewById(R.id.textDateFirstOT);
            textView.setText(dateString);

            dateString = cursor.getString(MainActivity.myDb.COL_FIRSTSWALLOW);
            textView = (TextView) findViewById(R.id.textDateFirstSwallow);
            textView.setText(dateString);

            dateString = cursor.getString(MainActivity.myDb.COL_FIRSTPT);
            textView = (TextView) findViewById(R.id.textDateFirstPT);
            textView.setText(dateString);

            dateString = cursor.getString(MainActivity.myDb.COL_FIRSTSLT);
            textView = (TextView) findViewById(R.id.textDateFirstSLT);
            textView.setText(dateString);
        } else{
            existingPatient = false;
        }


        //}
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

            if (existingPatient){
                MainActivity.myDb.updateRowPatient(MainActivity.currentPatientId, firstName, lastName, hospitalId, admissionDate, discharged, dischargeDate, patientAge, patientGender,
                        strokeType, firstStroke, legionSide, hemiplegiaSide, consciousness, orientation, language, visualImpairment, hearingAid, hearingAssessed, aphasia,
                        peg, ng, foley, fallRisk, limitation, motivation, otherHistory, cognition,
                        dateFirstOT, totalOT, dateFirstSwallow, dateFirstPT, totalPT, dateFirstSLT, totalSLT);

            } else{
                MainActivity.myDb.insertRowPatient(firstName, lastName, hospitalId, admissionDate, discharged, dischargeDate, patientAge, patientGender,
                        strokeType, firstStroke, legionSide, hemiplegiaSide, consciousness, orientation, language, visualImpairment, hearingAid, hearingAssessed, aphasia,
                        peg, ng, foley, fallRisk, limitation, motivation, otherHistory, cognition,
                        dateFirstOT, totalOT, dateFirstSwallow, dateFirstPT, totalPT, dateFirstSLT, totalSLT);

            }






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
