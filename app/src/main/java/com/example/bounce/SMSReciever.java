package com.example.bounce;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class SMSReciever extends BroadcastReceiver {
    private static final String TAG = "SMSReciever";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            String message = extractMessage(intent);
            Data inputData = new Data.Builder().putString("message", message).build();

            OneTimeWorkRequest sendSmsWorkRequest = new OneTimeWorkRequest.Builder(SmsSender.class)
                    .setInputData(inputData)
                    .build();

            WorkManager.getInstance(context).enqueue(sendSmsWorkRequest);
        }
    }



    private String extractMessage(Intent intent){
        StringBuilder sb = new StringBuilder();
        for(SmsMessage smsMessage: Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
            String messageBody = smsMessage.getMessageBody();
            String address = smsMessage.getDisplayOriginatingAddress();
            Log.d(TAG, messageBody);
            Log.d(TAG, address);
            sb.append(address + " : " + messageBody);
        }
        return sb.toString();
    }
}