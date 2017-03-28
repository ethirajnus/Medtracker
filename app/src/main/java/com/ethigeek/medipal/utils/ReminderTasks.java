package com.ethigeek.medipal.utils;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.PersistableBundle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.ethigeek.medipal.domain.Medicine;
import com.ethigeek.medipal.domain.Appointment;
import com.ethigeek.medipal.domain.Reminder;
import com.ethigeek.medipal.managers.AppointmentManager;
import com.ethigeek.medipal.managers.ConsumptionManager;
import com.ethigeek.medipal.managers.MedicineManager;
import com.ethigeek.medipal.managers.ReminderManager;

import static com.ethigeek.medipal.utils.Constants.*;



/**
 * class for reminder tasks
 * @author Ethiraj Srinivasan
 */
public class ReminderTasks {

    public static int medicineConsumptionReminder = 0;
    public static int appointmentReminderNotification = 0;
    public static final String ACTION_MEDICINE_REMINDER = "notifyMedicineConsumption";
    public static final String ACTION_APPOINTMENT_REMINDER = "notifyAppointmentReminder";
    static JobScheduler jobConsumptionScheduler;
    static JobScheduler jobAppointmentScheduler;
    private static long set_time, current_time, interval;
    private static Medicine medicine;
    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            DATE_FORMAT, Locale.ENGLISH);

    public static void executeTask(Context context, String action) {
        if (ACTION_MEDICINE_REMINDER.equals(action)) {
            try {
                medicineConsumptionReminder(context);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if (ACTION_APPOINTMENT_REMINDER.equals(action)) {
            appointmentReminder(context);
        }
    }

    synchronized public static void medicineConsumptionReminder(Context context) throws ParseException {
        int medicineId, reminderId;
        Map<Integer, Integer> medicineList = MedicineManager.listAllMedicine(context);
        for (Map.Entry<Integer, Integer> entry : medicineList.entrySet()) {
            medicineId = entry.getKey();
            reminderId = entry.getValue();
            MedicineManager medicineManager = new MedicineManager();
            medicine = medicineManager.findById(context, medicineId);
            Date dateissued = new SimpleDateFormat(DATE_FORMAT).parse(medicine.getDateIssued());
            //add Consumption
            Calendar yesterday = Calendar.getInstance();
            if(yesterday.after(dateissued)){
                yesterday.add(Calendar.DATE, -1);
                String yesterdayDate = new SimpleDateFormat(DATE_FORMAT).format(yesterday.getTime());
                List<String> medicineTimeList = medicineManager.findConsumptionTime(context, medicineId);
                for (String time : medicineTimeList) {
                    Calendar calendar = Calendar.getInstance();
                    if (!ConsumptionManager.exists(context, medicineId, yesterdayDate, time) && !yesterdayDate.contentEquals(formatter.format(calendar.getTime()))) {
                        ConsumptionManager consumptionManager = new ConsumptionManager(medicineId, 0, yesterdayDate, time);
                        consumptionManager.save(context);
                    }
                }
            }
            if (medicineManager.getMedicine().getRemind()) {
                ReminderManager reminderManager = new ReminderManager();

                Reminder reminder = reminderManager.findById(context, reminderId);
                Calendar calendar = Calendar.getInstance();
                current_time = calendar.getTimeInMillis();
                String time[] = reminder.getStartTime().split(":");
                int hour = Integer.parseInt(time[0]);
                int minute = Integer.parseInt(time[1]);
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                set_time = calendar.getTimeInMillis();
                interval = set_time - current_time;
                if (interval < 0) {
                    calendar = Calendar.getInstance();
                    calendar.add(Calendar.DATE, 1);
                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    calendar.set(Calendar.MINUTE, minute);
                    set_time = calendar.getTimeInMillis();
                    interval = set_time - current_time;
                }

                PersistableBundle b = new PersistableBundle();
                b.putString(MEDICINE_NAME, medicine.getName());
                b.putInt(QUANTITY, medicine.getConsumeQuantity());
                b.putInt(ID, medicine.getId());
                for (int frequency = 0; frequency < reminder.getFrequency(); frequency++) {
                    long intervalBetweenConsumption = reminder.getInterval() * frequency * MINUTE;
                    ComponentName mServiceComponent = new ComponentName(context, MedicineConsumptionReminderJobService.class);
                    JobInfo.Builder builder = new JobInfo.Builder(medicineConsumptionReminder++, mServiceComponent);
                    builder.setMinimumLatency(interval + intervalBetweenConsumption);
                    builder.setOverrideDeadline(interval + intervalBetweenConsumption + MINUTE);
                    builder.setExtras(b);
                    jobConsumptionScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
                    jobConsumptionScheduler.schedule(builder.build());
                }
            }
        }
    }


    synchronized public static void appointmentReminder(Context context) {
        String date = new SimpleDateFormat(DATE_FORMAT).format(new Date());
        List<Appointment> appointmentList = AppointmentManager.findByDate(context, date);
        for (Appointment appointment : appointmentList) {
            Calendar calendar = Calendar.getInstance();
            long current_time = calendar.getTimeInMillis();
            String time[] = appointment.getTime().split(":");
            int hour = Integer.parseInt(time[0]);
            int minute = Integer.parseInt(time[1]);
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            long set_time = calendar.getTimeInMillis();
            long interval = set_time - current_time;
            if (interval < (MINUTE * HOUR) || interval < 0) {
                continue;
            }
            PersistableBundle b = new PersistableBundle();
            b.putString(CLINIC, appointment.getClinic());
            b.putInt(ID, appointment.getId());
            ComponentName mServiceComponent = new ComponentName(context, AppointmentReminderNotificationJobService.class);
            JobInfo.Builder builder = new JobInfo.Builder(appointmentReminderNotification++, mServiceComponent);
            builder.setMinimumLatency(interval - (MINUTE * HOUR));
            builder.setOverrideDeadline(interval - (MINUTE * HOUR) + MINUTE);
            builder.setExtras(b);
            jobAppointmentScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
            jobAppointmentScheduler.schedule(builder.build());
        }

    }
}
