package com.physiotherapy.mcgill.patientmonitoring;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.opencsv.CSVWriter;



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
    public static int currentPatientId;
    public static int currentDay;
    public static String currentMrn;
    public String[] patientListString;
    public int elapsedDays;
    public boolean saveToggle;
    //public boolean existingPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openDB();
        currentPatientId = -1;
        saveToggle = true;


        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.Indigo)));
        actionBar.setStackedBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.Indigo)));

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(3);

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
        invalidateOptionsMenu();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //Handle the back button
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            if (!saveToggle){
                //Ask the user if they want to quit
                new AlertDialog.Builder(this)
                        .setTitle("Save?")
                        .setCancelable(true)
                        .setMessage("Do you wish to save before quitting?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //savePatientData();
                                //Stop the activity
                                MainActivity.this.finish();
                            }

                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //Stop the activity
                                MainActivity.this.finish();
                            }

                        })
                        .show();

            } else{
                MainActivity.this.finish();
            }
            return true;


        }else {
            return super.onKeyDown(keyCode, event);
        }

    }

    public void getCurrentDay(){
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        if (currentPatientId != -1){
            Cursor cursor = myDb.getRowPatient(currentPatientId);

            String discharged = cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_DISCHARGED));

            if (discharged.equals("Yes")){
                currentDay = 1;
            } else {
                String dateString = cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_ADMISSIONDATE));
                SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

                Date admissionDate = today;
                try{
                    admissionDate = form.parse(dateString);
                } catch (java.text.ParseException e){
                    e.printStackTrace();

                }

                elapsedDays = getElapsedTimeInDays(admissionDate,today) + 1;

                currentDay = elapsedDays;
            }

            updateDayView();
            //loadPatientData();
            cursor.close();
        }
    }

    public void updateDayView(){
        TextView textView = (TextView) findViewById(R.id.dayNumber);
        textView.setText("Day " + currentDay);
        notifyAdapters();

    }


    public void notifyAdapters(){
        CnsFragment.cnsAdapter.notifyDataSetChanged();
        PtFragment.ptAdapter.notifyDataSetChanged();
        OtFragment.otAdapter.notifyDataSetChanged();
        NurseFragment.nurseAdapter.notifyDataSetChanged();
    }

    public void scrollToTop(){
        ListView listView = (ListView) findViewById(R.id.nurseList);
        listView.setSelectionAfterHeaderView();

        listView = (ListView) findViewById(R.id.otList);
        listView.setSelectionAfterHeaderView();

        listView = (ListView) findViewById(R.id.ptList);
        listView.setSelectionAfterHeaderView();

        listView = (ListView) findViewById(R.id.cnsList);
        listView.setSelectionAfterHeaderView();
    }

    public int getElapsedTimeInDays(Date start,Date end){
        long daysLong= (end.getTime()-start.getTime()) /(1000*60*60*24);
        return (int) (long) daysLong;
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
            menu.findItem(R.id.clear).setVisible(false);
            menu.findItem(R.id.action_score_report).setVisible(false);
        }
        else{
            menu.findItem(R.id.data_save).setVisible(true);
            menu.findItem(R.id.action_discharge).setVisible(true);
            menu.findItem(R.id.action_update_patient).setVisible(true);
            menu.findItem(R.id.clear).setVisible(true);
            menu.findItem(R.id.action_score_report).setVisible(true);
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

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Delete all patients?");
            builder.setMessage("This cannot be undone");
            builder.setCancelable(true);

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // if this button is clicked, close
                    // current activity

                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setTitle("Delete...");
                    alert.setMessage("Enter Pin :");

                    // Set an EditText view to get user input
                    final EditText input = new EditText(MainActivity.this);
                    alert.setView(input);

                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            String value = input.getText().toString();
                            if (value.equals("1379")){
                                myDb.deleteAllPatients();
                                clearPatientSelection();
                                //loadPatientData();
                                Context context = getApplicationContext();
                                CharSequence toastMessage = "All patients deleted";
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(context, toastMessage, duration);
                                toast.show();
                            } else{
                                Context context = getApplicationContext();
                                CharSequence toastMessage = "Incorrect PIN";
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(context, toastMessage, duration);
                                toast.show();
                            }
                        }
                    });

                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                        }
                    });
                    alert.show();

                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // if this button is clicked, just close
                    // the dialog box and do nothing
                    dialog.cancel();
                }
            });

            // create alert dialog
            AlertDialog alertDialog = builder.create();

            // show it
            alertDialog.show();

            return true;
        }

        if (id == R.id.clear) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Delete current patient?");
            builder.setMessage("This cannot be undone");
            builder.setCancelable(true);

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // if this button is clicked, close
                    // current activity
                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setTitle("Delete...");
                    alert.setMessage("Enter Pin :");

                    // Set an EditText view to get user input
                    final EditText input = new EditText(MainActivity.this);
                    alert.setView(input);

                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            String value = input.getText().toString();
                            if (value.equals("1379")) {
                                myDb.deleteCurrentPatient(currentPatientId);
                                clearPatientSelection();
                                //loadPatientData();
                                Context context = getApplicationContext();
                                CharSequence toastMessage = "Patient deleted";
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(context, toastMessage, duration);
                                toast.show();
                            } else {
                                Context context = getApplicationContext();
                                CharSequence toastMessage = "Incorrect PIN";
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(context, toastMessage, duration);
                                toast.show();
                            }
                            return;
                        }
                    });
                    alert.show();
                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // if this button is clicked, just close
                    // the dialog box and do nothing
                    dialog.cancel();
                }
            });

            // create alert dialog
            AlertDialog alertDialog = builder.create();

            // show it
            alertDialog.show();


            return true;
        }

        if (item.getItemId() == R.id.patient_new) {
            saveCurrentDay(newPatientRunnable);
            return true;
        }

        if (item.getItemId() == R.id.action_score_report) {
            saveCurrentDay(scoreReportRunnable);
            return true;
        }

        if (item.getItemId() == R.id.action_score_graphs) {
            saveCurrentDay(scoreGraphsRunnable);
            return true;
        }

        if (item.getItemId() == R.id.action_update_patient) {
            saveCurrentDay(updatePatientRunnable);
            return true;
        }

        if (item.getItemId() == R.id.action_discharge) {
            saveCurrentDay(dischargePatientRunnable);
            return true;
        }

        if (item.getItemId() == R.id.action_export_csv) {
            saveCurrentDay(exportRunnable);
            return true;
        }

        if (id == R.id.patient_list) {
            showPatientList();

            return true;
        }

        if (id == R.id.data_save) {
            //savePatientData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void switchSaveToggle(View view){
        saveToggle = false;
    }


//    public void showA1(View view){
//        switchSaveToggle(view);
//        LinearLayout a1layout = (LinearLayout) findViewById(R.id.cnsA1);
//        a1layout.setVisibility(View.VISIBLE);
//
//        LinearLayout a2layout = (LinearLayout) findViewById(R.id.cnsA2);
//        a2layout.setVisibility(View.GONE);
//
//        clearA1Selections();
//        clearA2Selections();
//    }
//
//
//    public void showA2(View view){
//        switchSaveToggle(view);
//        LinearLayout a2layout = (LinearLayout) findViewById(R.id.cnsA2);
//        a2layout.setVisibility(View.VISIBLE);
//
//        LinearLayout a1layout = (LinearLayout) findViewById(R.id.cnsA1);
//        a1layout.setVisibility(View.GONE);
//
//        clearA1Selections();
//        clearA2Selections();
//
//    }


//    public void clearA1Selections(){
//        RadioGroup rg;
//
//        rg = (RadioGroup) findViewById(R.id.rgCnsA1_4);
//        rg.clearCheck();
//
//        rg = (RadioGroup) findViewById(R.id.rgCnsA1_5);
//        rg.clearCheck();
//
//        rg = (RadioGroup) findViewById(R.id.rgCnsA1_6);
//        rg.clearCheck();
//
//        rg = (RadioGroup) findViewById(R.id.rgCnsA1_7);
//        rg.clearCheck();
//
//        rg = (RadioGroup) findViewById(R.id.rgCnsA1_8);
//        rg.clearCheck();
//    }
//
//
//    public void clearA2Selections(){
//
//        RadioGroup rg;
//
//        rg = (RadioGroup) findViewById(R.id.rgCnsA2_4);
//        rg.clearCheck();
//
//        rg = (RadioGroup) findViewById(R.id.rgCnsA2_5);
//        rg.clearCheck();
//
//        rg = (RadioGroup) findViewById(R.id.rgCnsA2_6);
//        rg.clearCheck();
//    }

    public void clearPatientSelection(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.app_name);
        currentDay = 1;
        currentPatientId = -1;
        ListView nurseLayout = (ListView) findViewById(R.id.nurseList);
        nurseLayout.setVisibility(View.INVISIBLE);
        ListView otLayout = (ListView) findViewById(R.id.otList);
        otLayout.setVisibility(View.INVISIBLE);
        ListView ptLayout = (ListView) findViewById(R.id.ptList);
        ptLayout.setVisibility(View.INVISIBLE);
        ListView cnsLayout = (ListView) findViewById(R.id.cnsList);
        cnsLayout.setVisibility(View.INVISIBLE);

        invalidateOptionsMenu();

        currentMrn = null;
        updateDayView();
        //loadPatientData();
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
                case 3: return CnsFragment.newInstance(position + 1);
                default: return NurseFragment.newInstance(position + 1);
            }


        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
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
                case 3:
                    return getString(R.string.title_section4).toUpperCase(l);
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
                int id = cursor.getInt(cursor.getColumnIndex(DBAdapter.KEY_ROWID));
                String mrn = cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_HOSPITALID));
                String firstName = cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_FIRSTNAME));
                String lastName = cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_LASTNAME));
                IDarray[cursor.getPosition()] = id;
                MRNarray[cursor.getPosition()] = mrn;
                patientListString[cursor.getPosition()] = mrn + " " + firstName + " " + lastName;

            } while(cursor.moveToNext());
        }

        builder.setItems(patientListString,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        currentPatientIndex = which;
                        ActionBar actionBar = getSupportActionBar();
                        actionBar.setTitle(patientListString[which]);
                        currentPatientId = IDarray[which];
                        currentMrn = MRNarray[which];

                        ListView nurseLayout = (ListView) findViewById(R.id.nurseList);
                        nurseLayout.setVisibility(View.VISIBLE);
                        ListView otLayout = (ListView) findViewById(R.id.otList);
                        otLayout.setVisibility(View.VISIBLE);
                        ListView ptLayout = (ListView) findViewById(R.id.ptList);
                        ptLayout.setVisibility(View.VISIBLE);
                        ListView cnsLayout = (ListView) findViewById(R.id.cnsList);
                        cnsLayout.setVisibility(View.VISIBLE);

                        invalidateOptionsMenu();

                        getCurrentDay();
                        updateDayView();

                    }
                }
        );

        builder.show();

        cursor.close();
    }

    Runnable decrementRunnable = new Runnable(){
        public void run(){
            currentDay = Math.max(1, currentDay - 1);
            updateDayView();
            //loadPatientData();
        }
    };

    Runnable incrementRunnable = new Runnable(){
        public void run(){
            currentDay = Math.min(99, currentDay + 1);
            updateDayView();
            //loadPatientData();
        }
    };

    Runnable newPatientRunnable = new Runnable(){
        public void run(){
            clearPatientSelection();

            Intent intent = new Intent(MainActivity.this, NewPatientFormActivity.class);
            startActivity(intent);
        }
    };

    Runnable scoreReportRunnable = new Runnable(){
        public void run(){

            Intent intent = new Intent(MainActivity.this, ScoreHistory.class);
            startActivity(intent);
        }
    };

    Runnable scoreGraphsRunnable = new Runnable(){
        public void run(){

            Intent intent = new Intent(MainActivity.this, ScoreGraphs.class);
            startActivity(intent);
        }
    };

    Runnable updatePatientRunnable = new Runnable(){
        public void run(){
            Intent intent = new Intent(MainActivity.this, NewPatientFormActivity.class);
            startActivity(intent);
        }
    };

    Runnable dischargePatientRunnable = new Runnable(){
        public void run(){
            Intent intent = new Intent(MainActivity.this, DischargePatientForm.class);
            startActivity(intent);
        }
    };

    Runnable exportRunnable = new Runnable(){
        public void run(){
            exportToCSV();
        }
    };

    public void saveCurrentDay(final Runnable r){

        if (saveToggle){
            r.run();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Save current day?");
            builder.setMessage("The current day has not been saved.  Do you wish to save?");
            builder.setCancelable(true);

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // if this button is clicked, close
                    // current activity

                    //savePatientData();

                    Context context = getApplicationContext();
                    CharSequence toastMessage = "Day saved";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, toastMessage, duration);
                    toast.show();

                    r.run();
                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // if this button is clicked, just close
                    // the dialog box and do nothing
                    r.run();
                    saveToggle = true;
                }
            });

            // create alert dialog
            AlertDialog alertDialog = builder.create();

            // show it
            alertDialog.show();
        }
    }

    public void decrementDayClicked(View view){
        saveCurrentDay(decrementRunnable);
    }


    public void incrementDayClicked(View view){
        saveCurrentDay(incrementRunnable);
    }

    public void exportToCSV() {
        File path = Environment.getExternalStorageDirectory();
        File filename = new File(path, "/exportPatientData.csv");

        try {
            CSVWriter writer = new CSVWriter(new FileWriter(filename), '\t');
            writer.writeNext(new String[]{"sep=\t"});
            Cursor c = myDb.getAllRowData();
            writer.writeNext(c.getColumnNames());
            if (c.moveToFirst()){
                do {
                    String arrStr[] ={c.getString(c.getColumnIndex(DBAdapter.KEY_ROWID)), c.getString(c.getColumnIndex(DBAdapter.KEY_PARENTID)), c.getString(c.getColumnIndex(DBAdapter.KEY_MRN)), c.getString(c.getColumnIndex(DBAdapter.KEY_DAY)),
                            c.getString(c.getColumnIndex(DBAdapter.KEY_PEG)), c.getString(c.getColumnIndex(DBAdapter.KEY_NG)), c.getString(c.getColumnIndex(DBAdapter.KEY_O2)), c.getString(c.getColumnIndex(DBAdapter.KEY_IV)), c.getString(c.getColumnIndex(DBAdapter.KEY_CPAP)), c.getString(c.getColumnIndex(DBAdapter.KEY_RESTRAINT)), c.getString(c.getColumnIndex(DBAdapter.KEY_BEDBARS)), c.getString(c.getColumnIndex(DBAdapter.KEY_BEHAVIOURAL)), c.getString(c.getColumnIndex(DBAdapter.KEY_CONFUSION)), c.getString(c.getColumnIndex(DBAdapter.KEY_BLADDER)), c.getString(c.getColumnIndex(DBAdapter.KEY_HOURS)),
                            c.getString(c.getColumnIndex(DBAdapter.KEY_NEGLECT)), c.getString(c.getColumnIndex(DBAdapter.KEY_DIGITSPAN)), c.getString(c.getColumnIndex(DBAdapter.KEY_MMSE)), c.getString(c.getColumnIndex(DBAdapter.KEY_FOLLOWS)), c.getString(c.getColumnIndex(DBAdapter.KEY_VERBAL)), c.getString(c.getColumnIndex(DBAdapter.KEY_MOTIVATION)), c.getString(c.getColumnIndex(DBAdapter.KEY_MOOD)), c.getString(c.getColumnIndex(DBAdapter.KEY_PAIN)), c.getString(c.getColumnIndex(DBAdapter.KEY_FATIGUE)), c.getString(c.getColumnIndex(DBAdapter.KEY_SWALLOW)), c.getString(c.getColumnIndex(DBAdapter.KEY_FEEDING)), c.getString(c.getColumnIndex(DBAdapter.KEY_DRESSING)), c.getString(c.getColumnIndex(DBAdapter.KEY_KITCHEN)),
                            c.getString(c.getColumnIndex(DBAdapter.KEY_LEFTARM)), c.getString(c.getColumnIndex(DBAdapter.KEY_RIGHTARM)), c.getString(c.getColumnIndex(DBAdapter.KEY_MOVEMENTBED)), c.getString(c.getColumnIndex(DBAdapter.KEY_LIESIT)), c.getString(c.getColumnIndex(DBAdapter.KEY_SITTING)), c.getString(c.getColumnIndex(DBAdapter.KEY_SITSTAND)), c.getString(c.getColumnIndex(DBAdapter.KEY_STAND)), c.getString(c.getColumnIndex(DBAdapter.KEY_LIFTSUNAFFECTED)), c.getString(c.getColumnIndex(DBAdapter.KEY_LIFTSAFFECTED)), c.getString(c.getColumnIndex(DBAdapter.KEY_WALKING)),
                            c.getString(c.getColumnIndex(DBAdapter.KEY_BARTHEL)), c.getString(c.getColumnIndex(DBAdapter.KEY_BERG)),
                            c.getString(c.getColumnIndex(DBAdapter.KEY_CNS_CONSCIOUSNESS)), c.getString(c.getColumnIndex(DBAdapter.KEY_CNS_ORIENTATION)), c.getString(c.getColumnIndex(DBAdapter.KEY_CNS_SPEECH)), c.getString(c.getColumnIndex(DBAdapter.KEY_CNS_FACE1)), c.getString(c.getColumnIndex(DBAdapter.KEY_CNS_UPPER_LIMB_PROXIMAL)), c.getString(c.getColumnIndex(DBAdapter.KEY_CNS_UPPER_LIMB_DISTAL)), c.getString(c.getColumnIndex(DBAdapter.KEY_CNS_LOWER_LIMB_PROXIMAL)), c.getString(c.getColumnIndex(DBAdapter.KEY_CNS_LOWER_LIMB_DISTAL)), c.getString(c.getColumnIndex(DBAdapter.KEY_CNS_FACE2)), c.getString(c.getColumnIndex(DBAdapter.KEY_CNS_UPPER_LIMBS)), c.getString(c.getColumnIndex(DBAdapter.KEY_CNS_LOWER_LIMBS)),
                            c.getString(c.getColumnIndex(DBAdapter.KEY_CNS)), c.getString(c.getColumnIndex(DBAdapter.KEY_NIHSS))};
                    writer.writeNext(arrStr);
                } while(c.moveToNext());
            }

            writer.close();
            c.close();


        } catch (IOException e){
            System.err.println("Caught IOException: " + e.getMessage());
        }

        filename = new File(path, "/exportPatientList.csv");

        try {
            CSVWriter writer = new CSVWriter(new FileWriter(filename), '\t');
            writer.writeNext(new String[]{"sep=\t"});
            Cursor c = myDb.getAllRowPatients();
            writer.writeNext(c.getColumnNames());
            if (c.moveToFirst()){
                do {
                    String arrStr[] ={c.getString(c.getColumnIndex(DBAdapter.KEY_ROWID)), c.getString(c.getColumnIndex(DBAdapter.KEY_FIRSTNAME)), c.getString(c.getColumnIndex(DBAdapter.KEY_LASTNAME)), c.getString(c.getColumnIndex(DBAdapter.KEY_HOSPITALID)), c.getString(c.getColumnIndex(DBAdapter.KEY_ADMISSIONDATE)), c.getString(c.getColumnIndex(DBAdapter.KEY_DISCHARGED)), c.getString(c.getColumnIndex(DBAdapter.KEY_DISCHARGEDATE)), c.getString(c.getColumnIndex(DBAdapter.KEY_PATIENTAGE)), c.getString(c.getColumnIndex(DBAdapter.KEY_PATIENTGENDER)), c.getString(c.getColumnIndex(DBAdapter.KEY_FIRSTLANGUAGE)),
                            c.getString(c.getColumnIndex(DBAdapter.KEY_MOCASCORE)), c.getString(c.getColumnIndex(DBAdapter.KEY_CUSTOMSCORE)), c.getString(c.getColumnIndex(DBAdapter.KEY_CUSTOMMAX)),
                            c.getString(c.getColumnIndex(DBAdapter.KEY_STROKETYPE)), c.getString(c.getColumnIndex(DBAdapter.KEY_FIRSTSTROKE)), c.getString(c.getColumnIndex(DBAdapter.KEY_LESIONSIDE)), c.getString(c.getColumnIndex(DBAdapter.KEY_HEMIPLEGIASIDE)), c.getString(c.getColumnIndex(DBAdapter.KEY_CONSCIOUSNESS)), c.getString(c.getColumnIndex(DBAdapter.KEY_ORIENTATION)), c.getString(c.getColumnIndex(DBAdapter.KEY_LANGUAGE)), c.getString(c.getColumnIndex(DBAdapter.KEY_VISUAL)), c.getString(c.getColumnIndex(DBAdapter.KEY_HEARINGAID)), c.getString(c.getColumnIndex(DBAdapter.KEY_HEARINGASSESSED)), c.getString(c.getColumnIndex(DBAdapter.KEY_APHASIA)),
                            c.getString(c.getColumnIndex(DBAdapter.KEY_PEGADMIT)), c.getString(c.getColumnIndex(DBAdapter.KEY_NGADMIT)), c.getString(c.getColumnIndex(DBAdapter.KEY_FOLEYADMIT)), c.getString(c.getColumnIndex(DBAdapter.KEY_FALLRISK)), c.getString(c.getColumnIndex(DBAdapter.KEY_MOTIVATIONADMIT)), c.getString(c.getColumnIndex(DBAdapter.KEY_OTHER)), c.getString(c.getColumnIndex(DBAdapter.KEY_COGNITION)),
                            c.getString(c.getColumnIndex(DBAdapter.KEY_FIRSTOT)), c.getString(c.getColumnIndex(DBAdapter.KEY_TOTALOT)), c.getString(c.getColumnIndex(DBAdapter.KEY_FIRSTSWALLOW)), c.getString(c.getColumnIndex(DBAdapter.KEY_FIRSTPT)), c.getString(c.getColumnIndex(DBAdapter.KEY_TOTALPT)), c.getString(c.getColumnIndex(DBAdapter.KEY_FIRSTSLT)), c.getString(c.getColumnIndex(DBAdapter.KEY_TOTALSLT))
                            };
                    writer.writeNext(arrStr);
                } while(c.moveToNext());
            }

            writer.close();
            c.close();

            Context context = getApplicationContext();
            CharSequence toastMessage = "Export successful";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, toastMessage, duration);
            toast.show();


        } catch (IOException e){
            System.err.println("Caught IOException: " + e.getMessage());
            Context context = getApplicationContext();
            CharSequence toastMessage = "Export failed";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, toastMessage, duration);
            toast.show();
        }
    }

//    public void savePatientData(){
//        saveToggle = true;
//        RadioGroup rg;
//        int id;
//        View radioButton;
//        int radioId;
//        RadioButton btn;
//        String currentPeg, currentNg, currentO2, currentIv, currentCpap, currentRestraint, currentBedbars, currentBehavioural, currentConfusion, currentBladder, currentHours,
//            currentNeglect, currentDigitSpan, currentMmse, currentFollows, currentVerbal, currentMotivation, currentMood, currentPain, currentFatigue, currentSwallow, currentFeeding, currentDressing, currentKitchen,
//            currentLeftArm, currentRightArm, currentMovementBed, currentLieSit, currentSitting, currentSitStand, currentStand, currentLiftsUnaffected, currentLiftsAffected, currentWalking,
//            currentBarthel, currentBerg,
//            currentCnsConsciousness, currentCnsOrientation, currentCnsSpeech, currentCnsFace1, currentCnsUpperLimbProximal, currentCnsUpperLimbDistal, currentCnsLowerLimbProximal, currentCnsLowerLimbDistal, currentCnsUpperLimbs, currentCnsLowerLimbs, currentCnsFace2,
//            currentCnsScore, currentNihssScore;
//
//        //Nurse
//        rg=(RadioGroup)findViewById(R.id.rgPeg);
//        id = rg.getCheckedRadioButtonId();
//        if (id!=-1) {
//            radioButton = rg.findViewById(id);
//            radioId = rg.indexOfChild(radioButton);
//            btn = (RadioButton) rg.getChildAt(radioId);
//            currentPeg = (String) btn.getText();
//        } else{
//            currentPeg = "";
//        }
//
//        rg=(RadioGroup)findViewById(R.id.rgNg);
//        id = rg.getCheckedRadioButtonId();
//        if (id!=-1) {
//            radioButton = rg.findViewById(id);
//            radioId = rg.indexOfChild(radioButton);
//            btn = (RadioButton) rg.getChildAt(radioId);
//            currentNg = (String) btn.getText();
//        } else{
//            currentNg = "";
//        }
//
//        rg=(RadioGroup)findViewById(R.id.rgO2);
//        id = rg.getCheckedRadioButtonId();
//        if (id!=-1) {
//            radioButton = rg.findViewById(id);
//            radioId = rg.indexOfChild(radioButton);
//            btn = (RadioButton) rg.getChildAt(radioId);
//            currentO2 = (String) btn.getText();
//        } else{
//            currentO2 = "";
//        }
//
//        rg=(RadioGroup)findViewById(R.id.rgIv);
//        id = rg.getCheckedRadioButtonId();
//        if (id!=-1) {
//            radioButton = rg.findViewById(id);
//            radioId = rg.indexOfChild(radioButton);
//            btn = (RadioButton) rg.getChildAt(radioId);
//            currentIv = (String) btn.getText();
//        } else{
//            currentIv = "";
//        }
//
//        rg=(RadioGroup)findViewById(R.id.rgCpap);
//        id = rg.getCheckedRadioButtonId();
//        if (id!=-1) {
//            radioButton = rg.findViewById(id);
//            radioId = rg.indexOfChild(radioButton);
//            btn = (RadioButton) rg.getChildAt(radioId);
//            currentCpap = (String) btn.getText();
//        } else{
//            currentCpap = "";
//        }
//
//        rg=(RadioGroup)findViewById(R.id.rgRestraint);
//        id = rg.getCheckedRadioButtonId();
//        if (id!=-1) {
//            radioButton = rg.findViewById(id);
//            radioId = rg.indexOfChild(radioButton);
//            btn = (RadioButton) rg.getChildAt(radioId);
//            currentRestraint = (String) btn.getText();
//        } else{
//            currentRestraint = "";
//        }
//
//        rg=(RadioGroup)findViewById(R.id.rgBedbars);
//        id = rg.getCheckedRadioButtonId();
//        if (id!=-1) {
//            radioButton = rg.findViewById(id);
//            radioId = rg.indexOfChild(radioButton);
//            btn = (RadioButton) rg.getChildAt(radioId);
//            currentBedbars = (String) btn.getText();
//        } else{
//            currentBedbars = "";
//        }
//
//        rg=(RadioGroup)findViewById(R.id.rgBehavioural);
//        id = rg.getCheckedRadioButtonId();
//        if (id!=-1) {
//            radioButton = rg.findViewById(id);
//            radioId = rg.indexOfChild(radioButton);
//            btn = (RadioButton) rg.getChildAt(radioId);
//            currentBehavioural = (String) btn.getText();
//        } else{
//            currentBehavioural = "";
//        }
//
//        rg=(RadioGroup)findViewById(R.id.rgConfusion);
//        id = rg.getCheckedRadioButtonId();
//        if (id!=-1) {
//            radioButton = rg.findViewById(id);
//            radioId = rg.indexOfChild(radioButton);
//            btn = (RadioButton) rg.getChildAt(radioId);
//            currentConfusion = (String) btn.getText();
//        } else{
//            currentConfusion = "";
//        }
//
//        rg=(RadioGroup)findViewById(R.id.rgBladder);
//        id = rg.getCheckedRadioButtonId();
//        if (id!=-1) {
//            radioButton = rg.findViewById(id);
//            radioId = rg.indexOfChild(radioButton);
//            btn = (RadioButton) rg.getChildAt(radioId);
//            currentBladder = (String) btn.getText();
//        } else {
//            currentBladder = "";
//        }
//
//        EditText editText = (EditText) findViewById(R.id.aHours);
//        currentHours = editText.getText().toString();
//
//        //OT
//        rg=(RadioGroup)findViewById(R.id.rgNeglect);
//        id = rg.getCheckedRadioButtonId();
//        if (id!=-1) {
//            radioButton = rg.findViewById(id);
//            radioId = rg.indexOfChild(radioButton);
//            btn = (RadioButton) rg.getChildAt(radioId);
//            currentNeglect = (String) btn.getText();
//        } else{
//            currentNeglect = "";
//        }
//
//        rg=(RadioGroup)findViewById(R.id.rgDigitSpan);
//        id = rg.getCheckedRadioButtonId();
//        if (id!=-1) {
//            radioButton = rg.findViewById(id);
//            radioId = rg.indexOfChild(radioButton);
//            btn = (RadioButton) rg.getChildAt(radioId);
//            currentDigitSpan = (String) btn.getText();
//        } else {
//            currentDigitSpan = "";
//        }
//
//        editText = (EditText) findViewById(R.id.aMmse);
//        currentMmse = editText.getText().toString();
//
//        rg=(RadioGroup)findViewById(R.id.rgFollows);
//        id = rg.getCheckedRadioButtonId();
//        if (id!=-1) {
//            radioButton = rg.findViewById(id);
//            radioId = rg.indexOfChild(radioButton);
//            btn = (RadioButton) rg.getChildAt(radioId);
//            currentFollows = (String) btn.getText();
//        } else {
//            currentFollows = "";
//        }
//
//        rg=(RadioGroup)findViewById(R.id.rgVerbal);
//        id = rg.getCheckedRadioButtonId();
//        if (id!=-1) {
//            radioButton = rg.findViewById(id);
//            radioId = rg.indexOfChild(radioButton);
//            btn = (RadioButton) rg.getChildAt(radioId);
//            currentVerbal = (String) btn.getText();
//        } else {
//            currentVerbal = "";
//        }
//
//        rg=(RadioGroup)findViewById(R.id.rgMotivation);
//        id = rg.getCheckedRadioButtonId();
//        if (id!=-1) {
//            radioButton = rg.findViewById(id);
//            radioId = rg.indexOfChild(radioButton);
//            btn = (RadioButton) rg.getChildAt(radioId);
//            currentMotivation = (String) btn.getText();
//        } else {
//            currentMotivation = "";
//        }
//
//        rg=(RadioGroup)findViewById(R.id.rgMood);
//        id = rg.getCheckedRadioButtonId();
//        if (id!=-1) {
//            radioButton = rg.findViewById(id);
//            radioId = rg.indexOfChild(radioButton);
//            btn = (RadioButton) rg.getChildAt(radioId);
//            currentMood = (String) btn.getText();
//        } else {
//            currentMood = "";
//        }
//
//        rg=(RadioGroup)findViewById(R.id.rgPain);
//        id = rg.getCheckedRadioButtonId();
//        if (id!=-1) {
//            radioButton = rg.findViewById(id);
//            radioId = rg.indexOfChild(radioButton);
//            btn = (RadioButton) rg.getChildAt(radioId);
//            currentPain = (String) btn.getText();
//        } else {
//            currentPain = "";
//        }
//
//        rg=(RadioGroup)findViewById(R.id.rgFatigue);
//        id = rg.getCheckedRadioButtonId();
//        if (id!=-1) {
//            radioButton = rg.findViewById(id);
//            radioId = rg.indexOfChild(radioButton);
//            btn = (RadioButton) rg.getChildAt(radioId);
//            currentFatigue = (String) btn.getText();
//        } else {
//            currentFatigue = "";
//        }
//
//        rg=(RadioGroup)findViewById(R.id.rgSwallow);
//        id = rg.getCheckedRadioButtonId();
//        if (id!=-1) {
//            radioButton = rg.findViewById(id);
//            radioId = rg.indexOfChild(radioButton);
//            btn = (RadioButton) rg.getChildAt(radioId);
//            currentSwallow = (String) btn.getText();
//        } else {
//            currentSwallow = "";
//        }
//
//        rg=(RadioGroup)findViewById(R.id.rgFeeding);
//        id = rg.getCheckedRadioButtonId();
//        if (id!=-1) {
//            radioButton = rg.findViewById(id);
//            radioId = rg.indexOfChild(radioButton);
//            btn = (RadioButton) rg.getChildAt(radioId);
//            currentFeeding = (String) btn.getText();
//        } else {
//            currentFeeding = "";
//        }
//
//        rg=(RadioGroup)findViewById(R.id.rgDressing);
//        id = rg.getCheckedRadioButtonId();
//        if (id!=-1) {
//            radioButton = rg.findViewById(id);
//            radioId = rg.indexOfChild(radioButton);
//            btn = (RadioButton) rg.getChildAt(radioId);
//            currentDressing = (String) btn.getText();
//        } else {
//            currentDressing = "";
//        }
//
//        rg=(RadioGroup)findViewById(R.id.rgKitchen);
//        id = rg.getCheckedRadioButtonId();
//        if (id!=-1) {
//            radioButton = rg.findViewById(id);
//            radioId = rg.indexOfChild(radioButton);
//            btn = (RadioButton) rg.getChildAt(radioId);
//            currentKitchen = (String) btn.getText();
//        } else {
//            currentKitchen = "";
//        }
//
//
//        //PT
////        rg=(RadioGroup)findViewById(R.id.rgLeftArm);
////        id = rg.getCheckedRadioButtonId();
////        if (id!=-1) {
////            radioButton = rg.findViewById(id);
////            radioId = rg.indexOfChild(radioButton);
////            btn = (RadioButton) rg.getChildAt(radioId);
////            currentLeftArm = (String) btn.getText();
////        } else {
//            currentLeftArm = "";
////        }
////
////        rg=(RadioGroup)findViewById(R.id.rgRightArm);
////        id = rg.getCheckedRadioButtonId();
////        if (id!=-1) {
////            radioButton = rg.findViewById(id);
////            radioId = rg.indexOfChild(radioButton);
////            btn = (RadioButton) rg.getChildAt(radioId);
////            currentRightArm = (String) btn.getText();
////        } else {
//            currentRightArm = "";
////        }
////
////        rg=(RadioGroup)findViewById(R.id.rgMovementBed);
////        id = rg.getCheckedRadioButtonId();
////        if (id!=-1) {
////            radioButton = rg.findViewById(id);
////            radioId = rg.indexOfChild(radioButton);
////            btn = (RadioButton) rg.getChildAt(radioId);
////            currentMovementBed = (String) btn.getText();
////        } else {
//            currentMovementBed = "";
////        }
////
////        rg=(RadioGroup)findViewById(R.id.rgLieSit);
////        id = rg.getCheckedRadioButtonId();
////        if (id!=-1) {
////            radioButton = rg.findViewById(id);
////            radioId = rg.indexOfChild(radioButton);
////            btn = (RadioButton) rg.getChildAt(radioId);
////            currentLieSit = (String) btn.getText();
////        } else {
//            currentLieSit = "";
////        }
////
////        rg=(RadioGroup)findViewById(R.id.rgSitting);
////        id = rg.getCheckedRadioButtonId();
////        if (id!=-1) {
////            radioButton = rg.findViewById(id);
////            radioId = rg.indexOfChild(radioButton);
////            btn = (RadioButton) rg.getChildAt(radioId);
////            currentSitting = (String) btn.getText();
////        } else {
//            currentSitting = "";
////        }
////
////        rg=(RadioGroup)findViewById(R.id.rgSitStand);
////        id = rg.getCheckedRadioButtonId();
////        if (id!=-1) {
////            radioButton = rg.findViewById(id);
////            radioId = rg.indexOfChild(radioButton);
////            btn = (RadioButton) rg.getChildAt(radioId);
////            currentSitStand = (String) btn.getText();
////        } else {
//            currentSitStand = "";
////        }
////
////        rg=(RadioGroup)findViewById(R.id.rgStand);
////        id = rg.getCheckedRadioButtonId();
////        if (id!=-1) {
////            radioButton = rg.findViewById(id);
////            radioId = rg.indexOfChild(radioButton);
////            btn = (RadioButton) rg.getChildAt(radioId);
////            currentStand = (String) btn.getText();
////        } else {
//            currentStand = "";
////        }
////
////        rg=(RadioGroup)findViewById(R.id.rgLiftsUnaffected);
////        id = rg.getCheckedRadioButtonId();
////        if (id!=-1) {
////            radioButton = rg.findViewById(id);
////            radioId = rg.indexOfChild(radioButton);
////            btn = (RadioButton) rg.getChildAt(radioId);
////            currentLiftsUnaffected = (String) btn.getText();
////        } else {
//            currentLiftsUnaffected = "";
////        }
////
////        rg=(RadioGroup)findViewById(R.id.rgLiftsAffected);
////        id = rg.getCheckedRadioButtonId();
////        if (id!=-1) {
////            radioButton = rg.findViewById(id);
////            radioId = rg.indexOfChild(radioButton);
////            btn = (RadioButton) rg.getChildAt(radioId);
////            currentLiftsAffected = (String) btn.getText();
////        } else {
//            currentLiftsAffected = "";
////        }
////
////        rg=(RadioGroup)findViewById(R.id.rgWalking);
////        id = rg.getCheckedRadioButtonId();
////        if (id!=-1) {
////            radioButton = rg.findViewById(id);
////            radioId = rg.indexOfChild(radioButton);
////            btn = (RadioButton) rg.getChildAt(radioId);
////            currentWalking = (String) btn.getText();
////        } else {
//            currentWalking = "";
////        }
//
//        currentBarthel = "placeholder";
//        currentBerg = "placeholder";
//
//
//        //////////////////////////////////
//
//
//        rg=(RadioGroup)findViewById(R.id.rgCns1);
//        id = rg.getCheckedRadioButtonId();
//        if (id!=-1) {
//            radioButton = rg.findViewById(id);
//            radioId = rg.indexOfChild(radioButton);
//            btn = (RadioButton) rg.getChildAt(radioId);
//            currentCnsConsciousness = (String) btn.getText();
//        } else {
//            currentCnsConsciousness = "";
//        }
//
//
//        rg=(RadioGroup)findViewById(R.id.rgCns2);
//        id = rg.getCheckedRadioButtonId();
//        if (id!=-1) {
//            radioButton = rg.findViewById(id);
//            radioId = rg.indexOfChild(radioButton);
//            btn = (RadioButton) rg.getChildAt(radioId);
//            currentCnsOrientation = (String) btn.getText();
//        } else {
//            currentCnsOrientation = "";
//        }
//
//        rg=(RadioGroup)findViewById(R.id.rgCns3);
//        id = rg.getCheckedRadioButtonId();
//        if (id!=-1) {
//            radioButton = rg.findViewById(id);
//            radioId = rg.indexOfChild(radioButton);
//            btn = (RadioButton) rg.getChildAt(radioId);
//            currentCnsSpeech = (String) btn.getText();
//        } else {
//            currentCnsSpeech = "";
//        }
//
//        rg=(RadioGroup)findViewById(R.id.rgCnsA1_4);
//        id = rg.getCheckedRadioButtonId();
//        if (id!=-1) {
//            radioButton = rg.findViewById(id);
//            radioId = rg.indexOfChild(radioButton);
//            btn = (RadioButton) rg.getChildAt(radioId);
//            currentCnsFace1 = (String) btn.getText();
//        } else {
//            currentCnsFace1 = "";
//        }
//
//        rg=(RadioGroup)findViewById(R.id.rgCnsA1_5);
//        id = rg.getCheckedRadioButtonId();
//        if (id!=-1) {
//            radioButton = rg.findViewById(id);
//            radioId = rg.indexOfChild(radioButton);
//            btn = (RadioButton) rg.getChildAt(radioId);
//            currentCnsUpperLimbProximal = (String) btn.getText();
//        } else {
//            currentCnsUpperLimbProximal = "";
//        }
//
//        rg=(RadioGroup)findViewById(R.id.rgCnsA1_6);
//        id = rg.getCheckedRadioButtonId();
//        if (id!=-1) {
//            radioButton = rg.findViewById(id);
//            radioId = rg.indexOfChild(radioButton);
//            btn = (RadioButton) rg.getChildAt(radioId);
//            currentCnsUpperLimbDistal = (String) btn.getText();
//        } else {
//            currentCnsUpperLimbDistal = "";
//        }
//
//        rg=(RadioGroup)findViewById(R.id.rgCnsA1_7);
//        id = rg.getCheckedRadioButtonId();
//        if (id!=-1) {
//            radioButton = rg.findViewById(id);
//            radioId = rg.indexOfChild(radioButton);
//            btn = (RadioButton) rg.getChildAt(radioId);
//            currentCnsLowerLimbProximal = (String) btn.getText();
//        } else {
//            currentCnsLowerLimbProximal = "";
//        }
//
//        rg=(RadioGroup)findViewById(R.id.rgCnsA1_8);
//        id = rg.getCheckedRadioButtonId();
//        if (id!=-1) {
//            radioButton = rg.findViewById(id);
//            radioId = rg.indexOfChild(radioButton);
//            btn = (RadioButton) rg.getChildAt(radioId);
//            currentCnsLowerLimbDistal = (String) btn.getText();
//        } else {
//            currentCnsLowerLimbDistal = "";
//        }
//
//        rg=(RadioGroup)findViewById(R.id.rgCnsA2_4);
//        id = rg.getCheckedRadioButtonId();
//        if (id!=-1) {
//            radioButton = rg.findViewById(id);
//            radioId = rg.indexOfChild(radioButton);
//            btn = (RadioButton) rg.getChildAt(radioId);
//            currentCnsFace2 = (String) btn.getText();
//        } else {
//            currentCnsFace2 = "";
//        }
//
//        rg=(RadioGroup)findViewById(R.id.rgCnsA2_5);
//        id = rg.getCheckedRadioButtonId();
//        if (id!=-1) {
//            radioButton = rg.findViewById(id);
//            radioId = rg.indexOfChild(radioButton);
//            btn = (RadioButton) rg.getChildAt(radioId);
//            currentCnsUpperLimbs = (String) btn.getText();
//        } else {
//            currentCnsUpperLimbs = "";
//        }
//
//        rg=(RadioGroup)findViewById(R.id.rgCnsA2_6);
//        id = rg.getCheckedRadioButtonId();
//        if (id!=-1) {
//            radioButton = rg.findViewById(id);
//            radioId = rg.indexOfChild(radioButton);
//            btn = (RadioButton) rg.getChildAt(radioId);
//            currentCnsLowerLimbs = (String) btn.getText();
//        } else {
//            currentCnsLowerLimbs = "";
//        }
//
//
//        currentCnsScore = "placeholder";
//        currentNihssScore = "placeholder";
//
//        ///////////////////////////////////
//
//        Cursor cursor = myDb.getRowData(currentPatientId, currentDay);
//        if (cursor.moveToFirst()){
//            myDb.updateRowData(currentPatientId, currentDay,
//                    currentPeg, currentNg, currentO2, currentIv, currentCpap, currentRestraint, currentBedbars, currentBehavioural, currentConfusion, currentBladder, currentHours,
//                    currentNeglect, currentDigitSpan, currentMmse, currentFollows, currentVerbal, currentMotivation, currentMood, currentPain, currentFatigue, currentSwallow, currentFeeding, currentDressing, currentKitchen,
//                    currentLeftArm, currentRightArm, currentMovementBed, currentLieSit, currentSitting, currentSitStand, currentStand, currentLiftsUnaffected, currentLiftsAffected, currentWalking,
//                    currentBarthel, currentBerg,
//                    currentCnsConsciousness, currentCnsOrientation, currentCnsSpeech, currentCnsFace1, currentCnsUpperLimbProximal, currentCnsUpperLimbDistal, currentCnsLowerLimbProximal, currentCnsLowerLimbDistal, currentCnsUpperLimbs, currentCnsLowerLimbs, currentCnsFace2,
//                    currentCnsScore, currentNihssScore);
//
//        }else{
//            myDb.insertRowData(currentPatientId, currentMrn, currentDay,
//                    currentPeg, currentNg, currentO2, currentIv, currentCpap, currentRestraint, currentBedbars, currentBehavioural, currentConfusion, currentBladder, currentHours,
//                    currentNeglect, currentDigitSpan, currentMmse, currentFollows, currentVerbal, currentMotivation, currentMood, currentPain, currentFatigue, currentSwallow, currentFeeding, currentDressing, currentKitchen,
//                    currentLeftArm, currentRightArm, currentMovementBed, currentLieSit, currentSitting, currentSitStand, currentStand, currentLiftsUnaffected, currentLiftsAffected, currentWalking,
//                    currentBarthel, currentBerg,
//                    currentCnsConsciousness, currentCnsOrientation, currentCnsSpeech, currentCnsFace1, currentCnsUpperLimbProximal, currentCnsUpperLimbDistal, currentCnsLowerLimbProximal, currentCnsLowerLimbDistal, currentCnsUpperLimbs, currentCnsLowerLimbs, currentCnsFace2,
//                    currentCnsScore, currentNihssScore);
//        }
//
//        cursor.close();
//
//        float[] scoreArrayBarthel = calculateScores(currentDay, "Barthel");
//        currentBarthel = String.valueOf(scoreArrayBarthel[0]);
//        float[] scoreArrayBerg = calculateScores(currentDay, "Berg");
//        currentBerg = String.valueOf(scoreArrayBerg[0]);
//        float[] scoreArrayCns = calculateScores(currentDay, "CNS");
//        currentCnsScore = String.valueOf(scoreArrayCns[0]);
//        float[] scoreArrayNihss = calculateScores(currentDay, "NIHSS");
//        currentNihssScore = String.valueOf(scoreArrayNihss[0]);
//
//        myDb.updateRowData(currentPatientId, currentDay,
//                currentPeg, currentNg, currentO2, currentIv, currentCpap, currentRestraint, currentBedbars, currentBehavioural, currentConfusion, currentBladder, currentHours,
//                currentNeglect, currentDigitSpan, currentMmse, currentFollows, currentVerbal, currentMotivation, currentMood, currentPain, currentFatigue, currentSwallow, currentFeeding, currentDressing, currentKitchen,
//                currentLeftArm, currentRightArm, currentMovementBed, currentLieSit, currentSitting, currentSitStand, currentStand, currentLiftsUnaffected, currentLiftsAffected, currentWalking,
//                currentBarthel, currentBerg,
//                currentCnsConsciousness, currentCnsOrientation, currentCnsSpeech, currentCnsFace1, currentCnsUpperLimbProximal, currentCnsUpperLimbDistal, currentCnsLowerLimbProximal, currentCnsLowerLimbDistal, currentCnsUpperLimbs, currentCnsLowerLimbs, currentCnsFace2,
//                currentCnsScore, currentNihssScore);
//    }

    public static void saveScores(){
        myDb.updateFieldData(currentPatientId, currentDay, DBAdapter.KEY_BARTHEL, String.valueOf(calculateScores(currentDay, "Barthel")[0]));
        myDb.updateFieldData(currentPatientId, currentDay, DBAdapter.KEY_BERG, String.valueOf(calculateScores(currentDay, "Berg")[0]));
        myDb.updateFieldData(currentPatientId, currentDay, DBAdapter.KEY_CNS, String.valueOf(calculateScores(currentDay, "CNS")[0]));
        myDb.updateFieldData(currentPatientId, currentDay, DBAdapter.KEY_NIHSS, String.valueOf(calculateScores(currentDay, "NIHSS")[0]));
    }

    public static float[] calculateScores(int scoreDay, String selectedScoreType){


        Cursor cursor = myDb.getRowData(currentPatientId, scoreDay);

        if (cursor.moveToFirst()){

            switch (selectedScoreType) {
                case "Barthel":
                    return ScoreCalculators.barthelScore(cursor);

                case "Berg":
                    return ScoreCalculators.bergScore(cursor);

                case "CNS":

                    return ScoreCalculators.cnsScore(cursor);

                default:
                    return ScoreCalculators.nihssScore(cursor);
            }
        } else{
            float score = -1;
            return new float[]{score};
        }

    }

}

