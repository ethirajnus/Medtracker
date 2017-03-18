package sg.edu.nus.iss.se.ft05.medipal.Util;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.support.annotation.NonNull;


import static sg.edu.nus.iss.se.ft05.medipal.constants.Constants.*;

/**
 * Created by ethi on 15/03/17.
 */

public class ReminderUtils {

    private static boolean sInitialized;
    private static int jobId = 0;
    private static JobScheduler jobScheduler;

    synchronized public static void scheduleMedicineReminder(@NonNull final Context context) {
        if (sInitialized) return;
        ComponentName mServiceComponent = new ComponentName(context, MedicineReminderJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(jobId++, mServiceComponent);
        builder.setPeriodic(MINUTE * HOUR * DAY);
        jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(builder.build());
        sInitialized = true;
    }

    public static void syncMedicineReminder(Context context) {
        if (jobScheduler != null) {
            jobScheduler.cancelAll();
            sInitialized = false;
        }
        if (ReminderTasks.jobScheduler != null) {
            ReminderTasks.jobScheduler.cancelAll();
        }
        ReminderUtils.scheduleMedicineReminder(context);
    }
}
