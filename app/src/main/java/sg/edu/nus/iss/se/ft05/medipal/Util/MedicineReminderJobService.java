package sg.edu.nus.iss.se.ft05.medipal.Util;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import sg.edu.nus.iss.se.ft05.medipal.Medicine;

/**
 * Created by ethi on 15/03/17.
 */

public class MedicineReminderJobService extends JobService {

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        Log.v("inside reminder","jobservice");
        ReminderTasks.executeTask(MedicineReminderJobService.this, ReminderTasks.ACTION_MEDICINE_REMINDER);
//        NotificationUtils.remindUserForConsumption(getApplicationContext(),"calpal");
        jobFinished(jobParameters, false);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        // COMPLETED (12) If mBackgroundTask is valid, cancel it
        // COMPLETED (13) Return true to signify the job should be retried
        return true;
    }
}
