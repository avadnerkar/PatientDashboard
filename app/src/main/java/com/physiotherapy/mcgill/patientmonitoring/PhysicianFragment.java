package com.physiotherapy.mcgill.patientmonitoring;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PhysicianFragment extends Fragment {


    private static final String ARG_SECTION_NUMBER = "section_number";
    private ArrayList<String> items;
    public static ListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.physician_frag, container, false);

        ListView listView = (ListView) v.findViewById(R.id.physicianList);

        items = new ArrayList<>();
        items.add(getString(R.string.charlson));
        items.add(getString(R.string.comorbidity));
        items.add(getString(R.string.rankin));
        items.add(getString(R.string.cns));
        items.add(getString(R.string.nihss));
        items.add(getString(R.string.toast));

        adapter = new ListAdapter(getActivity(), items);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (items.get(i).equals(getString(R.string.charlson))){

                } else if (items.get(i).equals(getString(R.string.comorbidity))){

                } else if (items.get(i).equals(getString(R.string.rankin))){

                } else if (items.get(i).equals(getString(R.string.cns))){

                } else if (items.get(i).equals(getString(R.string.nihss))){

                } else if (items.get(i).equals(getString(R.string.toast))){

                }
            }
        });

        return v;
    }


    public static PhysicianFragment newInstance(int sectionNumber) {

        PhysicianFragment f = new PhysicianFragment();
        Bundle b = new Bundle();

        b.putInt(ARG_SECTION_NUMBER, sectionNumber);
        f.setArguments(b);

        return f;
    }


    /**
     * *****************************************************************************************
     * List dialog custom adapter
     * *****************************************************************************************
     */

    public class ListAdapter extends ArrayAdapter<String> {
        private Context context;
        private List<String> items;

        public ListAdapter(Context context, List<String> items){
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
            TextView textView;
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            rowView = inflater.inflate(R.layout.cell_form_selection, parent, false);
            textView = (TextView) rowView.findViewById(R.id.title);
            textView.setText(items.get(position));


            return rowView;
        }



    }

}
