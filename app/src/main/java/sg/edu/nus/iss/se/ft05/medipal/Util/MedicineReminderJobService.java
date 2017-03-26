package sg.edu.nus.iss.se.ft05.medipal.Util;

import android.app.job.JobParameters;
import android.app.job.JobService;

/**
 * Created by ethi on 15/03/17.
 */

/**
 * Class for Medicine reminder job service
 */
public class MedicineReminderJobService extends JobService {
    /**
     *
     * @param jobParameters
     * @return
     */
    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        ReminderTasks.executeTask(MedicineReminderJobService.this, ReminderTasks.ACTION_MEDICINE_REMINDER);
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
