package com.physiotherapy.mcgill.patientmonitoring;

import android.content.Context;
import android.database.Cursor;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class DailyListAdapter extends ArrayAdapter<DailyItem> {

    private Context context;
    private ArrayList<DailyItem> items;


    public DailyListAdapter(Context context, ArrayList<DailyItem> items){
        super(context, R.layout.cell_daily_radio, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public int getViewTypeCount() {
        return DailyItem.CellType.values().length;
    }

    @Override
    public int getItemViewType(int position) {
        return (items.get(position).cellType).ordinal();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = null;
        TextView textView;
        Cursor cursor;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        switch (items.get(position).cellType){
            case NUMERIC:
                rowView = inflater.inflate(R.layout.cell_daily_numeric, parent, false);
                textView = (TextView) rowView.findViewById(R.id.title);
                textView.setText(items.get(position).title);
                EditText editText = (EditText) rowView.findViewById(R.id.edit_numeric);
                editText.setHint(items.get(position).options[0]);

                cursor = MainActivity.myDb.getDataField(MainActivity.currentPatientId, MainActivity.currentDay, items.get(position).dbKey);

                if (cursor.moveToFirst()){
                    String text = cursor.getString(cursor.getColumnIndex(items.get(position).dbKey));
                    if (text !=null){
                        editText.setText(text);
                    } else {
                        editText.setText("");
                    }

                } else {
                    editText.setText("");
                }

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(final CharSequence charSequence, int i, int i1, int i2) {
                        Thread thread = new Thread(){
                            @Override
                            public void run() {
                                MainActivity.myDb.updateFieldData(MainActivity.currentPatientId, MainActivity.currentDay, items.get(position).dbKey, charSequence.toString());
                            }
                        };
                        thread.start();

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });

                cursor.close();

                break;
            case RADIO:
                rowView = inflater.inflate(R.layout.cell_daily_radio, parent, false);
                textView = (TextView) rowView.findViewById(R.id.title);
                textView.setText(items.get(position).title);

                RadioGroup rg = (RadioGroup) rowView.findViewById(R.id.rg);

                if (items.get(position).options.length >2 && items.get(position).options.length < 7){
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
                        public void onClick(final View view) {
                            Thread thread = new Thread(){
                                @Override
                                public void run() {
                                    MainActivity.myDb.updateFieldData(MainActivity.currentPatientId, MainActivity.currentDay, items.get(position).dbKey, ((RadioButton) view).getText().toString());
                                }
                            };
                            thread.start();

                        }
                    });
                }

                cursor = MainActivity.myDb.getDataField(MainActivity.currentPatientId, MainActivity.currentDay, items.get(position).dbKey);

                if (cursor.moveToFirst()){
                    String radioValue = cursor.getString(cursor.getColumnIndex(items.get(position).dbKey));
                    if (radioValue != null && !radioValue.equals("")){
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

                cursor.close();
                break;
        }

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



}
