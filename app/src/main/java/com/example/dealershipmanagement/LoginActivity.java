package com.example.dealershipmanagement;
// Getting the list of imports needed
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
// Login activity class
public class LoginActivity extends AppCompatActivity {
    Button LoginButton;
    EditText UsernameField, PasswordField;
    String UsernameHolder, PasswordHolder;
    Boolean EditTextEmptyHolder;
    SQLiteDatabase db;
    DealerManagementDatabaseHelper sqLiteHelper;
    Cursor cursor;
    String TempPassword = "NOT_FOUND", AdminRole = "";
    // List of tasks done on the creation of the activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Linking to the buttons
        LoginButton = findViewById(R.id.Login_Button);
        // Linking the username and password to the input fields
        UsernameField = findViewById(R.id.Username_Login);
        PasswordField = findViewById(R.id.Password_Login);
        // Initializing the database connection
        sqLiteHelper = new DealerManagementDatabaseHelper(this);
        // Function for the login button
        LoginButton.setOnClickListener(view -> {
            CheckEditTextStatus();
            LoginFunction();
        });
    }
    // Login function
    @SuppressLint("Range")
    public void LoginFunction() {
        if (EditTextEmptyHolder) {
            db = sqLiteHelper.getWritableDatabase();
            cursor = db.query(DealerManagementDatabaseHelper.USER_TABLE_NAME, null,
                    " " + DealerManagementDatabaseHelper.USER_COLUMN_USERNAME + "=?", new String[] { UsernameHolder }, null, null,
                    null);
            while (cursor.moveToNext()) {
                if (cursor.isFirst()) {
                    cursor.moveToFirst();
                    TempPassword = cursor.getString(cursor.getColumnIndex(DealerManagementDatabaseHelper.USER_COLUMN_PASSWORD));
                    AdminRole = cursor.getString(cursor.getColumnIndex(DealerManagementDatabaseHelper.USER_COLUMN_ROLE));
                    cursor.close();
                }
            }
            CheckFinalResult();
        } else {
            // Message to check username or password is entered
            Toast.makeText(LoginActivity.this, "Please enter Username or Password!", Toast.LENGTH_LONG).show();
        }
    }
    // Function to check input fields are not empty
    public void CheckEditTextStatus() {
        UsernameHolder = UsernameField.getText().toString();
        PasswordHolder = PasswordField.getText().toString();
        EditTextEmptyHolder = !TextUtils.isEmpty(UsernameHolder) && !TextUtils.isEmpty(PasswordHolder);
    }
    // Function to check final result and open the corresponding activity
    public void CheckFinalResult() {
        if (TempPassword.equalsIgnoreCase(PasswordHolder)) {
            if (AdminRole.equalsIgnoreCase("Admin")) {
                // Message to confirm username and password match with data from the database
                Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(LoginActivity.this, AdminDashboardActivity.class);
                startActivity(intent);
            }
            else {
                // Message to confirm username and password match with data from the database
                Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        }
        else {
            // Error message to show username or password entered do not match with database records
            Toast.makeText(LoginActivity.this, "Username or Password is wrong, please try again!", Toast.LENGTH_LONG).show();
            UsernameField.getText().clear();
            PasswordField.getText().clear();
        }
        TempPassword = "NOT_FOUND";
    }

}