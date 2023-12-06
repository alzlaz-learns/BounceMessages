//package com.example.bounce;
//
//import android.app.Notification;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.Service;
//import android.content.Intent;
//import android.os.Build;
//import android.os.IBinder;
//import android.util.Log;
//
//import androidx.annotation.Nullable;
//import androidx.core.app.NotificationCompat;
//
//import java.io.IOException;
//import java.net.Socket;
//import java.net.UnknownHostException;
//
//import android.content.pm.ServiceInfo;
//
//
//public class TCPConnection extends Service {
//
//    // Define the Notification ID
//    private static final int NOTIFICATION_ID = 1;
//
//    // Define the Notification Channel ID
//    private static final String CHANNEL_ID = "tcp_connection_service_channel";
//    public static final String TARGET_IP = "10.0.2.2";
//    public static final int TARGET_PORT = 8081;
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//
//        Log.d("TCPConnection", "onStartCommand");
//        Log.d("TCPConnection", "Service onStartCommand");
//        // Build the notification
//        Notification notification = createNotification();
//
//        // Start in the foreground with the specified service type
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            startForeground(NOTIFICATION_ID, notification,  ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC);
//        }else {
//            startForeground(NOTIFICATION_ID, notification);
//        }
//
//        Log.d("TCPConnection", "Service started in foreground");
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Log.d("TCPConnection", "Network thread running");
//                try (Socket testSocket = new Socket(TARGET_IP, TARGET_PORT)) {
//                    Log.d("TCPConnection", "Attempting to connect to server");
//
//                    Log.d("TCPConnection", "Connection established");
//                    // Perform some operation like reading/writing from/to the socket
//                } catch (UnknownHostException e) {
//                    Log.e("TCPConnection", "Server not found: " + e.getMessage());
//                } catch (IOException e) {
//                    Log.e("TCPConnection", "I/O error: " + e.getMessage());
//                }
//            }
//        }).start();
//
//        Log.d("TCPConnection", "Network thread started");
//        // If we get killed, after returning from here, restart
//        return START_STICKY;
//    }
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    // Called when the service is no longer used and is being destroyed
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        // Clean up resources here, if necessary
//        Log.d("TCPConnection", "Service onDestroy");
//    }
//
//    // Method to create a notification
//    private Notification createNotification() {
//        // Create a notification channel, needed for Android O and above
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = "TCP Connection Service";
//            String description = "The channel for TCP connection service notifications";
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
//            channel.setDescription(description);
//            // Register the channel with the system
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
//
//        // Build the notification
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
//                .setContentTitle("TCP Connection Service")
//                .setContentText("The service is running...")
//                .setSmallIcon(android.R.drawable.ic_dialog_info) // Replace with your actual drawable resource
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//
//        return builder.build();
//    }
//}
