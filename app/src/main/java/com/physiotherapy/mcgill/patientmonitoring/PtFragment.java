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
public class PtFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    public static ArrayList<DailyItem> ptDailyItems;
    public static DailyListAdapter ptAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.pt_frag, container, false);

        ListView listView = (ListView) v.findViewById(R.id.ptList);

        ptDailyItems = new ArrayList<>();
        ptDailyItems.add(new DailyItem(getString(R.string.qLeftArm), DailyItem.CellType.RADIO, new String[]{getString(R.string.none), getString(R.string.partial), getString(R.string.full)}, DBAdapter.dataMap.get("KEY_LEFTARM")));
        ptDailyItems.add(new DailyItem(getString(R.string.qRightArm), DailyItem.CellType.RADIO, new String[]{getString(R.string.none), getString(R.string.partial), getString(R.string.full)}, DBAdapter.dataMap.get("KEY_RIGHTARM")));
        ptDailyItems.add(new DailyItem(getString(R.string.qMovementBed), DailyItem.CellType.RADIO, new String[]{getString(R.string.none), getString(R.string.partial), getString(R.string.full)}, DBAdapter.dataMap.get("KEY_MOVEMENTBED")));
        ptDailyItems.add(new DailyItem(getString(R.string.qLieSit), DailyItem.CellType.RADIO, new String[]{getString(R.string.none), getString(R.string.partial), getString(R.string.full)}, DBAdapter.dataMap.get("KEY_LIESIT")));
        ptDailyItems.add(new DailyItem(getString(R.string.qSitting), DailyItem.CellType.RADIO, new String[]{getString(R.string.none), getString(R.string.partial), getString(R.string.full)}, DBAdapter.dataMap.get("KEY_SITTING")));
        ptDailyItems.add(new DailyItem(getString(R.string.qSitStand), DailyItem.CellType.RADIO, new String[]{getString(R.string.none), getString(R.string.partial), getString(R.string.full)}, DBAdapter.dataMap.get("KEY_SITSTAND")));
        ptDailyItems.add(new DailyItem(getString(R.string.qStand), DailyItem.CellType.RADIO, new String[]{getString(R.string.none), getString(R.string.partial), getString(R.string.full)}, DBAdapter.dataMap.get("KEY_STAND")));
        ptDailyItems.add(new DailyItem(getString(R.string.qLiftsUnaffected), DailyItem.CellType.RADIO, new String[]{getString(R.string.none), getString(R.string.partial), getString(R.string.full)}, DBAdapter.dataMap.get("KEY_LIFTSUNAFFECTED")));
        ptDailyItems.add(new DailyItem(getString(R.string.qLiftsAffected), DailyItem.CellType.RADIO, new String[]{getString(R.string.none), getString(R.string.partial), getString(R.string.full)}, DBAdapter.dataMap.get("KEY_LIFTSAFFECTED")));
        ptDailyItems.add(new DailyItem(getString(R.string.qWalking), DailyItem.CellType.RADIO, new String[]{getString(R.string.none), getString(R.string.partial), getString(R.string.full)}, DBAdapter.dataMap.get("KEY_WALKING")));

        for (DailyItem item : ptDailyItems){
            item.group = DailyItem.Group.PT;
        }

        ptAdapter = new DailyListAdapter(getActivity(), ptDailyItems);

        listView.setAdapter(ptAdapter);


        return v;
    }

    public static PtFragment newInstance(int sectionNumber) {

        PtFragment f = new PtFragment();
        Bundle b = new Bundle();

        b.putInt(ARG_SECTION_NUMBER, sectionNumber);
        f.setArguments(b);

        return f;
    }
}