package com.physiotherapy.mcgill.patientmonitoring;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.opencsv.CSVWriter;


//TEST FOR GIIIIIT

public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    public static DBAdapter myDb;
    public int currentPatientIndex;
    public static int currentPatientId = -1;
    public int currentDay;
    public static String currentMrn;
    public String[] patientListString;
    public int elapsedDays;
    //public boolean existingPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openDB();


        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }





    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    private void openDB() {
        myDb = new DBAdapter(this);
        myDb.open();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDB();
    }

    private void closeDB() {
        myDb.close();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void getCurrentDay(){
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        if (currentPatientId != -1){
            Cursor cursor = myDb.getRowPatient(currentPatientId);
            String dateString = cursor.getString(DBAdapter.COL_ADMISSIONDATE);
            SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

            Date admissionDate = today;
            try{
                admissionDate = form.parse(dateString);
            } catch (java.text.ParseException e){
                e.printStackTrace();

            }

            elapsedDays = getElapsedTimeInDays(admissionDate,today) + 1;

            currentDay = elapsedDays;
            TextView textView = (TextView) findViewById(R.id.dayNumberNurse);
            textView.setText("Day " + currentDay);
            textView = (TextView) findViewById(R.id.dayNumberOt);
            textView.setText("Day " + currentDay);
            textView = (TextView) findViewById(R.id.dayNumberPt);
            textView.setText("Day " + currentDay);


        }
    }

    public int getElapsedTimeInDays(Date start,Date end){
        int days=(int)(end.getTime()-start.getTime())/(1000*60*60*24);
        return days;
    }

    public void updatePatientList() {
        Cursor cursor = myDb.getAllRowPatients();

        patientListString = new String[cursor.getCount()];

        if (cursor.moveToFirst()) {
            do {
                // Process the data:
                int id = cursor.getInt(DBAdapter.COL_ROWID);
                String firstName = cursor.getString(DBAdapter.COL_FIRSTNAME);
                String lastName = cursor.getString(DBAdapter.COL_LASTNAME);

                patientListString[cursor.getPosition()] = firstName + " " + lastName;

            } while(cursor.moveToNext());
        }



        if (patientListString!=null) {

            cursor.moveToLast();
            currentPatientIndex = cursor.getPosition();

            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle(patientListString[currentPatientIndex]);
        }


        // Close the cursor to avoid a resource leak.
        cursor.close();
        //TextView textView = (TextView) findViewById(R.id.tvFragNurse);
        //textView.setText(message);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);



        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if(currentPatientId==-1){
            menu.findItem(R.id.data_save).setVisible(false);
            menu.findItem(R.id.action_discharge).setVisible(false);
            menu.findItem(R.id.action_update_patient).setVisible(false);
            menu.findItem(R.id.action_update_patient).setVisible(false);
            menu.findItem(R.id.action_view_history).setVisible(false);
        }
        else{
            menu.findItem(R.id.data_save).setVisible(true);
            menu.findItem(R.id.action_discharge).setVisible(true);
            menu.findItem(R.id.action_update_patient).setVisible(true);
            menu.findItem(R.id.action_update_patient).setVisible(true);
            menu.findItem(R.id.action_view_history).setVisible(true);
        }
        return super.onPrepareOptionsMenu(menu);
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

        if (id == R.id.clear_all) {
            myDb.deleteAllPatients();
            clearPatientSelection();
            loadPatientData();

            return true;
        }

        if (id == R.id.clear) {
            myDb.deleteCurrentPatient(currentPatientId);
            clearPatientSelection();
            loadPatientData();
            return true;
        }

        if (item.getItemId() == R.id.patient_new) {
            //Toast.makeText(getActivity(), "Example action.", Toast.LENGTH_SHORT).show();
            //existingPatient = false;
            clearPatientSelection();

            Intent intent = new Intent(this, NewPatientFormActivity.class);
            //intent.putExtra("EXISTING_PATIENT",existingPatient);
            startActivity(intent);
            return true;
        }

        if (item.getItemId() == R.id.action_update_patient) {
            //Toast.makeText(getActivity(), "Example action.", Toast.LENGTH_SHORT).show();
            //existingPatient = true;
            Intent intent = new Intent(this, NewPatientFormActivity.class);
            //intent.putExtra("EXISTING_PATIENT",existingPatient);
            startActivity(intent);
            return true;
        }

        if (item.getItemId() == R.id.action_discharge) {
            //Toast.makeText(getActivity(), "Example action.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, DischargePatientForm.class);
            startActivity(intent);
            return true;
        }


        if (item.getItemId() == R.id.action_view_history) {
            Intent intent = new Intent(this, ViewHistory.class);
            startActivity(intent);
            return true;
        }

        if (item.getItemId() == R.id.action_export_csv) {
            exportToCSV();
            return true;
        }

        if (id == R.id.patient_list) {
            showPatientList();

            return true;
        }

        if (id == R.id.data_save) {
            savePatientData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void clearPatientSelection(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.app_name);
        currentDay = 1;
        currentPatientId = -1;
        LinearLayout nurseLayout = (LinearLayout) findViewById(R.id.nurseLinearLayout);
        nurseLayout.setVisibility(View.INVISIBLE);
        LinearLayout otLayout = (LinearLayout) findViewById(R.id.otLinearLayout);
        otLayout.setVisibility(View.INVISIBLE);
        LinearLayout ptLayout = (LinearLayout) findViewById(R.id.ptLinearLayout);
        ptLayout.setVisibility(View.INVISIBLE);

        invalidateOptionsMenu();

        currentMrn = null;
        TextView textView = (TextView) findViewById(R.id.dayNumberNurse);
        textView.setText("Day " + currentDay);
        textView = (TextView) findViewById(R.id.dayNumberOt);
        textView.setText("Day " + currentDay);
        textView = (TextView) findViewById(R.id.dayNumberPt);
        textView.setText("Day " + currentDay);
        loadPatientData();
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            switch(position){
                case 0: return NurseFragment.newInstance(position + 1);
                case 1: return OtFragment.newInstance(position + 1);
                case 2: return PtFragment.newInstance(position + 1);
                default: return NurseFragment.newInstance(position + 1);
            }


        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }


    public void showPatientList(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Choose patient");

        Cursor cursor = myDb.getAllRowPatients();

        patientListString = new String[cursor.getCount()];
        final int[] IDarray = new int[cursor.getCount()];
        final String[] MRNarray = new String[cursor.getCount()];

        if (cursor.moveToFirst()) {
            do {
                // Process the data:
                int id = cursor.getInt(DBAdapter.COL_ROWID);
                String mrn = cursor.getString(DBAdapter.COL_HOSPITALID);
                String firstName = cursor.getString(DBAdapter.COL_FIRSTNAME);
                String lastName = cursor.getString(DBAdapter.COL_LASTNAME);
                IDarray[cursor.getPosition()] = id;
                MRNarray[cursor.getPosition()] = mrn;
                patientListString[cursor.getPosition()] = firstName + " " + lastName;

            } while(cursor.moveToNext());
        }

        builder.setItems(patientListString,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        currentPatientIndex = which;
                        //TextView textView = (TextView) findViewById(R.id.tvFragNurse);
                        //textView.setText(patientListString[which]);
                        ActionBar actionBar = getSupportActionBar();
                        actionBar.setTitle(patientListString[which]);
                        currentPatientId = IDarray[which];
                        currentMrn = MRNarray[which];
                        currentDay = 1;
                        TextView textView = (TextView) findViewById(R.id.dayNumberNurse);
                        textView.setText("Day " + currentDay);
                        textView = (TextView) findViewById(R.id.dayNumberOt);
                        textView.setText("Day " + currentDay);
                        textView = (TextView) findViewById(R.id.dayNumberPt);
                        textView.setText("Day " + currentDay);
                        loadPatientData();

                        LinearLayout nurseLayout = (LinearLayout) findViewById(R.id.nurseLinearLayout);
                        nurseLayout.setVisibility(View.VISIBLE);
                        LinearLayout otLayout = (LinearLayout) findViewById(R.id.otLinearLayout);
                        otLayout.setVisibility(View.VISIBLE);
                        LinearLayout ptLayout = (LinearLayout) findViewById(R.id.ptLinearLayout);
                        ptLayout.setVisibility(View.VISIBLE);

                        invalidateOptionsMenu();

                        getCurrentDay();


                    }
                }
        );

        builder.show();





    }

    public void selectPatientWarning(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please select a patient first!")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    public void decrementDayClicked(View view){

        currentDay = Math.max(1, currentDay - 1);
        TextView textView = (TextView) findViewById(R.id.dayNumberNurse);
        textView.setText("Day " + currentDay);

        textView = (TextView) findViewById(R.id.dayNumberOt);
        textView.setText("Day " + currentDay);

        textView = (TextView) findViewById(R.id.dayNumberPt);
        textView.setText("Day " + currentDay);

        loadPatientData();

    }


    public void incrementDayClicked(View view){

        currentDay = Math.min(99, currentDay + 1);
        TextView textView = (TextView) findViewById(R.id.dayNumberNurse);
        textView.setText("Day " + currentDay);

        textView = (TextView) findViewById(R.id.dayNumberOt);
        textView.setText("Day " + currentDay);

        textView = (TextView) findViewById(R.id.dayNumberPt);
        textView.setText("Day " + currentDay);

        loadPatientData();

    }

    public void exportToCSV() {
        File path = Environment.getExternalStorageDirectory();
        File filename = new File(path, "/exportPatientData.csv");

        try {
            CSVWriter writer = new CSVWriter(new FileWriter(filename), '\t');
            Cursor c = myDb.getAllRowData();
            writer.writeNext(c.getColumnNames());
            do {
                String arrStr[] ={c.getString(myDb.COL_ROWID), c.getString(myDb.COL_PARENTID), c.getString(myDb.COL_MRN), c.getString(myDb.COL_DAY),
                        c.getString(myDb.COL_PEG), c.getString(myDb.COL_NG), c.getString(myDb.COL_O2), c.getString(myDb.COL_IV), c.getString(myDb.COL_FOLEY), c.getString(myDb.COL_CPAP), c.getString(myDb.COL_RESTRAINT), c.getString(myDb.COL_BEHAVIOURAL), c.getString(myDb.COL_CONFUSION), c.getString(myDb.COL_BLADDER), c.getString(myDb.COL_HOURS),
                        c.getString(myDb.COL_NEGLECT), c.getString(myDb.COL_DIGITSPAN), c.getString(myDb.COL_MMSE), c.getString(myDb.COL_FOLLOWS), c.getString(myDb.COL_VERBAL), c.getString(myDb.COL_MOTIVATION), c.getString(myDb.COL_MOOD), c.getString(myDb.COL_PAIN), c.getString(myDb.COL_FATIGUE), c.getString(myDb.COL_SWALLOW), c.getString(myDb.COL_FEEDING), c.getString(myDb.COL_DRESSING), c.getString(myDb.COL_KITCHEN),
                        c.getString(myDb.COL_LEFTARM), c.getString(myDb.COL_RIGHTARM), c.getString(myDb.COL_MOVEMENTBED), c.getString(myDb.COL_LIESIT), c.getString(myDb.COL_SITTING), c.getString(myDb.COL_SITSTAND), c.getString(myDb.COL_STAND), c.getString(myDb.COL_LIFTSUNAFFECTED), c.getString(myDb.COL_LIFTSAFFECTED), c.getString(myDb.COL_WALKING)};
                writer.writeNext(arrStr);
            } while(c.moveToNext());
            writer.close();
            c.close();


        } catch (IOException e){
            System.err.println("Caught IOException: " + e.getMessage());
        }

        filename = new File(path, "/exportPatientList.csv");

        try {
            CSVWriter writer = new CSVWriter(new FileWriter(filename), '\t');
            Cursor c = myDb.getAllRowPatients();
            writer.writeNext(c.getColumnNames());
            do {
                String arrStr[] ={c.getString(myDb.COL_ROWID), c.getString(myDb.COL_FIRSTNAME), c.getString(myDb.COL_LASTNAME), c.getString(myDb.COL_HOSPITALID), c.getString(myDb.COL_ADMISSIONDATE), c.getString(myDb.COL_DISCHARGED), c.getString(myDb.COL_DISCHARGEDATE), c.getString(myDb.COL_PATIENTAGE), c.getString(myDb.COL_PATIENTGENDER),
                        c.getString(myDb.COL_STROKETYPE), c.getString(myDb.COL_FIRSTSTROKE), c.getString(myDb.COL_LEGIONSIDE), c.getString(myDb.COL_HEMIPLEGIASIDE), c.getString(myDb.COL_CONSCIOUSNESS), c.getString(myDb.COL_ORIENTATION), c.getString(myDb.COL_LANGUAGE), c.getString(myDb.COL_VISUAL), c.getString(myDb.COL_HEARINGAID), c.getString(myDb.COL_HEARINGASSESSED), c.getString(myDb.COL_APHASIA),
                        c.getString(myDb.COL_PEGADMIT), c.getString(myDb.COL_NGADMIT), c.getString(myDb.COL_FOLEYADMIT), c.getString(myDb.COL_FALLRISK), c.getString(myDb.COL_LIMITATION), c.getString(myDb.COL_MOTIVATIONADMIT), c.getString(myDb.COL_OTHER), c.getString(myDb.COL_COGNITION),
                        c.getString(myDb.COL_FIRSTOT), c.getString(myDb.COL_TOTALOT), c.getString(myDb.COL_FIRSTSWALLOW), c.getString(myDb.COL_FIRSTPT), c.getString(myDb.COL_TOTALPT), c.getString(myDb.COL_FIRSTSLT), c.getString(myDb.COL_TOTALSLT)};
                writer.writeNext(arrStr);
            } while(c.moveToNext());
            writer.close();
            c.close();


        } catch (IOException e){
            System.err.println("Caught IOException: " + e.getMessage());
        }
    }

    public void savePatientData(){
        RadioGroup rg;
        int id;
        View radioButton;
        int radioId;
        RadioButton btn;
        String currentPeg, currentNg, currentO2, currentIv, currentFoley, currentCpap, currentRestraint, currentBehavioural, currentConfusion, currentBladder, currentHours,
            currentNeglect, currentDigitSpan, currentMmse, currentFollows, currentVerbal, currentMotivation, currentMood, currentPain, currentFatigue, currentSwallow, currentFeeding, currentDressing, currentKitchen,
            currentLeftArm, currentRightArm, currentMovementBed, currentLieSit, currentSitting, currentSitStand, currentStand, currentLiftsUnaffected, currentLiftsAffected, currentWalking;

        //Nurse
        rg=(RadioGroup)findViewById(R.id.rgPeg);
        id = rg.getCheckedRadioButtonId();
        if (id!=-1) {
            radioButton = rg.findViewById(id);
            radioId = rg.indexOfChild(radioButton);
            btn = (RadioButton) rg.getChildAt(radioId);
            currentPeg = (String) btn.getText();
        } else{
            currentPeg = "";
        }

        rg=(RadioGroup)findViewById(R.id.rgNg);
        id = rg.getCheckedRadioButtonId();
        if (id!=-1) {
            radioButton = rg.findViewById(id);
            radioId = rg.indexOfChild(radioButton);
            btn = (RadioButton) rg.getChildAt(radioId);
            currentNg = (String) btn.getText();
        } else{
            currentNg = "";
        }

        rg=(RadioGroup)findViewById(R.id.rgO2);
        id = rg.getCheckedRadioButtonId();
        if (id!=-1) {
            radioButton = rg.findViewById(id);
            radioId = rg.indexOfChild(radioButton);
            btn = (RadioButton) rg.getChildAt(radioId);
            currentO2 = (String) btn.getText();
        } else{
            currentO2 = "";
        }

        rg=(RadioGroup)findViewById(R.id.rgIv);
        id = rg.getCheckedRadioButtonId();
        if (id!=-1) {
            radioButton = rg.findViewById(id);
            radioId = rg.indexOfChild(radioButton);
            btn = (RadioButton) rg.getChildAt(radioId);
            currentIv = (String) btn.getText();
        } else{
            currentIv = "";
        }

        rg=(RadioGroup)findViewById(R.id.rgFoley);
        id = rg.getCheckedRadioButtonId();
        if (id!=-1) {
            radioButton = rg.findViewById(id);
            radioId = rg.indexOfChild(radioButton);
            btn = (RadioButton) rg.getChildAt(radioId);
            currentFoley = (String) btn.getText();
        } else{
            currentFoley = "";
        }

        rg=(RadioGroup)findViewById(R.id.rgCpap);
        id = rg.getCheckedRadioButtonId();
        if (id!=-1) {
            radioButton = rg.findViewById(id);
            radioId = rg.indexOfChild(radioButton);
            btn = (RadioButton) rg.getChildAt(radioId);
            currentCpap = (String) btn.getText();
        } else{
            currentCpap = "";
        }

        rg=(RadioGroup)findViewById(R.id.rgRestraint);
        id = rg.getCheckedRadioButtonId();
        if (id!=-1) {
            radioButton = rg.findViewById(id);
            radioId = rg.indexOfChild(radioButton);
            btn = (RadioButton) rg.getChildAt(radioId);
            currentRestraint = (String) btn.getText();
        } else{
            currentRestraint = "";
        }

        rg=(RadioGroup)findViewById(R.id.rgBehavioural);
        id = rg.getCheckedRadioButtonId();
        if (id!=-1) {
            radioButton = rg.findViewById(id);
            radioId = rg.indexOfChild(radioButton);
            btn = (RadioButton) rg.getChildAt(radioId);
            currentBehavioural = (String) btn.getText();
        } else{
            currentBehavioural = "";
        }

        rg=(RadioGroup)findViewById(R.id.rgConfusion);
        id = rg.getCheckedRadioButtonId();
        if (id!=-1) {
            radioButton = rg.findViewById(id);
            radioId = rg.indexOfChild(radioButton);
            btn = (RadioButton) rg.getChildAt(radioId);
            currentConfusion = (String) btn.getText();
        } else{
            currentConfusion = "";
        }

        rg=(RadioGroup)findViewById(R.id.rgBladder);
        id = rg.getCheckedRadioButtonId();
        if (id!=-1) {
            radioButton = rg.findViewById(id);
            radioId = rg.indexOfChild(radioButton);
            btn = (RadioButton) rg.getChildAt(radioId);
            currentBladder = (String) btn.getText();
        } else {
            currentBladder = "";
        }

        EditText editText = (EditText) findViewById(R.id.aHours);
        currentHours = editText.getText().toString();

        //OT
        rg=(RadioGroup)findViewById(R.id.rgNeglect);
        id = rg.getCheckedRadioButtonId();
        if (id!=-1) {
            radioButton = rg.findViewById(id);
            radioId = rg.indexOfChild(radioButton);
            btn = (RadioButton) rg.getChildAt(radioId);
            currentNeglect = (String) btn.getText();
        } else{
            currentNeglect = "";
        }

        rg=(RadioGroup)findViewById(R.id.rgDigitSpan);
        id = rg.getCheckedRadioButtonId();
        if (id!=-1) {
            radioButton = rg.findViewById(id);
            radioId = rg.indexOfChild(radioButton);
            btn = (RadioButton) rg.getChildAt(radioId);
            currentDigitSpan = (String) btn.getText();
        } else {
            currentDigitSpan = "";
        }

        editText = (EditText) findViewById(R.id.aMmse);
        currentMmse = editText.getText().toString();

        rg=(RadioGroup)findViewById(R.id.rgFollows);
        id = rg.getCheckedRadioButtonId();
        if (id!=-1) {
            radioButton = rg.findViewById(id);
            radioId = rg.indexOfChild(radioButton);
            btn = (RadioButton) rg.getChildAt(radioId);
            currentFollows = (String) btn.getText();
        } else {
            currentFollows = "";
        }

        rg=(RadioGroup)findViewById(R.id.rgVerbal);
        id = rg.getCheckedRadioButtonId();
        if (id!=-1) {
            radioButton = rg.findViewById(id);
            radioId = rg.indexOfChild(radioButton);
            btn = (RadioButton) rg.getChildAt(radioId);
            currentVerbal = (String) btn.getText();
        } else {
            currentVerbal = "";
        }

        rg=(RadioGroup)findViewById(R.id.rgMotivation);
        id = rg.getCheckedRadioButtonId();
        if (id!=-1) {
            radioButton = rg.findViewById(id);
            radioId = rg.indexOfChild(radioButton);
            btn = (RadioButton) rg.getChildAt(radioId);
            currentMotivation = (String) btn.getText();
        } else {
            currentMotivation = "";
        }

        rg=(RadioGroup)findViewById(R.id.rgMood);
        id = rg.getCheckedRadioButtonId();
        if (id!=-1) {
            radioButton = rg.findViewById(id);
            radioId = rg.indexOfChild(radioButton);
            btn = (RadioButton) rg.getChildAt(radioId);
            currentMood = (String) btn.getText();
        } else {
            currentMood = "";
        }

        rg=(RadioGroup)findViewById(R.id.rgPain);
        id = rg.getCheckedRadioButtonId();
        if (id!=-1) {
            radioButton = rg.findViewById(id);
            radioId = rg.indexOfChild(radioButton);
            btn = (RadioButton) rg.getChildAt(radioId);
            currentPain = (String) btn.getText();
        } else {
            currentPain = "";
        }

        rg=(RadioGroup)findViewById(R.id.rgFatigue);
        id = rg.getCheckedRadioButtonId();
        if (id!=-1) {
            radioButton = rg.findViewById(id);
            radioId = rg.indexOfChild(radioButton);
            btn = (RadioButton) rg.getChildAt(radioId);
            currentFatigue = (String) btn.getText();
        } else {
            currentFatigue = "";
        }

        rg=(RadioGroup)findViewById(R.id.rgSwallow);
        id = rg.getCheckedRadioButtonId();
        if (id!=-1) {
            radioButton = rg.findViewById(id);
            radioId = rg.indexOfChild(radioButton);
            btn = (RadioButton) rg.getChildAt(radioId);
            currentSwallow = (String) btn.getText();
        } else {
            currentSwallow = "";
        }

        rg=(RadioGroup)findViewById(R.id.rgFeeding);
        id = rg.getCheckedRadioButtonId();
        if (id!=-1) {
            radioButton = rg.findViewById(id);
            radioId = rg.indexOfChild(radioButton);
            btn = (RadioButton) rg.getChildAt(radioId);
            currentFeeding = (String) btn.getText();
        } else {
            currentFeeding = "";
        }

        rg=(RadioGroup)findViewById(R.id.rgDressing);
        id = rg.getCheckedRadioButtonId();
        if (id!=-1) {
            radioButton = rg.findViewById(id);
            radioId = rg.indexOfChild(radioButton);
            btn = (RadioButton) rg.getChildAt(radioId);
            currentDressing = (String) btn.getText();
        } else {
            currentDressing = "";
        }

        rg=(RadioGroup)findViewById(R.id.rgKitchen);
        id = rg.getCheckedRadioButtonId();
        if (id!=-1) {
            radioButton = rg.findViewById(id);
            radioId = rg.indexOfChild(radioButton);
            btn = (RadioButton) rg.getChildAt(radioId);
            currentKitchen = (String) btn.getText();
        } else {
            currentKitchen = "";
        }


        //PT
        rg=(RadioGroup)findViewById(R.id.rgLeftArm);
        id = rg.getCheckedRadioButtonId();
        if (id!=-1) {
            radioButton = rg.findViewById(id);
            radioId = rg.indexOfChild(radioButton);
            btn = (RadioButton) rg.getChildAt(radioId);
            currentLeftArm = (String) btn.getText();
        } else {
            currentLeftArm = "";
        }

        rg=(RadioGroup)findViewById(R.id.rgRightArm);
        id = rg.getCheckedRadioButtonId();
        if (id!=-1) {
            radioButton = rg.findViewById(id);
            radioId = rg.indexOfChild(radioButton);
            btn = (RadioButton) rg.getChildAt(radioId);
            currentRightArm = (String) btn.getText();
        } else {
            currentRightArm = "";
        }

        rg=(RadioGroup)findViewById(R.id.rgMovementBed);
        id = rg.getCheckedRadioButtonId();
        if (id!=-1) {
            radioButton = rg.findViewById(id);
            radioId = rg.indexOfChild(radioButton);
            btn = (RadioButton) rg.getChildAt(radioId);
            currentMovementBed = (String) btn.getText();
        } else {
            currentMovementBed = "";
        }

        rg=(RadioGroup)findViewById(R.id.rgLieSit);
        id = rg.getCheckedRadioButtonId();
        if (id!=-1) {
            radioButton = rg.findViewById(id);
            radioId = rg.indexOfChild(radioButton);
            btn = (RadioButton) rg.getChildAt(radioId);
            currentLieSit = (String) btn.getText();
        } else {
            currentLieSit = "";
        }

        rg=(RadioGroup)findViewById(R.id.rgSitting);
        id = rg.getCheckedRadioButtonId();
        if (id!=-1) {
            radioButton = rg.findViewById(id);
            radioId = rg.indexOfChild(radioButton);
            btn = (RadioButton) rg.getChildAt(radioId);
            currentSitting = (String) btn.getText();
        } else {
            currentSitting = "";
        }

        rg=(RadioGroup)findViewById(R.id.rgSitStand);
        id = rg.getCheckedRadioButtonId();
        if (id!=-1) {
            radioButton = rg.findViewById(id);
            radioId = rg.indexOfChild(radioButton);
            btn = (RadioButton) rg.getChildAt(radioId);
            currentSitStand = (String) btn.getText();
        } else {
            currentSitStand = "";
        }

        rg=(RadioGroup)findViewById(R.id.rgStand);
        id = rg.getCheckedRadioButtonId();
        if (id!=-1) {
            radioButton = rg.findViewById(id);
            radioId = rg.indexOfChild(radioButton);
            btn = (RadioButton) rg.getChildAt(radioId);
            currentStand = (String) btn.getText();
        } else {
            currentStand = "";
        }

        rg=(RadioGroup)findViewById(R.id.rgLiftsUnaffected);
        id = rg.getCheckedRadioButtonId();
        if (id!=-1) {
            radioButton = rg.findViewById(id);
            radioId = rg.indexOfChild(radioButton);
            btn = (RadioButton) rg.getChildAt(radioId);
            currentLiftsUnaffected = (String) btn.getText();
        } else {
            currentLiftsUnaffected = "";
        }

        rg=(RadioGroup)findViewById(R.id.rgLiftsAffected);
        id = rg.getCheckedRadioButtonId();
        if (id!=-1) {
            radioButton = rg.findViewById(id);
            radioId = rg.indexOfChild(radioButton);
            btn = (RadioButton) rg.getChildAt(radioId);
            currentLiftsAffected = (String) btn.getText();
        } else {
            currentLiftsAffected = "";
        }

        rg=(RadioGroup)findViewById(R.id.rgWalking);
        id = rg.getCheckedRadioButtonId();
        if (id!=-1) {
            radioButton = rg.findViewById(id);
            radioId = rg.indexOfChild(radioButton);
            btn = (RadioButton) rg.getChildAt(radioId);
            currentWalking = (String) btn.getText();
        } else {
            currentWalking = "";
        }

        ///////////////////////////////////

        Cursor cursor = myDb.getRowData(currentPatientId, currentDay);
        if (cursor.moveToFirst()){
            myDb.updateRowData(currentPatientId, currentDay,
                    currentPeg, currentNg, currentO2, currentIv, currentFoley, currentCpap, currentRestraint, currentBehavioural, currentConfusion, currentBladder, currentHours,
                    currentNeglect, currentDigitSpan, currentMmse, currentFollows, currentVerbal, currentMotivation, currentMood, currentPain, currentFatigue, currentSwallow, currentFeeding, currentDressing, currentKitchen,
                    currentLeftArm, currentRightArm, currentMovementBed, currentLieSit, currentSitting, currentSitStand, currentStand, currentLiftsUnaffected, currentLiftsAffected, currentWalking);

        }else{
            myDb.insertRowData(currentPatientId, currentMrn, currentDay,
                    currentPeg, currentNg, currentO2, currentIv, currentFoley, currentCpap, currentRestraint, currentBehavioural, currentConfusion, currentBladder, currentHours,
                    currentNeglect, currentDigitSpan, currentMmse, currentFollows, currentVerbal, currentMotivation, currentMood, currentPain, currentFatigue, currentSwallow, currentFeeding, currentDressing, currentKitchen,
                    currentLeftArm, currentRightArm, currentMovementBed, currentLieSit, currentSitting, currentSitStand, currentStand, currentLiftsUnaffected, currentLiftsAffected, currentWalking);
        }

    }

    public void loadPatientData(){

        Cursor cursor = myDb.getRowData(currentPatientId, currentDay);

        if (cursor.moveToFirst()){

            //Nurse

            RadioGroup rg=(RadioGroup)findViewById(R.id.rgPeg);
            if (cursor.getString(myDb.COL_PEG).equals("Yes")){
                rg.check(R.id.radio_PegYes);
            } else if(cursor.getString(myDb.COL_PEG).equals("No")){
                rg.check(R.id.radio_PegNo);
            } else{
                rg.clearCheck();
            }

            rg=(RadioGroup)findViewById(R.id.rgNg);
            if (cursor.getString(myDb.COL_NG).equals("Yes")){
                rg.check(R.id.radio_NgYes);
            } else if(cursor.getString(myDb.COL_NG).equals("No")){
                rg.check(R.id.radio_NgNo);
            } else{
                rg.clearCheck();
            }

            rg=(RadioGroup)findViewById(R.id.rgO2);
            if (cursor.getString(myDb.COL_O2).equals("Yes")){
                rg.check(R.id.radio_O2Yes);
            } else if(cursor.getString(myDb.COL_O2).equals("No")){
                rg.check(R.id.radio_O2No);
            } else{
                rg.clearCheck();
            }

            rg=(RadioGroup)findViewById(R.id.rgIv);
            if (cursor.getString(myDb.COL_IV).equals("Yes")){
                rg.check(R.id.radio_IvYes);
            } else if(cursor.getString(myDb.COL_IV).equals("No")){
                rg.check(R.id.radio_IvNo);
            } else{
                rg.clearCheck();
            }

            rg=(RadioGroup)findViewById(R.id.rgFoley);
            if (cursor.getString(myDb.COL_FOLEY).equals("Yes")){
                rg.check(R.id.radio_FoleyYes);
            } else if(cursor.getString(myDb.COL_FOLEY).equals("No")){
                rg.check(R.id.radio_FoleyNo);
            } else{
                rg.clearCheck();
            }

            rg=(RadioGroup)findViewById(R.id.rgCpap);
            if (cursor.getString(myDb.COL_CPAP).equals("Yes")){
                rg.check(R.id.radio_CpapYes);
            } else if(cursor.getString(myDb.COL_CPAP).equals("No")){
                rg.check(R.id.radio_CpapNo);
            } else{
                rg.clearCheck();
            }

            rg=(RadioGroup)findViewById(R.id.rgRestraint);
            if (cursor.getString(myDb.COL_RESTRAINT).equals("Yes")){
                rg.check(R.id.radio_RestraintYes);
            } else if(cursor.getString(myDb.COL_RESTRAINT).equals("No")){
                rg.check(R.id.radio_RestraintNo);
            } else{
                rg.clearCheck();
            }

            rg=(RadioGroup)findViewById(R.id.rgBehavioural);
            if (cursor.getString(myDb.COL_BEHAVIOURAL).equals("Yes")){
                rg.check(R.id.radio_BehaviouralYes);
            } else if(cursor.getString(myDb.COL_BEHAVIOURAL).equals("No")){
                rg.check(R.id.radio_BehaviouralNo);
            } else{
                rg.clearCheck();
            }

            rg=(RadioGroup)findViewById(R.id.rgConfusion);
            if (cursor.getString(myDb.COL_CONFUSION).equals("Yes")){
                rg.check(R.id.radio_ConfusionYes);
            } else if(cursor.getString(myDb.COL_CONFUSION).equals("No")){
                rg.check(R.id.radio_ConfusionNo);
            } else{
                rg.clearCheck();
            }

            rg=(RadioGroup)findViewById(R.id.rgBladder);
            if (cursor.getString(myDb.COL_BLADDER).equals("Yes")){
                rg.check(R.id.radio_BladderYes);
            } else if(cursor.getString(myDb.COL_BLADDER).equals("Partial")){
                rg.check(R.id.radio_BladderPartial);
            } else if(cursor.getString(myDb.COL_BLADDER).equals("No")){
                rg.check(R.id.radio_BladderNo);
            } else{
                rg.clearCheck();
            }

            EditText editText = (EditText) findViewById(R.id.aHours);
            editText.setText(cursor.getString(myDb.COL_HOURS));

            //OT

            rg=(RadioGroup)findViewById(R.id.rgNeglect);
            if (cursor.getString(myDb.COL_NEGLECT).equals("Yes")){
                rg.check(R.id.radio_NeglectYes);
            } else if(cursor.getString(myDb.COL_NEGLECT).equals("No")){
                rg.check(R.id.radio_NeglectNo);
            } else{
                rg.clearCheck();
            }

            rg=(RadioGroup)findViewById(R.id.rgDigitSpan);
            if (cursor.getString(myDb.COL_DIGITSPAN).equals("1")){
                rg.check(R.id.radio_DigitSpan1);
            } else if (cursor.getString(myDb.COL_DIGITSPAN).equals("2")){
                rg.check(R.id.radio_DigitSpan2);
            } else if (cursor.getString(myDb.COL_DIGITSPAN).equals("3")){
                rg.check(R.id.radio_DigitSpan3);
            } else if (cursor.getString(myDb.COL_DIGITSPAN).equals("4")){
                rg.check(R.id.radio_DigitSpan4);
            } else if (cursor.getString(myDb.COL_DIGITSPAN).equals("5")){
                rg.check(R.id.radio_DigitSpan5);
            } else if (cursor.getString(myDb.COL_DIGITSPAN).equals("6")){
                rg.check(R.id.radio_DigitSpan6);
            } else if (cursor.getString(myDb.COL_DIGITSPAN).equals("7")){
                rg.check(R.id.radio_DigitSpan7);
            } else{
                rg.clearCheck();
            }

            editText = (EditText) findViewById(R.id.aMmse);
            editText.setText(cursor.getString(myDb.COL_MMSE));

            rg=(RadioGroup)findViewById(R.id.rgFollows);
            if (cursor.getString(myDb.COL_FOLLOWS).equals("None")){
                rg.check(R.id.radio_Follows0);
            } else if(cursor.getString(myDb.COL_FOLLOWS).equals("1 step")){
                rg.check(R.id.radio_Follows1);
            } else if(cursor.getString(myDb.COL_FOLLOWS).equals("2 step")){
                rg.check(R.id.radio_Follows2);
            } else{
                rg.clearCheck();
            }

            rg=(RadioGroup)findViewById(R.id.rgVerbal);
            if (cursor.getString(myDb.COL_VERBAL).equals("None")){
                rg.check(R.id.radio_VerbalNone);
            } else if(cursor.getString(myDb.COL_VERBAL).equals("Partial")){
                rg.check(R.id.radio_VerbalPartial);
            } else if(cursor.getString(myDb.COL_VERBAL).equals("Full")){
                rg.check(R.id.radio_VerbalFull);
            } else{
                rg.clearCheck();
            }

            rg=(RadioGroup)findViewById(R.id.rgMotivation);
            if (cursor.getString(myDb.COL_MOTIVATION).equals("1")){
                rg.check(R.id.radio_Motivation1);
            } else if (cursor.getString(myDb.COL_MOTIVATION).equals("2")){
                rg.check(R.id.radio_Motivation2);
            } else if (cursor.getString(myDb.COL_MOTIVATION).equals("3")){
                rg.check(R.id.radio_Motivation3);
            } else if (cursor.getString(myDb.COL_MOTIVATION).equals("4")){
                rg.check(R.id.radio_Motivation4);
            } else if (cursor.getString(myDb.COL_MOTIVATION).equals("5")){
                rg.check(R.id.radio_Motivation5);
            } else if (cursor.getString(myDb.COL_MOTIVATION).equals("6")){
                rg.check(R.id.radio_Motivation6);
            } else if (cursor.getString(myDb.COL_MOTIVATION).equals("7")){
                rg.check(R.id.radio_Motivation7);
            } else{
                rg.clearCheck();
            }

            rg=(RadioGroup)findViewById(R.id.rgMood);
            if (cursor.getString(myDb.COL_MOOD).equals("1")){
                rg.check(R.id.radio_Mood1);
            } else if (cursor.getString(myDb.COL_MOOD).equals("2")){
                rg.check(R.id.radio_Mood2);
            } else if (cursor.getString(myDb.COL_MOOD).equals("3")){
                rg.check(R.id.radio_Mood3);
            } else if (cursor.getString(myDb.COL_MOOD).equals("4")){
                rg.check(R.id.radio_Mood4);
            } else if (cursor.getString(myDb.COL_MOOD).equals("5")){
                rg.check(R.id.radio_Mood5);
            } else if (cursor.getString(myDb.COL_MOOD).equals("6")){
                rg.check(R.id.radio_Mood6);
            } else if (cursor.getString(myDb.COL_MOOD).equals("7")){
                rg.check(R.id.radio_Mood7);
            } else{
                rg.clearCheck();
            }

            rg=(RadioGroup)findViewById(R.id.rgPain);
            if (cursor.getString(myDb.COL_PAIN).equals("1")){
                rg.check(R.id.radio_Pain1);
            } else if (cursor.getString(myDb.COL_PAIN).equals("2")){
                rg.check(R.id.radio_Pain2);
            } else if (cursor.getString(myDb.COL_PAIN).equals("3")){
                rg.check(R.id.radio_Pain3);
            } else if (cursor.getString(myDb.COL_PAIN).equals("4")){
                rg.check(R.id.radio_Pain4);
            } else if (cursor.getString(myDb.COL_PAIN).equals("5")){
                rg.check(R.id.radio_Pain5);
            } else if (cursor.getString(myDb.COL_PAIN).equals("6")){
                rg.check(R.id.radio_Pain6);
            } else if (cursor.getString(myDb.COL_PAIN).equals("7")){
                rg.check(R.id.radio_Pain7);
            } else{
                rg.clearCheck();
            }

            rg=(RadioGroup)findViewById(R.id.rgFatigue);
            if (cursor.getString(myDb.COL_FATIGUE).equals("1")){
                rg.check(R.id.radio_Fatigue1);
            } else if (cursor.getString(myDb.COL_FATIGUE).equals("2")){
                rg.check(R.id.radio_Fatigue2);
            } else if (cursor.getString(myDb.COL_FATIGUE).equals("3")){
                rg.check(R.id.radio_Fatigue3);
            } else if (cursor.getString(myDb.COL_FATIGUE).equals("4")){
                rg.check(R.id.radio_Fatigue4);
            } else if (cursor.getString(myDb.COL_FATIGUE).equals("5")){
                rg.check(R.id.radio_Fatigue5);
            } else if (cursor.getString(myDb.COL_FATIGUE).equals("6")){
                rg.check(R.id.radio_Fatigue6);
            } else if (cursor.getString(myDb.COL_FATIGUE).equals("7")){
                rg.check(R.id.radio_Fatigue7);
            } else{
                rg.clearCheck();
            }

            rg=(RadioGroup)findViewById(R.id.rgSwallow);
            if (cursor.getString(myDb.COL_SWALLOW).equals("None")){
                rg.check(R.id.radio_SwallowNone);
            } else if(cursor.getString(myDb.COL_SWALLOW).equals("Partial")){
                rg.check(R.id.radio_SwallowPartial);
            } else if(cursor.getString(myDb.COL_SWALLOW).equals("Full")){
                rg.check(R.id.radio_SwallowFull);
            } else{
                rg.clearCheck();
            }

            rg=(RadioGroup)findViewById(R.id.rgFeeding);
            if (cursor.getString(myDb.COL_FEEDING).equals("None")){
                rg.check(R.id.radio_FeedingNone);
            } else if(cursor.getString(myDb.COL_FEEDING).equals("Partial")){
                rg.check(R.id.radio_FeedingPartial);
            } else if(cursor.getString(myDb.COL_FEEDING).equals("Full")){
                rg.check(R.id.radio_FeedingFull);
            } else{
                rg.clearCheck();
            }

            rg=(RadioGroup)findViewById(R.id.rgDressing);
            if (cursor.getString(myDb.COL_DRESSING).equals("None")){
                rg.check(R.id.radio_DressingNone);
            } else if(cursor.getString(myDb.COL_DRESSING).equals("Partial")){
                rg.check(R.id.radio_DressingPartial);
            } else if(cursor.getString(myDb.COL_DRESSING).equals("Full")){
                rg.check(R.id.radio_DressingFull);
            } else{
                rg.clearCheck();
            }

            rg=(RadioGroup)findViewById(R.id.rgKitchen);
            if (cursor.getString(myDb.COL_KITCHEN).equals("None")){
                rg.check(R.id.radio_KitchenNone);
            } else if(cursor.getString(myDb.COL_KITCHEN).equals("Partial")){
                rg.check(R.id.radio_KitchenPartial);
            } else if(cursor.getString(myDb.COL_KITCHEN).equals("Full")){
                rg.check(R.id.radio_KitchenFull);
            } else{
                rg.clearCheck();
            }

            //PT

            rg=(RadioGroup)findViewById(R.id.rgLeftArm);
            if (cursor.getString(myDb.COL_LEFTARM).equals("None")){
                rg.check(R.id.radio_LeftArmNone);
            } else if (cursor.getString(myDb.COL_LEFTARM).equals("Partial")){
                rg.check(R.id.radio_LeftArmPartial);
            } else if (cursor.getString(myDb.COL_LEFTARM).equals("Full")){
                rg.check(R.id.radio_LeftArmFull);
            } else{
                rg.clearCheck();
            }

            rg=(RadioGroup)findViewById(R.id.rgRightArm);
            if (cursor.getString(myDb.COL_RIGHTARM).equals("None")){
                rg.check(R.id.radio_RightArmNone);
            } else if (cursor.getString(myDb.COL_RIGHTARM).equals("Partial")){
                rg.check(R.id.radio_RightArmPartial);
            } else if (cursor.getString(myDb.COL_RIGHTARM).equals("Full")){
                rg.check(R.id.radio_RightArmFull);
            } else{
                rg.clearCheck();
            }

            rg=(RadioGroup)findViewById(R.id.rgMovementBed);
            if (cursor.getString(myDb.COL_MOVEMENTBED).equals("None")){
                rg.check(R.id.radio_MovementBedNone);
            } else if (cursor.getString(myDb.COL_MOVEMENTBED).equals("Partial")){
                rg.check(R.id.radio_MovementBedPartial);
            } else if (cursor.getString(myDb.COL_MOVEMENTBED).equals("Full")){
                rg.check(R.id.radio_MovementBedFull);
            } else{
                rg.clearCheck();
            }

            rg=(RadioGroup)findViewById(R.id.rgLieSit);
            if (cursor.getString(myDb.COL_LIESIT).equals("None")){
                rg.check(R.id.radio_LieSitNone);
            } else if(cursor.getString(myDb.COL_LIESIT).equals("Partial")){
                rg.check(R.id.radio_LieSitPartial);
            } else if(cursor.getString(myDb.COL_LIESIT).equals("Full")){
                rg.check(R.id.radio_LieSitFull);
            } else{
                rg.clearCheck();
            }

            rg=(RadioGroup)findViewById(R.id.rgSitting);
            if (cursor.getString(myDb.COL_SITTING).equals("None")){
                rg.check(R.id.radio_SittingNone);
            } else if(cursor.getString(myDb.COL_SITTING).equals("Partial")){
                rg.check(R.id.radio_SittingPartial);
            } else if(cursor.getString(myDb.COL_SITTING).equals("Full")){
                rg.check(R.id.radio_SittingFull);
            } else{
                rg.clearCheck();
            }

            rg=(RadioGroup)findViewById(R.id.rgSitStand);
            if (cursor.getString(myDb.COL_SITSTAND).equals("None")){
                rg.check(R.id.radio_SitStandNone);
            } else if(cursor.getString(myDb.COL_SITSTAND).equals("Partial")){
                rg.check(R.id.radio_SitStandPartial);
            } else if(cursor.getString(myDb.COL_SITSTAND).equals("Full")){
                rg.check(R.id.radio_SitStandFull);
            } else{
                rg.clearCheck();
            }

            rg=(RadioGroup)findViewById(R.id.rgStand);
            if (cursor.getString(myDb.COL_STAND).equals("None")){
                rg.check(R.id.radio_StandNone);
            } else if(cursor.getString(myDb.COL_STAND).equals("Partial")){
                rg.check(R.id.radio_StandPartial);
            } else if(cursor.getString(myDb.COL_STAND).equals("Full")){
                rg.check(R.id.radio_StandFull);
            } else{
                rg.clearCheck();
            }

            rg=(RadioGroup)findViewById(R.id.rgLiftsUnaffected);
            if (cursor.getString(myDb.COL_LIFTSUNAFFECTED).equals("None")){
                rg.check(R.id.radio_LiftsUnaffectedNone);
            } else if(cursor.getString(myDb.COL_LIFTSUNAFFECTED).equals("Partial")){
                rg.check(R.id.radio_LiftsUnaffectedPartial);
            } else if(cursor.getString(myDb.COL_LIFTSUNAFFECTED).equals("Full")){
                rg.check(R.id.radio_LiftsUnaffectedFull);
            } else{
                rg.clearCheck();
            }

            rg=(RadioGroup)findViewById(R.id.rgLiftsAffected);
            if (cursor.getString(myDb.COL_LIFTSAFFECTED).equals("None")){
                rg.check(R.id.radio_LiftsAffectedNone);
            } else if(cursor.getString(myDb.COL_LIFTSAFFECTED).equals("Partial")){
                rg.check(R.id.radio_LiftsAffectedPartial);
            } else if(cursor.getString(myDb.COL_LIFTSAFFECTED).equals("Full")){
                rg.check(R.id.radio_LiftsAffectedFull);
            } else{
                rg.clearCheck();
            }

            rg=(RadioGroup)findViewById(R.id.rgWalking);
            if (cursor.getString(myDb.COL_WALKING).equals("None")){
                rg.check(R.id.radio_WalkingNone);
            } else if(cursor.getString(myDb.COL_WALKING).equals("Partial")){
                rg.check(R.id.radio_WalkingPartial);
            } else if(cursor.getString(myDb.COL_WALKING).equals("Full")){
                rg.check(R.id.radio_WalkingFull);
            } else{
                rg.clearCheck();
            }


        } else{

            //Nurse
            RadioGroup rg=(RadioGroup)findViewById(R.id.rgPeg);
            rg.clearCheck();

            rg=(RadioGroup)findViewById(R.id.rgNg);
            rg.clearCheck();

            rg=(RadioGroup)findViewById(R.id.rgO2);
            rg.clearCheck();

            rg=(RadioGroup)findViewById(R.id.rgIv);
            rg.clearCheck();

            rg=(RadioGroup)findViewById(R.id.rgFoley);
            rg.clearCheck();

            rg=(RadioGroup)findViewById(R.id.rgCpap);
            rg.clearCheck();

            rg=(RadioGroup)findViewById(R.id.rgRestraint);
            rg.clearCheck();

            rg=(RadioGroup)findViewById(R.id.rgBehavioural);
            rg.clearCheck();

            rg=(RadioGroup)findViewById(R.id.rgConfusion);
            rg.clearCheck();

            rg=(RadioGroup)findViewById(R.id.rgBladder);
            rg.clearCheck();

            EditText editText = (EditText) findViewById(R.id.aHours);
            editText.setText("");
            editText.setHint(R.string.hoursHint);


            //OT
            rg=(RadioGroup)findViewById(R.id.rgNeglect);
            rg.clearCheck();

            rg=(RadioGroup)findViewById(R.id.rgDigitSpan);
            rg.clearCheck();

            editText = (EditText) findViewById(R.id.aMmse);
            editText.setText("");
            editText.setHint(R.string.mmseHint);

            rg=(RadioGroup)findViewById(R.id.rgFollows);
            rg.clearCheck();

            rg=(RadioGroup)findViewById(R.id.rgVerbal);
            rg.clearCheck();

            rg=(RadioGroup)findViewById(R.id.rgMotivation);
            rg.clearCheck();

            rg=(RadioGroup)findViewById(R.id.rgMood);
            rg.clearCheck();

            rg=(RadioGroup)findViewById(R.id.rgPain);
            rg.clearCheck();

            rg=(RadioGroup)findViewById(R.id.rgFatigue);
            rg.clearCheck();

            rg=(RadioGroup)findViewById(R.id.rgSwallow);
            rg.clearCheck();

            rg=(RadioGroup)findViewById(R.id.rgFeeding);
            rg.clearCheck();

            rg=(RadioGroup)findViewById(R.id.rgDressing);
            rg.clearCheck();

            rg=(RadioGroup)findViewById(R.id.rgKitchen);
            rg.clearCheck();


            //PT
            rg=(RadioGroup)findViewById(R.id.rgLeftArm);
            rg.clearCheck();

            rg=(RadioGroup)findViewById(R.id.rgRightArm);
            rg.clearCheck();

            rg=(RadioGroup)findViewById(R.id.rgMovementBed);
            rg.clearCheck();

            rg=(RadioGroup)findViewById(R.id.rgLieSit);
            rg.clearCheck();

            rg=(RadioGroup)findViewById(R.id.rgSitting);
            rg.clearCheck();

            rg=(RadioGroup)findViewById(R.id.rgSitStand);
            rg.clearCheck();

            rg=(RadioGroup)findViewById(R.id.rgStand);
            rg.clearCheck();

            rg=(RadioGroup)findViewById(R.id.rgLiftsUnaffected);
            rg.clearCheck();

            rg=(RadioGroup)findViewById(R.id.rgLiftsAffected);
            rg.clearCheck();

            rg=(RadioGroup)findViewById(R.id.rgWalking);
            rg.clearCheck();

        }


    }

}

