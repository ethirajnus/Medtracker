package sg.edu.nus.iss.se.ft05.medipal.activities;

import android.app.DatePickerDialog;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.enums.BloodType;
import sg.edu.nus.iss.se.ft05.medipal.fragments.DatePickerFragment;

/**
 * @author Moushumi Seal
 */
public class PersonalBioActivity extends AppCompatActivity {

    private EditText name, dob, idNo, address, postalCode, height;
    private Spinner spn_bloodType;
    private Button saveBtn;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
    Calendar selectedDate = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_bio);
        name = (EditText)findViewById(R.id.name);
        dob = (EditText)findViewById(R.id.dob);
        idNo = (EditText)findViewById(R.id.idNo);

        spn_bloodType = (Spinner) findViewById(R.id.spn_bloodType);
        spn_bloodType.setAdapter(
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, BloodType.values()));
        dob.setText(dateFormatter.format(selectedDate.getTime()));
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
    }
}
