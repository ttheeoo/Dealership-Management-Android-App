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
// Vehicle information view activity
public class VehicleInformationViewActivity extends AppCompatActivity {
    EditText NumberPlate, VehicleMake, VehicleModel, VehicleYear, ServiceNeeded, VehicleLocation, Comments;
    String NumberPlateHolder, VehicleMakeHolder, VehicleModelHolder, VehicleYearHolder, ServiceNeededHolder, VehicleLocationHolder, CommentsHolder;
    Button UpdateButton, DeleteButton, SearchAgainButton, DashboardButton;
    SQLiteDatabase database;
    DealerManagementDatabaseHelper dbHelper;
    Cursor cursor;
    // List of tasks done on the creation of the activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_information_view);
        // Linking to the buttons
        UpdateButton = findViewById(R.id.UpdateVehicle_Button);
        DeleteButton = findViewById(R.id.DeleteVehicle_Button);
        SearchAgainButton = findViewById(R.id.SearchAgain_Button);
        DashboardButton = findViewById(R.id.Dashboard_Button);
        // Linking to the input fields
        VehicleMake = findViewById(R.id.VehicleMake_View);
        VehicleModel = findViewById(R.id.VehicleModel_View);
        VehicleYear = findViewById(R.id.VehicleYear_View);
        ServiceNeeded = findViewById(R.id.ServiceNeeded_View);
        VehicleLocation = findViewById(R.id.VehicleLocation_View);
        Comments = findViewById(R.id.Comments_View);
        NumberPlate = findViewById(R.id.NumberPlate_View);
        // Database Connection
        dbHelper = new DealerManagementDatabaseHelper(this);
        displayInputText();
        displayVehicleData();
        // Function for update button
        UpdateButton.setOnClickListener(view -> updateData());
        // Function for delete button
        DeleteButton.setOnClickListener(view -> deleteData());
        // Function for update button
        SearchAgainButton.setOnClickListener(view -> {
            Intent intent = new Intent(VehicleInformationViewActivity.this, VehicleInformationSearchActivity.class);
            startActivity(intent);});
        // Function for dashboard button
        DashboardButton.setOnClickListener(view -> {
            Intent intent = new Intent(VehicleInformationViewActivity.this, DashboardActivity.class);
            startActivity(intent);});
    }
    private void displayInputText() {
        Intent intent = getIntent();
        String inputText = intent.getStringExtra("input_text");
        TextView inputTextView = findViewById(R.id.NumberPlate_View);
        inputTextView.setText(inputText);
    }
    private void updateData() {
        NumberPlateHolder = NumberPlate.getText().toString().trim();
        VehicleMakeHolder = VehicleMake.getText().toString().trim();
        VehicleModelHolder = VehicleModel.getText().toString().trim();
        VehicleYearHolder = VehicleYear.getText().toString().trim();
        ServiceNeededHolder = ServiceNeeded.getText().toString().trim();
        VehicleLocationHolder = VehicleLocation.getText().toString().trim();
        CommentsHolder = Comments.getText().toString().trim();
        database = dbHelper.getReadableDatabase();
        // Create ContentValues object to hold the new values
        ContentValues values = new ContentValues();
        values.put(DealerManagementDatabaseHelper.VEHICLES_COLUMN_VEHICLEMAKE, VehicleMakeHolder);
        values.put(DealerManagementDatabaseHelper.VEHICLES_COLUMN_VEHICLEMODEL, VehicleModelHolder);
        values.put(DealerManagementDatabaseHelper.VEHICLES_COLUMN_VEHICLEYEAR, VehicleYearHolder);
        values.put(DealerManagementDatabaseHelper.VEHICLES_COLUMN_SERVICENEEDED, ServiceNeededHolder);
        values.put(DealerManagementDatabaseHelper.VEHICLES_COLUMN_VEHICLELOCATION, VehicleLocationHolder);
        values.put(DealerManagementDatabaseHelper.VEHICLES_COLUMN_COMMENTS, CommentsHolder);
        // Update the data in the database
        int rowsUpdated = database.update(DealerManagementDatabaseHelper.VEHICLES_TABLE_NAME,values,DealerManagementDatabaseHelper.VEHICLES_COLUMN_NUMBERPLATE + " = ?",new String[]{NumberPlateHolder});
        // Show a message indicating whether the update was successful
        if (rowsUpdated > 0) {
            // Getting the necessary columns for the data to be entered into the database
            NumberPlate.setText(NumberPlateHolder);
            VehicleMake.setText(VehicleMakeHolder);
            VehicleModel.setText(VehicleModelHolder);
            VehicleYear.setText(VehicleYearHolder);
            ServiceNeeded.setText(ServiceNeededHolder);
            VehicleLocation.setText(VehicleLocationHolder);
            Comments.setText(CommentsHolder);
            // Message to confirm the updated data is saved into the database
            Toast.makeText(VehicleInformationViewActivity.this,"Vehicle data updated successfully!",Toast.LENGTH_LONG).show();
            NumberPlate.getText().clear();
            VehicleMake.getText().clear();
            VehicleModel.getText().clear();
            VehicleYear.getText().clear();
            ServiceNeeded.getText().clear();
            VehicleLocation.getText().clear();
            Comments.getText().clear();
        }
        else {
            // Message to deny the updated data is saved into the database
            Toast.makeText(VehicleInformationViewActivity.this,"Vehicle data update failed!",Toast.LENGTH_LONG).show();
        }
        database.close();
    }
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
                ServiceNeeded.setText(cursor.getString(cursor.getColumnIndex(DealerManagementDatabaseHelper.VEHICLES_COLUMN_SERVICENEEDED)));
                VehicleLocation.setText(cursor.getString(cursor.getColumnIndex(DealerManagementDatabaseHelper.VEHICLES_COLUMN_VEHICLELOCATION)));
                Comments.setText(cursor.getString(cursor.getColumnIndex(DealerManagementDatabaseHelper.VEHICLES_COLUMN_COMMENTS)));
            } else {
                // Message if vehicle not found in the database
                Toast.makeText(VehicleInformationViewActivity.this,"Vehicle not in database!",Toast.LENGTH_LONG).show();
            }
            cursor.close();
        }
    }
    private void deleteData() {
        NumberPlateHolder = NumberPlate.getText().toString().trim();
        database = dbHelper.getWritableDatabase();
        // Delete the data from the database
        int rowsDeleted = database.delete(DealerManagementDatabaseHelper.VEHICLES_TABLE_NAME, DealerManagementDatabaseHelper.VEHICLES_COLUMN_NUMBERPLATE + " = ?", new String[]{NumberPlateHolder});
        // Show a message indicating if the delete was successful or not
        if (rowsDeleted > 0) {
            // Message to confirm the deletion of vehicle from database
            Toast.makeText(VehicleInformationViewActivity.this, "Vehicle data deleted successfully!", Toast.LENGTH_LONG).show();
            NumberPlate.getText().clear();
            VehicleMake.getText().clear();
            VehicleModel.getText().clear();
            VehicleYear.getText().clear();
            ServiceNeeded.getText().clear();
            VehicleLocation.getText().clear();
            Comments.getText().clear();
        }
        else {
            // Message to deny the deletion of vehicle from database
            Toast.makeText(VehicleInformationViewActivity.this,"Failed to delete vehicle data!",Toast.LENGTH_LONG).show();
        }
        database.close();
    }
}
