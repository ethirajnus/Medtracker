package sg.edu.nus.iss.se.ft05.medipal.Util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import sg.edu.nus.iss.se.ft05.medipal.Medicine;
import sg.edu.nus.iss.se.ft05.medipal.Reminder;
import sg.edu.nus.iss.se.ft05.medipal.fragments.ConsumptionFragment;

import static sg.edu.nus.iss.se.ft05.medipal.constants.Constants.*;

/**
 * Created by ethi on 15/03/17.
 */

public class ReminderTasks {

    public static int medicineConsumptionReminder = 0;
    public static final String ACTION_MEDICINE_REMINDER = "notifyMedicineConsumption";
    static JobScheduler jobScheduler;

    public static void executeTask(Context context, String action) {
        if (ACTION_MEDICINE_REMINDER.equals(action)) {
            medicineConsumptionReminder(context);
        }
    }

    synchronized public static void medicineConsumptionReminder(Context context) {
        int medicineId,reminderId;
        Map<Integer, Integer> medicineList = Medicine.listAllMedicine(context);
        for (Map.Entry<Integer, Integer> entry : medicineList.entrySet()) {
            medicineId = entry.getKey();
            reminderId = entry.getValue();
            Medicine medicine = Medicine.findById(context, medicineId);
            if (medicine.getRemind()) {
                Reminder reminder = Reminder.findById(context, reminderId);
                Calendar calendar = Calendar.getInstance();
                long current_time = calendar.getTimeInMillis();
                String time[] = reminder.getStartTime().split(":");
                int hour = Integer.parseInt(time[0]);
                int minute = Integer.parseInt(time[1]);
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                long set_time = calendar.getTimeInMillis();
                long interval = set_time - current_time;
                PersistableBundle b = new PersistableBundle();
                b.putString(MEDICINE_NAME, medicine.getName());
                for (int frequency = 0; frequency < reminder.getFrequency(); frequency++) {
                    long intervalBetweenConsumption = reminder.getInterval() * frequency * MINUTE;
                    ComponentName mServiceComponent = new ComponentName(context, MedicineConsumptionReminderJobService.class);
                    JobInfo.Builder builder = new JobInfo.Builder(medicineConsumptionReminder++, mServiceComponent);
                    builder.setMinimumLatency(interval + intervalBetweenConsumption);
                    builder.setOverrideDeadline(interval + intervalBetweenConsumption + MINUTE);
                    builder.setExtras(b);
                    jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
                    jobScheduler.schedule(builder.build());
                }
            }
        }
    }
}
