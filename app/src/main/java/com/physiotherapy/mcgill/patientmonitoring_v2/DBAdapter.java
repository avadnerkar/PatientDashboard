// ------------------------------------ DBADapter.java ---------------------------------------------

// TODO: Change the package to match your project.
package com.physiotherapy.mcgill.patientmonitoring_v2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


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
	public static final String KEY_FIRSTNAME = "firstname";
	public static final String KEY_LASTNAME = "lastname";
	public static final String KEY_HOSPITALID = "hospitalid";

	// TODO: Setup your data fields here:
	public static final String KEY_PARENTID = "parentId";
	public static final String KEY_DAY = "day";

	//Nurse
	public static final String KEY_PEG = "peg";
	public static final String KEY_NG = "ng";
	public static final String KEY_O2 = "o2";
	public static final String KEY_IV = "iv";
	public static final String KEY_FOLEY = "foley";
	public static final String KEY_CPAP = "cpap";
	public static final String KEY_RESTRAINT = "restraint";
	public static final String KEY_BEHAVIOURAL = "behavioural";
	public static final String KEY_CONFUSION = "confusion";
	public static final String KEY_BLADDER = "bladder";
	public static final String KEY_HOURS = "hours";

	//OT
	public static final String KEY_NEGLECT = "neglect";
	public static final String KEY_DIGITSPAN = "digitspan";
	public static final String KEY_MMSE = "mmse";
	public static final String KEY_FOLLOWS = "follows";
	public static final String KEY_VERBAL = "verbal";
	public static final String KEY_MOTIVATION = "motivation";
	public static final String KEY_MOOD = "mood";
	public static final String KEY_PAIN = "pain";
	public static final String KEY_FATIGUE = "fatigue";
	public static final String KEY_SWALLOW = "swallow";
	public static final String KEY_FEEDING = "feeding";
	public static final String KEY_DRESSING = "dressing";
	public static final String KEY_KITCHEN = "kitchen";

	//PT
	public static final String KEY_LEFTARM = "leftarm";
	public static final String KEY_RIGHTARM = "rightarm";
	public static final String KEY_MOVEMENTBED = "movementbed";
	public static final String KEY_LIESIT = "liesit";
	public static final String KEY_SITTING = "sitting";
	public static final String KEY_SITSTAND = "sitstand";
	public static final String KEY_STAND = "stand";
	public static final String KEY_LIFTSUNAFFECTED = "liftsunaffected";
	public static final String KEY_LIFTSAFFECTED = "liftsaffected";
	public static final String KEY_WALKING = "walking";
	
	// TODO: Setup your patient field numbers here (0 = KEY_ROWID, 1=...)
	public static final int COL_FIRSTNAME = 1;
	public static final int COL_LASTNAME = 2;
	public static final int COL_HOSPITALID = 3;

	// TODO: Setup your data field numbers here (0 = KEY_ROWID, 1=...)
	public static final int COL_PARENTID = 1;
	public static final int COL_DAY = 2;

	//Nurse
	public static final int COL_PEG = 3;
	public static final int COL_NG = 4;
	public static final int COL_O2 = 5;
	public static final int COL_IV = 6;
	public static final int COL_FOLEY = 7;
	public static final int COL_CPAP = 8;
	public static final int COL_RESTRAINT = 9;
	public static final int COL_BEHAVIOURAL = 10;
	public static final int COL_CONFUSION = 11;
	public static final int COL_BLADDER = 12;
	public static final int COL_HOURS = 13;

	//OT
	public static final int COL_NEGLECT = 14;
	public static final int COL_DIGITSPAN = 15;
	public static final int COL_MMSE = 16;
	public static final int COL_FOLLOWS = 17;
	public static final int COL_VERBAL = 18;
	public static final int COL_MOTIVATION = 19;
	public static final int COL_MOOD = 20;
	public static final int COL_PAIN = 21;
	public static final int COL_FATIGUE = 22;
	public static final int COL_SWALLOW = 23;
	public static final int COL_FEEDING = 24;
	public static final int COL_DRESSING = 25;
	public static final int COL_KITCHEN = 26;

	//PT
	public static final int COL_LEFTARM = 27;
	public static final int COL_RIGHTARM = 28;
	public static final int COL_MOVEMENTBED = 29;
	public static final int COL_LIESIT = 30;
	public static final int COL_SITTING = 31;
	public static final int COL_SITSTAND = 32;
	public static final int COL_STAND = 33;
	public static final int COL_LIFTSUNAFFECTED = 34;
	public static final int COL_LIFTSAFFECTED = 35;
	public static final int COL_WALKING = 36;


	// TODO: Set all keys for patient table
	public static final String[] ALL_PATIENT_KEYS = new String[] {KEY_ROWID, KEY_FIRSTNAME, KEY_LASTNAME, KEY_HOSPITALID};

	//TODO: Set all keys for data table
	public static final String[] ALL_DATA_KEYS = new String[] {KEY_ROWID, KEY_PARENTID, KEY_DAY,
			KEY_PEG, KEY_NG, KEY_O2, KEY_IV, KEY_FOLEY, KEY_CPAP, KEY_RESTRAINT, KEY_BEHAVIOURAL, KEY_CONFUSION, KEY_BLADDER, KEY_HOURS,
			KEY_NEGLECT, KEY_DIGITSPAN, KEY_MMSE, KEY_FOLLOWS, KEY_VERBAL, KEY_MOTIVATION, KEY_MOOD, KEY_PAIN, KEY_FATIGUE, KEY_SWALLOW, KEY_FEEDING, KEY_DRESSING, KEY_KITCHEN,
			KEY_LEFTARM, KEY_RIGHTARM, KEY_MOVEMENTBED, KEY_LIESIT, KEY_SITTING, KEY_SITSTAND, KEY_STAND, KEY_LIFTSUNAFFECTED, KEY_LIFTSAFFECTED, KEY_WALKING};
	
	// DB info: its name, and the tables we are using
	public static final String DATABASE_NAME = "PatientMonitoringDb";
	public static final String PATIENT_TABLE = "patientTable";
	public static final String DATA_TABLE = "dataTable";

	// Track DB version if a new version of your app changes the format.
	public static final int DATABASE_VERSION = 7;


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
			+ KEY_HOSPITALID + " integer not null"
			
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
	public long insertRowPatient(String firstName, String lastName, String hospitalId) {
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
		
		// Insert it into the database.
		return db.insert(PATIENT_TABLE, null, initialValues);
	}
	
	// Delete a row from the database, by rowId (primary key)
	public boolean deleteRowPatient(long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		return db.delete(PATIENT_TABLE, where, null) != 0;
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
	public boolean updateRowPatient(long rowId, String firstName, String lastName, String hospitalId) {
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
		
		// Insert it into the database.
		return db.update(PATIENT_TABLE, newValues, where, null) != 0;
	}

	/////////////////////////////////////////////////////////////////////
	//	Data methods:
	/////////////////////////////////////////////////////////////////////

	// Add a new set of values to the database.
	public long insertRowData(int parentId, int day,
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
