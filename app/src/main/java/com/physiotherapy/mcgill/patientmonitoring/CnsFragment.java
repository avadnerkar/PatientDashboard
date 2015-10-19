package com.physiotherapy.mcgill.patientmonitoring;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by alexandrehuot on 15-10-03.
 */
public class CnsFragment extends Fragment{

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    public static ArrayList<DailyItem> cnsDailyItems;
    public static DailyListAdapter cnsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.cns_frag, container, false);

        ListView listView = (ListView) v.findViewById(R.id.cnsList);

        cnsDailyItems = new ArrayList<>();
        cnsDailyItems.add(new DailyItem(getString(R.string.qCns1), DailyItem.CellType.RADIO, new String[]{getString(R.string.aCns1alert), getString(R.string.aCns1drowsy)}, DBAdapter.dataMap.get("KEY_CNS_CONSCIOUSNESS")));
        cnsDailyItems.add(new DailyItem(getString(R.string.qCns2), DailyItem.CellType.RADIO, new String[]{getString(R.string.aCns2oriented), getString(R.string.aCns2disoriented)}, DBAdapter.dataMap.get("KEY_CNS_ORIENTATION")));
        cnsDailyItems.add(new DailyItem(getString(R.string.qCns3), DailyItem.CellType.RADIO, new String[]{getString(R.string.aCns3receptiveDeficit), getString(R.string.aCns3expressiveDeficit), getString(R.string.aCns3normal)}, DBAdapter.dataMap.get("KEY_CNS_SPEECH")));
        cnsDailyItems.add(new DailyItem(getString(R.string.qCnsA1_4), DailyItem.CellType.RADIO, new String[]{getString(R.string.aCnsA1_4noWeakness), getString(R.string.aCnsA1_4Weakness)}, DBAdapter.dataMap.get("KEY_CNS_FACE1")));
        cnsDailyItems.add(new DailyItem(getString(R.string.qCnsA1_5), DailyItem.CellType.RADIO, new String[]{getString(R.string.aCnsA1_noWeakness), getString(R.string.aCnsA1_mildWeakness), getString(R.string.aCnsA1_significantWeakness), getString(R.string.aCnsA1_totalWeakness)}, DBAdapter.dataMap.get("KEY_CNS_UPPER_LIMB_PROXIMAL")));
        cnsDailyItems.add(new DailyItem(getString(R.string.qCnsA1_6), DailyItem.CellType.RADIO, new String[]{getString(R.string.aCnsA1_noWeakness), getString(R.string.aCnsA1_mildWeakness), getString(R.string.aCnsA1_significantWeakness), getString(R.string.aCnsA1_totalWeakness)}, DBAdapter.dataMap.get("KEY_CNS_UPPER_LIMB_DISTAL")));
        cnsDailyItems.add(new DailyItem(getString(R.string.qCnsA1_7), DailyItem.CellType.RADIO, new String[]{getString(R.string.aCnsA1_noWeakness), getString(R.string.aCnsA1_mildWeakness), getString(R.string.aCnsA1_significantWeakness), getString(R.string.aCnsA1_totalWeakness)}, DBAdapter.dataMap.get("KEY_CNS_LOWER_LIMB_PROXIMAL")));
        cnsDailyItems.add(new DailyItem(getString(R.string.qCnsA1_8), DailyItem.CellType.RADIO, new String[]{getString(R.string.aCnsA1_noWeakness), getString(R.string.aCnsA1_mildWeakness), getString(R.string.aCnsA1_significantWeakness), getString(R.string.aCnsA1_totalWeakness)}, DBAdapter.dataMap.get("KEY_CNS_LOWER_LIMB_DISTAL")));
        cnsDailyItems.add(new DailyItem(getString(R.string.qCnsA2_5), DailyItem.CellType.RADIO, new String[]{getString(R.string.aCnsA2_equal), getString(R.string.aCnsA2_unequal)}, DBAdapter.dataMap.get("KEY_CNS_UPPER_LIMBS")));
        cnsDailyItems.add(new DailyItem(getString(R.string.qCnsA2_6), DailyItem.CellType.RADIO, new String[]{getString(R.string.aCnsA2_equal), getString(R.string.aCnsA2_unequal)}, DBAdapter.dataMap.get("KEY_CNS_LOWER_LIMBS")));
        cnsDailyItems.add(new DailyItem(getString(R.string.qCnsA2_4), DailyItem.CellType.RADIO, new String[]{getString(R.string.aCnsA2_4symmetrical), getString(R.string.aCnsA2_4asymmetrical)}, DBAdapter.dataMap.get("KEY_CNS_FACE2")));

        for (DailyItem item : cnsDailyItems){
            item.group = DailyItem.Group.CNS;
        }

        cnsAdapter = new DailyListAdapter(getActivity(), cnsDailyItems);

        listView.setAdapter(cnsAdapter);

        return v;
    }

    public static CnsFragment newInstance(int sectionNumber) {

        CnsFragment f = new CnsFragment();
        Bundle b = new Bundle();

        b.putInt(ARG_SECTION_NUMBER, sectionNumber);
        f.setArguments(b);

        return f;
    }

}
