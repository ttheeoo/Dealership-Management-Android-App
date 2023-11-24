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
// Vehicle locator search activity class
public class VehicleLocatorSearchActivity extends AppCompatActivity {
    Button FindVehicle;
    EditText EnteredNumberPlate;
    String InputText;
    SQLiteDatabase sqLiteDatabaseObj;
    String SQLiteDataBaseQueryHolder;
    DealerManagementDatabaseHelper dbHelper;
    Cursor cursor;
    // List of task running at the creation of the activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_locator_search);
        // Initialise the button
        FindVehicle = findViewById(R.id.LocateVehicle_Button);
        // Initialize the views
        EnteredNumberPlate = findViewById(R.id.NumberPlate_Search);
        // initialize the database connection
        dbHelper = new DealerManagementDatabaseHelper(this); // Instantiate the database helper
        //Initialize the find vehicle function
        FindVehicle.setOnClickListener(view -> CheckNumberPlateInDataBase());
    }
    // Function to check if the numberplate is in the database
    private boolean checkNumberPlateInDatabase(String numberPlate) {
        // Access the database
        sqLiteDatabaseObj = dbHelper.getReadableDatabase(); // Use the dbHelper to get the database
        SQLiteDataBaseQueryHolder = "SELECT * FROM " + DealerManagementDatabaseHelper.VEHICLES_TABLE_NAME + " WHERE " + DealerManagementDatabaseHelper.VEHICLES_COLUMN_NUMBERPLATE + " = '" + numberPlate + "'";
        cursor = sqLiteDatabaseObj.rawQuery(SQLiteDataBaseQueryHolder, null);
        // Check if the cursor has any rows
        boolean numberPlateFound = cursor.moveToFirst();
        // Close the cursor and the database connection
        cursor.close();
        sqLiteDatabaseObj.close();
        return numberPlateFound;
    }
    // Function that checks the numberplate is in the database
    private void CheckNumberPlateInDataBase() {
        EnteredNumberPlate = findViewById(R.id.NumberPlate_Search);
        // Getting the input from the user
        InputText = EnteredNumberPlate.getText().toString();
        boolean numberPlateFound = checkNumberPlateInDatabase(InputText);
        if (numberPlateFound) {
            Toast.makeText(this, "Vehicle found!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(VehicleLocatorSearchActivity.this, VehicleLocatorViewActivity.class);
            intent.putExtra("input_text", InputText);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Vehicle not found, try again!", Toast.LENGTH_SHORT).show();
            EnteredNumberPlate.getText().clear();
        }
    }
}
