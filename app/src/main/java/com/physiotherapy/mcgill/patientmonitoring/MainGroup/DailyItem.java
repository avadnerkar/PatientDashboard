package com.physiotherapy.mcgill.patientmonitoring.MainGroup;

/**
 * Created by Abhishek Vadnerkar on 15-10-12.
 */
public class DailyItem {

    public String dbKey;
    public String title;
    public enum CellType {
        RADIO, NUMERIC
    }
    public CellType cellType;
    public enum Group {
        NURSE, OT, PT, CNS
    }
    public Group group;
    public String[] options;


    public DailyItem(String title, CellType cellType, String[] options, String dbKey){
        this.title = title;
        this.cellType = cellType;
        this.options = options;
        this.dbKey = dbKey;
    }

}
