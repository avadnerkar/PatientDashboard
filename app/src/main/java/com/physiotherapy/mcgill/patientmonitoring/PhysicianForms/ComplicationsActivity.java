package com.physiotherapy.mcgill.patientmonitoring.PhysicianForms;

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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.physiotherapy.mcgill.patientmonitoring.MainGroup.MainActivity;
import com.physiotherapy.mcgill.patientmonitoring.MainGroup.PhysicianFragment;
import com.physiotherapy.mcgill.patientmonitoring.R;
import com.physiotherapy.mcgill.patientmonitoring.Utilities.DBAdapter;

import java.util.ArrayList;
import java.util.List;

public class ComplicationsActivity extends ActionBarActivity {

    private ArrayList<String> items;
    public static ListAdapter adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complications);

        listView = (ListView) findViewById(R.id.list);

        items = new ArrayList<>();
        items.add(getString(R.string.complicationsSepsis));
        items.add(getString(R.string.complicationsCerebralEdema));
        items.add(getString(R.string.complicationsFall));
        items.add(getString(R.string.complicationsICH));
        items.add(getString(R.string.complicationsCHF));
        items.add(getString(R.string.complicationsArrhythmia));
        items.add(getString(R.string.complicationsAfib));
        items.add(getString(R.string.complicationsDelirium));
        items.add(getString(R.string.complicationsUti));
        items.add(getString(R.string.complicationsPneumonia));

        adapter = new ListAdapter(this, items);
        listView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_complications, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
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

            TextView title;
            String dbKey = null;
            if (convertView == null){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.cell_complications, parent, false);
            }

            title = (TextView) convertView.findViewById(R.id.title);
            title.setText(items.get(position));

            if (items.get(position).equals(getString(R.string.complicationsSepsis))){
                dbKey = DBAdapter.patientMap.get("KEY_COMPLICATIONS_SEPSIS");
            } else if (items.get(position).equals(getString(R.string.complicationsCerebralEdema))){
                dbKey = DBAdapter.patientMap.get("KEY_COMPLICATIONS_CEREBRALEDEMA");
            } else if (items.get(position).equals(getString(R.string.complicationsFall))){
                dbKey = DBAdapter.patientMap.get("KEY_COMPLICATIONS_FALL");
            } else if (items.get(position).equals(getString(R.string.complicationsICH))){
                dbKey = DBAdapter.patientMap.get("KEY_COMPLICATIONS_ICH");
            } else if (items.get(position).equals(getString(R.string.complicationsCHF))){
                dbKey = DBAdapter.patientMap.get("KEY_COMPLICATIONS_CHF");
            } else if (items.get(position).equals(getString(R.string.complicationsArrhythmia))){
                dbKey = DBAdapter.patientMap.get("KEY_COMPLICATIONS_ARRHYTHMIA");
            } else if (items.get(position).equals(getString(R.string.complicationsAfib))){
                dbKey = DBAdapter.patientMap.get("KEY_COMPLICATIONS_AFIB");
            } else if (items.get(position).equals(getString(R.string.complicationsDelirium))){
                dbKey = DBAdapter.patientMap.get("KEY_COMPLICATIONS_DELIRIUM");
            } else if (items.get(position).equals(getString(R.string.complicationsUti))){
                dbKey = DBAdapter.patientMap.get("KEY_COMPLICATIONS_UTI");
            } else if (items.get(position).equals(getString(R.string.complicationsPneumonia))){
                dbKey = DBAdapter.patientMap.get("KEY_COMPLICATIONS_PNEUMONIA");
            }

            final String dbKeyFinal = dbKey;

            CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);

            Cursor cursor = MainActivity.myDb.getPatientField(MainActivity.currentPatientId, dbKey);

            if (cursor.moveToFirst()){
                String checkBoxValue = cursor.getString(cursor.getColumnIndex(dbKey));
                if (checkBoxValue !=null){
                    checkBox.setChecked(true);
                } else {
                    checkBox.setChecked(false);
                }

            } else {
                checkBox.setChecked(false);
            }

            cursor.close();

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final boolean b = ((CheckBox)view).isChecked();
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            MainActivity.myDb.updateFieldPatient(MainActivity.currentPatientId, dbKeyFinal, b ? "Yes" : null);

                        }
                    };
                    thread.start();

                }
            });


            return convertView;
        }
    }
}
