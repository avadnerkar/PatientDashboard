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

	public static final String KEY_FIRSTNAME = "FirstName";
	public static final String KEY_LASTNAME = "LastName";
	public static final String KEY_HOSPITALID = "MRN";
	public static final String KEY_ADMISSIONDATE = "AdmissionDate";
	public static final String KEY_DISCHARGED = "Discharged";
	public static final String KEY_DISCHARGEDATE = "DischargeDate";
	public static final String KEY_PATIENTAGE = "PatientAge";
	public static final String KEY_PATIENTGENDER = "PatientGender";
	public static final String KEY_FIRSTLANGUAGE = "FirstLanguage";

	public static final String KEY_MOCASCORE = "MOCAscore";
	public static final String KEY_CUSTOMSCORE = "CustomScore";
	public static final String KEY_CUSTOMMAX = "CustomMaxScore";

	public static final String KEY_STROKETYPE = "StrokeType";
	public static final String KEY_FIRSTSTROKE = "FirstStroke";
	public static final String KEY_LESIONSIDE = "LesionSide";
	public static final String KEY_HEMIPLEGIASIDE = "HemiplegiaSide";
	public static final String KEY_CONSCIOUSNESS = "Consciousness";
	public static final String KEY_ORIENTATION = "Orientation";
	public static final String KEY_LANGUAGE = "Language";
	public static final String KEY_VISUAL = "VisualImpairment";
	public static final String KEY_HEARINGAID = "HearingAid";
	public static final String KEY_HEARINGASSESSED = "HearingAssessed";
	public static final String KEY_APHASIA = "Aphasia";

	public static final String KEY_PEGADMIT = "PegAdmission";
	public static final String KEY_NGADMIT = "NgAdmission";
	public static final String KEY_FOLEYADMIT = "FoleyAdmission";
	public static final String KEY_FALLRISK = "FallRisk";
	public static final String KEY_MOTIVATIONADMIT = "MotivationAdmission";
	public static final String KEY_OTHER = "OtherHistory";
	public static final String KEY_COGNITION = "Cognition";

	public static final String KEY_FIRSTOT = "FirstOTAssessment";
	public static final String KEY_TOTALOT = "TotalOTSessions";
	public static final String KEY_FIRSTSWALLOW = "FirstSwallowAssessment";
	public static final String KEY_FIRSTPT = "FirstPTAssessment";
	public static final String KEY_TOTALPT = "TotalPTSessions";
	public static final String KEY_FIRSTSLT = "FirstSLTAssessment";
	public static final String KEY_TOTALSLT = "TotalSLTSessions";

	// TODO: Setup your data fields here:
	public static Map<String, String> dataMap;


	// DB info: its name, and the tables we are using
	public static final String DATABASE_NAME = "PatientMonitoringDb";
	public static final String PATIENT_TABLE = "patientTable";
	public static final String DATA_TABLE = "dataTable";

	// Track DB version if a new version of your app changes the format.
	public static final int DATABASE_VERSION = 24;


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

		dataMap.put("KEY_CNS_CONSCIOUSNESS", "CnsConsciousness");
		dataMap.put("KEY_CNS_ORIENTATION", "CnsOrientation");
		dataMap.put("KEY_CNS_SPEECH", "CnsSpeech");
		dataMap.put("KEY_CNS_FACE1", "CnsFace1");
		dataMap.put("KEY_CNS_UPPER_LIMB_PROXIMAL", "CnsUpperLimbProximal");
		dataMap.put("KEY_CNS_UPPER_LIMB_DISTAL", "CnsUpperLimbDistal");
		dataMap.put("KEY_CNS_LOWER_LIMB_PROXIMAL", "CnsLowerLimbProximal");
		dataMap.put("KEY_CNS_LOWER_LIMB_DISTAL", "CnsLowerLimbDistal");
		dataMap.put("KEY_CNS_UPPER_LIMBS", "CnsUpperLimbs");
		dataMap.put("KEY_CNS_LOWER_LIMBS", "CnsLowerLimbs");
		dataMap.put("KEY_CNS_FACE2", "CnsFace2");

		dataMap.put("KEY_BARTHEL", "EstBarthelScore");
		dataMap.put("KEY_BERG", "EstBergScore");
		dataMap.put("KEY_CNS", "CnsScore");
		dataMap.put("KEY_NIHSS", "NihssScore");

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
	
	// Add a new set of values to the database.
	public long insertRowPatient(String firstName, String lastName, String hospitalId, String admissionDate, String discharged, String dischargeDate, String patientAge, String patientGender, String firstLanguage,
								 String mocaScore, String customScore, String customMax,
								 String strokeType, String firstStroke, String lesionSide, String hemiplegiaSide, String consciousness, String orientation, String language, String visualImpairment, String hearingAid, String hearingAssessed, String aphasia,
								 String peg, String ng, String foley, String fallRisk, String motivation, String otherHistory, String cognition,
								 String dateFirstOT, String totalOT, String dateFirstSwallow, String dateFirstPT, String totalPT, String dateFirstSLT, String totalSLT) {
		/*
		 * CHANGE 3:
		 */		
		// TODO: Update data in the row with new fields.
		// TODO: Also change the function's arguments to be what you need!
		// Create row's data:
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_FIRSTNAME, firstName);
		initialValues.put(KEY_LASTNAME, lastName);
		initialValues.put(KEY_HOSPITALID, hospitalId);
		initialValues.put(KEY_ADMISSIONDATE, admissionDate);
		initialValues.put(KEY_DISCHARGED, discharged);
		initialValues.put(KEY_DISCHARGEDATE, dischargeDate);
		initialValues.put(KEY_PATIENTAGE, patientAge);
		initialValues.put(KEY_PATIENTGENDER, patientGender);
		initialValues.put(KEY_FIRSTLANGUAGE, firstLanguage);

		initialValues.put(KEY_MOCASCORE, mocaScore);
		initialValues.put(KEY_CUSTOMSCORE, customScore);
		initialValues.put(KEY_CUSTOMMAX, customMax);

		initialValues.put(KEY_STROKETYPE, strokeType);
		initialValues.put(KEY_FIRSTSTROKE, firstStroke);
		initialValues.put(KEY_LESIONSIDE, lesionSide);
		initialValues.put(KEY_HEMIPLEGIASIDE, hemiplegiaSide);
		initialValues.put(KEY_CONSCIOUSNESS, consciousness);
		initialValues.put(KEY_ORIENTATION, orientation);
		initialValues.put(KEY_LANGUAGE, language);
		initialValues.put(KEY_VISUAL, visualImpairment);
		initialValues.put(KEY_HEARINGAID, hearingAid);
		initialValues.put(KEY_HEARINGASSESSED, hearingAssessed);
		initialValues.put(KEY_APHASIA, aphasia);

		initialValues.put(KEY_PEGADMIT, peg);
		initialValues.put(KEY_NGADMIT, ng);
		initialValues.put(KEY_FOLEYADMIT, foley);
		initialValues.put(KEY_FALLRISK, fallRisk);
		initialValues.put(KEY_MOTIVATIONADMIT, motivation);
		initialValues.put(KEY_OTHER, otherHistory);
		initialValues.put(KEY_COGNITION, cognition);

		initialValues.put(KEY_FIRSTOT, dateFirstOT);
		initialValues.put(KEY_TOTALOT, totalOT);
		initialValues.put(KEY_FIRSTSWALLOW, dateFirstSwallow);
		initialValues.put(KEY_FIRSTPT, dateFirstPT);
		initialValues.put(KEY_TOTALPT, totalPT);
		initialValues.put(KEY_FIRSTSLT, dateFirstSLT);
		initialValues.put(KEY_TOTALSLT, totalSLT);
		
		// Insert it into the database.
		return db.insert(PATIENT_TABLE, null, initialValues);
	}

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

	// Change an existing row to be equal to new data.
	public boolean updateRowPatient(long rowId, String firstName, String lastName, String hospitalId, String admissionDate, String discharged, String dischargeDate, String patientAge, String patientGender, String firstLanguage,
									String mocaScore, String customScore, String customMax,
									String strokeType, String firstStroke, String lesionSide, String hemiplegiaSide, String consciousness, String orientation, String language, String visualImpairment, String hearingAid, String hearingAssessed, String aphasia,
									String peg, String ng, String foley, String fallRisk, String motivation, String otherHistory, String cognition,
									String dateFirstOT, String totalOT, String dateFirstSwallow, String dateFirstPT, String totalPT, String dateFirstSLT, String totalSLT) {
		String where = KEY_ROWID + "=" + rowId;

		/*
		 * CHANGE 4:
		 */
		// TODO: Update data in the row with new fields.
		// TODO: Also change the function's arguments to be what you need!
		// Create row's data:
		ContentValues newValues = new ContentValues();
		newValues.put(KEY_FIRSTNAME, firstName);
		newValues.put(KEY_LASTNAME, lastName);
		newValues.put(KEY_HOSPITALID, hospitalId);
		newValues.put(KEY_ADMISSIONDATE, admissionDate);
		newValues.put(KEY_DISCHARGED, discharged);
		newValues.put(KEY_DISCHARGEDATE, dischargeDate);
		newValues.put(KEY_PATIENTAGE, patientAge);
		newValues.put(KEY_PATIENTGENDER, patientGender);
		newValues.put(KEY_FIRSTLANGUAGE, firstLanguage);

		newValues.put(KEY_MOCASCORE, mocaScore);
		newValues.put(KEY_CUSTOMSCORE, customScore);
		newValues.put(KEY_CUSTOMMAX, customMax);

		newValues.put(KEY_STROKETYPE, strokeType);
		newValues.put(KEY_FIRSTSTROKE, firstStroke);
		newValues.put(KEY_LESIONSIDE, lesionSide);
		newValues.put(KEY_HEMIPLEGIASIDE, hemiplegiaSide);
		newValues.put(KEY_CONSCIOUSNESS, consciousness);
		newValues.put(KEY_ORIENTATION, orientation);
		newValues.put(KEY_LANGUAGE, language);
		newValues.put(KEY_VISUAL, visualImpairment);
		newValues.put(KEY_HEARINGAID, hearingAid);
		newValues.put(KEY_HEARINGASSESSED, hearingAssessed);
		newValues.put(KEY_APHASIA, aphasia);

		newValues.put(KEY_PEGADMIT, peg);
		newValues.put(KEY_NGADMIT, ng);
		newValues.put(KEY_FOLEYADMIT, foley);
		newValues.put(KEY_FALLRISK, fallRisk);
		newValues.put(KEY_MOTIVATIONADMIT, motivation);
		newValues.put(KEY_OTHER, otherHistory);
		newValues.put(KEY_COGNITION, cognition);

		newValues.put(KEY_FIRSTOT, dateFirstOT);
		newValues.put(KEY_TOTALOT, totalOT);
		newValues.put(KEY_FIRSTSWALLOW, dateFirstSwallow);
		newValues.put(KEY_FIRSTPT, dateFirstPT);
		newValues.put(KEY_TOTALPT, totalPT);
		newValues.put(KEY_FIRSTSLT, dateFirstSLT);
		newValues.put(KEY_TOTALSLT, totalSLT);
		
		// Insert it into the database.
		return db.update(PATIENT_TABLE, newValues, where, null) != 0;
	}


	public Cursor dischargeRowPatient(int rowId, String dischargeDate, String mocaScore, String customScore, String customMax, String totalOT, String totalPT, String totalSLT){
		String dischargedString = "Yes";
		String updateQuery = "UPDATE " + PATIENT_TABLE + " SET " + KEY_DISCHARGED + " = ?, " + KEY_DISCHARGEDATE + " = ?, "
				+ KEY_MOCASCORE + " = ?, " + KEY_CUSTOMSCORE + " = ?, " + KEY_CUSTOMMAX + " = ?, "
				+ KEY_TOTALOT + " = ?, " + KEY_TOTALPT + " = ?, " + KEY_TOTALSLT + " = ? WHERE " + KEY_ROWID + " = " + rowId;

		Cursor c =  db.rawQuery(updateQuery, new String[]{dischargedString,dischargeDate,mocaScore,customScore,customMax,totalOT,totalPT,totalSLT});
		if (c != null) {
			c.moveToFirst();
		}
		return c;
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
