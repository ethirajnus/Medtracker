package sg.edu.nus.iss.se.ft05.medipal.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import sg.edu.nus.iss.se.ft05.medipal.Category;
import sg.edu.nus.iss.se.ft05.medipal.Medicine;
import sg.edu.nus.iss.se.ft05.medipal.R;

import static sg.edu.nus.iss.se.ft05.medipal.activities.AddOrUpdateMedicine.DOSAGE_REVERSE_HASH_MAP;

public class ShowMedicine extends AppCompatActivity {

    private TextView name,description,category,reminder,quantity,dosage,consumeQuantity,threshold,dateIssued,expireFactor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_medicine);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setTitle("Medicine");
        Bundle b = getIntent().getExtras();
        Medicine medicine = Medicine.findById(getApplicationContext(),b.getInt("id"));
        findViewsById();
        updateValues(medicine);
    }

    private void updateValues(Medicine medicine) {
        name.setText(medicine.getName());
        description.setText(medicine.getDescription());
        category.setText(Category.findById(getApplicationContext(),medicine.getCategoryId()).getCategoryName());
        reminder.setText(medicine.getRemind().toString());
        quantity.setText(String.valueOf(medicine.getQuantity()));
        dosage.setText(DOSAGE_REVERSE_HASH_MAP.get(medicine.getDosage()));
        consumeQuantity.setText(String.valueOf(medicine.getConsumeQuality()));
        threshold.setText(String.valueOf(medicine.getThreshold()));
        dateIssued.setText(medicine.getDateIssued());
        expireFactor.setText(String.valueOf(medicine.getExpireFactor()));
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
    }
}
