package sg.edu.nus.iss.se.ft05.medipal.utils;

import android.app.job.JobParameters;
import android.app.job.JobService;



/**
 * Class for Appointment reminder job service
 * Created by ethiraj srinivasan on 19/03/17.
 */
public class AppointmentReminderJobService extends JobService {
    /**
     *
     * @param jobParameters
     * @return
     */
    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        ReminderTasks.executeTask(AppointmentReminderJobService.this, ReminderTasks.ACTION_APPOINTMENT_REMINDER);
        jobFinished(jobParameters, false);
        return true;
    }

    /**
     *
     * @param jobParameters
     * @return
     */
    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }
}
