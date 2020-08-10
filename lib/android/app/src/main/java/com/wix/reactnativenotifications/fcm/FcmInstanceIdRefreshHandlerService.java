package com.wix.reactnativenotifications.fcm;

import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import static com.wix.reactnativenotifications.Defs.LOGTAG;

public class FcmInstanceIdRefreshHandlerService extends JobIntentService {

    public static String EXTRA_IS_APP_INIT = "isAppInit";
    public static String EXTRA_MANUAL_REFRESH = "doManualRefresh";

    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, FcmInstanceIdRefreshHandlerService.class, JOB_ID, work);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.v(LOGCAT, "Handling FCM token refresh");
        IFcmToken fcmToken = FcmToken.get(this);
        if (fcmToken == null) {
            return;
        }

        if (intent.getBooleanExtra(EXTRA_IS_APP_INIT, false)) {
            fcmToken.onAppReady();
        } else if (intent.getBooleanExtra(EXTRA_MANUAL_REFRESH, false)) {
            fcmToken.onManualRefresh();
        } else {
            fcmToken.onNewTokenReady();
        }
    }
}
