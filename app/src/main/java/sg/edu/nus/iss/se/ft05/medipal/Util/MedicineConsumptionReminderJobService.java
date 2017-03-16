package sg.edu.nus.iss.se.ft05.medipal.Util;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

/**
 * Created by ethi on 15/03/17.
 */

public class MedicineConsumptionReminderJobService  extends JobService {


    @Override
    public boolean onStartJob(final JobParameters jobParameters) {


        PersistableBundle b = jobParameters.getExtras();
        String medicineName = b.getString("medicineName");
        Log.v("inside consumptionremin","medi");
        NotificationUtils.remindUserForConsumption(MedicineConsumptionReminderJobService.this,medicineName);
        jobFinished(jobParameters, false);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        // COMPLETED (12) If mBackgroundTask is valid, cancel it
        // COMPLETED (13) Return true to signify the job should be retried
        Log.v("job id",String.valueOf(jobParameters.getJobId()));
        return true;
    }
}
