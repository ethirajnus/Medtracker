package sg.edu.nus.iss.se.ft05.medipal.Util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import sg.edu.nus.iss.se.ft05.medipal.Medicine;
import sg.edu.nus.iss.se.ft05.medipal.Reminder;
import sg.edu.nus.iss.se.ft05.medipal.fragments.ConsumptionFragment;

/**
 * Created by ethi on 15/03/17.
 */

public class ReminderTasks {

    public static int medicineConsumptionReminder = 0;
    public static final String ACTION_MEDICINE_REMINDER = "notifyMedicineConsumption";

    public static void executeTask(Context context, String action) {
        if (ACTION_MEDICINE_REMINDER.equals(action)) {
            medicineConsumptionReminder(context);
        }
    }

    private static void medicineConsumptionReminder(Context context) {
        HashMap<Integer,Integer> medicineList = Medicine.listAllMedicine(context);
        for(Map.Entry<Integer, Integer> entry : medicineList.entrySet()) {
            int medicineId = entry.getKey();
            int reminderId = entry.getValue();
            Reminder reminder = Reminder.findById(context,reminderId);
            int index;
//            for(index=0;index<reminder.getFrequency();index++)
//            {

            AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, ConsumptionReminderJobService.class);
            PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

// Set the alarm to start at 8:30 a.m.
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 8);
            calendar.set(Calendar.MINUTE, 5);

// setRepeating() lets you specify a precise custom interval--in this case,
// 20 minutes.
            alarmMgr.setExact(AlarmManager.RTC_WAKEUP, , alarmIntent);
//            }
        }

    }
}
