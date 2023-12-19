package com.example.bounce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
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
        //request permissions if they dont exists
        if(!Permissions.hasAllPermissions(this, permissions)){
            Permissions.requestAllPermissions(this, permissions, PERMISSION_CODE);
        }
        //Conencting button to method
        Button connectButton = findViewById(R.id.connect);
        connectButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                connectToDesktop();
            }
        });


    }
    // Handle the permissions request response
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_CODE) {
            // Check if the permissions have been granted
            if (Permissions.hasAllPermissions(this, permissions)) {
                Toast.makeText(this, "SMS permissions granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "SMS permissions denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void connectToDesktop(){
        Intent serviceIntent = new Intent(this, WebSocketReceiver.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                NotificationChannel channel = new NotificationChannel(
//                        "my_channel_id",
//                        "My Channel Name",
//                        NotificationManager.IMPORTANCE_DEFAULT
//                );
//                NotificationManager notificationManager = getSystemService(NotificationManager.class);
//                notificationManager.createNotificationChannel(channel);
            startForegroundService(serviceIntent); // For Android Oreo and later
        } else {
            startService(serviceIntent); // For pre-Oreo devices
        }
    }
}
