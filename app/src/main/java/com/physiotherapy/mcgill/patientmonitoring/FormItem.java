package com.physiotherapy.mcgill.patientmonitoring;

/**
 * Created by alexandrehuot on 15-10-12.
 */
public class FormItem {

    public String dbKey;
    public String title;
    public enum CellType {
        radio, numeric
    }
    public CellType cellType;
    public String[] options;


    public FormItem(String title, CellType cellType, String[] options, String dbKey){
        this.title = title;
        this.cellType = cellType;
        this.options = options;
        this.dbKey = dbKey;
    }

}
