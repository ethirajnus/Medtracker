package sg.edu.nus.iss.se.ft05.medipal.Util;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.PersistableBundle;

import static sg.edu.nus.iss.se.ft05.medipal.constants.Constants.CLINIC;
import static sg.edu.nus.iss.se.ft05.medipal.constants.Constants.ID;

/**
 * Created by ethi on 19/03/17.
 */

/**
 * Class for appointment reminder notification job service
 */
public class AppointmentReminderNotificationJobService extends JobService {
    /**
     *
     * @param jobParameters
     * @return
     */
    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        PersistableBundle b = jobParameters.getExtras();
        String clinicName = b.getString(CLINIC);
        int appointmentId = b.getInt(ID);
        NotificationUtils.remindUserForAppointment(AppointmentReminderNotificationJobService.this,clinicName,appointmentId);
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
