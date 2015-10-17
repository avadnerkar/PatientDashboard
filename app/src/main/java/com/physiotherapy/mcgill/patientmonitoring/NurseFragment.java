package com.physiotherapy.mcgill.patientmonitoring;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Abhishek on 6/05/2015.
 */
public class NurseFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    public static ArrayList<FormItem> nurseFormItems;
    public static FormListAdapter nurseAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.nurse_frag, container, false);

        ListView listView = (ListView) v.findViewById(R.id.nurseList);

        nurseFormItems = new ArrayList<>();
        nurseFormItems.add(new FormItem(getString(R.string.qPeg), FormItem.CellType.RADIO, new String[]{getString(R.string.yes), getString(R.string.no)}, DBAdapter.dataMap.get("KEY_PEG")));
        nurseFormItems.add(new FormItem(getString(R.string.qNg), FormItem.CellType.RADIO, new String[]{getString(R.string.yes), getString(R.string.no)}, DBAdapter.dataMap.get("KEY_NG")));
        nurseFormItems.add(new FormItem(getString(R.string.qO2), FormItem.CellType.RADIO, new String[]{getString(R.string.yes), getString(R.string.no)}, DBAdapter.dataMap.get("KEY_O2")));
        nurseFormItems.add(new FormItem(getString(R.string.qIv), FormItem.CellType.RADIO, new String[]{getString(R.string.yes), getString(R.string.no)}, DBAdapter.dataMap.get("KEY_IV")));
        nurseFormItems.add(new FormItem(getString(R.string.qCpap), FormItem.CellType.RADIO, new String[]{getString(R.string.yes), getString(R.string.no)}, DBAdapter.dataMap.get("KEY_CPAP")));
        nurseFormItems.add(new FormItem(getString(R.string.qRestraint), FormItem.CellType.RADIO, new String[]{getString(R.string.yes), getString(R.string.no)}, DBAdapter.dataMap.get("KEY_RESTRAINT")));
        nurseFormItems.add(new FormItem(getString(R.string.qBedbars), FormItem.CellType.RADIO, new String[]{getString(R.string.yes), getString(R.string.no)}, DBAdapter.dataMap.get("KEY_BEDBARS")));
        nurseFormItems.add(new FormItem(getString(R.string.qBehavioural), FormItem.CellType.RADIO, new String[]{getString(R.string.yes), getString(R.string.no)}, DBAdapter.dataMap.get("KEY_BEHAVIOURAL")));
        nurseFormItems.add(new FormItem(getString(R.string.qConfusion), FormItem.CellType.RADIO, new String[]{getString(R.string.yes), getString(R.string.no)}, DBAdapter.dataMap.get("KEY_CONFUSION")));
        nurseFormItems.add(new FormItem(getString(R.string.qBladder), FormItem.CellType.RADIO, new String[]{getString(R.string.foley), getString(R.string.diaper), getString(R.string.bedpan), getString(R.string.toilet)}, DBAdapter.dataMap.get("KEY_BLADDER")));
        nurseFormItems.add(new FormItem(getString(R.string.qHours), FormItem.CellType.NUMERIC, new String[]{getString(R.string.hoursHint)}, DBAdapter.dataMap.get("KEY_HOURS")));

        for (FormItem item : nurseFormItems){
            item.group = FormItem.Group.NURSE;
        }

        nurseAdapter = new FormListAdapter(getActivity(), nurseFormItems);

        listView.setAdapter(nurseAdapter);


        return v;
    }

    public static NurseFragment newInstance(int sectionNumber) {

        NurseFragment f = new NurseFragment();
        Bundle b = new Bundle();

        b.putInt(ARG_SECTION_NUMBER, sectionNumber);
        f.setArguments(b);

        return f;
    }
}

