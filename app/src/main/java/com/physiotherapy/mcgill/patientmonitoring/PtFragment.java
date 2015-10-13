package com.physiotherapy.mcgill.patientmonitoring;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abhishek on 6/05/2015.
 */
public class PtFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    public static ArrayList<FormItem> ptFormItems;
    public static FormListAdapter ptAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.test_frag, container, false);

        ListView listView = (ListView) v.findViewById(R.id.test_list);

        ptFormItems = new ArrayList<>();
        ptFormItems.add(new FormItem(getString(R.string.qLeftArm), FormItem.CellType.RADIO, new String[]{getString(R.string.none), getString(R.string.partial), getString(R.string.full)}, DBAdapter.KEY_LEFTARM));
        ptFormItems.add(new FormItem(getString(R.string.qRightArm), FormItem.CellType.RADIO, new String[]{getString(R.string.none), getString(R.string.partial), getString(R.string.full)}, DBAdapter.KEY_RIGHTARM));
        ptFormItems.add(new FormItem(getString(R.string.qMovementBed), FormItem.CellType.RADIO, new String[]{getString(R.string.none), getString(R.string.partial), getString(R.string.full)}, DBAdapter.KEY_MOVEMENTBED));
        ptFormItems.add(new FormItem(getString(R.string.qLieSit), FormItem.CellType.RADIO, new String[]{getString(R.string.none), getString(R.string.partial), getString(R.string.full)}, DBAdapter.KEY_LIESIT));
        ptFormItems.add(new FormItem(getString(R.string.qSitting), FormItem.CellType.RADIO, new String[]{getString(R.string.none), getString(R.string.partial), getString(R.string.full)}, DBAdapter.KEY_SITTING));
        ptFormItems.add(new FormItem(getString(R.string.qSitStand), FormItem.CellType.RADIO, new String[]{getString(R.string.none), getString(R.string.partial), getString(R.string.full)}, DBAdapter.KEY_SITSTAND));
        ptFormItems.add(new FormItem(getString(R.string.qStand), FormItem.CellType.RADIO, new String[]{getString(R.string.none), getString(R.string.partial), getString(R.string.full)}, DBAdapter.KEY_STAND));
        ptFormItems.add(new FormItem(getString(R.string.qLiftsUnaffected), FormItem.CellType.RADIO, new String[]{getString(R.string.none), getString(R.string.partial), getString(R.string.full)}, DBAdapter.KEY_LIFTSUNAFFECTED));
        ptFormItems.add(new FormItem(getString(R.string.qLiftsAffected), FormItem.CellType.RADIO, new String[]{getString(R.string.none), getString(R.string.partial), getString(R.string.full)}, DBAdapter.KEY_LIFTSAFFECTED));
        ptFormItems.add(new FormItem(getString(R.string.qWalking), FormItem.CellType.RADIO, new String[]{getString(R.string.none), getString(R.string.partial), getString(R.string.full)}, DBAdapter.KEY_WALKING));

        for (FormItem item : ptFormItems){
            item.group = FormItem.Group.PT;
        }

        ptAdapter = new FormListAdapter(getActivity(), ptFormItems);

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