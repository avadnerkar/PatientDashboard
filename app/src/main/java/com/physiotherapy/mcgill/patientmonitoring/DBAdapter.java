// ------------------------------------ DBADapter.java ---------------------------------------------

// TODO: Change the package to match your project.
package com.physiotherapy.mcgill.patientmonitoring;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

// \test change for git
// TO USE:
// Change the package (at top) to match your project.
// Search for "TODO", and make the appropriate changes.
public class DBAdapter {

	/////////////////////////////////////////////////////////////////////
	//	Constants & Data
	/////////////////////////////////////////////////////////////////////
	// For logging:
	private static final String TAG = "DBAdapter";


	// Common fields Fields
	public static final String KEY_ROWID = "_id";


	// TODO: Setup your  patient fields here:

	public static Map<String, String> patientMap;


	// TODO: Setup your data fields here:
	public static Map<String, String> dataMap;


	// DB info: its name, and the tables we are using
	public static final String DATABASE_NAME = "PatientMonitoringDb";
	public static final String PATIENT_TABLE = "patientTable";
	public static final String DATA_TABLE = "dataTable";

	// Track DB version if a new version of your app changes the format.
	public static final int DATABASE_VERSION = 26;


	//Table Create Statements

	private static String PATIENT_CREATE_SQL;
	private static String DATA_CREATE_SQL;
	
	// Context of application who uses us.
	private final Context context;
	
	private DatabaseHelper myDBHelper;
	private SQLiteDatabase db;

	/////////////////////////////////////////////////////////////////////
	//	Public methods:
	/////////////////////////////////////////////////////////////////////
	
	public DBAdapter(Context ctx) {
		this.context = ctx;
		myDBHelper = new DatabaseHelper(context);
	}
	
	// Open the database connection.
	public DBAdapter open() {

		patientMap = new LinkedHashMap<>();
		patientMap.put("KEY_FIRSTNAME","FirstName");
		patientMap.put("KEY_LASTNAME","LastName");
		patientMap.put("KEY_MRN","MRN");
		patientMap.put("KEY_ADMISSIONDATE","AdmissionDate");
		patientMap.put("KEY_DISCHARGED","Discharged");
		patientMap.put("KEY_DISCHARGEDATE","DischargeDate");
		patientMap.put("KEY_PATIENTAGE","PatientAge");
		patientMap.put("KEY_PATIENTGENDER","PatientGender");
		patientMap.put("KEY_FIRSTLANGUAGE","FirstLanguage");

		patientMap.put("KEY_MOCASCORE","MOCAscore");
		patientMap.put("KEY_CUSTOMSCORE","CustomScore");
		patientMap.put("KEY_CUSTOMMAX","CustomMaxScore");
		patientMap.put("KEY_CNS", "CnsScore");
		patientMap.put("KEY_NIHSS", "NihssScore");

		patientMap.put("KEY_STROKETYPE","StrokeType");
		patientMap.put("KEY_FIRSTSTROKE","FirstStroke");
		patientMap.put("KEY_LESIONSIDE","LesionSide");
		patientMap.put("KEY_HEMIPLEGIASIDE","HemiplegiaSide");
		patientMap.put("KEY_CONSCIOUSNESS","Consciousness");
		patientMap.put("KEY_ORIENTATION","Orientation");
		patientMap.put("KEY_LANGUAGE","Language");
		patientMap.put("KEY_VISUAL","VisualImpairment");
		patientMap.put("KEY_HEARINGAID","HearingAid");
		patientMap.put("KEY_HEARINGASSESSED","HearingAssessed");
		patientMap.put("KEY_APHASIA","Aphasia");

		patientMap.put("KEY_PEGADMIT","PegAdmission");
		patientMap.put("KEY_NGADMIT","NgAdmission");
		patientMap.put("KEY_FOLEYADMIT","FoleyAdmission");
		patientMap.put("KEY_FALLRISK","FallRisk");
		patientMap.put("KEY_MOTIVATIONADMIT","MotivationAdmission");
		patientMap.put("KEY_OTHER","OtherHistory");
		patientMap.put("KEY_COGNITION","Cognition");

		patientMap.put("KEY_FIRSTOT","FirstOTAssessment");
		patientMap.put("KEY_TOTALOT","TotalOTSessions");
		patientMap.put("KEY_FIRSTSWALLOW","FirstSwallowAssessment");
		patientMap.put("KEY_FIRSTPT","FirstPTAssessment");
		patientMap.put("KEY_TOTALPT","TotalPTSessions");
		patientMap.put("KEY_FIRSTSLT","FirstSLTAssessment");
		patientMap.put("KEY_TOTALSLT","TotalSLTSessions");

		patientMap.put("KEY_CNS_CONSCIOUSNESS", "CnsConsciousness");
		patientMap.put("KEY_CNS_ORIENTATION", "CnsOrientation");
		patientMap.put("KEY_CNS_SPEECH", "CnsSpeech");
		patientMap.put("KEY_CNS_FACE1", "CnsFace1");
		patientMap.put("KEY_CNS_UPPER_LIMB_PROXIMAL", "CnsUpperLimbProximal");
		patientMap.put("KEY_CNS_UPPER_LIMB_DISTAL", "CnsUpperLimbDistal");
		patientMap.put("KEY_CNS_LOWER_LIMB_PROXIMAL", "CnsLowerLimbProximal");
		patientMap.put("KEY_CNS_LOWER_LIMB_DISTAL", "CnsLowerLimbDistal");
		patientMap.put("KEY_CNS_UPPER_LIMBS", "CnsUpperLimbs");
		patientMap.put("KEY_CNS_LOWER_LIMBS", "CnsLowerLimbs");
		patientMap.put("KEY_CNS_FACE2", "CnsFace2");

		dataMap = new LinkedHashMap<>();
		dataMap.put("KEY_PARENTID", "ParentID");
		dataMap.put("KEY_MRN", "MRNnumber");
		dataMap.put("KEY_DAY", "Day");

		dataMap.put("KEY_PEG", "PEG");
		dataMap.put("KEY_NG", "NG");
		dataMap.put("KEY_O2", "O2");
		dataMap.put("KEY_IV", "IV");
		dataMap.put("KEY_CPAP", "CPAP");
		dataMap.put("KEY_RESTRAINT", "Restraint");
		dataMap.put("KEY_BEDBARS", "BedBars");
		dataMap.put("KEY_BEHAVIOURAL", "BehaviouralIssue");
		dataMap.put("KEY_CONFUSION", "Confusion");
		dataMap.put("KEY_BLADDER", "BladderControl");
		dataMap.put("KEY_HOURS", "EstimatedHoursOutOfBed");

		dataMap.put("KEY_NEGLECT", "Neglect");
		dataMap.put("KEY_DIGITSPAN", "DigitSpan");
		dataMap.put("KEY_MMSE", "MMSE");
		dataMap.put("KEY_FOLLOWS", "FollowsCommands");
		dataMap.put("KEY_VERBAL", "VerbalCommunication");
		dataMap.put("KEY_MOTIVATION", "Motivation");
		dataMap.put("KEY_MOOD", "Mood");
		dataMap.put("KEY_PAIN", "Pain");
		dataMap.put("KEY_FATIGUE", "Fatigue");
		dataMap.put("KEY_SWALLOW", "Swallow");
		dataMap.put("KEY_FEEDING", "Feeding");
		dataMap.put("KEY_DRESSING", "Dressing");
		dataMap.put("KEY_KITCHEN", "Kitchen");

		dataMap.put("KEY_LEFTARM", "LeftArmMovement");
		dataMap.put("KEY_RIGHTARM", "RightArmMovement");
		dataMap.put("KEY_MOVEMENTBED", "MovementInBed");
		dataMap.put("KEY_LIESIT", "LieToSit");
		dataMap.put("KEY_SITTING", "SittingUnsupported");
		dataMap.put("KEY_SITSTAND", "SitToStand");
		dataMap.put("KEY_STAND", "StandUnsupported");
		dataMap.put("KEY_LIFTSUNAFFECTED", "LiftsUnaffectedLegStanding");
		dataMap.put("KEY_LIFTSAFFECTED", "LiftsAffectedLegStanding");
		dataMap.put("KEY_WALKING", "Walking");

		dataMap.put("KEY_BARTHEL", "EstBarthelScore");
		dataMap.put("KEY_BERG", "EstBergScore");

		generateCreatePatientString();
		generateCreateDataString();


		db = myDBHelper.getWritableDatabase();

		return this;
	}

	private void generateCreatePatientString(){
		PATIENT_CREATE_SQL = "create table " + PATIENT_TABLE + " (" + KEY_ROWID + " integer primary key autoincrement, ";

		for (String key : patientMap.keySet()){
			PATIENT_CREATE_SQL = PATIENT_CREATE_SQL + patientMap.get(key) + " text, ";
		}

		PATIENT_CREATE_SQL = PATIENT_CREATE_SQL.substring(0, PATIENT_CREATE_SQL.length()-2); //Trim last comma
		PATIENT_CREATE_SQL = PATIENT_CREATE_SQL + ");";
	}

	private void generateCreateDataString(){
		DATA_CREATE_SQL = "create table " + DATA_TABLE + " (" + KEY_ROWID + " integer primary key autoincrement, ";

		for (String key : dataMap.keySet()){
			DATA_CREATE_SQL = DATA_CREATE_SQL + dataMap.get(key) + " text, ";
		}
		DATA_CREATE_SQL = DATA_CREATE_SQL.substring(0, DATA_CREATE_SQL.length()-2); //Trim last comma
		DATA_CREATE_SQL = DATA_CREATE_SQL + ");";
	}
	
	// Close the database connection.
	public void close() {
		myDBHelper.close();
	}

	/////////////////////////////////////////////////////////////////////
	//	Patient methods:
	/////////////////////////////////////////////////////////////////////


    public long insertNewPatient(){

		final Calendar c = Calendar.getInstance();
		int mYear = c.get(Calendar.YEAR);
		int mMonth = c.get(Calendar.MONTH);
		int mDay = c.get(Calendar.DAY_OF_MONTH);

		String date = mYear + "-" + (mMonth + 1) + "-" + mDay;

        ContentValues initialValues = new ContentValues();
        initialValues.put(patientMap.get("KEY_ADMISSIONDATE"), date);
        return db.insert(PATIENT_TABLE, null, initialValues);
    }
	
	// Delete a row from the database, by rowId (primary key)
	public boolean deleteRowPatient(long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		return db.delete(PATIENT_TABLE, where, null) != 0;
	}

	public void deleteCurrentPatient(int currentPatientId){
		String deleteQuery = "DELETE FROM " + PATIENT_TABLE + " WHERE " + KEY_ROWID + " = " + currentPatientId;
		Cursor c =  db.rawQuery(deleteQuery, null);
		if (c != null) {
			c.moveToFirst();
			c.close();
		}


		deleteQuery = "DELETE FROM " + DATA_TABLE + " WHERE " + dataMap.get("KEY_PARENTID") + " = " + currentPatientId;
		c =  db.rawQuery(deleteQuery, null);
		if (c != null) {
			c.moveToFirst();
			c.close();
		}

	}
	
	public void deleteAllPatients() {
		Cursor c = getAllRowPatients();
		long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
		if (c.moveToFirst()) {
			do {
				deleteRowPatient(c.getLong((int) rowId));
			} while (c.moveToNext());
		}
		c.close();

		c = getAllRowData();
		rowId = c.getColumnIndexOrThrow(KEY_ROWID);
		if (c.moveToFirst()) {
			do {
				deleteRowData(c.getLong((int) rowId));
			} while (c.moveToNext());
		}
		c.close();
	}
	
	// Return all data in the database.
	public Cursor getAllRowPatients() {
		String where = null;
		Cursor c = 	db.query(true, PATIENT_TABLE, null,
				where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	// Get a specific row (by rowId)
	public Cursor getRowPatient(long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		Cursor c = 	db.query(true, PATIENT_TABLE, null,
						where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	public Cursor getPatientField(int rowId, String key){
		String where = KEY_ROWID + "= ?";
		Cursor c = db.query(PATIENT_TABLE, new String[]{key}, where, new String[]{String.valueOf(rowId)}, null, null, null);
		if (c != null){
			c.moveToFirst();
		}
		return c;
	}

	public boolean updateFieldPatient(int rowId, String key, String value){

		String where = KEY_ROWID + "= ?";
		ContentValues newValues = new ContentValues();
		newValues.put(key, value);
		return db.update(PATIENT_TABLE, newValues, where, new String[]{String.valueOf(rowId)}) != 0;
	}



	/////////////////////////////////////////////////////////////////////
	//	Data methods:
	/////////////////////////////////////////////////////////////////////



	public long insertNewRow(int parentId, String mrnNumber, int day){
		ContentValues initialValues = new ContentValues();
		initialValues.put(dataMap.get("KEY_PARENTID"), parentId);
		initialValues.put(dataMap.get("KEY_MRN"), mrnNumber);
		initialValues.put(dataMap.get("KEY_DAY"), day);

		return db.insert(DATA_TABLE, null, initialValues);
	}

	// Delete a row from the database, by rowId (primary key)
	public boolean deleteRowData(long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		return db.delete(DATA_TABLE, where, null) != 0;
	}


	// Return all data in the database.
	public Cursor getAllRowData() {
		String where = null;
		Cursor c = 	db.query(true, DATA_TABLE, null,
				where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	// Get a specific row (by rowId)
	public Cursor getRowData(int patientId, int currentDay) {
		String selectQuery = "SELECT * FROM " + DATA_TABLE + " dt WHERE dt." + dataMap.get("KEY_PARENTID") + " = " + patientId + " AND dt." + dataMap.get("KEY_DAY") + " = " + currentDay;

		Cursor c = 	db.rawQuery(selectQuery, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}



	public boolean updateFieldData(int parentId, int day, String key, String value){

		Cursor c = getDataField(parentId, day, key);

		if (!c.moveToFirst()){
			insertNewRow(parentId, MainActivity.currentMrn, day);
		}
		c.close();
		String where = dataMap.get("KEY_PARENTID") + "= ? AND " + dataMap.get("KEY_DAY") + "= ?";
		ContentValues newValues = new ContentValues();
		newValues.put(key, value);
		return db.update(DATA_TABLE, newValues, where, new String[]{String.valueOf(parentId), String.valueOf(day)}) != 0;
	}


	public Cursor getAllPatientDataSorted(int patientId){
		String where = dataMap.get("KEY_PARENTID") + "=" + patientId;
		Cursor c = db.query(DATA_TABLE, null, where, null, null, null, dataMap.get("KEY_DAY") + " ASC");
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}


	public Cursor getDataField(int patientId, int day, String key){
		String where = dataMap.get("KEY_PARENTID") + "= ? AND " + dataMap.get("KEY_DAY") + "= ?";
		Cursor c = db.query(DATA_TABLE, new String[]{key}, where, new String[]{String.valueOf(patientId), String.valueOf(day)}, null, null, null);
		if (c != null){
			c.moveToFirst();
		}
		return c;
	}

	
	/////////////////////////////////////////////////////////////////////
	//	Private Helper Classes:
	/////////////////////////////////////////////////////////////////////
	
	/**
	 * Private class which handles database creation and upgrading.
	 * Used to handle low-level database access.
	 */
	private static class DatabaseHelper extends SQLiteOpenHelper
	{
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase _db) {
			_db.execSQL(PATIENT_CREATE_SQL);
			_db.execSQL(DATA_CREATE_SQL);
		}

		@Override
		public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading application's database from version " + oldVersion
					+ " to " + newVersion + ", attempting to keep old data");
			
			// Destroy old database:
			_db.execSQL("DROP TABLE IF EXISTS " + PATIENT_TABLE);
			_db.execSQL("DROP TABLE IF EXISTS " + DATA_TABLE);

			//If column adding is needed:
			//_db.execSQL("ALTER TABLE " + DATA_TABLE + " ADD COLUMN " + KEY_CNS_CONSCIOUSNESS + " text");

			
			// Recreate new database:
			onCreate(_db);
		}
	}
}
