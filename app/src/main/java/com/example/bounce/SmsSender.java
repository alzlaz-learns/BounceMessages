package com.example.bounce;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class SmsSender extends Worker {

    public static final String TARGET_IP = "10.0.2.2";
    public static final int TARGET_PORT = 8081;

    public static final String TAG = "SmsSender";
    public SmsSender(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String message = getInputData().getString("message");
        try {
            sendMessageToServer(message);
            return Result.success();
        } catch (Exception e) {
            return Result.retry();
        }
    }


    private void sendMessageToServer(String message){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG , "Network thread running");
                try (Socket testSocket = new Socket(TARGET_IP, TARGET_PORT)) {
                    if(!testSocket.isConnected()){
                        Log.e(TAG, "socket is not connected");
                        return;
                    }
                    try(OutputStream out = testSocket.getOutputStream()){
                        Log.d(TAG, "Attempting to connect to server");
                        out.write(message.getBytes());
                        out.flush();
                        Log.d(TAG, "Message sent to server");
                    }
                } catch (UnknownHostException e) {
                    Log.e(TAG, "Server not found: " + e.getMessage());
                } catch (IOException e) {
                    Log.e(TAG, "I/O error: " + e.getMessage());
                }
            }
        }).start();
    }
}
