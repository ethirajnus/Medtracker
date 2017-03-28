package com.ethigeek.medtracker.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.ethigeek.medtracker.R;
import com.ethigeek.medtracker.activities.AddOrUpdateConsumptionActivity;
import com.ethigeek.medtracker.activities.ShowAppointmentActivity;
import com.ethigeek.medtracker.activities.ShowMedicineActivity;

import static com.ethigeek.medtracker.utils.Constants.ACTION;
import static com.ethigeek.medtracker.utils.Constants.CONSUMPTION_MESSAGE;
import static com.ethigeek.medtracker.utils.Constants.ID;
import static com.ethigeek.medtracker.utils.Constants.MEDICINE;
import static com.ethigeek.medtracker.utils.Constants.NOTIFICATION;
import static com.ethigeek.medtracker.utils.Constants.QUANTITY;



/**
 * class for notification
 * @author Ethiraj Srinivasan
 */
public class NotificationUtils {

    private static final int MEDICINE_REMINDER_PENDING_INTENT_ID = 3417;
    private static int MEDICINE_REMINDER_NOTIFICATION_ID = 0;
    private static int APPOINTMENT_REMINDER_NOTIFICATION_ID = 0;
    private static int REPLENISH_REMINDER_NOTIFICATION_ID = 0;

    private static PendingIntent consumptionIntent(Context context, int medicineId, int quantity) {
        Intent startActivityIntent = new Intent(context, AddOrUpdateConsumptionActivity.class);
        Bundle b = new Bundle();
        b.putInt(ID,medicineId);
        b.putInt(QUANTITY,quantity);
        b.putString(ACTION,NOTIFICATION);
        startActivityIntent.putExtras(b);
        return PendingIntent.getActivity(
                context,
                MEDICINE_REMINDER_PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static PendingIntent appointmentIntent(Context context,int appointmentId) {
        Intent startActivityIntent = new Intent(context, ShowAppointmentActivity.class);
        Bundle b = new Bundle();
        b.putInt(ID,appointmentId);
        startActivityIntent.putExtras(b);
        return PendingIntent.getActivity(
                context,
                MEDICINE_REMINDER_PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static PendingIntent replenishIntent(Context context,int medicineId) {
        Intent startActivityIntent = new Intent(context, ShowMedicineActivity.class);
        Bundle b = new Bundle();
        b.putInt(ID,medicineId);
        startActivityIntent.putExtras(b);
        return PendingIntent.getActivity(
                context,
                MEDICINE_REMINDER_PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static void remindUserForConsumption(Context context, String name, int medicineId, int quantity) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setContentText(constructNotificationMessageForConsumption(name))
                .setSmallIcon(R.drawable.ic_opacity_black_24dp)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        constructNotificationMessageForConsumption(name)))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(consumptionIntent(context, medicineId, quantity))
                .setAutoCancel(true);

            notificationBuilder.setPriority(Notification.PRIORITY_HIGH);


        NotificationManager notificationManager = (NotificationManager)
        context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(MEDICINE_REMINDER_NOTIFICATION_ID++, notificationBuilder.build());
    }

    public static String constructNotificationMessageForConsumption(String medicineName){
        return CONSUMPTION_MESSAGE + " " +medicineName + " " + MEDICINE;
    }


    public static void replenishReminder(Context context, String medicineName,int medicineId) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setContentText(constructNotificationMessageForReplenish(medicineName))
                .setSmallIcon(R.drawable.ic_add_box_black_24dp)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        constructNotificationMessageForReplenish(medicineName)))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(replenishIntent(context,medicineId))
                .setAutoCancel(true);

        notificationBuilder.setPriority(Notification.PRIORITY_HIGH);


        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(REPLENISH_REMINDER_NOTIFICATION_ID++, notificationBuilder.build());
    }

    public static void remindUserForAppointment(Context context, String clinicName,int appointmentId) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setContentText(constructNotificationMessageForAppointment(clinicName))
                .setSmallIcon(R.drawable.ic_date_range_black_24dp)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        constructNotificationMessageForAppointment(clinicName)))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(appointmentIntent(context,appointmentId))
                .setAutoCancel(true);

            notificationBuilder.setPriority(Notification.PRIORITY_HIGH);


        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(APPOINTMENT_REMINDER_NOTIFICATION_ID++, notificationBuilder.build());
    }

    private static String constructNotificationMessageForAppointment(String clinicName) {
        return "It is time to visit " + clinicName;
    }

    private static String constructNotificationMessageForReplenish(String medicineName) {
        return "Please replenish this " + medicineName;
    }
}
