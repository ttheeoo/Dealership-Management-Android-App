package com.example.dealershipmanagement;
// Getting the needed imports
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
// Book vehicle activity class
public class BookVehicleActivity extends AppCompatActivity {
    EditText NumberPlateField, VehicleMakeField, VehicleModelField, VehicleYearField, ServiceNeededField, VehicleLocationField, CommentsField;
    String NumberPlate, VehicleMake, VehicleModel, VehicleYear, ServiceNeeded, VehicleLocation, Comments;
    Button BookButton, DashboardButton;
    SQLiteDatabase database;
    DealerManagementDatabaseHelper dbHelper;
    // List of tasks done on the creation of the activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_vehicle);
        // Initialize the database connection
        dbHelper = new DealerManagementDatabaseHelper(this);
        database = dbHelper.getWritableDatabase();
        // Initialize button
        BookButton = findViewById(R.id.BookVehicle_Button);
        DashboardButton = findViewById(R.id.Dashboard_Button);
        // Initialize views
        NumberPlateField = findViewById(R.id.NumberPlate_Book);
        VehicleMakeField = findViewById(R.id.VehicleMake_Book);
        VehicleModelField = findViewById(R.id.VehicleModel_Book);
        VehicleYearField = findViewById(R.id.VehicleYear_Book);
        ServiceNeededField = findViewById(R.id.VehicleService_Book);
        VehicleLocationField = findViewById(R.id.VehicleLocation_Book);
        CommentsField = findViewById(R.id.Comments_Book);
        // Function for dashboard button
        DashboardButton.setOnClickListener(view -> {
            Intent intent = new Intent(BookVehicleActivity.this, DashboardActivity.class);
            startActivity(intent);
        });
        // Function for book button
        BookButton.setOnClickListener(v -> {
            // Getting the entered data from the user
            NumberPlate = NumberPlateField.getText().toString();
            VehicleMake = VehicleMakeField.getText().toString();
            VehicleModel = VehicleModelField.getText().toString();
            VehicleYear = VehicleYearField.getText().toString();
            ServiceNeeded = ServiceNeededField.getText().toString();
            VehicleLocation = VehicleLocationField.getText().toString();
            Comments = CommentsField.getText().toString();
            // Data validation to ensure all fields are completed
            if (NumberPlate.isEmpty() || VehicleMake.isEmpty() || VehicleModel.isEmpty() || VehicleYear.isEmpty() || ServiceNeeded.isEmpty() || VehicleLocation.isEmpty() || Comments.isEmpty()) {
                Toast.makeText(BookVehicleActivity.this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
                return;
            }
            // Checks the numberplate entered is not already in the database
            Cursor cursor = database.rawQuery("SELECT * FROM " + DealerManagementDatabaseHelper.VEHICLES_TABLE_NAME + " WHERE " +
                    DealerManagementDatabaseHelper.VEHICLES_COLUMN_NUMBERPLATE + "=?", new String[]{NumberPlate});
            if (cursor.getCount() > 0) {
                Toast.makeText(BookVehicleActivity.this, "Vehicle already booked!", Toast.LENGTH_SHORT).show();
                cursor.close();
                return;
            }
            cursor.close();
            // Updates the database with the new entered data
            ContentValues values = new ContentValues();
            values.put(DealerManagementDatabaseHelper.VEHICLES_COLUMN_NUMBERPLATE, NumberPlate);
            values.put(DealerManagementDatabaseHelper.VEHICLES_COLUMN_VEHICLEMAKE, VehicleMake);
            values.put(DealerManagementDatabaseHelper.VEHICLES_COLUMN_VEHICLEMODEL, VehicleModel);
            values.put(DealerManagementDatabaseHelper.VEHICLES_COLUMN_VEHICLEYEAR, VehicleYear);
            values.put(DealerManagementDatabaseHelper.VEHICLES_COLUMN_SERVICENEEDED, ServiceNeeded);
            values.put(DealerManagementDatabaseHelper.VEHICLES_COLUMN_VEHICLELOCATION, VehicleLocation);
            values.put(DealerManagementDatabaseHelper.VEHICLES_COLUMN_COMMENTS, Comments);
            // Messages to confirm or deny the vehicle booking process results
            long newRowId = database.insert(DealerManagementDatabaseHelper.VEHICLES_TABLE_NAME, null, values);
            if (newRowId != -1) {
                // Confirm the vehicle booking process is successful
                Toast.makeText(BookVehicleActivity.this, "Vehicle successfully added!", Toast.LENGTH_SHORT).show();
                clearFields();
            } else {
                // Confirm the vehicle booking process is not successful
                Toast.makeText(BookVehicleActivity.this, "Error adding vehicle!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    // Function to clear the input fields after the data is successfully saved onto the database
    private void clearFields() {
        NumberPlateField.getText().clear();
        VehicleMakeField.getText().clear();
        VehicleModelField.getText().clear();
        VehicleYearField.getText().clear();
        ServiceNeededField.getText().clear();
        VehicleLocationField.getText().clear();
        CommentsField.getText().clear();
    }
    // Function to close the database connection
    @Override
    protected void onDestroy(){
        super.onDestroy();
        database.close();
    }
}