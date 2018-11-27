package com.ccs.bankebihari.wycairlinesales;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.RequestQueue;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by CCS79 on 25-05-2018.
 */

public class FcmInstanceIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        String recentToken = FirebaseInstanceId.getInstance().getToken();
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.FCM_PREF),MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.FCM_TOKEN),recentToken);
        editor.commit();

    }


}
