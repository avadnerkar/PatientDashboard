package com.physiotherapy.mcgill.patientmonitoring.PhysicianForms;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.physiotherapy.mcgill.patientmonitoring.MainGroup.MainActivity;
import com.physiotherapy.mcgill.patientmonitoring.MainGroup.PhysicianFragment;
import com.physiotherapy.mcgill.patientmonitoring.R;
import com.physiotherapy.mcgill.patientmonitoring.Utilities.DBAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class RankinActivity extends ActionBarActivity {


    private ArrayList<String> items;
    public static ListAdapter adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rankin);

        listView = (ListView) findViewById(R.id.list);

        items = new ArrayList<>();
        items.add("0");
        items.add("1");
        items.add("2");
        items.add("3");
        items.add("4");
        items.add("5");
        items.add("6");

        adapter = new ListAdapter(this, items);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MainActivity.myDb.updateFieldPatient(MainActivity.currentPatientId, DBAdapter.patientMap.get("KEY_RANKIN"), items.get(i));
                PhysicianFragment.adapter.notifyDataSetChanged();
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

            TextView description;

            if (convertView == null){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.cell_rankin, parent, false);
            }

            description = (TextView) convertView.findViewById(R.id.description);

            TextView score = (TextView) convertView.findViewById(R.id.score);
            score.setText(items.get(position));

            switch (items.get(position)) {
                case "0":
                    description.setText(getString(R.string.rankin0));
                    break;
                case "1":
                    description.setText(getString(R.string.rankin1));
                    break;
                case "2":
                    description.setText(getString(R.string.rankin2));
                    break;
                case "3":
                    description.setText(getString(R.string.rankin3));
                    break;
                case "4":
                    description.setText(getString(R.string.rankin4));
                    break;
                case "5":
                    description.setText(getString(R.string.rankin5));
                    break;
                case "6":
                    description.setText(getString(R.string.rankin6));
                    break;

            }



            return convertView;
        }
    }
}
