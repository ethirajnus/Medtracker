package sg.edu.nus.iss.se.ft05.medipal.Listeners;

import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by ashish katre on 3/18/2017.
 */

public class PhoneCallListener extends PhoneStateListener {

    private boolean isPhoneCalling = false;

    public static final String LOG_TAG = "PhoneCallListener";

    private Context context;

    public PhoneCallListener(Context context) {

        this.context = context;
    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {

        if (TelephonyManager.CALL_STATE_RINGING == state) {
            // phone ringing
            Log.i(LOG_TAG, "RINGING, number: " + incomingNumber);
        }

        if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
            // active
            Log.i(LOG_TAG, "OFFHOOK");

            isPhoneCalling = true;
        }

        if (TelephonyManager.CALL_STATE_IDLE == state) {
            // run when class initial and phone call ended,
            // need detect flag from CALL_STATE_OFFHOOK
            Log.i(LOG_TAG, "IDLE");

            if (isPhoneCalling) {

                Log.i(LOG_TAG, "restart app");

                // restart app
                Intent intent = context.getPackageManager()
                        .getLaunchIntentForPackage(
                                context.getPackageName());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);

                isPhoneCalling = false;
            }
        }
    }
}


