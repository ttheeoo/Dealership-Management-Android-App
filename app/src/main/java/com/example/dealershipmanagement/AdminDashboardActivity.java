package com.example.dealershipmanagement;
// Getting the list of imports needed
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
// Admin Dashboard activity class
public class AdminDashboardActivity extends AppCompatActivity {
    Button LocateButton, BookButton, VehicleInfoButton, CurrentVehicleButton, RegisterEmployees, EmployeeInformation, CurrentEmployees, SignOutButton;
    // List of tasks done on the creation of the activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        // Linking button holder to buttons
        LocateButton = findViewById(R.id.LocateVehicle_Button);
        BookButton = findViewById(R.id.BookVehicle_Button);
        VehicleInfoButton = findViewById(R.id.VehicleInfo_Button);
        CurrentVehicleButton = findViewById(R.id.CurrentVehicles_Button);
        RegisterEmployees = findViewById(R.id.RegisterEmployee_Button);
        EmployeeInformation = findViewById(R.id.EmployeeInformation_Button);
        CurrentEmployees = findViewById(R.id.CurrentEmployees_Button);
        SignOutButton = findViewById(R.id.Signout_Button);
        // Starting the locate vehicle activity
        LocateButton.setOnClickListener(view -> {
            Intent intent = new Intent(AdminDashboardActivity.this, VehicleLocatorSearchActivity.class);
            startActivity(intent);
        });
        // Starting the book vehicle activity
        BookButton.setOnClickListener(view -> {
            Intent intent = new Intent(AdminDashboardActivity.this, BookVehicleActivity.class);
            startActivity(intent);});
        // Starting the vehicle information activity
        VehicleInfoButton.setOnClickListener(view -> {
            Intent intent = new Intent(AdminDashboardActivity.this, VehicleInformationSearchActivity.class);
            startActivity(intent);});
        // Starting the current vehicles activity
        CurrentVehicleButton.setOnClickListener(view -> {
            Intent intent = new Intent(AdminDashboardActivity.this, CurrentVehiclesActivity.class);
            startActivity(intent);});
        // Starting the register employee information activity
        RegisterEmployees.setOnClickListener(view -> {
            Intent intent = new Intent(AdminDashboardActivity.this, RegisterActivity.class);
            startActivity(intent);});
        // Starting the employee information activity
        EmployeeInformation.setOnClickListener(view -> {
            Intent intent = new Intent(AdminDashboardActivity.this, EmployeeInformationSearchActivity.class);
            startActivity(intent);});
        // Starting the current employee activity
        CurrentEmployees.setOnClickListener(view -> {
            Intent intent = new Intent(AdminDashboardActivity.this, CurrentEmployeesActivity.class);
            startActivity(intent);});
        // Button to signout
        SignOutButton.setOnClickListener(view -> {
            Intent intent = new Intent(AdminDashboardActivity.this, LoginActivity.class);
            startActivity(intent);});
    }
}