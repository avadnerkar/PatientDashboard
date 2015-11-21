package com.physiotherapy.mcgill.patientmonitoring.MainGroup;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.physiotherapy.mcgill.patientmonitoring.PhysicianForms.ComplicationsActivity;
import com.physiotherapy.mcgill.patientmonitoring.R;
import com.physiotherapy.mcgill.patientmonitoring.Utilities.DBAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SelectPatientActivity extends ActionBarActivity {

    private ArrayList<Patient> items;
    public static ListAdapter adapter;
    private ListView listView;
    //private boolean[] DischargeArray;

    public class Patient {
        public boolean discharged;
        public int id;
        public String mrn;
        public String title;

        public Patient(String title, int id, String mrn, boolean discharged){
            this.title = title;
            this.id = id;
            this.mrn = mrn;
            this.discharged = discharged;
        }
    }

    public class DischargeComparator implements Comparator<Patient>{

        @Override
        public int compare(Patient o1, Patient o2) {
            if (o1.discharged == o2.discharged){
                return 0;
            } else {
                return o1.discharged? -1:1;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_patient);

        listView = (ListView) findViewById(R.id.list);

        Cursor cursor = MainActivity.myDb.getAllRowPatients();

        items = new ArrayList<>();
        //final int[] IDarray = new int[cursor.getCount()];
        //final String[] MrnArray = new String[cursor.getCount()];
        //DischargeArray = new boolean[cursor.getCount()];

        if (cursor.moveToFirst()) {
            do {
                // Process the data:
                int id = cursor.getInt(cursor.getColumnIndex(DBAdapter.KEY_ROWID));
                String mrn = cursor.getString(cursor.getColumnIndex(DBAdapter.patientMap.get("KEY_MRN"))) != null ? cursor.getString(cursor.getColumnIndex(DBAdapter.patientMap.get("KEY_MRN"))) : "";
                String firstName = cursor.getString(cursor.getColumnIndex(DBAdapter.patientMap.get("KEY_FIRSTNAME"))) != null ? cursor.getString(cursor.getColumnIndex(DBAdapter.patientMap.get("KEY_FIRSTNAME"))) : "";
                String lastName = cursor.getString(cursor.getColumnIndex(DBAdapter.patientMap.get("KEY_LASTNAME"))) != null ? cursor.getString(cursor.getColumnIndex(DBAdapter.patientMap.get("KEY_LASTNAME"))) : "";
                boolean discharged;
                if (cursor.getString(cursor.getColumnIndex(DBAdapter.patientMap.get("KEY_DISCHARGED"))) != null){
                    discharged = (cursor.getString(cursor.getColumnIndex(DBAdapter.patientMap.get("KEY_DISCHARGED")))).equals("YES");
                } else {
                    discharged = false;
                }
                //IDarray[cursor.getPosition()] = id;
                //MrnArray[cursor.getPosition()] = mrn;
                //DischargeArray[cursor.getPosition()] = discharged;
                items.add(new Patient(mrn + " " + firstName + " " + lastName, id, mrn, discharged));

            } while(cursor.moveToNext());
        }

        Collections.sort(items, new DischargeComparator());

        adapter = new ListAdapter(this, items);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MainActivity.currentPatientId = items.get(i).id;
                MainActivity.actionBar.setTitle(items.get(i).title);
                MainActivity.currentMrn = items.get(i).mrn;
                finish();
            }
        });

    }


    public class ListAdapter extends ArrayAdapter<Patient> {
        private Context context;
        private List<Patient> items;

        public ListAdapter(Context context, List<Patient> items){
            super(context, R.layout.cell_rankin, items);
            this.context = context;
            this.items = items;
        }


        //******************************* Array adapter methods ***********************************//
        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public int getItemViewType(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if (convertView == null){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.cell_select_patient, parent, false);
            }

            TextView title = (TextView) convertView.findViewById(R.id.title);
            title.setText(items.get(position).title);

            TextView description = (TextView) convertView.findViewById(R.id.description);

            if (items.get(position).discharged){
                description.setTextColor(getResources().getColor(R.color.Red));
                description.setText("Discharged");
            } else {
                description.setTextColor(getResources().getColor(R.color.Green));
                description.setText("Active");
            }

            return convertView;
        }
    }
}
