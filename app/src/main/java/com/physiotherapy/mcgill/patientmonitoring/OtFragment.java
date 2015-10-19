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
public class OtFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    public static ArrayList<DailyItem> otDailyItems;
    public static DailyListAdapter otAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.ot_frag, container, false);

        ListView listView = (ListView) v.findViewById(R.id.otList);

        otDailyItems = new ArrayList<>();
        otDailyItems.add(new DailyItem(getString(R.string.qNeglect), DailyItem.CellType.RADIO, new String[]{getString(R.string.yes), getString(R.string.no)}, DBAdapter.dataMap.get("KEY_NEGLECT")));
        otDailyItems.add(new DailyItem(getString(R.string.qDigitSpan), DailyItem.CellType.RADIO, new String[]{"1", "2", "3", "4", "5", "6", "7"}, DBAdapter.dataMap.get("KEY_DIGITSPAN")));
        otDailyItems.add(new DailyItem(getString(R.string.qMmse), DailyItem.CellType.NUMERIC, new String[]{getString(R.string.mmseHint)}, DBAdapter.dataMap.get("KEY_MMSE")));
        otDailyItems.add(new DailyItem(getString(R.string.qFollows), DailyItem.CellType.RADIO, new String[]{getString(R.string.none), getString(R.string.a1step), getString(R.string.a2step)}, DBAdapter.dataMap.get("KEY_FOLLOWS")));
        otDailyItems.add(new DailyItem(getString(R.string.qVerbal), DailyItem.CellType.RADIO, new String[]{getString(R.string.none), getString(R.string.partial), getString(R.string.full)}, DBAdapter.dataMap.get("KEY_VERBAL")));
        otDailyItems.add(new DailyItem(getString(R.string.qMotivation), DailyItem.CellType.RADIO, new String[]{"1", "2", "3", "4", "5", "6", "7"}, DBAdapter.dataMap.get("KEY_MOTIVATION")));
        otDailyItems.add(new DailyItem(getString(R.string.qMood), DailyItem.CellType.RADIO, new String[]{"1", "2", "3", "4", "5", "6", "7"}, DBAdapter.dataMap.get("KEY_MOOD")));
        otDailyItems.add(new DailyItem(getString(R.string.qPain), DailyItem.CellType.RADIO, new String[]{"1", "2", "3", "4", "5", "6", "7"}, DBAdapter.dataMap.get("KEY_PAIN")));
        otDailyItems.add(new DailyItem(getString(R.string.qFatigue), DailyItem.CellType.RADIO, new String[]{"1", "2", "3", "4", "5", "6", "7"}, DBAdapter.dataMap.get("KEY_FATIGUE")));
        otDailyItems.add(new DailyItem(getString(R.string.qSwallow), DailyItem.CellType.RADIO, new String[]{getString(R.string.none), getString(R.string.partial), getString(R.string.full)}, DBAdapter.dataMap.get("KEY_SWALLOW")));
        otDailyItems.add(new DailyItem(getString(R.string.qFeeding), DailyItem.CellType.RADIO, new String[]{getString(R.string.none), getString(R.string.partial), getString(R.string.full)}, DBAdapter.dataMap.get("KEY_FEEDING")));
        otDailyItems.add(new DailyItem(getString(R.string.qDressing), DailyItem.CellType.RADIO, new String[]{getString(R.string.none), getString(R.string.partial), getString(R.string.full)}, DBAdapter.dataMap.get("KEY_DRESSING")));
        otDailyItems.add(new DailyItem(getString(R.string.qKitchen), DailyItem.CellType.RADIO, new String[]{getString(R.string.none), getString(R.string.partial), getString(R.string.full)}, DBAdapter.dataMap.get("KEY_KITCHEN")));

        for (DailyItem item : otDailyItems){
            item.group = DailyItem.Group.OT;
        }

        otAdapter = new DailyListAdapter(getActivity(), otDailyItems);

        listView.setAdapter(otAdapter);

        return v;
    }

    public static OtFragment newInstance(int sectionNumber) {

        OtFragment f = new OtFragment();
        Bundle b = new Bundle();

        b.putInt(ARG_SECTION_NUMBER, sectionNumber);
        f.setArguments(b);

        return f;
    }
}