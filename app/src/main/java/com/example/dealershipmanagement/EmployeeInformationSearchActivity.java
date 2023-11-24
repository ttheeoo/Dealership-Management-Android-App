package com.example.dealershipmanagement;
// Getting the needed imports
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
// Vehicle information search activity class
public class EmployeeInformationSearchActivity extends AppCompatActivity {
    Button FindEmployeeButton;
    EditText EnteredUsername;
    String InputText;
    SQLiteDatabase database;
    String SQLiteDataBaseQueryHolder;
    DealerManagementDatabaseHelper dbHelper;
    Cursor cursor;
    // List of tasks done on the creation of the activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_information_search);
        // Initialize the database connection
        dbHelper = new DealerManagementDatabaseHelper(this);
        // Initialize the buttons
        FindEmployeeButton = findViewById(R.id.FindEmployee_Button);
        // Initialize the views
        EnteredUsername = findViewById(R.id.Username_Search);
        // Function for vehicle find button
        FindEmployeeButton.setOnClickListener(view -> checkUserNameInDatabase());
    }
    // Function to check number plate in the database
    private boolean checkUserNameInDatabase(String userName) {
        // Getting writable database connection
        database = dbHelper.getReadableDatabase();
        // Query to extract all data from database that matched the number plate entered
        SQLiteDataBaseQueryHolder = "SELECT * FROM " + DealerManagementDatabaseHelper.USER_TABLE_NAME + " WHERE " + DealerManagementDatabaseHelper.USER_COLUMN_USERNAME + " = '" + userName + "'";
        cursor = database.rawQuery(SQLiteDataBaseQueryHolder, null);
        // Check if the cursor has any rows
        boolean userNameFound = cursor.moveToFirst();
        // Close the cursor and the database connection
        cursor.close();
        database.close();
        return userNameFound;
    }
    // Function to check number plate in the database
    private void checkUserNameInDatabase() {
        // Getting the number plate from input field
        EnteredUsername = findViewById(R.id.Username_Search);
        InputText = EnteredUsername.getText().toString();
        // Inserting the number plate into the search function
        boolean userNameFound = checkUserNameInDatabase(InputText);
        if (userNameFound) {
            // Message to confirm vehicle number plate matches data from the database
            Toast.makeText(this, "Employee found!", Toast.LENGTH_SHORT).show();
            // Starts the vehicle information view activity to display the vehicle data
            Intent intent = new Intent(EmployeeInformationSearchActivity.this, EmployeeInformationViewActivity.class);
            intent.putExtra("input_text", InputText);
            startActivity(intent);
        }
        else {
            // Message to confirm vehicle number plate does not match data from the database
            Toast.makeText(this, "Employee not found, try again!", Toast.LENGTH_SHORT).show();
            // Command to clear the input field
            EnteredUsername.getText().clear();
        }
    }
}
