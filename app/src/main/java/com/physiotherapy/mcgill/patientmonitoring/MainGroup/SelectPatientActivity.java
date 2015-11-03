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

import com.physiotherapy.mcgill.patientmonitoring.R;
import com.physiotherapy.mcgill.patientmonitoring.Utilities.DBAdapter;

import java.util.ArrayList;
import java.util.List;

public class SelectPatientActivity extends ActionBarActivity {

    private ArrayList<String> items;
    public static ListAdapter adapter;
    private ListView listView;
    private boolean[] DischargeArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_patient);

        listView = (ListView) findViewById(R.id.list);

        Cursor cursor = MainActivity.myDb.getAllRowPatients();

        items = new ArrayList<>();
        final int[] IDarray = new int[cursor.getCount()];
        final String[] MrnArray = new String[cursor.getCount()];
        DischargeArray = new boolean[cursor.getCount()];

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
                IDarray[cursor.getPosition()] = id;
                MrnArray[cursor.getPosition()] = mrn;
                DischargeArray[cursor.getPosition()] = discharged;
                items.add(mrn + " " + firstName + " " + lastName);

            } while(cursor.moveToNext());
        }


        adapter = new ListAdapter(this, items);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MainActivity.currentPatientId = IDarray[i];
                MainActivity.actionBar.setTitle(items.get(i));
                MainActivity.currentMrn = MrnArray[i];
                finish();
            }
        });

    }


    public class ListAdapter extends ArrayAdapter<String> {
        private Context context;
        private List<String> items;

        public ListAdapter(Context context, List<String> items){
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
            title.setText(items.get(position));

            TextView description = (TextView) convertView.findViewById(R.id.description);

            if (DischargeArray[position]){
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
