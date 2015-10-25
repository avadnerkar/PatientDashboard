package com.physiotherapy.mcgill.patientmonitoring.MainGroup;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.opencsv.CSVWriter;
import com.physiotherapy.mcgill.patientmonitoring.Utilities.ActivityIndicator;
import com.physiotherapy.mcgill.patientmonitoring.Utilities.DBAdapter;
import com.physiotherapy.mcgill.patientmonitoring.PatientForms.DischargeFormActivity;
import com.physiotherapy.mcgill.patientmonitoring.PatientForms.PatientFormActivity;
import com.physiotherapy.mcgill.patientmonitoring.R;
import com.physiotherapy.mcgill.patientmonitoring.Scores.ScoreCalculators;
import com.physiotherapy.mcgill.patientmonitoring.Scores.ScoreGraphs;
import com.physiotherapy.mcgill.patientmonitoring.Scores.ScoreHistory;


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
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openDB();
        currentPatientId = -1;
        saveToggle = true;

        context = this;

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


    public void getCurrentDay(){
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        if (currentPatientId != -1){
            Cursor cursor = myDb.getRowPatient(currentPatientId);

            String discharged = cursor.getString(cursor.getColumnIndex(DBAdapter.patientMap.get("KEY_DISCHARGED")));

            if (discharged !=null && discharged.equals("Yes")){
                currentDay = 1;
            } else {
                String dateString = cursor.getString(cursor.getColumnIndex(DBAdapter.patientMap.get("KEY_ADMISSIONDATE")));
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
            cursor.close();
        }
    }

    public void updateDayView(){
        TextView textView = (TextView) findViewById(R.id.dayNumber);
        textView.setText("Day " + currentDay);
        notifyAdapters();

    }


    public void notifyAdapters(){
        PhysicianFragment.adapter.notifyDataSetChanged();
        PtFragment.ptAdapter.notifyDataSetChanged();
        OtFragment.otAdapter.notifyDataSetChanged();
        NurseFragment.nurseAdapter.notifyDataSetChanged();
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
            menu.findItem(R.id.action_discharge).setVisible(false);
            menu.findItem(R.id.action_update_patient).setVisible(false);
            menu.findItem(R.id.clear).setVisible(false);
            menu.findItem(R.id.action_score_report).setVisible(false);
            menu.findItem(R.id.action_score_graphs).setVisible(false);
        }
        else{
            menu.findItem(R.id.action_discharge).setVisible(true);
            menu.findItem(R.id.action_update_patient).setVisible(true);
            menu.findItem(R.id.clear).setVisible(true);
            menu.findItem(R.id.action_score_report).setVisible(true);
            menu.findItem(R.id.action_score_graphs).setVisible(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


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
            newPatientRunnable.run();
            return true;
        }

        if (item.getItemId() == R.id.action_score_report) {
            scoreReportRunnable.run();
            return true;
        }

        if (item.getItemId() == R.id.action_score_graphs) {
            scoreGraphsRunnable.run();
            return true;
        }

        if (item.getItemId() == R.id.action_update_patient) {
            updatePatientRunnable.run();
            return true;
        }

        if (item.getItemId() == R.id.action_discharge) {
            dischargePatientRunnable.run();
            return true;
        }

        if (item.getItemId() == R.id.action_export_csv) {
            exportRunnable.run();
            return true;
        }

        if (id == R.id.patient_list) {
            showPatientList();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }



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
        ListView physicianLayout = (ListView) findViewById(R.id.physicianList);
        physicianLayout.setVisibility(View.INVISIBLE);

        invalidateOptionsMenu();

        currentMrn = null;
        updateDayView();
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());

        LinearLayout toolbar = (LinearLayout) findViewById(R.id.toolbar);
        if (tab.getPosition() == 3){
            toolbar.setVisibility(View.GONE);
        } else {
            toolbar.setVisibility(View.VISIBLE);
        }
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
                case 3: return PhysicianFragment.newInstance(position + 1);
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
                String mrn = cursor.getString(cursor.getColumnIndex(DBAdapter.patientMap.get("KEY_MRN"))) != null ? cursor.getString(cursor.getColumnIndex(DBAdapter.patientMap.get("KEY_MRN"))) : "";
                String firstName = cursor.getString(cursor.getColumnIndex(DBAdapter.patientMap.get("KEY_FIRSTNAME"))) != null ? cursor.getString(cursor.getColumnIndex(DBAdapter.patientMap.get("KEY_FIRSTNAME"))) : "";
                String lastName = cursor.getString(cursor.getColumnIndex(DBAdapter.patientMap.get("KEY_LASTNAME"))) != null ? cursor.getString(cursor.getColumnIndex(DBAdapter.patientMap.get("KEY_LASTNAME"))) : "";
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
                        ListView physicianLayout = (ListView) findViewById(R.id.physicianList);
                        physicianLayout.setVisibility(View.VISIBLE);

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
        }
    };

    Runnable incrementRunnable = new Runnable(){
        public void run(){
            currentDay = Math.min(99, currentDay + 1);
            updateDayView();
        }
    };

    Runnable newPatientRunnable = new Runnable(){
        public void run(){
            clearPatientSelection();

            currentPatientId = (int) myDb.insertNewPatient();
            Intent intent = new Intent(MainActivity.this, PatientFormActivity.class);
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
            Intent intent = new Intent(MainActivity.this, PatientFormActivity.class);
            startActivity(intent);
        }
    };

    Runnable dischargePatientRunnable = new Runnable(){
        public void run(){
            Intent intent = new Intent(MainActivity.this, DischargeFormActivity.class);
            startActivity(intent);
        }
    };

    Runnable exportRunnable = new Runnable(){
        public void run(){
            exportToCSV();
        }
    };



    public void decrementDayClicked(View view){
        decrementRunnable.run();
    }


    public void incrementDayClicked(View view){
        incrementRunnable.run();
    }

    public void exportToCSV() {


        ActivityIndicator.showProgressDialog(this);

        Thread thread = new Thread(){
            @Override
            public void run() {
                File path = Environment.getExternalStorageDirectory();
                File filename = new File(path, "/exportPatientData.csv");

                try {
                    CSVWriter writer = new CSVWriter(new FileWriter(filename), '\t');
                    writer.writeNext(new String[]{"sep=\t"});

                    saveScores();

                    Cursor c = myDb.getAllRowData();
                    writer.writeNext(c.getColumnNames());
                    if (c.moveToFirst()){
                        do {
                            List<String> list = new ArrayList<>();
                            for (int i=0; i<c.getColumnCount(); i++){
                                list.add(c.getString(i));
                            }
                            String[] arrStr = list.toArray(new String[list.size()]);
                            writer.writeNext(arrStr);
                        } while(c.moveToNext());
                    }

                    writer.close();
                    c.close();


                } catch (final IOException e){

                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ActivityIndicator.dismissProgressDialog();
                            System.err.println("Caught IOException: " + e.getMessage());
                            CharSequence toastMessage = "Export failed";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, toastMessage, duration);
                            toast.show();
                        }
                    });

                }

                filename = new File(path, "/exportPatientList.csv");

                try {
                    CSVWriter writer = new CSVWriter(new FileWriter(filename), '\t');
                    writer.writeNext(new String[]{"sep=\t"});
                    Cursor c = myDb.getAllRowPatients();
                    writer.writeNext(c.getColumnNames());
                    if (c.moveToFirst()){
                        do {
                            List<String> list = new ArrayList<>();
                            for (int i=0; i<c.getColumnCount(); i++){
                                list.add(c.getString(i));
                            }
                            String[] arrStr = list.toArray(new String[list.size()]);
                            writer.writeNext(arrStr);
                        } while(c.moveToNext());
                    }

                    writer.close();
                    c.close();


                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ActivityIndicator.dismissProgressDialog();
                            CharSequence toastMessage = "Export successful";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, toastMessage, duration);
                            toast.show();
                        }
                    });


                } catch (final IOException e){

                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ActivityIndicator.dismissProgressDialog();
                            System.err.println("Caught IOException: " + e.getMessage());
                            CharSequence toastMessage = "Export failed";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, toastMessage, duration);
                            toast.show();
                        }
                    });
                }
            }
        };
        thread.start();


    }




    public static void saveScores(){

        Cursor c = myDb.getAllRowData();

        if (c.moveToFirst()){
            do {
                myDb.updateFieldData(c.getInt(c.getColumnIndex(DBAdapter.dataMap.get("KEY_PARENTID"))), c.getInt(c.getColumnIndex(DBAdapter.dataMap.get("KEY_DAY"))), DBAdapter.dataMap.get("KEY_BARTHEL"), String.valueOf(calculateScores(c, "Barthel")[0]));
                myDb.updateFieldData(c.getInt(c.getColumnIndex(DBAdapter.dataMap.get("KEY_PARENTID"))), c.getInt(c.getColumnIndex(DBAdapter.dataMap.get("KEY_DAY"))), DBAdapter.dataMap.get("KEY_BERG"), String.valueOf(calculateScores(c, "Berg")[0]));
                myDb.updateFieldData(c.getInt(c.getColumnIndex(DBAdapter.dataMap.get("KEY_PARENTID"))), c.getInt(c.getColumnIndex(DBAdapter.dataMap.get("KEY_DAY"))), DBAdapter.dataMap.get("KEY_CNS"), String.valueOf(calculateScores(c, "CNS")[0]));
                myDb.updateFieldData(c.getInt(c.getColumnIndex(DBAdapter.dataMap.get("KEY_PARENTID"))), c.getInt(c.getColumnIndex(DBAdapter.dataMap.get("KEY_DAY"))), DBAdapter.dataMap.get("KEY_NIHSS"), String.valueOf(calculateScores(c, "NIHSS")[0]));
            } while (c.moveToNext());
        }

        c.close();
    }



    public static float[] calculateScores(Cursor cursor, String selectedScoreType){


        switch (selectedScoreType) {
            case "Barthel":
                return ScoreCalculators.barthelScore(cursor);

            case "Berg":
                return ScoreCalculators.bergScore(cursor);

            case "CNS":

                return ScoreCalculators.cnsScore(cursor);

            case "NIHSS":

                return ScoreCalculators.nihssScore(cursor);

            default:

                return new float[]{-1};
        }

    }

}

