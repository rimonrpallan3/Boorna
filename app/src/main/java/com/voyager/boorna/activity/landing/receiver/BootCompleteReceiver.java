package com.voyager.boorna.activity.landing.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.voyager.boorna.activity.landing.services.LocationJobIntentService;


public class BootCompleteReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Intent mIntent = new Intent(context, LocationJobIntentService.class);
            mIntent.putExtra("maxCountValue", 1000);
            LocationJobIntentService.enqueueWork(context, mIntent);
        }
    }
}
