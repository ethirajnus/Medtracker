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
        ReminderTasks.executeTask(MedicineReminderJobService.this, ReminderTasks.ACTION_MEDICINE_REMINDER);
        jobFinished(jobParameters, false);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }
}
