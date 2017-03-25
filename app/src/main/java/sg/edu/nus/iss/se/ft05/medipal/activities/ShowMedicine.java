package sg.edu.nus.iss.se.ft05.medipal.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import sg.edu.nus.iss.se.ft05.medipal.domain.Reminder;
import sg.edu.nus.iss.se.ft05.medipal.managers.CategoryManager;
import sg.edu.nus.iss.se.ft05.medipal.model.Medicine;
import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.managers.ReminderManager;

import static sg.edu.nus.iss.se.ft05.medipal.activities.AddOrUpdateMedicine.DOSAGE_REVERSE_HASH_MAP;
import static sg.edu.nus.iss.se.ft05.medipal.constants.Constants.ID;
import static sg.edu.nus.iss.se.ft05.medipal.constants.Constants.MEDICINE;

public class ShowMedicine extends AppCompatActivity {

    private TextView name, description, category, reminder, quantity, dosage, consumeQuantity, threshold, dateIssued, expireFactor, frequency, startTime, interval;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_medicine);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setTitle(MEDICINE);
        context = getApplicationContext();
        Bundle b = getIntent().getExtras();
        Medicine medicine = Medicine.findById(context, b.getInt("id"));
        ReminderManager reminderManager = new ReminderManager();
        Reminder reminder = reminderManager.findById(context, medicine.getReminderId());
        findViewsById();
        updateValues(medicine, reminder);
    }

    private void updateValues(Medicine medicine, Reminder reminderMedicine) {
        name.setText(medicine.getName());
        description.setText(medicine.getDescription());
        category.setText(new CategoryManager().findById(getApplicationContext(), medicine.getCategoryId()).getCategoryName());
        this.reminder.setText(medicine.getRemind().toString());
        quantity.setText(String.valueOf(medicine.getQuantity()));
        dosage.setText(DOSAGE_REVERSE_HASH_MAP.get(medicine.getDosage()));
        consumeQuantity.setText(String.valueOf(medicine.getConsumeQuantity()));
        threshold.setText(String.valueOf(medicine.getThreshold()));
        dateIssued.setText(medicine.getDateIssued());
        expireFactor.setText(String.valueOf(medicine.getExpireFactor()));
        frequency.setText(String.valueOf(reminderMedicine.getFrequency()));
        startTime.setText(reminderMedicine.getStartTime());
        interval.setText(String.valueOf(reminderMedicine.getInterval()));

    }

    private void findViewsById() {
        name = (TextView) findViewById(R.id.medicineName);
        description = (TextView) findViewById(R.id.medicineDescription);
        category = (TextView) findViewById(R.id.medicineCategory);
        reminder = (TextView) findViewById(R.id.medicineReminder);
        quantity = (TextView) findViewById(R.id.medicineQuantity);
        dosage = (TextView) findViewById(R.id.medicineDosage);
        consumeQuantity = (TextView) findViewById(R.id.medicineConsumeQuantity);
        threshold = (TextView) findViewById(R.id.medicineThreshold);
        dateIssued = (TextView) findViewById(R.id.medicineDateIssued);
        expireFactor = (TextView) findViewById(R.id.medicineExpireFactor);
        frequency = (TextView) findViewById(R.id.reminderFrequency);
        startTime = (TextView) findViewById(R.id.reminderStartTime);
        interval = (TextView) findViewById(R.id.reminderInterval);
    }
}
