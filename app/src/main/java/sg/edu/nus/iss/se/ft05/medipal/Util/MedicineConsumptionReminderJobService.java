package sg.edu.nus.iss.se.ft05.medipal.Util;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import static sg.edu.nus.iss.se.ft05.medipal.constants.Constants.MEDICINE_NAME;

/**
 * Created by ethi on 15/03/17.
 */

public class MedicineConsumptionReminderJobService  extends JobService {


    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        PersistableBundle b = jobParameters.getExtras();
        String medicineName = b.getString(MEDICINE_NAME);
        NotificationUtils.remindUserForConsumption(MedicineConsumptionReminderJobService.this,medicineName);
        jobFinished(jobParameters, false);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }
}
