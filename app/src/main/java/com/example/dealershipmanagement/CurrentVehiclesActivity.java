package com.example.dealershipmanagement;
// Getting the needed imports
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
// Current vehicles activity class
public class CurrentVehiclesActivity extends AppCompatActivity {
    TextView NumberPlateView, VehicleMakeView, VehicleModelView, VehicleYearView, ServiceNeededView, VehicleLocationView, CommentsView;
    String NumberPlate, VehicleMake, VehicleModel, VehicleYear, ServiceNeeded, VehicleLocation, Comments;
    Button DashboardButton;
    SQLiteDatabase database;
    DealerManagementDatabaseHelper dbHelper;
    Cursor cursor;
    TableLayout tableLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_vehicles);
        // Initialize buttons
        DashboardButton = findViewById(R.id.Dashboard_Button);
        // Initialize views
        tableLayout = findViewById(R.id.VehicleTable_View);
        // Initialize database connection
        dbHelper = new DealerManagementDatabaseHelper(this);
        database = dbHelper.getReadableDatabase();
        // Retrieve all vehicle data from database
        cursor = database.rawQuery("SELECT * FROM " + DealerManagementDatabaseHelper.VehicleEntry.VEHICLES_TABLE_NAME, null);
        // Starting the dashboard activity
        DashboardButton.setOnClickListener(view -> {
            Intent intent = new Intent(CurrentVehiclesActivity.this, DashboardActivity.class);
            startActivity(intent);});
        // Bind data to table view
        while (cursor.moveToNext()) {
            TableRow row = new TableRow(this);
            NumberPlateView = new TextView(this);
            VehicleMakeView = new TextView(this);
            VehicleModelView = new TextView(this);
            VehicleYearView = new TextView(this);
            ServiceNeededView = new TextView(this);
            VehicleLocationView = new TextView(this);
            CommentsView = new TextView(this);
            // Getting the data from the database
            NumberPlate = cursor.getString(cursor.getColumnIndexOrThrow(DealerManagementDatabaseHelper.VehicleEntry.VEHICLES_COLUMN_NUMBERPLATE));
            VehicleMake = cursor.getString(cursor.getColumnIndexOrThrow(DealerManagementDatabaseHelper.VehicleEntry.VEHICLES_COLUMN_VEHICLEMAKE));
            VehicleModel = cursor.getString(cursor.getColumnIndexOrThrow(DealerManagementDatabaseHelper.VehicleEntry.VEHICLES_COLUMN_VEHICLEMODEL));
            VehicleYear = cursor.getString(cursor.getColumnIndexOrThrow(DealerManagementDatabaseHelper.VehicleEntry.VEHICLES_COLUMN_VEHICLEYEAR));
            ServiceNeeded = cursor.getString(cursor.getColumnIndexOrThrow(DealerManagementDatabaseHelper.VehicleEntry.VEHICLES_COLUMN_SERVICENEEDED));
            VehicleLocation = cursor.getString(cursor.getColumnIndexOrThrow(DealerManagementDatabaseHelper.VehicleEntry.VEHICLES_COLUMN_VEHICLELOCATION));
            Comments = cursor.getString(cursor.getColumnIndexOrThrow(DealerManagementDatabaseHelper.VehicleEntry.VEHICLES_COLUMN_COMMENTS));
            // Setting the data into the corresponding view text
            NumberPlateView.setText(NumberPlate);
            VehicleMakeView.setText(VehicleMake);
            VehicleModelView.setText(VehicleModel);
            VehicleYearView.setText(VehicleYear);
            ServiceNeededView.setText(ServiceNeeded);
            VehicleLocationView.setText(VehicleLocation);
            CommentsView.setText(Comments);
            // Adding the rows to the table layout
            row.addView(NumberPlateView);
            row.addView(VehicleMakeView);
            row.addView(VehicleModelView);
            row.addView(VehicleYearView);
            row.addView(ServiceNeededView);
            row.addView(VehicleLocationView);
            row.addView(CommentsView);
            tableLayout.addView(row);
        }
    }
    // Closing the database connection
    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        database.close();
    }
}
