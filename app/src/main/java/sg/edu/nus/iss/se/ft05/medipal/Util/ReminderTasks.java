package sg.edu.nus.iss.se.ft05.medipal.Util;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.PersistableBundle;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import sg.edu.nus.iss.se.ft05.medipal.model.Appointment;
import sg.edu.nus.iss.se.ft05.medipal.model.Consumption;
import sg.edu.nus.iss.se.ft05.medipal.model.Medicine;
import sg.edu.nus.iss.se.ft05.medipal.model.Reminder;

import static sg.edu.nus.iss.se.ft05.medipal.constants.Constants.*;

/**
 * Created by ethi on 15/03/17.
 */

public class ReminderTasks {

    public static int medicineConsumptionReminder = 0;
    public static int appointmentReminderNotification = 0;
    public static final String ACTION_MEDICINE_REMINDER = "notifyMedicineConsumption";
    public static final String ACTION_APPOINTMENT_REMINDER = "notifyAppointmentReminder";
    static JobScheduler jobConsumptionScheduler;
    static JobScheduler jobAppointmentScheduler;
    private static long set_time,current_time,interval;

    public static void executeTask(Context context, String action) {
        if (ACTION_MEDICINE_REMINDER.equals(action)) {
            medicineConsumptionReminder(context);
        } else if (ACTION_APPOINTMENT_REMINDER.equals(action)) {
            appointmentReminder(context);
        }
    }

    synchronized public static void medicineConsumptionReminder(Context context) {
        int medicineId,reminderId;
        Map<Integer, Integer> medicineList = Medicine.listAllMedicine(context);
        for (Map.Entry<Integer, Integer> entry : medicineList.entrySet()) {
            medicineId = entry.getKey();
            reminderId = entry.getValue();
            Medicine medicine = Medicine.findById(context, medicineId);
            //add Consumption
            Calendar yesterday = Calendar.getInstance();
            yesterday.add(Calendar.DATE, -1);
            String yesterdayDate = new SimpleDateFormat(DATE_FORMAT).format(yesterday.getTime());
            List<Consumption> consumptions = Consumption.filterByDate(medicine.consumptions(context),yesterdayDate);
            int medicineFrequency = medicine.getReminder(context).getFrequency();
            if (consumptions.size() < medicineFrequency){
                for(int i =0;i < (medicineFrequency - consumptions.size());i++){
                    Consumption consumption = new Consumption(medicineId,0,yesterdayDate,new SimpleDateFormat("HH:mm").format(yesterday.getTime()));
                    consumption.save(context);
                }
            }
            if (medicine.getRemind()) {
                Reminder reminder = Reminder.findById(context, reminderId);
                Calendar calendar = Calendar.getInstance();
                current_time = calendar.getTimeInMillis();
                String time[] = reminder.getStartTime().split(":");
                int hour = Integer.parseInt(time[0]);
                int minute = Integer.parseInt(time[1]);
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                set_time = calendar.getTimeInMillis();
                interval = set_time - current_time;
                if(interval<0){
                    calendar = Calendar.getInstance();
                    calendar.add(Calendar.DATE,1);
                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    calendar.set(Calendar.MINUTE, minute);
                    set_time = calendar.getTimeInMillis();
                    interval = set_time - current_time;
                }

                PersistableBundle b = new PersistableBundle();
                b.putString(MEDICINE_NAME, medicine.getName());
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
        List<Appointment> appointments = Appointment.findByDate(context, date);
        for (Appointment appointment : appointments){
            Calendar calendar = Calendar.getInstance();
            long current_time = calendar.getTimeInMillis();
            String time[] = appointment.getTime().split(":");
            int hour = Integer.parseInt(time[0]);
            int minute = Integer.parseInt(time[1]);
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            long set_time = calendar.getTimeInMillis();
            long interval = set_time - current_time;
            if(interval < (MINUTE * HOUR) || interval < 0 ){
                continue;
            }
            PersistableBundle b = new PersistableBundle();
            b.putString(CLINIC, appointment.getClinic());
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
