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
public class VehicleInformationSearchActivity extends AppCompatActivity {
    Button FindVehicleButton;
    EditText EnteredNumberPlate;
    String InputText;
    SQLiteDatabase database;
    String SQLiteDataBaseQueryHolder;
    DealerManagementDatabaseHelper dbHelper;
    Cursor cursor;
    // List of tasks done on the creation of the activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_information_search);
        // Initialise the database connection
        dbHelper = new DealerManagementDatabaseHelper(this);
        // Initialise the buttons
        FindVehicleButton = findViewById(R.id.FindVehicle_Button);
        // Initialise the views
        EnteredNumberPlate = findViewById(R.id.NumberPlate_Search);
        // Function for vehicle find button
        FindVehicleButton.setOnClickListener(view -> CheckNumberPlateInDataBase());
    }
    // Function to check number plate in the database
    private boolean checkNumberPlateInDatabase(String numberPlate) {
        // Getting writable database connection
        database = dbHelper.getReadableDatabase();
        // Query to extract all data from database that matched the number plate entered
        SQLiteDataBaseQueryHolder = "SELECT * FROM " + DealerManagementDatabaseHelper.VEHICLES_TABLE_NAME + " WHERE " + DealerManagementDatabaseHelper.VEHICLES_COLUMN_NUMBERPLATE + " = '" + numberPlate + "'";
        cursor = database.rawQuery(SQLiteDataBaseQueryHolder, null);
        // Check if the cursor has any rows
        boolean numberPlateFound = cursor.moveToFirst();
        // Close the cursor and the database connection
        cursor.close();
        database.close();
        return numberPlateFound;
    }
    // Function to check number plate in the database
    private void CheckNumberPlateInDataBase() {
        // Getting the number plate from input field
        EnteredNumberPlate = findViewById(R.id.NumberPlate_Search);
        InputText = EnteredNumberPlate.getText().toString();
        // Inserting the number plate into the search function
        boolean numberPlateFound = checkNumberPlateInDatabase(InputText);
        if (numberPlateFound) {
            // Message to confirm vehicle number plate matches data from the database
            Toast.makeText(this, "Vehicle found!", Toast.LENGTH_SHORT).show();
            // Starts the vehicle information view activity to display the vehicle data
            Intent intent = new Intent(VehicleInformationSearchActivity.this, VehicleInformationViewActivity.class);
            intent.putExtra("input_text", InputText);
            startActivity(intent);
        }
        else {
            // Message to confirm vehicle number plate does not match data from the database
            Toast.makeText(this, "Vehicle not found, try again!", Toast.LENGTH_SHORT).show();
            // Command to clear the input field
            EnteredNumberPlate.getText().clear();
        }
    }
}
