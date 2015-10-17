// ------------------------------------ DBADapter.java ---------------------------------------------

// TODO: Change the package to match your project.
package com.physiotherapy.mcgill.patientmonitoring;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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

//	public static final String KEY_PARENTID = "ParentID";
//	public static final String KEY_MRN = "MRNnumber";
//	public static final String KEY_DAY = "Day";
//
//	//Nurse
//	public static final String KEY_PEG = "PEG";
//	public static final String KEY_NG = "NG";
//	public static final String KEY_O2 = "O2";
//	public static final String KEY_IV = "IV";
//	public static final String KEY_CPAP = "CPAP";
//	public static final String KEY_RESTRAINT = "Restraint";
//	public static final String KEY_BEDBARS = "BedBars";
//	public static final String KEY_BEHAVIOURAL = "BehaviouralIssue";
//	public static final String KEY_CONFUSION = "Confusion";
//	public static final String KEY_BLADDER = "BladderControl";
//	public static final String KEY_HOURS = "EstimatedHoursOutOfBed";
//
//	//OT
//	public static final String KEY_NEGLECT = "Neglect";
//	public static final String KEY_DIGITSPAN = "DigitSpan";
//	public static final String KEY_MMSE = "MMSE";
//	public static final String KEY_FOLLOWS = "FollowsCommands";
//	public static final String KEY_VERBAL = "VerbalCommunication";
//	public static final String KEY_MOTIVATION = "Motivation";
//	public static final String KEY_MOOD = "Mood";
//	public static final String KEY_PAIN = "Pain";
//	public static final String KEY_FATIGUE = "Fatigue";
//	public static final String KEY_SWALLOW = "Swallow";
//	public static final String KEY_FEEDING = "Feeding";
//	public static final String KEY_DRESSING = "Dressing";
//	public static final String KEY_KITCHEN = "Kitchen";
//
//	//PT
//	public static final String KEY_LEFTARM = "LeftArmMovement";
//	public static final String KEY_RIGHTARM = "RightArmMovement";
//	public static final String KEY_MOVEMENTBED = "MovementInBed";
//	public static final String KEY_LIESIT = "LieToSit";
//	public static final String KEY_SITTING = "SittingUnsupported";
//	public static final String KEY_SITSTAND = "SitToStand";
//	public static final String KEY_STAND = "StandUnsupported";
//	public static final String KEY_LIFTSUNAFFECTED = "LiftsUnaffectedLegStanding";
//	public static final String KEY_LIFTSAFFECTED = "LiftsAffectedLegStanding";
//	public static final String KEY_WALKING = "Walking";
//
//	//CNS
//	public static final String KEY_CNS_CONSCIOUSNESS = "CnsConsciousness";
//	public static final String KEY_CNS_ORIENTATION = "CnsOrientation";
//	public static final String KEY_CNS_SPEECH = "CnsSpeech";
//	public static final String KEY_CNS_FACE1 = "CnsFace1";
//	public static final String KEY_CNS_UPPER_LIMB_PROXIMAL = "CnsUpperLimbProximal";
//	public static final String KEY_CNS_UPPER_LIMB_DISTAL = "CnsUpperLimbDistal";
//	public static final String KEY_CNS_LOWER_LIMB_PROXIMAL = "CnsLowerLimbProximal";
//	public static final String KEY_CNS_LOWER_LIMB_DISTAL = "CnsLowerLimbDistal";
//	public static final String KEY_CNS_UPPER_LIMBS = "CnsUpperLimbs";
//	public static final String KEY_CNS_LOWER_LIMBS = "CnsLowerLimbs";
//	public static final String KEY_CNS_FACE2 = "CnsFace2";
//
//
//	//Scores
//	public static final String KEY_BARTHEL = "EstBarthelScore";
//	public static final String KEY_BERG = "EstBergScore";
//	public static final String KEY_CNS = "CnsScore";
//	public static final String KEY_NIHSS = "NihssScore";

	
	// DB info: its name, and the tables we are using
	public static final String DATABASE_NAME = "PatientMonitoringDb";
	public static final String PATIENT_TABLE = "patientTable";
	public static final String DATA_TABLE = "dataTable";

	// Track DB version if a new version of your app changes the format.
	public static final int DATABASE_VERSION = 23;


	//Table Create Statements

	//Patient create statement
	private static final String PATIENT_CREATE_SQL =
			"create table " + PATIENT_TABLE
			+ " (" + KEY_ROWID + " integer primary key autoincrement, "
			
			/*
			 * CHANGE 2:
			 */
			// TODO: Place your fields here!
			// + KEY_{...} + " {type} not null"
			//	- Key is the column name you created above.
			//	- {type} is one of: text, integer, real, blob
			//		(http://www.sqlite.org/datatype3.html)
			//  - "not null" means it is a required field (must be given a value).
			// NOTE: All must be comma separated (end of line!) Last one must have NO comma!!
			+ KEY_FIRSTNAME + " text not null, "
			+ KEY_LASTNAME + " text not null, "
			+ KEY_HOSPITALID + " text not null, "
			+ KEY_ADMISSIONDATE + " text not null, "
			+ KEY_DISCHARGED + " text not null, "
			+ KEY_DISCHARGEDATE + " text not null, "
			+ KEY_PATIENTAGE + " text not null, "
			+ KEY_PATIENTGENDER + " text not null, "
			+ KEY_FIRSTLANGUAGE + " text not null, "

			+ KEY_MOCASCORE + " text not null, "
			+ KEY_CUSTOMSCORE + " text not null, "
			+ KEY_CUSTOMMAX + " text not null, "

			+ KEY_STROKETYPE + " text not null, "
			+ KEY_FIRSTSTROKE + " text not null, "
			+ KEY_LESIONSIDE + " text not null, "
			+ KEY_HEMIPLEGIASIDE + " text not null, "
			+ KEY_CONSCIOUSNESS + " text not null, "
			+ KEY_ORIENTATION + " text not null, "
			+ KEY_LANGUAGE + " text not null, "
			+ KEY_VISUAL + " text not null, "
			+ KEY_HEARINGAID + " text not null, "
			+ KEY_HEARINGASSESSED + " text not null, "
			+ KEY_APHASIA + " text not null, "

			+ KEY_PEGADMIT + " text not null, "
			+ KEY_NGADMIT + " text not null, "
			+ KEY_FOLEYADMIT + " text not null, "
			+ KEY_FALLRISK + " text not null, "
			+ KEY_MOTIVATIONADMIT + " text not null, "
			+ KEY_OTHER + " text not null, "
			+ KEY_COGNITION + " text not null, "

			+ KEY_FIRSTOT + " text not null, "
			+ KEY_TOTALOT + " text not null, "
			+ KEY_FIRSTSWALLOW + " text not null, "
			+ KEY_FIRSTPT + " text not null, "
			+ KEY_TOTALPT + " text not null, "
			+ KEY_FIRSTSLT + " text not null, "
			+ KEY_TOTALSLT + " text not null"
			// Rest  of creation:
			+ ");";

//	private static final String DATA_CREATE_SQL =
//			"create table " + DATA_TABLE
//					+ " (" + KEY_ROWID + " integer primary key autoincrement, "
//
//			/*
//			 * CHANGE 2:
//			 */
//			// TODO: Place your fields here!
//			// + KEY_{...} + " {type} not null"
//			//	- Key is the column name you created above.
//			//	- {type} is one of: text, integer, real, blob
//			//		(http://www.sqlite.org/datatype3.html)
//			//  - "not null" means it is a required field (must be given a value).
//			// NOTE: All must be comma separated (end of line!) Last one must have NO comma!
//			+ KEY_PARENTID + " integer not null, "
//			+ KEY_MRN + " text not null, "
//			+ KEY_DAY + " integer not null, "
//
//			+ KEY_PEG + " text, "
//			+ KEY_NG + " text, "
//			+ KEY_O2 + " text, "
//			+ KEY_IV + " text, "
//			+ KEY_CPAP + " text, "
//			+ KEY_RESTRAINT + " text, "
//			+ KEY_BEDBARS + " text, "
//			+ KEY_BEHAVIOURAL + " text, "
//			+ KEY_CONFUSION + " text, "
//			+ KEY_BLADDER + " text, "
//			+ KEY_HOURS + " text, "
//
//			+ KEY_NEGLECT + " text, "
//			+ KEY_DIGITSPAN + " text, "
//			+ KEY_MMSE + " text, "
//			+ KEY_FOLLOWS + " text, "
//			+ KEY_VERBAL + " text, "
//			+ KEY_MOTIVATION + " text, "
//			+ KEY_MOOD + " text, "
//			+ KEY_PAIN + " text, "
//			+ KEY_FATIGUE + " text, "
//			+ KEY_SWALLOW + " text, "
//			+ KEY_FEEDING + " text, "
//			+ KEY_DRESSING + " text, "
//			+ KEY_KITCHEN + " text, "
//
//			+ KEY_LEFTARM + " text, "
//			+ KEY_RIGHTARM + " text, "
//			+ KEY_MOVEMENTBED + " text, "
//			+ KEY_LIESIT + " text, "
//			+ KEY_SITTING + " text, "
//			+ KEY_SITSTAND + " text, "
//			+ KEY_STAND + " text, "
//			+ KEY_LIFTSUNAFFECTED + " text, "
//			+ KEY_LIFTSAFFECTED + " text, "
//			+ KEY_WALKING + " text, "
//
//			+ KEY_BARTHEL + " text, "
//			+ KEY_BERG + " text, "
//
//			+ KEY_CNS_CONSCIOUSNESS + " text, "
//			+ KEY_CNS_ORIENTATION + " text, "
//			+ KEY_CNS_SPEECH + " text, "
//			+ KEY_CNS_FACE1 + " text, "
//			+ KEY_CNS_UPPER_LIMB_PROXIMAL + " text, "
//			+ KEY_CNS_UPPER_LIMB_DISTAL + " text, "
//			+ KEY_CNS_LOWER_LIMB_PROXIMAL + " text, "
//			+ KEY_CNS_LOWER_LIMB_DISTAL + " text, "
//			+ KEY_CNS_UPPER_LIMBS + " text, "
//			+ KEY_CNS_LOWER_LIMBS + " text, "
//			+ KEY_CNS_FACE2 + " text, "
//
//			+ KEY_CNS + " text, "
//			+ KEY_NIHSS + " text"
//
//					// Rest  of creation:
//			+ ");";

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

		generateCreateDataString();

		db = myDBHelper.getWritableDatabase();

		return this;
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
			//_db.execSQL("DROP TABLE IF EXISTS " + PATIENT_TABLE);
			//_db.execSQL("DROP TABLE IF EXISTS " + DATA_TABLE);

			//_db.execSQL("ALTER TABLE " + DATA_TABLE + " ADD COLUMN " + KEY_CNS_CONSCIOUSNESS + " text");
//			_db.execSQL("ALTER TABLE " + DATA_TABLE + " ADD COLUMN " + KEY_CNS_ORIENTATION + " text");
//			_db.execSQL("ALTER TABLE " + DATA_TABLE + " ADD COLUMN " + KEY_CNS_SPEECH + " text");
//			_db.execSQL("ALTER TABLE " + DATA_TABLE + " ADD COLUMN " + KEY_CNS_FACE1 + " text");
//			_db.execSQL("ALTER TABLE " + DATA_TABLE + " ADD COLUMN " + KEY_CNS_UPPER_LIMB_PROXIMAL + " text");
//			_db.execSQL("ALTER TABLE " + DATA_TABLE + " ADD COLUMN " + KEY_CNS_UPPER_LIMB_DISTAL + " text");
//			_db.execSQL("ALTER TABLE " + DATA_TABLE + " ADD COLUMN " + KEY_CNS_LOWER_LIMB_PROXIMAL + " text");
//			_db.execSQL("ALTER TABLE " + DATA_TABLE + " ADD COLUMN " + KEY_CNS_LOWER_LIMB_DISTAL + " text");
//			_db.execSQL("ALTER TABLE " + DATA_TABLE + " ADD COLUMN " + KEY_CNS_UPPER_LIMBS + " text");
//			_db.execSQL("ALTER TABLE " + DATA_TABLE + " ADD COLUMN " + KEY_CNS_LOWER_LIMBS + " text");
//			_db.execSQL("ALTER TABLE " + DATA_TABLE + " ADD COLUMN " + KEY_CNS_FACE2 + " text");
//
//			_db.execSQL("ALTER TABLE " + DATA_TABLE + " ADD COLUMN " + KEY_CNS + " text");
//			_db.execSQL("ALTER TABLE " + DATA_TABLE + " ADD COLUMN " + KEY_NIHSS + " text");
			
			// Recreate new database:
			//onCreate(_db);
		}
	}
}
