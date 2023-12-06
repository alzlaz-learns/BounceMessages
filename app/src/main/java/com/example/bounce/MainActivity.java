package com.example.bounce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {

    String TAG = "test";
    private static final int PERMISSION_CODE = 101;
    private String[] permissions = new String[]{
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.SEND_SMS,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.FOREGROUND_SERVICE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: test");
        if(!Permissions.hasAllPermissions(this, permissions)){
            Permissions.requestAllPermissions(this, permissions, PERMISSION_CODE);
        }




    }
    // Handle the permissions request response
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_CODE) {
            // Check if the permissions have been granted
            if (Permissions.hasAllPermissions(this, permissions)) {
                Toast.makeText(this, "SMS permissions granted", Toast.LENGTH_SHORT).show();
//                Intent serviceIntent = new Intent(this, TCPConnection.class);
//                ContextCompat.startForegroundService(this, serviceIntent);
            } else {
                Toast.makeText(this, "SMS permissions denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
