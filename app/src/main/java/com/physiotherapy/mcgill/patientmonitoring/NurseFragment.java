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
    public static ArrayList<DailyItem> nurseDailyItems;
    public static DailyListAdapter nurseAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.nurse_frag, container, false);

        ListView listView = (ListView) v.findViewById(R.id.nurseList);

        nurseDailyItems = new ArrayList<>();
        nurseDailyItems.add(new DailyItem(getString(R.string.qPeg), DailyItem.CellType.RADIO, new String[]{getString(R.string.yes), getString(R.string.no)}, DBAdapter.dataMap.get("KEY_PEG")));
        nurseDailyItems.add(new DailyItem(getString(R.string.qNg), DailyItem.CellType.RADIO, new String[]{getString(R.string.yes), getString(R.string.no)}, DBAdapter.dataMap.get("KEY_NG")));
        nurseDailyItems.add(new DailyItem(getString(R.string.qO2), DailyItem.CellType.RADIO, new String[]{getString(R.string.yes), getString(R.string.no)}, DBAdapter.dataMap.get("KEY_O2")));
        nurseDailyItems.add(new DailyItem(getString(R.string.qIv), DailyItem.CellType.RADIO, new String[]{getString(R.string.yes), getString(R.string.no)}, DBAdapter.dataMap.get("KEY_IV")));
        nurseDailyItems.add(new DailyItem(getString(R.string.qCpap), DailyItem.CellType.RADIO, new String[]{getString(R.string.yes), getString(R.string.no)}, DBAdapter.dataMap.get("KEY_CPAP")));
        nurseDailyItems.add(new DailyItem(getString(R.string.qRestraint), DailyItem.CellType.RADIO, new String[]{getString(R.string.yes), getString(R.string.no)}, DBAdapter.dataMap.get("KEY_RESTRAINT")));
        nurseDailyItems.add(new DailyItem(getString(R.string.qBedbars), DailyItem.CellType.RADIO, new String[]{getString(R.string.yes), getString(R.string.no)}, DBAdapter.dataMap.get("KEY_BEDBARS")));
        nurseDailyItems.add(new DailyItem(getString(R.string.qBehavioural), DailyItem.CellType.RADIO, new String[]{getString(R.string.yes), getString(R.string.no)}, DBAdapter.dataMap.get("KEY_BEHAVIOURAL")));
        nurseDailyItems.add(new DailyItem(getString(R.string.qConfusion), DailyItem.CellType.RADIO, new String[]{getString(R.string.yes), getString(R.string.no)}, DBAdapter.dataMap.get("KEY_CONFUSION")));
        nurseDailyItems.add(new DailyItem(getString(R.string.qBladder), DailyItem.CellType.RADIO, new String[]{getString(R.string.foley), getString(R.string.diaper), getString(R.string.bedpan), getString(R.string.toilet)}, DBAdapter.dataMap.get("KEY_BLADDER")));
        nurseDailyItems.add(new DailyItem(getString(R.string.qHours), DailyItem.CellType.NUMERIC, new String[]{getString(R.string.hoursHint)}, DBAdapter.dataMap.get("KEY_HOURS")));

        for (DailyItem item : nurseDailyItems){
            item.group = DailyItem.Group.NURSE;
        }

        nurseAdapter = new DailyListAdapter(getActivity(), nurseDailyItems);

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

