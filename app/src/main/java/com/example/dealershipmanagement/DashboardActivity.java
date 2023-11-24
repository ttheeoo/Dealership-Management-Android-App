package com.example.dealershipmanagement;
// Getting the list of imports needed
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
// Dashboard activity class
public class DashboardActivity extends AppCompatActivity {
    Button LocateButton, BookButton, VehicleInfoButton, CurrentVehicleButton, SignOutButton;
    // List of tasks done on the creation of the activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        // Initialize buttons
        LocateButton = findViewById(R.id.LocateVehicle_Button);
        BookButton = findViewById(R.id.BookVehicle_Button);
        VehicleInfoButton = findViewById(R.id.VehicleInfo_Button);
        CurrentVehicleButton = findViewById(R.id.CurrentVehicles_Button);
        SignOutButton = findViewById(R.id.Signout_Button);
        // Starting the locate vehicle activity
        LocateButton.setOnClickListener(view -> {
            Intent intent = new Intent(DashboardActivity.this, VehicleLocatorSearchActivity.class);
            startActivity(intent);
        });
        // Starting the book vehicle activity
        BookButton.setOnClickListener(view -> {
            Intent intent = new Intent(DashboardActivity.this, BookVehicleActivity.class);
            startActivity(intent);});
        // Starting the vehicle information activity
        VehicleInfoButton.setOnClickListener(view -> {
            Intent intent = new Intent(DashboardActivity.this, VehicleInformationSearchActivity.class);
            startActivity(intent);});
        // Starting the current vehicles activity
        CurrentVehicleButton.setOnClickListener(view -> {
            Intent intent = new Intent(DashboardActivity.this, CurrentVehiclesActivity.class);
            startActivity(intent);});
        // Button to signout
        SignOutButton.setOnClickListener(view -> {
            Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
            startActivity(intent);});
    }
}