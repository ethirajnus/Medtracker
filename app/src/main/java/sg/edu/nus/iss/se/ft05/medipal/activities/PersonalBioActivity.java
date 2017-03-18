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
import sg.edu.nus.iss.se.ft05.medipal.constants.Constants;
import sg.edu.nus.iss.se.ft05.medipal.enums.BloodType;
import sg.edu.nus.iss.se.ft05.medipal.fragments.DatePickerFragment;

/**
 * @author Moushumi Seal
 */
public class PersonalBioActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText mName, mDob, mIdNo, mAddress, mPostalCode, mHeight;
    private Spinner mSpn_bloodType;
    private Button mSaveBtn;
    private Calendar dateCalendar, newCalendar;

    private static final SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.ENGLISH);

    DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_bio);
        mName = (EditText)findViewById(R.id.name);
        mDob = (EditText)findViewById(R.id.dob);
        mIdNo = (EditText)findViewById(R.id.idNo);

        mSpn_bloodType = (Spinner) findViewById(R.id.spn_bloodType);
        mSpn_bloodType.setAdapter(
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, BloodType.values()));
    }

    private void setListener() {
        mSaveBtn.setOnClickListener(this);
        mDob.setOnClickListener(this);
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dateCalendar = Calendar.getInstance();
                dateCalendar.set(year, monthOfYear, dayOfMonth);
                mDob.setText(formatter.format(dateCalendar.getTime()));
            }
        },
                newCalendar.get(Calendar.YEAR),
                newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.dob:
                datePickerDialog.show();
                break;
            case R.id.savePersonalBio:
                break;
        }
    }
}
