package com.physiotherapy.mcgill.patientmonitoring.PatientForms;

import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.physiotherapy.mcgill.patientmonitoring.MainGroup.MainActivity;
import com.physiotherapy.mcgill.patientmonitoring.Utilities.DBAdapter;
import com.physiotherapy.mcgill.patientmonitoring.R;

import java.util.ArrayList;

public class PatientFormActivity extends ActionBarActivity {


    public static ArrayList<FormItem> patientFormItems;
    public static FormListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_form);

        ListView listView = (ListView) findViewById(R.id.list);

        patientFormItems = new ArrayList<>();
        patientFormItems.add(new FormItem(getString(R.string.first_name), FormItem.CellType.TEXT, new String[]{""}, DBAdapter.patientMap.get("KEY_FIRSTNAME")));
        patientFormItems.add(new FormItem(getString(R.string.last_name), FormItem.CellType.TEXT, new String[]{""}, DBAdapter.patientMap.get("KEY_LASTNAME")));
        patientFormItems.add(new FormItem(getString(R.string.patient_ID), FormItem.CellType.NUMERIC, new String[]{""}, DBAdapter.patientMap.get("KEY_MRN")));
        patientFormItems.add(new FormItem(getString(R.string.select_admission_date), FormItem.CellType.DATEPICKER, null, DBAdapter.patientMap.get("KEY_ADMISSIONDATE")));
        patientFormItems.add(new FormItem(getString(R.string.patient_age), FormItem.CellType.NUMERIC, new String[]{""}, DBAdapter.patientMap.get("KEY_PATIENTAGE")));
        patientFormItems.add(new FormItem(getString(R.string.patient_gender), FormItem.CellType.RADIO, new String[]{getString(R.string.female), getString(R.string.male)}, DBAdapter.patientMap.get("KEY_PATIENTGENDER")));
        patientFormItems.add(new FormItem(getString(R.string.first_language), FormItem.CellType.CHECKBOX, new String[]{getString(R.string.french), getString(R.string.english), getString(R.string.otherAnswer)}, DBAdapter.patientMap.get("KEY_FIRSTLANGUAGE")));

        patientFormItems.add(new FormItem(getString(R.string.stroke_type), FormItem.CellType.RADIO, new String[]{getString(R.string.tia), getString(R.string.ischemic), getString(R.string.hemorrhagic_ich), getString(R.string.hemorrhagic_sah)}, DBAdapter.patientMap.get("KEY_STROKETYPE")));
        patientFormItems.add(new FormItem(getString(R.string.openCnsForm), FormItem.CellType.BUTTON, null, null));

        patientFormItems.add(new FormItem(getString(R.string.first_stroke), FormItem.CellType.RADIO, new String[]{getString(R.string.yes), getString(R.string.no)}, DBAdapter.patientMap.get("KEY_FIRSTSTROKE")));
        patientFormItems.add(new FormItem(getString(R.string.lesion_side), FormItem.CellType.RADIO, new String[]{getString(R.string.diffuse), getString(R.string.right), getString(R.string.left), getString(R.string.both)}, DBAdapter.patientMap.get("KEY_LESIONSIDE")));
        patientFormItems.add(new FormItem(getString(R.string.hemiplegia_side), FormItem.CellType.RADIO, new String[]{getString(R.string.left), getString(R.string.none), getString(R.string.right)}, DBAdapter.patientMap.get("KEY_HEMIPLEGIASIDE")));
        patientFormItems.add(new FormItem(getString(R.string.consciousness), FormItem.CellType.RADIO, new String[]{getString(R.string.alert), getString(R.string.drowsy)}, DBAdapter.patientMap.get("KEY_CONSCIOUSNESS")));
        patientFormItems.add(new FormItem(getString(R.string.orientation), FormItem.CellType.RADIO, new String[]{getString(R.string.oriented), getString(R.string.disoriented)}, DBAdapter.patientMap.get("KEY_ORIENTATION")));
        patientFormItems.add(new FormItem(getString(R.string.visual), FormItem.CellType.RADIO, new String[]{getString(R.string.yes), getString(R.string.no)}, DBAdapter.patientMap.get("KEY_VISUAL")));
        patientFormItems.add(new FormItem(getString(R.string.hearing_aid), FormItem.CellType.RADIO, new String[]{getString(R.string.yes), getString(R.string.no)}, DBAdapter.patientMap.get("KEY_HEARINGAID")));
        patientFormItems.add(new FormItem(getString(R.string.hearing_assessed), FormItem.CellType.RADIO, new String[]{getString(R.string.yes), getString(R.string.no)}, DBAdapter.patientMap.get("KEY_HEARINGASSESSED")));
        patientFormItems.add(new FormItem(getString(R.string.aphasia), FormItem.CellType.CHECKBOX, new String[]{getString(R.string.aCns3receptiveDeficit), getString(R.string.aCns3expressiveDeficit), getString(R.string.aCns3normal)}, DBAdapter.patientMap.get("KEY_APHASIA")));

        //patientFormItems.add(new FormItem(getString(R.string.qPeg), FormItem.CellType.RADIO, new String[]{getString(R.string.yes), getString(R.string.no)}, DBAdapter.patientMap.get("KEY_PEGADMIT")));
        //patientFormItems.add(new FormItem(getString(R.string.qNg), FormItem.CellType.RADIO, new String[]{getString(R.string.yes), getString(R.string.no)}, DBAdapter.patientMap.get("KEY_NGADMIT")));
        //patientFormItems.add(new FormItem(getString(R.string.foley), FormItem.CellType.RADIO, new String[]{getString(R.string.yes), getString(R.string.no)}, DBAdapter.patientMap.get("KEY_FOLEYADMIT")));
        patientFormItems.add(new FormItem(getString(R.string.fall_risk), FormItem.CellType.RADIO, new String[]{getString(R.string.yes), getString(R.string.no)}, DBAdapter.patientMap.get("KEY_FALLRISK")));

        patientFormItems.add(new FormItem(getString(R.string.levelOfIntervention), FormItem.CellType.RADIO, new String[]{"1", "2", "3", "4"}, DBAdapter.patientMap.get("KEY_INTERVENTIONLEVEL")));
        patientFormItems.add(new FormItem(getString(R.string.depressed), FormItem.CellType.RADIO, new String[]{getString(R.string.yes), getString(R.string.no)}, DBAdapter.patientMap.get("KEY_DEPRESSED")));

        patientFormItems.add(new FormItem(getString(R.string.provenance), FormItem.CellType.RADIO_PROVENANCE, new String[]{getString(R.string.provenance_receiving_area), getString(R.string.provenance_rvh), getString(R.string.provenance_mgh), getString(R.string.provenance_outside_hospital), getString(R.string.provenance_ltc), getString(R.string.provenance_home)}, DBAdapter.patientMap.get("KEY_PROVENANCE")));

        patientFormItems.add(new FormItem(getString(R.string.dateFirstOT), FormItem.CellType.DATEPICKERDIALOG, new String[]{""}, DBAdapter.patientMap.get("KEY_FIRSTOT")));
        patientFormItems.add(new FormItem(getString(R.string.dateFirstSwallow), FormItem.CellType.DATEPICKERDIALOG, new String[]{""}, DBAdapter.patientMap.get("KEY_FIRSTSWALLOW")));
        patientFormItems.add(new FormItem(getString(R.string.dateFirstPT), FormItem.CellType.DATEPICKERDIALOG, new String[]{""}, DBAdapter.patientMap.get("KEY_FIRSTPT")));
        patientFormItems.add(new FormItem(getString(R.string.dateFirstSLT), FormItem.CellType.DATEPICKERDIALOG, new String[]{""}, DBAdapter.patientMap.get("KEY_FIRSTSLT")));

        for (FormItem item : patientFormItems){
            item.group = FormItem.Group.REGISTER;
        }

        adapter = new FormListAdapter(this, patientFormItems);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_patient_form, menu);
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

            Cursor cursor = MainActivity.myDb.getRowPatient(MainActivity.currentPatientId);

            if (cursor.moveToFirst()){
                String mrn = cursor.getString(cursor.getColumnIndex(DBAdapter.patientMap.get("KEY_MRN"))) != null ? cursor.getString(cursor.getColumnIndex(DBAdapter.patientMap.get("KEY_MRN"))) : "";
                String firstName = cursor.getString(cursor.getColumnIndex(DBAdapter.patientMap.get("KEY_FIRSTNAME"))) != null ? cursor.getString(cursor.getColumnIndex(DBAdapter.patientMap.get("KEY_FIRSTNAME"))) : "";
                String lastName = cursor.getString(cursor.getColumnIndex(DBAdapter.patientMap.get("KEY_LASTNAME"))) != null ? cursor.getString(cursor.getColumnIndex(DBAdapter.patientMap.get("KEY_LASTNAME"))) : "";

                MainActivity.actionBar.setTitle(mrn + " " + firstName + " " + lastName);
                MainActivity.currentMrn = mrn;

            }




            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
