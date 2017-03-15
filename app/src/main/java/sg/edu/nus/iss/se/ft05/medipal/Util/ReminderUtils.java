package sg.edu.nus.iss.se.ft05.medipal.Util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;

import java.sql.Driver;
import java.util.Calendar;

/**
 * Created by ethi on 15/03/17.
 */

public class ReminderUtils {

    private static final long REFRESH_INTERVAL = 3000 ;
    private static boolean sInitialized;

    private static AlarmManager alarmMgr;
    private static PendingIntent alarmIntent;

    private static int jobId = 0;
    synchronized public static void scheduleMedicineReminder(@NonNull final Context context) {

        if (sInitialized) return;

        ComponentName mServiceComponent = new ComponentName(context, MedicineReminderJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(jobId++, mServiceComponent);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.setPeriodic(1000 * 60 * 15);


        } else {
            builder.setPeriodic(1000 * 60 * 15);
        }
        JobScheduler jobScheduler = (JobScheduler)context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(builder.build());

        sInitialized = true;
    }
}
