package com.example.dealershipmanagement;
// Getting the needed imports
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
// Dealer management database helper class
public class DealerManagementDatabaseHelper extends SQLiteOpenHelper {
    // Constants for the user database
    static final String DATABASE_NAME = "DealerManagementDB";
    public static final String USER_TABLE_NAME = "Staff";
    public static final String USER_COLUMN_ID = "id";
    public static final String USER_COLUMN_USERNAME = "username";
    public static final String USER_COLUMN_FIRSTNAME = "firstname";
    public static final String USER_COLUMN_LASTNAME = "lastname";
    public static final String USER_COLUMN_PASSWORD = "password";
    public static final String USER_COLUMN_ROLE = "role";
    // Constants for the vehicles database
    public static final String VEHICLES_TABLE_NAME = "Vehicles";
    public static final String VEHICLES_COLUMN_ID = "id";
    public static final String VEHICLES_COLUMN_NUMBERPLATE = "numberplate";
    public static final String VEHICLES_COLUMN_VEHICLEMAKE = "vehiclemake";
    public static final String VEHICLES_COLUMN_VEHICLEMODEL = "vehiclemodel";
    public static final String VEHICLES_COLUMN_VEHICLEYEAR = "vehicleyear";
    public static final String VEHICLES_COLUMN_SERVICENEEDED = "serviceneeded";
    public static final String VEHICLES_COLUMN_VEHICLELOCATION = "vehiclelocation";
    public static final String VEHICLES_COLUMN_COMMENTS = "comments";

    public DealerManagementDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
    // List of tasks that are done on the creation of the activity
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the Staff table
        String createUserTableQuery = "CREATE TABLE " + USER_TABLE_NAME + " (" +
                USER_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USER_COLUMN_USERNAME + " VARCHAR, " +
                USER_COLUMN_FIRSTNAME + " VARCHAR, " +
                USER_COLUMN_LASTNAME + " VARCHAR, " +
                USER_COLUMN_PASSWORD + " VARCHAR, " +
                USER_COLUMN_ROLE + " VARCHAR)";
        db.execSQL(createUserTableQuery);
        // Insert an admin user
        String insertAdminQuery = "INSERT INTO " + USER_TABLE_NAME + " (" +
                USER_COLUMN_USERNAME + ", " +
                USER_COLUMN_FIRSTNAME + ", " +
                USER_COLUMN_LASTNAME + ", " +
                USER_COLUMN_PASSWORD + ", " +
                USER_COLUMN_ROLE + ") VALUES ('admin', 'Admin', 'User', 'password', 'admin')";
        db.execSQL(insertAdminQuery);
        // Create the Vehicles table
        String createVehicleTableQuery = "CREATE TABLE " + VEHICLES_TABLE_NAME + " (" +
                VEHICLES_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                VEHICLES_COLUMN_NUMBERPLATE + " VARCHAR, " +
                VEHICLES_COLUMN_VEHICLEMAKE + " VARCHAR, " +
                VEHICLES_COLUMN_VEHICLEMODEL + " VARCHAR, " +
                VEHICLES_COLUMN_VEHICLEYEAR + " VARCHAR, " +
                VEHICLES_COLUMN_SERVICENEEDED + " VARCHAR, " +
                VEHICLES_COLUMN_VEHICLELOCATION + " VARCHAR, " +
                VEHICLES_COLUMN_COMMENTS + " VARCHAR)";
        db.execSQL(createVehicleTableQuery);
    }
    // Updating the database if changes were made
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the old tables and create new ones
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + VEHICLES_TABLE_NAME);
        onCreate(db);
    }
    // Class to be used to insert data from database into the table view
    public static class VehicleEntry implements BaseColumns {
        public static final String VEHICLES_TABLE_NAME = "Vehicles";
        public static final String VEHICLES_COLUMN_NUMBERPLATE = "numberplate";
        public static final String VEHICLES_COLUMN_VEHICLEMAKE = "vehiclemake";
        public static final String VEHICLES_COLUMN_VEHICLEMODEL = "vehiclemodel";
        public static final String VEHICLES_COLUMN_VEHICLEYEAR = "vehicleyear";
        public static final String VEHICLES_COLUMN_SERVICENEEDED = "serviceneeded";
        public static final String VEHICLES_COLUMN_VEHICLELOCATION = "vehiclelocation";
        public static final String VEHICLES_COLUMN_COMMENTS = "comments";
    }
    // Class to be used to insert data from database into the table view
    public static class UserEntry implements BaseColumns {
        public static final String USER_TABLE_NAME = "Staff";
        public static final String USER_COLUMN_USERNAME = "username";
        public static final String USER_COLUMN_FIRSTNAME = "firstname";
        public static final String USER_COLUMN_LASTNAME = "lastname";
        public static final String USER_COLUMN_PASSWORD = "password";
        public static final String USER_COLUMN_ROLE = "role";
    }
}
