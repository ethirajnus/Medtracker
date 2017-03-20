package sg.edu.nus.iss.se.ft05.medipal.Util;

import android.app.job.JobParameters;
import android.app.job.JobService;

/**
 * Created by ethi on 19/03/17.
 */

public class AppointmentReminderJobService extends JobService {

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        ReminderTasks.executeTask(AppointmentReminderJobService.this, ReminderTasks.ACTION_APPOINTMENT_REMINDER);
        jobFinished(jobParameters, false);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }
}
