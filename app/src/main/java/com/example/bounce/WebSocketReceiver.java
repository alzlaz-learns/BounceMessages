package com.example.bounce;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class WebSocketReceiver extends Service {
    private String TAG = "WebSocketReciever";
    private WebSocket ws;

    private Map<String, String> phoneNumberToMessageMap = new HashMap<>();
    private static final String TARGET_IP = "10.0.2.2";
    private static final int TARGET_PORT = 8081;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Start webSocket
        startWebSocket();
        // Start as a foreground service
        startForeground(1, createNotification());
        return START_STICKY;
    }

    private void startWebSocket() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("ws://10.0.2.2:9091").build();
        ws = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onMessage(WebSocket webSocket, String text) {
                Log.d(TAG, " recieving from webSocket: " + text);
                if ("OpenTcpConnection".equals(text)) {
                    openTcpConnection();
                }
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                Log.e(TAG, "WebSocket error: " + t.getMessage());
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                Log.d(TAG, "WebSocket closed: " + reason);
            }
        });

    }

    private Notification createNotification() {

        return new NotificationCompat.Builder(this, "my_channel_id")
                .setContentTitle("WebSocket Service")
                .setContentText("Listening for messages...")
                // Replace with actual drawable resource
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .build();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Close the WebSocket connection
        if (ws != null) {
            ws.close(1000, null);
        }
    }
    private void openTcpConnection() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try (Socket socket = new Socket(TARGET_IP, TARGET_PORT)) {

                    Log.d(TAG, "TCP Connection established");
                    readDataFromTcpConnection(socket);
                } catch (IOException e) {
                    Log.e(TAG, "TCP Connection error: " + e.getMessage());
                }
            }
        }).start();
    }

    private void readDataFromTcpConnection(Socket socket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                processLine(line);
            }
        } catch (IOException e) {
            Log.e(TAG, "Error reading from TCP connection: " + e.getMessage());
        }
    }

    private void processLine(String line) {
        String[] parts = line.split(" ", 2);
        if (parts.length == 2) {
            String phoneNumber = parts[0];
            String message = parts[1];
            phoneNumberToMessageMap.put(phoneNumber, message);
            SMSHandler smsHandler = new SMSHandler();
            smsHandler.sendSMS(phoneNumber, message);
        }
    }
}
