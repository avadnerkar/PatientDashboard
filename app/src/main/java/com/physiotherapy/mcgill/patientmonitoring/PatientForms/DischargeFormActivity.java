package com.physiotherapy.mcgill.patientmonitoring.PatientForms;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.physiotherapy.mcgill.patientmonitoring.Utilities.DBAdapter;
import com.physiotherapy.mcgill.patientmonitoring.MainGroup.MainActivity;
import com.physiotherapy.mcgill.patientmonitoring.R;

import java.util.ArrayList;

public class DischargeFormActivity extends ActionBarActivity {


    public static ArrayList<FormItem> dischargeFormItems;
    public static FormListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discharge_form);

        ListView listView = (ListView) findViewById(R.id.list);

        dischargeFormItems = new ArrayList<>();
        dischargeFormItems.add(new FormItem(getString(R.string.text_moca), FormItem.CellType.SCORE, new String[]{""}, DBAdapter.patientMap.get("KEY_MOCASCORE")));
        dischargeFormItems.get(dischargeFormItems.size()-1).misc = 30;
        dischargeFormItems.add(new FormItem(getString(R.string.text_custom), FormItem.CellType.SCORE, new String[]{""}, DBAdapter.patientMap.get("KEY_CUSTOMSCORE")));
        dischargeFormItems.get(dischargeFormItems.size()-1).misc = DBAdapter.patientMap.get("KEY_CUSTOMMAX");
        dischargeFormItems.add(new FormItem(getString(R.string.text_discharge), FormItem.CellType.DATEPICKER, null, DBAdapter.patientMap.get("KEY_DISCHARGEDATE")));
        dischargeFormItems.add(new FormItem(getString(R.string.totalOT), FormItem.CellType.NUMERIC, new String[]{""}, DBAdapter.patientMap.get("KEY_TOTALOT")));
        dischargeFormItems.add(new FormItem(getString(R.string.totalPT), FormItem.CellType.NUMERIC, new String[]{""}, DBAdapter.patientMap.get("KEY_TOTALPT")));
        dischargeFormItems.add(new FormItem(getString(R.string.totalSLT), FormItem.CellType.NUMERIC, new String[]{""}, DBAdapter.patientMap.get("KEY_TOTALSLT")));

        for (FormItem item : dischargeFormItems){
            item.group = FormItem.Group.DISCHARGE;
        }

        adapter = new FormListAdapter(this, dischargeFormItems);
        listView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_discharge_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.patient_save) {
            MainActivity.myDb.updateFieldPatient(MainActivity.currentPatientId, DBAdapter.patientMap.get("KEY_DISCHARGED"), "YES");
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
