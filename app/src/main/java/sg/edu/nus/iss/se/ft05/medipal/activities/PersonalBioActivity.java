package sg.edu.nus.iss.se.ft05.medipal.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.constants.Constants;
import sg.edu.nus.iss.se.ft05.medipal.enums.BloodType;
import sg.edu.nus.iss.se.ft05.medipal.model.PersonalBio;

/**
 * @author Moushumi Seal
 */
public class PersonalBioActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText mName, mDob, mIdNo, mAddress, mPostalCode, mHeight;
    private Spinner mSpn_bloodType;
    private Button mSaveBtn;
    private Calendar dateCalendar;

    private static final SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.ENGLISH);

    DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_bio);

        findViewsById();
        setListeners();
    }

    private void findViewsById(){
        mName = (EditText)findViewById(R.id.name);
        mDob = (EditText)findViewById(R.id.dob);
        mIdNo = (EditText)findViewById(R.id.idNo);
        mAddress = (EditText) findViewById(R.id.address);
        mPostalCode = (EditText) findViewById(R.id.postalCode);
        mHeight = (EditText) findViewById(R.id.height);
        mSpn_bloodType = (Spinner) findViewById(R.id.spn_bloodType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.blood_type_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpn_bloodType.setAdapter(adapter);
        mSaveBtn = (Button) findViewById(R.id.savePersonalBio);
    }

    private void setListeners() {
        mSaveBtn.setOnClickListener(this);
        mDob.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();
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
        switch (id) {
            case R.id.dob:
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
                break;
            case R.id.savePersonalBio:
                savePersonalbio();
                break;
        }
    }

    public void savePersonalbio() {
        String name = mName.getText().toString();
        String dob = mDob.getText().toString();
        String idNo = mIdNo.getText().toString();
        String address = mAddress.getText().toString();
        String postalCode = mPostalCode.getText().toString();
        String height = mHeight.getText().toString();
        String bloodType = mSpn_bloodType.getSelectedItem().toString();
        Context context = getApplicationContext();
        PersonalBio personalBio = new PersonalBio(name,dob,idNo,address,postalCode,height,bloodType);
        if(personalBio.save(context) == -1)
            Toast.makeText(context,R.string.insert_error, Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(context, R.string.add_success, Toast.LENGTH_SHORT).show();
            Intent resultIntent = new Intent();
            resultIntent.putExtra(Constants.COMPLETION_STATUS,Constants.COMPLETION_STATUS_SUCCESS);
            setResult(RESULT_OK,resultIntent);
            finish();
        }

    }
}
