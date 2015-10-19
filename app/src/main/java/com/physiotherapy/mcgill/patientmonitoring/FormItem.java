package com.physiotherapy.mcgill.patientmonitoring;

/**
 * Created by Abhishek Vadnerkar on 15-10-17.
 */
public class FormItem {

    public String dbKey;
    public String title;
    public enum CellType {
        RADIO, NUMERIC, TEXT, DATEPICKER, DATEPICKERDIALOG
    }
    public CellType cellType;
    public enum Group {
        REGISTER, DISCHARGE
    }
    public Group group;
    public String[] options;


    public FormItem(String title, CellType cellType, String[] options, String dbKey){
        this.title = title;
        this.cellType = cellType;
        this.options = options;
        this.dbKey = dbKey;
    }

}
