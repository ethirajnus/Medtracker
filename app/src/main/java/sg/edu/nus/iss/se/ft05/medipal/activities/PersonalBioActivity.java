package sg.edu.nus.iss.se.ft05.medipal.activities;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Locale;

import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.enums.BloodType;
import sg.edu.nus.iss.se.ft05.medipal.fragments.DatePickerFragment;

/**
 * @author Moushumi Seal
 */
public class PersonalBioActivity extends AppCompatActivity {

    private EditText mName, mDob, mIdNo, mAddress, mPostalCode, mHeight;
    private Spinner mSpn_bloodType;
    private Button mSaveBtn;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

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
        mDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
    }
}
