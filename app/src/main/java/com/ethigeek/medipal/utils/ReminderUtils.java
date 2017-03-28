package com.ethigeek.medipal.utils;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.support.annotation.NonNull;


import static com.ethigeek.medipal.utils.Constants.*;



/**
 * class for reminder utils
 * @author Ethiraj Srinivasan
 */
public class ReminderUtils {

    private static boolean sMedicineReminderInitialized;
    private static boolean sAppointmentReminderInitialized;
    private static int medicineConsumptionReminderJobId = 0;
    private static int appointmentReminderJobId = 0;
    private static JobScheduler jobConsumptionScheduler;
    private static JobScheduler jobAppointmentScheduler;

    synchronized public static void scheduleMedicineReminder(@NonNull final Context context) {
        if (sMedicineReminderInitialized) return;
        ComponentName mServiceComponent = new ComponentName(context, MedicineReminderJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(medicineConsumptionReminderJobId++, mServiceComponent);
        builder.setPeriodic(MINUTE * HOUR * DAY);
        jobConsumptionScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobConsumptionScheduler.schedule(builder.build());
        sMedicineReminderInitialized = true;
    }

    /**
     *
     * @param context
     */
    public static void syncMedicineReminder(Context context) {
        if (jobConsumptionScheduler != null) {
            jobConsumptionScheduler.cancelAll();
            sMedicineReminderInitialized = false;
        }
        if (ReminderTasks.jobConsumptionScheduler != null) {
            ReminderTasks.jobConsumptionScheduler.cancelAll();
        }
        ReminderUtils.scheduleMedicineReminder(context);
    }

    synchronized public static void scheduleAppointmentReminder(@NonNull final Context context) {
        if (sAppointmentReminderInitialized) return;
        ComponentName mServiceComponent = new ComponentName(context, AppointmentReminderJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(appointmentReminderJobId++, mServiceComponent);
        builder.setPeriodic(MINUTE * HOUR);
        jobAppointmentScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobAppointmentScheduler.schedule(builder.build());
        sAppointmentReminderInitialized = true;
    }

    /**
     *
     * @param context
     */
    public static void syncAppointmentReminder(Context context) {
        if (jobAppointmentScheduler != null) {
            jobAppointmentScheduler.cancelAll();
            sAppointmentReminderInitialized = false;
        }
        if (ReminderTasks.jobAppointmentScheduler != null) {
            ReminderTasks.jobAppointmentScheduler.cancelAll();
        }
        ReminderUtils.scheduleAppointmentReminder(context);
    }
}
