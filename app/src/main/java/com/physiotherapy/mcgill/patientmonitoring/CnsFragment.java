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
    public static ArrayList<FormItem> cnsFormItems;
    public static FormListAdapter cnsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.cns_frag, container, false);

        ListView listView = (ListView) v.findViewById(R.id.cnsList);

        cnsFormItems = new ArrayList<>();
        cnsFormItems.add(new FormItem(getString(R.string.qCns1), FormItem.CellType.RADIO, new String[]{getString(R.string.aCns1alert), getString(R.string.aCns1drowsy)}, DBAdapter.dataMap.get("KEY_CNS_CONSCIOUSNESS")));
        cnsFormItems.add(new FormItem(getString(R.string.qCns2), FormItem.CellType.RADIO, new String[]{getString(R.string.aCns2oriented), getString(R.string.aCns2disoriented)}, DBAdapter.dataMap.get("KEY_CNS_ORIENTATION")));
        cnsFormItems.add(new FormItem(getString(R.string.qCns3), FormItem.CellType.RADIO, new String[]{getString(R.string.aCns3receptiveDeficit), getString(R.string.aCns3expressiveDeficit), getString(R.string.aCns3normal)}, DBAdapter.dataMap.get("KEY_CNS_SPEECH")));
        cnsFormItems.add(new FormItem(getString(R.string.qCnsA1_4), FormItem.CellType.RADIO, new String[]{getString(R.string.aCnsA1_4noWeakness), getString(R.string.aCnsA1_4Weakness)}, DBAdapter.dataMap.get("KEY_CNS_FACE1")));
        cnsFormItems.add(new FormItem(getString(R.string.qCnsA1_5), FormItem.CellType.RADIO, new String[]{getString(R.string.aCnsA1_noWeakness), getString(R.string.aCnsA1_mildWeakness), getString(R.string.aCnsA1_significantWeakness), getString(R.string.aCnsA1_totalWeakness)}, DBAdapter.dataMap.get("KEY_CNS_UPPER_LIMB_PROXIMAL")));
        cnsFormItems.add(new FormItem(getString(R.string.qCnsA1_6), FormItem.CellType.RADIO, new String[]{getString(R.string.aCnsA1_noWeakness), getString(R.string.aCnsA1_mildWeakness), getString(R.string.aCnsA1_significantWeakness), getString(R.string.aCnsA1_totalWeakness)}, DBAdapter.dataMap.get("KEY_CNS_UPPER_LIMB_DISTAL")));
        cnsFormItems.add(new FormItem(getString(R.string.qCnsA1_7), FormItem.CellType.RADIO, new String[]{getString(R.string.aCnsA1_noWeakness), getString(R.string.aCnsA1_mildWeakness), getString(R.string.aCnsA1_significantWeakness), getString(R.string.aCnsA1_totalWeakness)}, DBAdapter.dataMap.get("KEY_CNS_LOWER_LIMB_PROXIMAL")));
        cnsFormItems.add(new FormItem(getString(R.string.qCnsA1_8), FormItem.CellType.RADIO, new String[]{getString(R.string.aCnsA1_noWeakness), getString(R.string.aCnsA1_mildWeakness), getString(R.string.aCnsA1_significantWeakness), getString(R.string.aCnsA1_totalWeakness)}, DBAdapter.dataMap.get("KEY_CNS_LOWER_LIMB_DISTAL")));
        cnsFormItems.add(new FormItem(getString(R.string.qCnsA2_5), FormItem.CellType.RADIO, new String[]{getString(R.string.aCnsA2_equal), getString(R.string.aCnsA2_unequal)}, DBAdapter.dataMap.get("KEY_CNS_UPPER_LIMBS")));
        cnsFormItems.add(new FormItem(getString(R.string.qCnsA2_6), FormItem.CellType.RADIO, new String[]{getString(R.string.aCnsA2_equal), getString(R.string.aCnsA2_unequal)}, DBAdapter.dataMap.get("KEY_CNS_LOWER_LIMBS")));
        cnsFormItems.add(new FormItem(getString(R.string.qCnsA2_4), FormItem.CellType.RADIO, new String[]{getString(R.string.aCnsA2_4symmetrical), getString(R.string.aCnsA2_4asymmetrical)}, DBAdapter.dataMap.get("KEY_CNS_FACE2")));

        for (FormItem item : cnsFormItems){
            item.group = FormItem.Group.CNS;
        }

        cnsAdapter = new FormListAdapter(getActivity(), cnsFormItems);

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
