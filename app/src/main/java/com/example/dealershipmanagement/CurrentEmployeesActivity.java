package com.example.dealershipmanagement;
// Getting the necessary imports
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
// Current employees activity class
public class CurrentEmployeesActivity extends AppCompatActivity {
    TextView UsernameView, FirstNameView, LastNameView, PasswordView, RoleView;
    String Username, FirstName, LastName, Password, Role;
    Button DashboardButton;
    SQLiteDatabase database;
    DealerManagementDatabaseHelper dbHelper;
    Cursor cursor;
    TableLayout tableLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_employees);
        // Initialize button
        DashboardButton = findViewById(R.id.Dashboard_Button);
        // Initialize views
        tableLayout = findViewById(R.id.UserTable_View);
        // Initialize database helper
        dbHelper = new DealerManagementDatabaseHelper(this);
        database = dbHelper.getReadableDatabase();
        // Retrieve all vehicle data from database
        cursor = database.rawQuery("SELECT * FROM " + DealerManagementDatabaseHelper.UserEntry.USER_TABLE_NAME, null);
        // Starting the dashboard activity
        DashboardButton.setOnClickListener(view -> {
            Intent intent = new Intent(CurrentEmployeesActivity.this, AdminDashboardActivity.class);
            startActivity(intent);});
        // Bind data to table view
        while (cursor.moveToNext()) {
            TableRow row = new TableRow(this);
            UsernameView = new TextView(this);
            FirstNameView = new TextView(this);
            LastNameView = new TextView(this);
            PasswordView = new TextView(this);
            RoleView = new TextView(this);
            // Getting the data from the database
            Username = cursor.getString(cursor.getColumnIndexOrThrow(DealerManagementDatabaseHelper.UserEntry.USER_COLUMN_USERNAME));
            FirstName = cursor.getString(cursor.getColumnIndexOrThrow(DealerManagementDatabaseHelper.UserEntry.USER_COLUMN_FIRSTNAME));
            LastName = cursor.getString(cursor.getColumnIndexOrThrow(DealerManagementDatabaseHelper.UserEntry.USER_COLUMN_LASTNAME));
            Password = cursor.getString(cursor.getColumnIndexOrThrow(DealerManagementDatabaseHelper.UserEntry.USER_COLUMN_PASSWORD));
            Role = cursor.getString(cursor.getColumnIndexOrThrow(DealerManagementDatabaseHelper.UserEntry.USER_COLUMN_ROLE));
            // Setting the text view to the data from the database
            UsernameView.setText(Username);
            FirstNameView.setText(FirstName);
            LastNameView.setText(LastName);
            PasswordView.setText(Password);
            RoleView.setText(Role);
            // Adding rows to the table view
            row.addView(UsernameView);
            row.addView(FirstNameView);
            row.addView(LastNameView);
            row.addView(PasswordView);
            row.addView(RoleView);
            tableLayout.addView(row);
        }
    }
    // Closes the database connection
    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        database.close();
    }
}
