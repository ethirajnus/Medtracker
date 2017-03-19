package sg.edu.nus.iss.se.ft05.medipal.Util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.activities.MainActivity;

import static sg.edu.nus.iss.se.ft05.medipal.constants.Constants.CONSUMPTION_MESSAGE;
import static sg.edu.nus.iss.se.ft05.medipal.constants.Constants.MEDICINE;

/**
 * Created by ethi on 14/03/17.
 */

public class NotificationUtils {

    private static final int MEDICINE_REMINDER_PENDING_INTENT_ID = 3417;
    private static int MEDICINE_REMINDER_NOTIFICATION_ID = 0;

    private static PendingIntent contentIntent(Context context) {
        Intent startActivityIntent = new Intent(context, MainActivity.class);
        return PendingIntent.getActivity(
                context,
                MEDICINE_REMINDER_PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static void remindUserForConsumption(Context context, String name) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setContentText(constructNotificationMessage(name))
                .setSmallIcon(R.drawable.ic_menu_gallery)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        constructNotificationMessage(name)))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context))
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notificationBuilder.setPriority(Notification.PRIORITY_HIGH);
        }


        NotificationManager notificationManager = (NotificationManager)
        context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(MEDICINE_REMINDER_NOTIFICATION_ID++, notificationBuilder.build());
    }

    public static String constructNotificationMessage(String medicineName){
        return CONSUMPTION_MESSAGE + " " +medicineName + " " + MEDICINE;
    }

}
