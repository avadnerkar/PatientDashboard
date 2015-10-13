package com.physiotherapy.mcgill.patientmonitoring;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by alexandrehuot on 15-10-12.
 */
public class FormListAdapter extends ArrayAdapter<FormItem> {

    private Context context;
    private ArrayList<FormItem> items;


    public FormListAdapter(Context context, ArrayList<FormItem> items){
        super(context, R.layout.cell_option_radio, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public int getViewTypeCount() {
        return FormItem.CellType.values().length;
    }

    @Override
    public int getItemViewType(int position) {
        return (items.get(position).cellType).ordinal();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView;
        TextView textView;
        switch (items.get(position).cellType){
            case NUMERIC:
            case RADIO:
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                rowView = inflater.inflate(R.layout.cell_option_radio, parent, false);
                textView = (TextView) rowView.findViewById(R.id.title);
                textView.setText(items.get(position).title);

                RadioGroup rg = (RadioGroup) rowView.findViewById(R.id.rg);

                if (items.get(position).options.length >2){
                    rg.setOrientation(RadioGroup.VERTICAL);
                } else {
                    rg.setOrientation(RadioGroup.HORIZONTAL);
                }

                for (int i=0; i<items.get(position).options.length; i++){
                    RadioButton rb = new RadioButton(context);
                    rb.setText(items.get(position).options[i]);
                    rg.addView(rb);

                    rb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            MainActivity.myDb.updateFieldData(MainActivity.currentPatientId, MainActivity.currentDay, items.get(position).dbKey, ((RadioButton) view).getText().toString());
                        }
                    });
                }

                Cursor cursor = MainActivity.myDb.getDataField(MainActivity.currentPatientId, MainActivity.currentDay, items.get(position).dbKey);

                if (cursor.moveToFirst()){
                    String radioValue = cursor.getString(cursor.getColumnIndex(items.get(position).dbKey));
                    if (!radioValue.equals("")){
                        for (int i=0; i<items.get(position).options.length; i++){
                            String rbString = items.get(position).options[i];
                            if (rbString.equals(radioValue)){
                                ((RadioButton)rg.getChildAt(i)).setChecked(true);
                            }
                        }
                    } else {
                        rg.clearCheck();
                    }

                } else {
                    rg.clearCheck();
                }

//                rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(RadioGroup radioGroup, int j) {
//                        MainActivity.myDb.updateFieldData(MainActivity.currentPatientId, MainActivity.currentDay, items.get(position).dbKey, radioGroup.getCheckedRadioButtonId());
//                    }
//                });

                RelativeLayout container = (RelativeLayout) rowView.findViewById(R.id.container);
                switch (items.get(position).group){

                    case NURSE:
                        container.setBackgroundColor(context.getResources().getColor(R.color.White));
                        break;
                    case OT:
                        container.setBackgroundColor(context.getResources().getColor(R.color.Salmon));
                        break;
                    case PT:
                        container.setBackgroundColor(context.getResources().getColor(R.color.CornflowerBlue));
                        break;
                    case CNS:
                        container.setBackgroundColor(context.getResources().getColor(R.color.MediumSeaGreen));
                        break;
                }

                return rowView;
        }

        return null;
    }


    public void updateValues(){
        for (int i=0; i<items.size(); i++){

        }
    }


}
