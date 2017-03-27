package sg.edu.nus.iss.se.ft05.medipal.Util;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.PersistableBundle;

import static sg.edu.nus.iss.se.ft05.medipal.constants.Constants.*;



/**
 * Class for Medicine consumption reminder job service
 * Created by ethiraj srinivasan on 15/03/17.
 */
public class MedicineConsumptionReminderJobService  extends JobService {

    /**
     *
     * @param jobParameters
     * @return
     */
    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        PersistableBundle b = jobParameters.getExtras();
        String medicineName = b.getString(MEDICINE_NAME);
        int medicineId = b.getInt(ID);
        int quantity = b.getInt(QUANTITY);
        NotificationUtils.remindUserForConsumption(MedicineConsumptionReminderJobService.this,medicineName, medicineId, quantity);
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
