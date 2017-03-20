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
    private static int APPOINTMENT_REMINDER_NOTIFICATION_ID = 0;
    private static int REPLENISH_REMINDER_NOTIFICATION_ID = 0;

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
                .setContentText(constructNotificationMessageForConsumption(name))
                .setSmallIcon(R.drawable.ic_menu_gallery)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        constructNotificationMessageForConsumption(name)))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context))
                .setAutoCancel(true);

            notificationBuilder.setPriority(Notification.PRIORITY_HIGH);


        NotificationManager notificationManager = (NotificationManager)
        context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(MEDICINE_REMINDER_NOTIFICATION_ID++, notificationBuilder.build());
    }

    public static String constructNotificationMessageForConsumption(String medicineName){
        return CONSUMPTION_MESSAGE + " " +medicineName + " " + MEDICINE;
    }


    public static void replenishReminder(Context context, String medicineName) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setContentText(constructNotificationMessageForReplenish(medicineName))
                .setSmallIcon(R.drawable.ic_menu_gallery)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        constructNotificationMessageForReplenish(medicineName)))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context))
                .setAutoCancel(true);

        notificationBuilder.setPriority(Notification.PRIORITY_HIGH);


        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(REPLENISH_REMINDER_NOTIFICATION_ID++, notificationBuilder.build());
    }

    public static void remindUserForAppointment(Context context, String clinicName) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setContentText(constructNotificationMessageForAppointment(clinicName))
                .setSmallIcon(R.drawable.ic_menu_gallery)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        constructNotificationMessageForAppointment(clinicName)))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context))
                .setAutoCancel(true);

            notificationBuilder.setPriority(Notification.PRIORITY_HIGH);


        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(APPOINTMENT_REMINDER_NOTIFICATION_ID++, notificationBuilder.build());
    }

    private static String constructNotificationMessageForAppointment(String clinicName) {
        return "It is time to visit" + clinicName;
    }

    private static String constructNotificationMessageForReplenish(String medicineName) {
        return "Please replenish this" + medicineName;
    }
}
