package com.physiotherapy.mcgill.patientmonitoring;

import android.app.DatePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class FormListAdapter extends ArrayAdapter<FormItem>{

    private Context context;
    private ArrayList<FormItem> items;

    public FormListAdapter(Context context, ArrayList<FormItem> items){
        super(context, R.layout.cell_daily_radio, items);
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

        View rowView = null;
        final TextView textView;
        Cursor cursor;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        switch (items.get(position).cellType) {

            case RADIO:

                rowView = inflater.inflate(R.layout.cell_form_radio, parent, false);
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
                                    MainActivity.myDb.updateFieldPatient(MainActivity.currentPatientId, items.get(position).dbKey, ((RadioButton) view).getText().toString());
                                }
                            };
                            thread.start();

                        }
                    });
                }

                cursor = MainActivity.myDb.getPatientField(MainActivity.currentPatientId, items.get(position).dbKey);

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
            case TEXT:
            case NUMERIC:

                rowView = inflater.inflate(R.layout.cell_form_text, parent, false);
                textView = (TextView) rowView.findViewById(R.id.title);
                textView.setText(items.get(position).title);
                EditText editText = (EditText) rowView.findViewById(R.id.field);
                editText.setHint(items.get(position).options[0]);
                if (items.get(position).cellType == FormItem.CellType.NUMERIC){
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                } else {
                    editText.setInputType(InputType.TYPE_CLASS_TEXT);
                }


                cursor = MainActivity.myDb.getPatientField(MainActivity.currentPatientId, items.get(position).dbKey);

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
                                MainActivity.myDb.updateFieldPatient(MainActivity.currentPatientId, items.get(position).dbKey, charSequence.toString());
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
            case DATEPICKER:
                rowView = inflater.inflate(R.layout.cell_form_datepicker, parent, false);
                textView = (TextView) rowView.findViewById(R.id.title);
                textView.setText(items.get(position).title);

                DatePicker datePicker = (DatePicker) rowView.findViewById(R.id.date_picker);

                cursor = MainActivity.myDb.getPatientField(MainActivity.currentPatientId, items.get(position).dbKey);

                Calendar now = Calendar.getInstance();
                SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                datePicker.init(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                        final String value = i + "-" + (i1+1) + "-" + i2;
                        Thread thread = new Thread(){
                            @Override
                            public void run() {
                                MainActivity.myDb.updateFieldPatient(MainActivity.currentPatientId, items.get(position).dbKey, value);
                            }
                        };
                        thread.start();

                    }
                });

                if (cursor.moveToFirst()){
                    String dateString = cursor.getString(cursor.getColumnIndex(items.get(position).dbKey));

                    if (dateString != null){
                        Date date = null;
                        try{
                            date = form.parse(dateString);
                        } catch (java.text.ParseException e){
                            e.printStackTrace();
                        }
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        int month = calendar.get(Calendar.MONTH);
                        int year = calendar.get(Calendar.YEAR);

                        datePicker.updateDate(year, month, day);
                    }
                }


                break;
            case DATEPICKERDIALOG:

                rowView = inflater.inflate(R.layout.cell_form_datepickerdialog, parent, false);
                textView = (TextView) rowView.findViewById(R.id.title);
                textView.setText(items.get(position).title);

                final TextView dateText = (TextView) rowView.findViewById(R.id.text_date);

                Button button = (Button) rowView.findViewById(R.id.button);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Calendar c = Calendar.getInstance();
                        int mYear = c.get(Calendar.YEAR);
                        int mMonth = c.get(Calendar.MONTH);
                        int mDay = c.get(Calendar.DAY_OF_MONTH);

                        // Launch Date Picker Dialog
                        DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // Display Selected date in textbox
                                final String value = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                dateText.setText(value);

                                Thread thread = new Thread(){
                                    @Override
                                    public void run() {
                                        MainActivity.myDb.updateFieldPatient(MainActivity.currentPatientId, items.get(position).dbKey, value);
                                    }
                                };
                                thread.start();

                            }
                        }, mYear, mMonth, mDay);
                        dpd.show();
                    }
                });

                break;

            case SCORE:
                rowView = inflater.inflate(R.layout.cell_form_score, parent, false);
                textView = (TextView) rowView.findViewById(R.id.title);
                textView.setText(items.get(position).title);

                EditText score = (EditText) rowView.findViewById(R.id.score);
                EditText total = (EditText) rowView.findViewById(R.id.total);

                if (items.get(position).misc instanceof Integer){
                    total.setEnabled(false);
                    total.setText(String.valueOf(items.get(position).misc));
                }

                cursor = MainActivity.myDb.getPatientField(MainActivity.currentPatientId, items.get(position).dbKey);

                if (cursor.moveToFirst()){
                    String text = cursor.getString(cursor.getColumnIndex(items.get(position).dbKey));
                    if (text !=null){
                        score.setText(text);
                    } else {
                        score.setText("");
                    }

                } else {
                    score.setText("");
                }

                score.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(final CharSequence charSequence, int i, int i1, int i2) {
                        Thread thread = new Thread(){
                            @Override
                            public void run() {
                                MainActivity.myDb.updateFieldPatient(MainActivity.currentPatientId, items.get(position).dbKey, charSequence.toString());
                            }
                        };
                        thread.start();
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });

                if (items.get(position).misc instanceof String){

                    cursor = MainActivity.myDb.getPatientField(MainActivity.currentPatientId, (String) items.get(position).misc);
                    if (cursor.moveToFirst()){
                        String text = cursor.getString(cursor.getColumnIndex((String) items.get(position).misc));
                        if (text !=null){
                            total.setText(text);
                        } else {
                            total.setText("");
                        }

                    } else {
                        total.setText("");
                    }

                    total.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(final CharSequence charSequence, int i, int i1, int i2) {
                            Thread thread = new Thread(){
                                @Override
                                public void run() {
                                    MainActivity.myDb.updateFieldPatient(MainActivity.currentPatientId, (String) items.get(position).misc, charSequence.toString());
                                }
                            };
                            thread.start();
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });
                }


                break;

        }


        return rowView;
    }


}
