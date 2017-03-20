package sg.edu.nus.iss.se.ft05.medipal.Util;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.PersistableBundle;

import static sg.edu.nus.iss.se.ft05.medipal.constants.Constants.CLINIC;

/**
 * Created by ethi on 19/03/17.
 */

public class AppointmentReminderNotificationJobService extends JobService {

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        PersistableBundle b = jobParameters.getExtras();
        String clinicName = b.getString(CLINIC);
        NotificationUtils.remindUserForAppointment(AppointmentReminderNotificationJobService.this,clinicName);
        jobFinished(jobParameters, false);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }
}
