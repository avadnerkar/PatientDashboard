package com.physiotherapy.mcgill.patientmonitoring;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Abhishek on 6/05/2015.
 */
public class NurseFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.nurse_frag, container, false);

        //String debugMessage = MainActivity.updateTextField();


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

