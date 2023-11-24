package com.example.dealershipmanagement;
// Getting the list of imports needed
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
// Register activity class
public class RegisterActivity extends AppCompatActivity {
    EditText UsernameField, PasswordField, FirstNameField, LastNameField, RoleField;
    Button RegisterButton, DashboardButton;
    String FirstnameHolder, LastnameHolder, UsernameHolder, PasswordHolder, RoleHolder;
    Boolean EditTextEmptyHolder;
    SQLiteDatabase db;
    String SQLiteDataBaseQueryHolder;
    DealerManagementDatabaseHelper sqLiteHelper;
    Cursor cursor;
    String F_Result = "Not_Found";
    // List of tasks done on the creation of the activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Linking button holder to buttons
        RegisterButton = findViewById(R.id.Register_Button);
        DashboardButton = findViewById(R.id.Dashboard_Button);
        // Linking text fields holders to text fields
        UsernameField = findViewById(R.id.Username_Register);
        PasswordField = findViewById(R.id.Password_Register);
        FirstNameField = findViewById(R.id.FirstName_Register);
        LastNameField = findViewById(R.id.LastName_Register);
        RoleField = findViewById(R.id.Role_Register);
        // Initialising the database
        sqLiteHelper = new DealerManagementDatabaseHelper(this);
        // Starting the dashboard activity
        DashboardButton.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterActivity.this, AdminDashboardActivity.class);
            startActivity(intent);});
        // Initialising the register function
        RegisterButton.setOnClickListener(view -> {
            SQLiteDataBaseBuild();
            SQLiteTableBuild();
            CheckEditTextStatus();
            CheckingUsernameAlreadyExistsOrNot();
            EmptyEditTextAfterDataInsert();
        });
    }
    // Building the database
    public void SQLiteDataBaseBuild() {
        db = openOrCreateDatabase(DealerManagementDatabaseHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);
    }
    // Building the table in the database
    public void SQLiteTableBuild() {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DealerManagementDatabaseHelper.USER_TABLE_NAME + "("
                + DealerManagementDatabaseHelper.USER_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DealerManagementDatabaseHelper.USER_COLUMN_USERNAME + " VARCHAR, " + DealerManagementDatabaseHelper.USER_COLUMN_FIRSTNAME
                + " VARCHAR, " + DealerManagementDatabaseHelper.USER_COLUMN_LASTNAME + " VARCHAR, "
                + DealerManagementDatabaseHelper.USER_COLUMN_PASSWORD + " VARCHAR, " + DealerManagementDatabaseHelper.USER_COLUMN_ROLE + " VARCHAR)");
    }
    // Function to insert the data into the database
    public void InsertDataIntoSQLiteDatabase() {
        if (EditTextEmptyHolder) {
            SQLiteDataBaseQueryHolder = "INSERT INTO " + DealerManagementDatabaseHelper.USER_TABLE_NAME
                    + " (username,firstname,lastname,password,role) VALUES('" + UsernameHolder + "','" + FirstnameHolder
                    + "','" + LastnameHolder + "','" + PasswordHolder + "','" + RoleHolder + "')";
            db.execSQL(SQLiteDataBaseQueryHolder);
            db.close();
            Toast.makeText(RegisterActivity.this, "User Registered Successfully", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(RegisterActivity.this, "Please Fill All The Required Fields.", Toast.LENGTH_LONG).show();
        }
    }
    // Function to clear entry fields once the data has been saved in the database
    public void EmptyEditTextAfterDataInsert() {
        UsernameField.getText().clear();
        FirstNameField.getText().clear();
        LastNameField.getText().clear();
        PasswordField.getText().clear();
        RoleField.getText().clear();
    }
    // Function to check whether the entry fields are not empty
    public void CheckEditTextStatus() {
        UsernameHolder = UsernameField.getText().toString();
        FirstnameHolder = FirstNameField.getText().toString();
        LastnameHolder = LastNameField.getText().toString();
        PasswordHolder = PasswordField.getText().toString();
        RoleHolder = RoleField.getText().toString();
        EditTextEmptyHolder = !TextUtils.isEmpty(FirstnameHolder) && !TextUtils.isEmpty(LastnameHolder) && !TextUtils.isEmpty(PasswordHolder)
                && !TextUtils.isEmpty(PasswordHolder) && !TextUtils.isEmpty(RoleHolder);
    }
    // Function to check the username does not already exist in the database
    public void CheckingUsernameAlreadyExistsOrNot() {
        db = sqLiteHelper.getWritableDatabase();
        cursor = db.query(DealerManagementDatabaseHelper.USER_TABLE_NAME, null,
                " " + DealerManagementDatabaseHelper.USER_COLUMN_USERNAME + "=?", new String[] { UsernameHolder }, null, null, null);
        while (cursor.moveToNext()) {
            if (cursor.isFirst()) {
                cursor.moveToFirst();
                F_Result = "Username Found";
                cursor.close();
            }
        }
        CheckFinalResult();
    }
    // Function to check final result and start inserting the data into database
    public void CheckFinalResult() {
        if (F_Result.equalsIgnoreCase("Username Found")) {
            Toast.makeText(RegisterActivity.this, "Username Already Exists", Toast.LENGTH_LONG).show();
        } else {
            InsertDataIntoSQLiteDatabase();
        }
        F_Result = "Not_Found";
    }
}