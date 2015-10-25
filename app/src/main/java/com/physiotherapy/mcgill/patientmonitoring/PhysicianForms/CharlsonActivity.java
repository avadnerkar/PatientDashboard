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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.physiotherapy.mcgill.patientmonitoring.Utilities.DBAdapter;
import com.physiotherapy.mcgill.patientmonitoring.MainGroup.MainActivity;
import com.physiotherapy.mcgill.patientmonitoring.MainGroup.PhysicianFragment;
import com.physiotherapy.mcgill.patientmonitoring.R;
import com.physiotherapy.mcgill.patientmonitoring.Scores.ScoreCalculators;

import java.util.ArrayList;
import java.util.List;

public class CharlsonActivity extends ActionBarActivity {

    private enum ItemType {
        CANCER, LIVER, DIABETES, CVD, AMI, CHF, PVD, DEMENTIA, COPD, RHEUMATOLOGICAL, DIGESTIVE, RENAL, HIV

    }
    private ArrayList<ItemType> items;
    public static ListAdapter adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charlson);

        listView = (ListView) findViewById(R.id.list);

        items = new ArrayList<>();
        items.add(ItemType.CANCER);
        items.add(ItemType.LIVER);
        items.add(ItemType.DIABETES);
        items.add(ItemType.CVD);
        items.add(ItemType.AMI);
        items.add(ItemType.CHF);
        items.add(ItemType.PVD);
        items.add(ItemType.DEMENTIA);
        items.add(ItemType.COPD);
        items.add(ItemType.RHEUMATOLOGICAL);
        items.add(ItemType.DIGESTIVE);
        items.add(ItemType.RENAL);
        items.add(ItemType.HIV);


        adapter = new ListAdapter(this, items);
        listView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_charlson, menu);
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
            Cursor cursor = MainActivity.myDb.getRowPatient(MainActivity.currentPatientId);
            float charlsonScore = ScoreCalculators.charlsonScore(cursor, this)[0];
            cursor.close();
            MainActivity.myDb.updateFieldPatient(MainActivity.currentPatientId, DBAdapter.patientMap.get("KEY_CHARLSON"), String.valueOf(charlsonScore));
            PhysicianFragment.adapter.notifyDataSetChanged();
            finish();
            return true;
        } else if (id == R.id.action_delete){
            MainActivity.myDb.updateFieldPatient(MainActivity.currentPatientId, DBAdapter.patientMap.get("KEY_CHARLSON_CANCER"), null);
            MainActivity.myDb.updateFieldPatient(MainActivity.currentPatientId, DBAdapter.patientMap.get("KEY_CHARLSON_LIVER"), null);
            MainActivity.myDb.updateFieldPatient(MainActivity.currentPatientId, DBAdapter.patientMap.get("KEY_CHARLSON_DIABETES"), null);
            MainActivity.myDb.updateFieldPatient(MainActivity.currentPatientId, DBAdapter.patientMap.get("KEY_CHARLSON_CVD"), null);
            MainActivity.myDb.updateFieldPatient(MainActivity.currentPatientId, DBAdapter.patientMap.get("KEY_CHARLSON_AMI"), null);
            MainActivity.myDb.updateFieldPatient(MainActivity.currentPatientId, DBAdapter.patientMap.get("KEY_CHARLSON_CHF"), null);
            MainActivity.myDb.updateFieldPatient(MainActivity.currentPatientId, DBAdapter.patientMap.get("KEY_CHARLSON_PVD"), null);
            MainActivity.myDb.updateFieldPatient(MainActivity.currentPatientId, DBAdapter.patientMap.get("KEY_CHARLSON_DEMENTIA"), null);
            MainActivity.myDb.updateFieldPatient(MainActivity.currentPatientId, DBAdapter.patientMap.get("KEY_CHARLSON_COPD"), null);
            MainActivity.myDb.updateFieldPatient(MainActivity.currentPatientId, DBAdapter.patientMap.get("KEY_CHARLSON_RHEUMATOLOGIC"), null);
            MainActivity.myDb.updateFieldPatient(MainActivity.currentPatientId, DBAdapter.patientMap.get("KEY_CHARLSON_DIGESTIVE"), null);
            MainActivity.myDb.updateFieldPatient(MainActivity.currentPatientId, DBAdapter.patientMap.get("KEY_CHARLSON_RENAL"), null);
            MainActivity.myDb.updateFieldPatient(MainActivity.currentPatientId, DBAdapter.patientMap.get("KEY_CHARLSON_HIV"), null);

            adapter.notifyDataSetChanged();
        }

        return super.onOptionsItemSelected(item);
    }



    public class ListAdapter extends ArrayAdapter<ItemType> {
        private Context context;
        private List<ItemType> items;

        public ListAdapter(Context context, List<ItemType> items){
            super(context, R.layout.cell_form_selection, items);
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
            View rowView;
            TextView title;
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            rowView = inflater.inflate(R.layout.cell_daily_radio, parent, false);
            title = (TextView) rowView.findViewById(R.id.title);

            final ItemType itemType = items.get(position);
            String[] options = null;
            String dbKey = null;
            switch (itemType){


                case CANCER:
                    title.setText(getString(R.string.charlsonCancer));
                    dbKey = DBAdapter.patientMap.get("KEY_CHARLSON_CANCER");
                    options = new String[]{getString(R.string.charlsonMetastaticCancer), getString(R.string.charlsonPrimaryCancer), getString(R.string.none)};
                    break;
                case LIVER:
                    title.setText(getString(R.string.charlsonLiver));
                    dbKey = DBAdapter.patientMap.get("KEY_CHARLSON_LIVER");
                    options = new String[]{getString(R.string.charlsonLiverDisease), getString(R.string.charlsonMildLiverDisease), getString(R.string.none)};
                    break;
                case DIABETES:
                    title.setText(getString(R.string.charlsonDiabetes));
                    dbKey = DBAdapter.patientMap.get("KEY_CHARLSON_DIABETES");
                    options = new String[]{getString(R.string.charlsonDiabetesChronic), getString(R.string.charlsonDiabetes), getString(R.string.none)};
                    break;
                case CVD:
                    title.setText(getString(R.string.charlsonCerebrovascular));
                    dbKey = DBAdapter.patientMap.get("KEY_CHARLSON_CVD");
                    options = new String[]{getString(R.string.charlsonHemi), getString(R.string.charlsonCvd), getString(R.string.none)};
                    break;
                case AMI:
                    title.setText(getString(R.string.charlsonAmi));
                    dbKey = DBAdapter.patientMap.get("KEY_CHARLSON_AMI");
                    options = new String[]{getString(R.string.charlsonAmiOption), getString(R.string.none)};
                    break;
                case CHF:
                    title.setText(getString(R.string.charlsonChf));
                    dbKey = DBAdapter.patientMap.get("KEY_CHARLSON_CHF");
                    options = new String[]{getString(R.string.charlsonChfOption), getString(R.string.none)};
                    break;
                case PVD:
                    title.setText(getString(R.string.charlsonPvd));
                    dbKey = DBAdapter.patientMap.get("KEY_CHARLSON_PVD");
                    options = new String[]{getString(R.string.charlsonPvdOption), getString(R.string.none)};
                    break;
                case DEMENTIA:
                    title.setText(getString(R.string.charlsonDementia));
                    dbKey = DBAdapter.patientMap.get("KEY_CHARLSON_DEMENTIA");
                    options = new String[]{getString(R.string.charlsonDementiaOption), getString(R.string.none)};
                    break;
                case COPD:
                    title.setText(getString(R.string.charlsonCopd));
                    dbKey = DBAdapter.patientMap.get("KEY_CHARLSON_COPD");
                    options = new String[]{getString(R.string.charlsonCopdOption), getString(R.string.none)};
                    break;
                case RHEUMATOLOGICAL:
                    title.setText(getString(R.string.charlsonRheumatologic));
                    dbKey = DBAdapter.patientMap.get("KEY_CHARLSON_RHEUMATOLOGIC");
                    options = new String[]{getString(R.string.charlsonRheumatologicOption), getString(R.string.none)};
                    break;
                case DIGESTIVE:
                    title.setText(getString(R.string.charlsonDigestive));
                    dbKey = DBAdapter.patientMap.get("KEY_CHARLSON_DIGESTIVE");
                    options = new String[]{getString(R.string.charlsonDigestiveUlcer), getString(R.string.none)};
                    break;
                case RENAL:
                    title.setText(getString(R.string.charlsonRenal));
                    dbKey = DBAdapter.patientMap.get("KEY_CHARLSON_RENAL");
                    options = new String[]{getString(R.string.charlsonRenalDisease), getString(R.string.none)};
                    break;
                case HIV:
                    title.setText(getString(R.string.charlsonHiv));
                    dbKey = DBAdapter.patientMap.get("KEY_CHARLSON_HIV");
                    options = new String[]{getString(R.string.charlsonHivInfection), getString(R.string.none)};
                    break;
            }

            final String dbkeyFinal = dbKey;

            final RadioGroup rg = (RadioGroup) rowView.findViewById(R.id.rg);
            if (options.length >2 && options.length < 7){
                rg.setOrientation(RadioGroup.VERTICAL);
            } else {
                rg.setOrientation(RadioGroup.HORIZONTAL);
            }
            for (String option : options) {
                RadioButton rb = new RadioButton(context);
                rb.setText(option);
                rg.addView(rb);

                rb.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(final View view) {
                        Thread thread = new Thread() {
                            @Override
                            public void run() {
                                MainActivity.myDb.updateFieldPatient(MainActivity.currentPatientId, dbkeyFinal, ((RadioButton) view).getText().toString());
                            }
                        };
                        thread.start();

                    }
                });
            }

            Cursor cursor = MainActivity.myDb.getPatientField(MainActivity.currentPatientId, dbKey);

            if (cursor.moveToFirst()){
                String radioValue = cursor.getString(cursor.getColumnIndex(dbKey));
                if (radioValue != null && !radioValue.equals("")){
                    for (int i=0; i<options.length; i++){
                        String rbString = options[i];
                        if (rbString.equals(radioValue)){
                            ((RadioButton)rg.getChildAt(i)).setChecked(true);

                        }
                    }
                } else {
                    rg.clearCheck();

                }

            } else {
                rg.clearCheck();

            }

            cursor.close();


            return rowView;
        }



    }

}
