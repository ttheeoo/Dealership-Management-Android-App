package com.example.dealershipmanagement;
// Getting the necessary imports
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
// Vehicle locator view activity class
public class VehicleLocatorViewActivity extends AppCompatActivity {
    EditText NumberPlate, VehicleMake, VehicleModel, VehicleYear, VehicleLocation;
    Button DashboardButton, SearchAgainButton;
    SQLiteDatabase database;
    DealerManagementDatabaseHelper dbHelper;
    Cursor cursor;
    // Tasks that run when the activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_locator_view);
        // Buttons;
        DashboardButton = findViewById(R.id.Dashboard_Button);
        SearchAgainButton = findViewById(R.id.SearchAgain_Button);
        // Edit Text Fields
        NumberPlate = findViewById(R.id.NumberPlate_View);
        VehicleMake = findViewById(R.id.VehicleMake_View);
        VehicleModel = findViewById(R.id.VehicleModel_View);
        VehicleYear = findViewById(R.id.VehicleYear_View);
        VehicleLocation = findViewById(R.id.VehicleLocation_View);
        // Database Connection
        dbHelper = new DealerManagementDatabaseHelper(this);
        displayInputText();
        displayVehicleData();
        // Function for dashboard button
        DashboardButton.setOnClickListener(view -> {
            Intent intent = new Intent(VehicleLocatorViewActivity.this, DashboardActivity.class);
            startActivity(intent);
        });
        // Function for the search again button
        SearchAgainButton.setOnClickListener(view -> {
            Intent intent = new Intent(VehicleLocatorViewActivity.this, VehicleLocatorSearchActivity.class);
            startActivity(intent);
        });
    }
    // Function to display the entered data from input field
    private void displayInputText() {
        Intent intent = getIntent();
        String inputText = intent.getStringExtra("input_text");
        TextView inputTextView = findViewById(R.id.NumberPlate_View);
        inputTextView.setText(inputText);
    }
    // Function to display vehicle data from the database
    @SuppressLint({"Range", "SetTextI18n"})
    private void displayVehicleData() {
        String numberPlate = NumberPlate.getText().toString().trim();
        if (!TextUtils.isEmpty(numberPlate)) {
            database = dbHelper.getWritableDatabase();
            String query = "SELECT * FROM " + DealerManagementDatabaseHelper.VEHICLES_TABLE_NAME + " WHERE " +
                    DealerManagementDatabaseHelper.VEHICLES_COLUMN_NUMBERPLATE + "= '" + numberPlate + "'";
            cursor = database.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                // Getting the columns needed from the database
                VehicleMake.setText(cursor.getString(cursor.getColumnIndex(DealerManagementDatabaseHelper.VEHICLES_COLUMN_VEHICLEMAKE)));
                VehicleModel.setText(cursor.getString(cursor.getColumnIndex(DealerManagementDatabaseHelper.VEHICLES_COLUMN_VEHICLEMODEL)));
                VehicleYear.setText(cursor.getString(cursor.getColumnIndex(DealerManagementDatabaseHelper.VEHICLES_COLUMN_VEHICLEYEAR)));
                VehicleLocation.setText(cursor.getString(cursor.getColumnIndex(DealerManagementDatabaseHelper.VEHICLES_COLUMN_VEHICLELOCATION)));
            } else {
                // Message if vehicle not found in the database
                Toast.makeText(VehicleLocatorViewActivity.this,"Vehicle not in database!",Toast.LENGTH_LONG).show();
            }
            cursor.close();
        }
    }
}