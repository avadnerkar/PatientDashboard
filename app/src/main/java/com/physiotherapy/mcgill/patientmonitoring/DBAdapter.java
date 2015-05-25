// ------------------------------------ DBADapter.java ---------------------------------------------

// TODO: Change the package to match your project.
package com.physiotherapy.mcgill.patientmonitoring;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
	public static final int COL_ROWID = 0;


	// TODO: Setup your  patient fields here:
	public static final String KEY_FIRSTNAME = "FirstName";
	public static final String KEY_LASTNAME = "LastName";
	public static final String KEY_HOSPITALID = "MRN";
	public static final String KEY_ADMISSIONDATE = "AdmissionDate";

	// TODO: Setup your data fields here:
	public static final String KEY_PARENTID = "ParentID";
	public static final String KEY_MRN = "MRNnumber";
	public static final String KEY_DAY = "Day";

	//Nurse
	public static final String KEY_PEG = "PEG";
	public static final String KEY_NG = "NG";
	public static final String KEY_O2 = "O2";
	public static final String KEY_IV = "IV";
	public static final String KEY_FOLEY = "FOLEY";
	public static final String KEY_CPAP = "CPAP";
	public static final String KEY_RESTRAINT = "Restraint";
	public static final String KEY_BEHAVIOURAL = "BehaviouralIssue";
	public static final String KEY_CONFUSION = "Confusion";
	public static final String KEY_BLADDER = "BladderControl";
	public static final String KEY_HOURS = "EstimatedHoursOutOfBed";

	//OT
	public static final String KEY_NEGLECT = "Neglect";
	public static final String KEY_DIGITSPAN = "DigitSpan";
	public static final String KEY_MMSE = "MMSE";
	public static final String KEY_FOLLOWS = "FollowsCommands";
	public static final String KEY_VERBAL = "VerbalCommunication";
	public static final String KEY_MOTIVATION = "Motivation";
	public static final String KEY_MOOD = "Mood";
	public static final String KEY_PAIN = "Pain";
	public static final String KEY_FATIGUE = "Fatigue";
	public static final String KEY_SWALLOW = "Swallow";
	public static final String KEY_FEEDING = "Feeding";
	public static final String KEY_DRESSING = "Dressing";
	public static final String KEY_KITCHEN = "Kitchen";

	//PT
	public static final String KEY_LEFTARM = "LeftArmMovement";
	public static final String KEY_RIGHTARM = "RightArmMovement";
	public static final String KEY_MOVEMENTBED = "MovementInBed";
	public static final String KEY_LIESIT = "LieToSit";
	public static final String KEY_SITTING = "SittingUnsupported";
	public static final String KEY_SITSTAND = "SitToStand";
	public static final String KEY_STAND = "StandUnsupported";
	public static final String KEY_LIFTSUNAFFECTED = "LiftsUnaffectedLegStanding";
	public static final String KEY_LIFTSAFFECTED = "LiftsAffectedLegStanding";
	public static final String KEY_WALKING = "Walking";
	
	// TODO: Setup your patient field numbers here (0 = KEY_ROWID, 1=...)
	public static final int COL_FIRSTNAME = 1;
	public static final int COL_LASTNAME = 2;
	public static final int COL_HOSPITALID = 3;
	public static final int COL_ADMISSIONDATE = 4;

	// TODO: Setup your data field numbers here (0 = KEY_ROWID, 1=...)
	public static final int COL_PARENTID = 1;
	public static final int COL_MRN = 2;
	public static final int COL_DAY = 3;

	//Nurse
	public static final int COL_PEG = 4;
	public static final int COL_NG = 5;
	public static final int COL_O2 = 6;
	public static final int COL_IV = 7;
	public static final int COL_FOLEY = 8;
	public static final int COL_CPAP = 9;
	public static final int COL_RESTRAINT = 10;
	public static final int COL_BEHAVIOURAL = 11;
	public static final int COL_CONFUSION = 12;
	public static final int COL_BLADDER = 13;
	public static final int COL_HOURS = 14;

	//OT
	public static final int COL_NEGLECT = 15;
	public static final int COL_DIGITSPAN = 16;
	public static final int COL_MMSE = 17;
	public static final int COL_FOLLOWS = 18;
	public static final int COL_VERBAL = 19;
	public static final int COL_MOTIVATION = 20;
	public static final int COL_MOOD = 21;
	public static final int COL_PAIN = 22;
	public static final int COL_FATIGUE = 23;
	public static final int COL_SWALLOW = 24;
	public static final int COL_FEEDING = 25;
	public static final int COL_DRESSING = 26;
	public static final int COL_KITCHEN = 27;

	//PT
	public static final int COL_LEFTARM = 28;
	public static final int COL_RIGHTARM = 29;
	public static final int COL_MOVEMENTBED = 30;
	public static final int COL_LIESIT = 31;
	public static final int COL_SITTING = 32;
	public static final int COL_SITSTAND = 33;
	public static final int COL_STAND = 34;
	public static final int COL_LIFTSUNAFFECTED = 35;
	public static final int COL_LIFTSAFFECTED = 36;
	public static final int COL_WALKING = 37;


	// TODO: Set all keys for patient table
	public static final String[] ALL_PATIENT_KEYS = new String[] {KEY_ROWID, KEY_FIRSTNAME, KEY_LASTNAME, KEY_HOSPITALID, KEY_ADMISSIONDATE};

	//TODO: Set all keys for data table
	public static final String[] ALL_DATA_KEYS = new String[] {KEY_ROWID, KEY_PARENTID, KEY_MRN, KEY_DAY,
			KEY_PEG, KEY_NG, KEY_O2, KEY_IV, KEY_FOLEY, KEY_CPAP, KEY_RESTRAINT, KEY_BEHAVIOURAL, KEY_CONFUSION, KEY_BLADDER, KEY_HOURS,
			KEY_NEGLECT, KEY_DIGITSPAN, KEY_MMSE, KEY_FOLLOWS, KEY_VERBAL, KEY_MOTIVATION, KEY_MOOD, KEY_PAIN, KEY_FATIGUE, KEY_SWALLOW, KEY_FEEDING, KEY_DRESSING, KEY_KITCHEN,
			KEY_LEFTARM, KEY_RIGHTARM, KEY_MOVEMENTBED, KEY_LIESIT, KEY_SITTING, KEY_SITSTAND, KEY_STAND, KEY_LIFTSUNAFFECTED, KEY_LIFTSAFFECTED, KEY_WALKING};
	
	// DB info: its name, and the tables we are using
	public static final String DATABASE_NAME = "PatientMonitoringDb";
	public static final String PATIENT_TABLE = "patientTable";
	public static final String DATA_TABLE = "dataTable";

	// Track DB version if a new version of your app changes the format.
	public static final int DATABASE_VERSION = 14;


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
			+ KEY_ADMISSIONDATE + " text not null"
			
			// Rest  of creation:
			+ ");";

	private static final String DATA_CREATE_SQL =
			"create table " + DATA_TABLE
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
			// NOTE: All must be comma separated (end of line!) Last one must have NO comma!
			+ KEY_PARENTID + " integer not null, "
			+ KEY_MRN + " text not null, "
			+ KEY_DAY + " integer not null, "

			+ KEY_PEG + " text not null, "
			+ KEY_NG + " text not null, "
			+ KEY_O2 + " text not null, "
			+ KEY_IV + " text not null, "
			+ KEY_FOLEY + " text not null, "
			+ KEY_CPAP + " text not null, "
			+ KEY_RESTRAINT + " text not null, "
			+ KEY_BEHAVIOURAL + " text not null, "
			+ KEY_CONFUSION + " text not null, "
			+ KEY_BLADDER + " text not null, "
			+ KEY_HOURS + " text not null, "

			+ KEY_NEGLECT + " text not null, "
			+ KEY_DIGITSPAN + " text not null, "
			+ KEY_MMSE + " text not null, "
			+ KEY_FOLLOWS + " text not null, "
			+ KEY_VERBAL + " text not null, "
			+ KEY_MOTIVATION + " text not null, "
			+ KEY_MOOD + " text not null, "
			+ KEY_PAIN + " text not null, "
			+ KEY_FATIGUE + " text not null, "
			+ KEY_SWALLOW + " text not null, "
			+ KEY_FEEDING + " text not null, "
			+ KEY_DRESSING + " text not null, "
			+ KEY_KITCHEN + " text not null, "

			+ KEY_LEFTARM + " text not null, "
			+ KEY_RIGHTARM + " text not null, "
			+ KEY_MOVEMENTBED + " text not null, "
			+ KEY_LIESIT + " text not null, "
			+ KEY_SITTING + " text not null, "
			+ KEY_SITSTAND + " text not null, "
			+ KEY_STAND + " text not null, "
			+ KEY_LIFTSUNAFFECTED + " text not null, "
			+ KEY_LIFTSAFFECTED + " text not null, "
			+ KEY_WALKING + " text not null"

					// Rest  of creation:
			+ ");";
	
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
		db = myDBHelper.getWritableDatabase();
		return this;
	}
	
	// Close the database connection.
	public void close() {
		myDBHelper.close();
	}

	/////////////////////////////////////////////////////////////////////
	//	Patient methods:
	/////////////////////////////////////////////////////////////////////
	
	// Add a new set of values to the database.
	public long insertRowPatient(String firstName, String lastName, String hospitalId, String admissionDate) {
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
		}
		c.close();

		deleteQuery = "DELETE FROM " + DATA_TABLE + " WHERE " + KEY_PARENTID + " = " + currentPatientId;
		c =  db.rawQuery(deleteQuery, null);
		if (c != null) {
			c.moveToFirst();
		}
		c.close();
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
		Cursor c = 	db.query(true, PATIENT_TABLE, ALL_PATIENT_KEYS,
							where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	// Get a specific row (by rowId)
	public Cursor getRowPatient(long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		Cursor c = 	db.query(true, PATIENT_TABLE, ALL_PATIENT_KEYS,
						where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}
	
	// Change an existing row to be equal to new data.
	public boolean updateRowPatient(long rowId, String firstName, String lastName, String hospitalId, String admissionDate) {
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
		
		// Insert it into the database.
		return db.update(PATIENT_TABLE, newValues, where, null) != 0;
	}

	/////////////////////////////////////////////////////////////////////
	//	Data methods:
	/////////////////////////////////////////////////////////////////////

	// Add a new set of values to the database.
	public long insertRowData(int parentId, String mrnNumber, int day,
							  String peg, String ng, String o2, String iv, String foley, String cpap, String restraint, String behavioural, String confusion, String bladder, String hours,
							  String neglect, String digitspan, String mmse, String follows, String verbal, String motivation, String mood, String pain, String fatigue, String swallow, String feeding, String dressing, String kitchen,
							  String leftarm, String rightarm, String movementbed, String liesit, String sitting, String sitstand, String stand, String liftsunaffected, String liftsaffected, String walking) {
		/*
		 * CHANGE 3:
		 */
		// TODO: Update data in the row with new fields.
		// TODO: Also change the function's arguments to be what you need!
		// Create row's data:
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_PARENTID, parentId);
		initialValues.put(KEY_MRN, mrnNumber);
		initialValues.put(KEY_DAY, day);

		initialValues.put(KEY_PEG, peg);
		initialValues.put(KEY_NG, ng);
		initialValues.put(KEY_O2, o2);
		initialValues.put(KEY_IV, iv);
		initialValues.put(KEY_FOLEY, foley);
		initialValues.put(KEY_CPAP, cpap);
		initialValues.put(KEY_RESTRAINT, restraint);
		initialValues.put(KEY_BEHAVIOURAL, behavioural);
		initialValues.put(KEY_CONFUSION, confusion);
		initialValues.put(KEY_BLADDER, bladder);
		initialValues.put(KEY_HOURS, hours);

		initialValues.put(KEY_NEGLECT, neglect);
		initialValues.put(KEY_DIGITSPAN, digitspan);
		initialValues.put(KEY_MMSE, mmse);
		initialValues.put(KEY_FOLLOWS, follows);
		initialValues.put(KEY_VERBAL, verbal);
		initialValues.put(KEY_MOTIVATION, motivation);
		initialValues.put(KEY_MOOD, mood);
		initialValues.put(KEY_PAIN, pain);
		initialValues.put(KEY_FATIGUE, fatigue);
		initialValues.put(KEY_SWALLOW, swallow);
		initialValues.put(KEY_FEEDING, feeding);
		initialValues.put(KEY_DRESSING, dressing);
		initialValues.put(KEY_KITCHEN, kitchen);

		initialValues.put(KEY_LEFTARM, leftarm);
		initialValues.put(KEY_RIGHTARM, rightarm);
		initialValues.put(KEY_MOVEMENTBED, movementbed);
		initialValues.put(KEY_LIESIT, liesit);
		initialValues.put(KEY_SITTING, sitting);
		initialValues.put(KEY_SITSTAND, sitstand);
		initialValues.put(KEY_STAND, stand);
		initialValues.put(KEY_LIFTSUNAFFECTED, liftsunaffected);
		initialValues.put(KEY_LIFTSAFFECTED, liftsaffected);
		initialValues.put(KEY_WALKING, walking);


		// Insert it into the database.
		return db.insert(DATA_TABLE, null, initialValues);
	}

	// Delete a row from the database, by rowId (primary key)
	public boolean deleteRowData(long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		return db.delete(DATA_TABLE, where, null) != 0;
	}

	public void deleteAllData() {
		Cursor c = getAllRowData();
		long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
		if (c.moveToFirst()) {
			do {
				deleteRowData(c.getLong((int) rowId));
			} while (c.moveToNext());
		}
		c.close();
	}

	// Return all data in the database.
	public Cursor getAllRowData() {
		String where = null;
		Cursor c = 	db.query(true, DATA_TABLE, ALL_DATA_KEYS,
				where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	// Get a specific row (by rowId)
	public Cursor getRowData(int patientId, int currentDay) {
		String selectQuery = "SELECT * FROM " + DATA_TABLE + " dt WHERE dt." + KEY_PARENTID + " = " + patientId + " AND dt." + KEY_DAY + " = " + currentDay;

		Cursor c = 	db.rawQuery(selectQuery, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	// Change an existing row to be equal to new data.
	public Cursor updateRowData(int parentId, int day,
								String peg, String ng, String o2, String iv, String foley, String cpap, String restraint, String behavioural, String confusion, String bladder, String hours,
								String neglect, String digitspan, String mmse, String follows, String verbal, String motivation, String mood, String pain, String fatigue, String swallow, String feeding, String dressing, String kitchen,
								String leftarm, String rightarm, String movementbed, String liesit, String sitting, String sitstand, String stand, String liftsunaffected, String liftsaffected, String walking) {
		//String where = KEY_ROWID + "=" + rowId;

		/*
		 * CHANGE 4:
		 */
		// TODO: Update data in the row with new fields.
		// TODO: Also change the function's arguments to be what you need!
		// Create row's data:
		//ContentValues newValues = new ContentValues();
		//newValues.put(KEY_PARENTID, parentId);
		//newValues.put(KEY_DAY, day);
		//newValues.put(KEY_PEG, peg);
		//newValues.put(KEY_NG, ng);
		//newValues.put(KEY_O2, o2);

		String updateQuery = "UPDATE " + DATA_TABLE + " SET " +
				KEY_PEG + " = '" + peg + "', " +
				KEY_NG + " = '" + ng + "', " +
				KEY_O2 + " = '" + o2 + "', " +
				KEY_IV + " = '" + iv + "', " +
				KEY_FOLEY + " = '" + foley + "', " +
				KEY_CPAP + " = '" + cpap + "', " +
				KEY_RESTRAINT + " = '" + restraint + "', " +
				KEY_BEHAVIOURAL + " = '" + behavioural + "', " +
				KEY_CONFUSION + " = '" + confusion + "', " +
				KEY_BLADDER + " = '" + bladder + "', " +
				KEY_HOURS + " = '" + hours + "', " +

				KEY_NEGLECT + " = '" + neglect + "', " +
				KEY_DIGITSPAN + " = '" + digitspan + "', " +
				KEY_MMSE + " = '" + mmse + "', " +
				KEY_FOLLOWS + " = '" + follows + "', " +
				KEY_VERBAL + " = '" + verbal + "', " +
				KEY_MOTIVATION + " = '" + motivation + "', " +
				KEY_MOOD + " = '" + mood + "', " +
				KEY_PAIN + " = '" + pain + "', " +
				KEY_FATIGUE + " = '" + fatigue + "', " +
				KEY_SWALLOW + " = '" + swallow + "', " +
				KEY_FEEDING + " = '" + feeding + "', " +
				KEY_DRESSING + " = '" + dressing + "', " +
				KEY_KITCHEN + " = '" + kitchen + "', " +

				KEY_LEFTARM + " = '" + leftarm + "', " +
				KEY_RIGHTARM + " = '" + rightarm + "', " +
				KEY_MOVEMENTBED + " = '" + movementbed + "', " +
				KEY_LIESIT + " = '" + liesit + "', " +
				KEY_SITTING + " = '" + sitting + "', " +
				KEY_SITSTAND + " = '" + sitstand + "', " +
				KEY_STAND + " = '" + stand + "', " +
				KEY_LIFTSUNAFFECTED + " = '" + liftsunaffected + "', " +
				KEY_LIFTSAFFECTED + " = '" + liftsaffected + "', " +
				KEY_WALKING + " = '" + walking +
				"' WHERE " + KEY_PARENTID + " = " + parentId + " AND " + KEY_DAY + " = " + day;

		// Insert it into the database.
		//return db.update(DATA_TABLE, newValues, where, null) != 0;
		Cursor c =  db.rawQuery(updateQuery, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;


	}

	// Return all data for a patient in the database.
	public Cursor getAllPatientData(int patientId) {
		String selectQuery = "SELECT * FROM " + DATA_TABLE + " dt WHERE dt." + KEY_PARENTID + " = " + patientId;

		Cursor c = 	db.rawQuery(selectQuery, null);
		if (c != null) {
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
					+ " to " + newVersion + ", which will destroy all old data!");
			
			// Destroy old database:
			_db.execSQL("DROP TABLE IF EXISTS " + PATIENT_TABLE);
			_db.execSQL("DROP TABLE IF EXISTS " + DATA_TABLE);
			
			// Recreate new database:
			onCreate(_db);
		}
	}
}
