package com.example.dealershipmanagement;
// Getting the needed imports
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
// Employee information view activity
public class EmployeeInformationViewActivity extends AppCompatActivity {
    EditText UsernameField, FirstNameField, LastNameField, PasswordField, RoleField;
    String UsernameHolder, FirstNameHolder, LastNameHolder, PasswordHolder, RoleHolder;
    Button UpdateButton, DeleteButton, SearchAgainButton, DashboardButton;
    SQLiteDatabase database;
    DealerManagementDatabaseHelper dbHelper;
    Cursor cursor;
    // List of tasks done on the creation of the activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_information_view);
        // Initialise the buttons
        UpdateButton = findViewById(R.id.UpdateVehicle_Button);
        DeleteButton = findViewById(R.id.DeleteVehicle_Button);
        SearchAgainButton = findViewById(R.id.SearchAgain_Button);
        DashboardButton = findViewById(R.id.Dashboard_Button);
        // Initialise the views
        UsernameField = findViewById(R.id.Username_View);
        FirstNameField = findViewById(R.id.FirstName_View);
        LastNameField = findViewById(R.id.LastName_View);
        PasswordField = findViewById(R.id.Password_View);
        RoleField = findViewById(R.id.Role_View);
        // Database Connection
        dbHelper = new DealerManagementDatabaseHelper(this);
        displayInputText();
        displayEmployeeData();
        // Function for update button
        UpdateButton.setOnClickListener(view -> updateData());
        // Function for delete button
        DeleteButton.setOnClickListener(view -> deleteData());
        // Function for update button
        SearchAgainButton.setOnClickListener(view -> {
            Intent intent = new Intent(EmployeeInformationViewActivity.this, EmployeeInformationSearchActivity.class);
            startActivity(intent);});
        // Function for dashboard button
        DashboardButton.setOnClickListener(view -> {
            Intent intent = new Intent(EmployeeInformationViewActivity.this, AdminDashboardActivity.class);
            startActivity(intent);});
    }
    // Function to display the input data
    private void displayInputText() {
        Intent intent = getIntent();
        String inputText = intent.getStringExtra("input_text");
        TextView inputTextView = findViewById(R.id.Username_View);
        inputTextView.setText(inputText);
    }
    // Function to update the data in the database
    private void updateData() {
        UsernameHolder = UsernameField.getText().toString().trim();
        FirstNameHolder = FirstNameField.getText().toString().trim();
        LastNameHolder = LastNameField.getText().toString().trim();
        PasswordHolder = PasswordField.getText().toString().trim();
        RoleHolder = RoleField.getText().toString().trim();
        // Getting a readable database
        database = dbHelper.getReadableDatabase();
        // Creating ContentValues object to hold the new values
        ContentValues values = new ContentValues();
        values.put(DealerManagementDatabaseHelper.USER_COLUMN_USERNAME, UsernameHolder);
        values.put(DealerManagementDatabaseHelper.USER_COLUMN_FIRSTNAME, FirstNameHolder);
        values.put(DealerManagementDatabaseHelper.USER_COLUMN_LASTNAME, LastNameHolder);
        values.put(DealerManagementDatabaseHelper.USER_COLUMN_PASSWORD, PasswordHolder);
        values.put(DealerManagementDatabaseHelper.USER_COLUMN_ROLE, RoleHolder);
        // Update the data in the database
        int rowsUpdated = database.update(DealerManagementDatabaseHelper.USER_TABLE_NAME,values,DealerManagementDatabaseHelper.USER_COLUMN_USERNAME + " = ?",new String[]{UsernameHolder});
        // Show a message indicating whether the update was successful
        if (rowsUpdated > 0) {
            // Getting the necessary columns for the data to be entered into the database
            UsernameField.setText(UsernameHolder);
            FirstNameField.setText(FirstNameHolder);
            LastNameField.setText(LastNameHolder);
            PasswordField.setText(PasswordHolder);
            RoleField.setText(RoleHolder);
            // Message to confirm the updated data is saved into the database
            Toast.makeText(EmployeeInformationViewActivity.this,"Employee data updated successfully!",Toast.LENGTH_LONG).show();
        }
        else {
            // Message to deny the updated data is saved into the database
            Toast.makeText(EmployeeInformationViewActivity.this,"Employee data update failed!",Toast.LENGTH_LONG).show();
        }
        database.close();
    }
    // Function to display employee data from the database
    @SuppressLint({"Range", "SetTextI18n"})
    private void displayEmployeeData() {
        String userName = UsernameField.getText().toString().trim();
        if (!TextUtils.isEmpty(userName)) {
            database = dbHelper.getWritableDatabase();
            String query = "SELECT * FROM " + DealerManagementDatabaseHelper.USER_TABLE_NAME + " WHERE " +
                    DealerManagementDatabaseHelper.USER_COLUMN_USERNAME + "= '" + userName + "'";
            cursor = database.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                // Getting the columns needed from the database
                FirstNameField.setText(cursor.getString(cursor.getColumnIndex(DealerManagementDatabaseHelper.USER_COLUMN_FIRSTNAME)));
                LastNameField.setText(cursor.getString(cursor.getColumnIndex(DealerManagementDatabaseHelper.USER_COLUMN_LASTNAME)));
                PasswordField.setText(cursor.getString(cursor.getColumnIndex(DealerManagementDatabaseHelper.USER_COLUMN_PASSWORD)));
                RoleField.setText(cursor.getString(cursor.getColumnIndex(DealerManagementDatabaseHelper.USER_COLUMN_ROLE)));
            }
            else {
                // Message if employee not found in the database
                Toast.makeText(EmployeeInformationViewActivity.this,"Employee not in database!",Toast.LENGTH_LONG).show();
            }
            cursor.close();
        }
    }
    // Function to delete employee from the database
    private void deleteData() {
        UsernameHolder = UsernameField.getText().toString().trim();
        database = dbHelper.getWritableDatabase();
        // Delete the data from the database
        int rowsDeleted = database.delete(DealerManagementDatabaseHelper.USER_TABLE_NAME,DealerManagementDatabaseHelper.USER_COLUMN_USERNAME + " = ?",new String[]{UsernameHolder});
        // Show a message indicating if the delete was successful or not
        if (rowsDeleted > 0) {
            // Message to confirm the deletion of employee data from database
            Toast.makeText(EmployeeInformationViewActivity.this,"Employee data deleted successfully!",Toast.LENGTH_LONG).show();}
        else {
            // Message to deny the deletion of employee data from database
            Toast.makeText(EmployeeInformationViewActivity.this,"Failed to delete employee data!",Toast.LENGTH_LONG).show();
        }
        database.close();
    }
}
