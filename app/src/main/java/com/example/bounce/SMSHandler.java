package com.example.bounce;

import android.telephony.SmsManager;
import android.util.Log;
import java.util.ArrayList;

public class SMSHandler {
    // handles logic for what kind of message to send.
    public void sendSMS(String phoneNumber, String message) {
        SmsManager smsManager = SmsManager.getDefault();
        ArrayList<String> parts = smsManager.divideMessage(message);

        if (parts.size() <= 1) {
            sendSingleSMS(phoneNumber, message);
        } else {
            sendMultipartSMS(phoneNumber, parts);
        }
    }

    // hanles message that are standard length
    private void sendSingleSMS(String phoneNumber, String message) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Log.d("SmsHandler", "Single SMS sent.");
        } catch (Exception e) {
            Log.e("SmsHandler", "Single SMS failed to send.", e);
        }
    }

    // handles messages longer then standard length
    private void sendMultipartSMS(String phoneNumber, ArrayList<String> parts) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendMultipartTextMessage(phoneNumber, null, parts, null, null);
            Log.d("SmsHandler", "Multipart SMS sent.");
        } catch (Exception e) {
            Log.e("SmsHandler", "Multipart SMS failed to send.", e);
        }
    }
}
