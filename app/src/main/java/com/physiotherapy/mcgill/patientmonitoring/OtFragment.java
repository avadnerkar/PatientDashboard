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
    public static ArrayList<FormItem> otFormItems;
    public static FormListAdapter otAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.ot_frag, container, false);

        ListView listView = (ListView) v.findViewById(R.id.otList);

        otFormItems = new ArrayList<>();
        otFormItems.add(new FormItem(getString(R.string.qNeglect), FormItem.CellType.RADIO, new String[]{getString(R.string.yes), getString(R.string.no)}, DBAdapter.KEY_NEGLECT));
        otFormItems.add(new FormItem(getString(R.string.qDigitSpan), FormItem.CellType.RADIO, new String[]{"1", "2", "3", "4", "5", "6", "7"}, DBAdapter.KEY_DIGITSPAN));
        otFormItems.add(new FormItem(getString(R.string.qMmse), FormItem.CellType.NUMERIC, new String[]{getString(R.string.mmseHint)}, DBAdapter.KEY_MMSE));
        otFormItems.add(new FormItem(getString(R.string.qFollows), FormItem.CellType.RADIO, new String[]{getString(R.string.none), getString(R.string.a1step), getString(R.string.a2step)}, DBAdapter.KEY_FOLLOWS));
        otFormItems.add(new FormItem(getString(R.string.qVerbal), FormItem.CellType.RADIO, new String[]{getString(R.string.none), getString(R.string.partial), getString(R.string.full)}, DBAdapter.KEY_VERBAL));
        otFormItems.add(new FormItem(getString(R.string.qMotivation), FormItem.CellType.RADIO, new String[]{"1", "2", "3", "4", "5", "6", "7"}, DBAdapter.KEY_MOTIVATION));
        otFormItems.add(new FormItem(getString(R.string.qMood), FormItem.CellType.RADIO, new String[]{"1", "2", "3", "4", "5", "6", "7"}, DBAdapter.KEY_MOOD));
        otFormItems.add(new FormItem(getString(R.string.qPain), FormItem.CellType.RADIO, new String[]{"1", "2", "3", "4", "5", "6", "7"}, DBAdapter.KEY_PAIN));
        otFormItems.add(new FormItem(getString(R.string.qFatigue), FormItem.CellType.RADIO, new String[]{"1", "2", "3", "4", "5", "6", "7"}, DBAdapter.KEY_FATIGUE));
        otFormItems.add(new FormItem(getString(R.string.qSwallow), FormItem.CellType.RADIO, new String[]{getString(R.string.none), getString(R.string.partial), getString(R.string.full)}, DBAdapter.KEY_SWALLOW));
        otFormItems.add(new FormItem(getString(R.string.qFeeding), FormItem.CellType.RADIO, new String[]{getString(R.string.none), getString(R.string.partial), getString(R.string.full)}, DBAdapter.KEY_FEEDING));
        otFormItems.add(new FormItem(getString(R.string.qDressing), FormItem.CellType.RADIO, new String[]{getString(R.string.none), getString(R.string.partial), getString(R.string.full)}, DBAdapter.KEY_DRESSING));
        otFormItems.add(new FormItem(getString(R.string.qKitchen), FormItem.CellType.RADIO, new String[]{getString(R.string.none), getString(R.string.partial), getString(R.string.full)}, DBAdapter.KEY_KITCHEN));

        for (FormItem item : otFormItems){
            item.group = FormItem.Group.OT;
        }

        otAdapter = new FormListAdapter(getActivity(), otFormItems);

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