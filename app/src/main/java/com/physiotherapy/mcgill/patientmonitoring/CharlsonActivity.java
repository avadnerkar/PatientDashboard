package com.physiotherapy.mcgill.patientmonitoring;

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
        setContentView(R.layout.activity_cns);

        listView = (ListView) findViewById(R.id.list);

        items = new ArrayList<>();


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
            return true;
        } else if (id == R.id.action_delete){

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
                    title.setText(getString(R.string.));
                    break;
                case LIVER:
                    break;
                case DIABETES:
                    break;
                case CVD:
                    break;
                case AMI:
                    break;
                case CHF:
                    break;
                case PVD:
                    break;
                case DEMENTIA:
                    break;
                case COPD:
                    break;
                case RHEUMATOLOGICAL:
                    break;
                case DIGESTIVE:
                    break;
                case RENAL:
                    break;
                case HIV:
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
