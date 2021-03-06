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

public class CnsActivity extends ActionBarActivity {

    private enum ItemType {
        CONSCIOUSNESS, ORIENTATION, SPEECH,
        FACE1, UPPERLIMBPROXIMAL, UPPERLIMBDISTAL, LOWERLIMBPROXIMAL, LOWERLIMBDISTAL,
        UPPERLIMBS, LOWERLIMBS, FACE2

    }
    private ArrayList<ItemType> items;
    public static ListAdapter adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cns);

        listView = (ListView) findViewById(R.id.list);

        items = new ArrayList<>();
        items.add(ItemType.CONSCIOUSNESS);
        items.add(ItemType.ORIENTATION);
        items.add(ItemType.SPEECH);
        items.add(ItemType.FACE1);
        items.add(ItemType.UPPERLIMBPROXIMAL);
        items.add(ItemType.UPPERLIMBDISTAL);
        items.add(ItemType.LOWERLIMBPROXIMAL);
        items.add(ItemType.LOWERLIMBDISTAL);
        items.add(ItemType.UPPERLIMBS);
        items.add(ItemType.LOWERLIMBS);
        items.add(ItemType.FACE2);

        adapter = new ListAdapter(this, items);
        listView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cns, menu);
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
            float cnsScore = ScoreCalculators.cnsScore(cursor)[0];
            float nihssScore = ScoreCalculators.nihssScore(cursor)[0];
            cursor.close();
            MainActivity.myDb.updateFieldPatient(MainActivity.currentPatientId, DBAdapter.patientMap.get("KEY_CNS"), String.valueOf(cnsScore));
            MainActivity.myDb.updateFieldPatient(MainActivity.currentPatientId, DBAdapter.patientMap.get("KEY_NIHSS"), String.valueOf(nihssScore));
            PhysicianFragment.adapter.notifyDataSetChanged();
            finish();
            return true;
        } else if (id == R.id.action_delete){
            MainActivity.myDb.updateFieldPatient(MainActivity.currentPatientId, DBAdapter.patientMap.get("KEY_CNS_CONSCIOUSNESS"), null);
            MainActivity.myDb.updateFieldPatient(MainActivity.currentPatientId, DBAdapter.patientMap.get("KEY_CNS_ORIENTATION"), null);
            MainActivity.myDb.updateFieldPatient(MainActivity.currentPatientId, DBAdapter.patientMap.get("KEY_CNS_SPEECH"), null);
            clearSectionA1();
            clearSectionA2();
            clearTotal();
            adapter.notifyDataSetChanged();
        }

        return super.onOptionsItemSelected(item);
    }

    private void clearSectionA1(){
        MainActivity.myDb.updateFieldPatient(MainActivity.currentPatientId, DBAdapter.patientMap.get("KEY_CNS_FACE1"), null);
        MainActivity.myDb.updateFieldPatient(MainActivity.currentPatientId, DBAdapter.patientMap.get("KEY_CNS_UPPER_LIMB_PROXIMAL"), null);
        MainActivity.myDb.updateFieldPatient(MainActivity.currentPatientId, DBAdapter.patientMap.get("KEY_CNS_UPPER_LIMB_DISTAL"), null);
        MainActivity.myDb.updateFieldPatient(MainActivity.currentPatientId, DBAdapter.patientMap.get("KEY_CNS_LOWER_LIMB_PROXIMAL"), null);
        MainActivity.myDb.updateFieldPatient(MainActivity.currentPatientId, DBAdapter.patientMap.get("KEY_CNS_LOWER_LIMB_DISTAL"), null);

    }

    private void clearSectionA2(){
        MainActivity.myDb.updateFieldPatient(MainActivity.currentPatientId, DBAdapter.patientMap.get("KEY_CNS_UPPER_LIMBS"), null);
        MainActivity.myDb.updateFieldPatient(MainActivity.currentPatientId, DBAdapter.patientMap.get("KEY_CNS_LOWER_LIMBS"), null);
        MainActivity.myDb.updateFieldPatient(MainActivity.currentPatientId, DBAdapter.patientMap.get("KEY_CNS_FACE2"), null);

    }

    private void clearTotal(){
        MainActivity.myDb.updateFieldPatient(MainActivity.currentPatientId, DBAdapter.patientMap.get("KEY_CNS"), null);

    }



    /**
     * *****************************************************************************************
     * List dialog custom adapter
     * *****************************************************************************************
     */

    private enum SectionView{
        A1,
        A2,
        NONE
    }

    public class ListAdapter extends ArrayAdapter<ItemType> {
        private Context context;
        private List<ItemType> items;
        private SectionView sectionView;

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

                case CONSCIOUSNESS:
                    title.setText(getString(R.string.qCns1));
                    options = new String[]{getString(R.string.aCns1alert), getString(R.string.aCns1drowsy)};
                    dbKey = DBAdapter.patientMap.get("KEY_CNS_CONSCIOUSNESS");
                    break;
                case ORIENTATION:
                    title.setText(getString(R.string.qCns2));
                    options = new String[]{getString(R.string.aCns2oriented), getString(R.string.aCns2disoriented)};
                    dbKey = DBAdapter.patientMap.get("KEY_CNS_ORIENTATION");
                    break;
                case SPEECH:
                    title.setText(getString(R.string.qCns3));
                    options = new String[]{getString(R.string.aCns3receptiveDeficit), getString(R.string.aCns3expressiveDeficit), getString(R.string.aCns3normal)};
                    dbKey = DBAdapter.patientMap.get("KEY_CNS_SPEECH");
                    break;
                case FACE1:
                    title.setText(getString(R.string.qCnsA1_4));
                    options = new String[]{getString(R.string.aCnsA1_4noWeakness), getString(R.string.aCnsA1_4Weakness)};
                    dbKey = DBAdapter.patientMap.get("KEY_CNS_FACE1");
                    if (sectionView != SectionView.A1){
                        rowView = inflater.inflate(R.layout.cell_null, parent, false);
                        return rowView;
                    }
                    break;
                case UPPERLIMBPROXIMAL:
                    title.setText(getString(R.string.qCnsA1_5));
                    options = new String[]{getString(R.string.aCnsA1_noWeakness), getString(R.string.aCnsA1_mildWeakness), getString(R.string.aCnsA1_significantWeakness), getString(R.string.aCnsA1_totalWeakness)};
                    dbKey = DBAdapter.patientMap.get("KEY_CNS_UPPER_LIMB_PROXIMAL");
                    if (sectionView != SectionView.A1){
                        rowView = inflater.inflate(R.layout.cell_null, parent, false);
                        return rowView;
                    }
                    break;
                case UPPERLIMBDISTAL:
                    title.setText(getString(R.string.qCnsA1_6));
                    options = new String[]{getString(R.string.aCnsA1_noWeakness), getString(R.string.aCnsA1_mildWeakness), getString(R.string.aCnsA1_significantWeakness), getString(R.string.aCnsA1_totalWeakness)};
                    dbKey = DBAdapter.patientMap.get("KEY_CNS_UPPER_LIMB_DISTAL");
                    if (sectionView != SectionView.A1){
                        rowView = inflater.inflate(R.layout.cell_null, parent, false);
                        return rowView;
                    }
                    break;
                case LOWERLIMBPROXIMAL:
                    title.setText(getString(R.string.qCnsA1_7));
                    options = new String[]{getString(R.string.aCnsA1_noWeakness), getString(R.string.aCnsA1_mildWeakness), getString(R.string.aCnsA1_significantWeakness), getString(R.string.aCnsA1_totalWeakness)};
                    dbKey = DBAdapter.patientMap.get("KEY_CNS_LOWER_LIMB_PROXIMAL");
                    if (sectionView != SectionView.A1){
                        rowView = inflater.inflate(R.layout.cell_null, parent, false);
                        return rowView;
                    }
                    break;
                case LOWERLIMBDISTAL:
                    title.setText(getString(R.string.qCnsA1_8));
                    options = new String[]{getString(R.string.aCnsA1_noWeakness), getString(R.string.aCnsA1_mildWeakness), getString(R.string.aCnsA1_significantWeakness), getString(R.string.aCnsA1_totalWeakness)};
                    dbKey = DBAdapter.patientMap.get("KEY_CNS_LOWER_LIMB_DISTAL");
                    if (sectionView != SectionView.A1){
                        rowView = inflater.inflate(R.layout.cell_null, parent, false);
                        return rowView;
                    }
                    break;
                case UPPERLIMBS:
                    title.setText(getString(R.string.qCnsA2_5));
                    options = new String[]{getString(R.string.aCnsA2_equal), getString(R.string.aCnsA2_unequal)};
                    dbKey = DBAdapter.patientMap.get("KEY_CNS_UPPER_LIMBS");
                    if (sectionView != SectionView.A2){
                        rowView = inflater.inflate(R.layout.cell_null, parent, false);
                        return rowView;
                    }
                    break;
                case LOWERLIMBS:
                    title.setText(getString(R.string.qCnsA2_6));
                    options = new String[]{getString(R.string.aCnsA2_equal), getString(R.string.aCnsA2_unequal)};
                    dbKey = DBAdapter.patientMap.get("KEY_CNS_LOWER_LIMBS");
                    if (sectionView != SectionView.A2){
                        rowView = inflater.inflate(R.layout.cell_null, parent, false);
                        return rowView;
                    }
                    break;
                case FACE2:
                    title.setText(getString(R.string.qCnsA2_4));
                    options = new String[]{getString(R.string.aCnsA2_4symmetrical), getString(R.string.aCnsA2_4asymmetrical)};
                    dbKey = DBAdapter.patientMap.get("KEY_CNS_FACE2");
                    if (sectionView != SectionView.A2){
                        rowView = inflater.inflate(R.layout.cell_null, parent, false);
                        return rowView;
                    }
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
                        if (itemType == ItemType.SPEECH) {
                            int i = rg.indexOfChild(view);
                            switch (i) {
                                case 0:
                                    sectionView = SectionView.A2;
                                    break;
                                case 1:
                                case 2:
                                    sectionView = SectionView.A1;
                                    break;
                            }
                            clearSectionA1();
                            clearSectionA2();
                            clearTotal();
                            ((ArrayAdapter)listView.getAdapter()).notifyDataSetChanged();
                        }

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
                            if (itemType==ItemType.SPEECH){
                                switch (i){
                                    case 0:
                                        sectionView = SectionView.A2;
                                        break;
                                    case 1:
                                    case 2:
                                        sectionView = SectionView.A1;
                                        break;
                                }
                                ((ArrayAdapter)listView.getAdapter()).notifyDataSetChanged();
                            }
                        }
                    }
                } else {
                    rg.clearCheck();
                    if (itemType==ItemType.SPEECH){
                        sectionView = SectionView.NONE;
                        ((ArrayAdapter)listView.getAdapter()).notifyDataSetChanged();
                    }
                }

            } else {
                rg.clearCheck();
                if (itemType==ItemType.SPEECH){
                    sectionView = SectionView.NONE;
                    ((ArrayAdapter)listView.getAdapter()).notifyDataSetChanged();
                }
            }

            cursor.close();


            return rowView;
        }



    }
}
