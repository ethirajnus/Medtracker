package sg.edu.nus.iss.se.ft05.medipal.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.constants.Constants;
import sg.edu.nus.iss.se.ft05.medipal.domain.HealthBio;
import sg.edu.nus.iss.se.ft05.medipal.fragments.HealthBioFragment;
import sg.edu.nus.iss.se.ft05.medipal.managers.HealthBioManager;

/**
 * @author Moushumi Seal
 */
public class AddOrUpdateHealthBioActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {

    private EditText mCondition, mStartDate;
    private RadioGroup mRG_ConditionType;
    private RadioButton mRadioBtn_ConditionType, mRadio_Condition, mRadio_Allergy;
    private CheckBox mAddAnother;
    private CheckedTextView mTV_addAnother;
    private Button mSaveBtn;
    private Calendar dateCalendar;

    private static final SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.ENGLISH);

    DatePickerDialog datePickerDialog;

    private Context context;
    private HealthBioManager healthBioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_update_health_bio);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        context = getApplicationContext();
        findViewsById();
        setListeners();
        Bundle b = getIntent().getExtras();
        if (b != null && b.getBoolean("firstRun")) {
            mSaveBtn.setText(Constants.SKIP);
            setTitle(Constants.TITLE_NEW_HEALTHBIO);
            mCondition.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() > 0 || mStartDate.getText().length() > 0)
                        mSaveBtn.setText(Constants.SAVE);
                    else
                        mSaveBtn.setText(Constants.SKIP);
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
            mStartDate.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() > 0 || mCondition.getText().length() > 0)
                        mSaveBtn.setText(Constants.SAVE);
                    else
                        mSaveBtn.setText(Constants.SKIP);
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        } else if (b != null && b.getString(Constants.ACTION).equalsIgnoreCase(Constants.EDIT)) {
            updateSaveButton();
            updateHealthbioValues(b.getInt("id"));
            setTitle(Constants.TITLE_EDIT_HEALTHBIO);
            mAddAnother.setVisibility(View.INVISIBLE);
            mTV_addAnother.setVisibility(View.INVISIBLE);
        } else {
            mSaveBtn.setText(Constants.SAVE);
            setTitle(Constants.TITLE_NEW_HEALTHBIO);
        }

    }

    private void setListeners() {
        mSaveBtn.setOnClickListener(this);
        mStartDate.setOnClickListener(this);
        mStartDate.setOnFocusChangeListener(this);
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dateCalendar = Calendar.getInstance();
                dateCalendar.set(year, monthOfYear, dayOfMonth);
                mStartDate.setText(formatter.format(dateCalendar.getTime()));
            }
        },
                newCalendar.get(Calendar.YEAR),
                newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveHealthBio:
                if (mSaveBtn.getText().equals(Constants.SKIP)) {
                    Intent i = new Intent(this, MainActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    saveOrUpdateHealthbio();
                }
                break;
            case R.id.startDate:
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                mStartDate.setError(null);
                datePickerDialog.show();
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.startDate:
                if (hasFocus) {
                    datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                    mStartDate.setError(null);
                    datePickerDialog.show();
                }
                break;
        }
    }

    private void updateHealthbioValues(int id) {

        healthBioManager = new HealthBioManager();
        HealthBio healthBio = healthBioManager.findById(getApplicationContext(), id);
        mCondition.setText(healthBio.getCondition());
        mStartDate.setText(healthBio.getStartDate());
        if (healthBio.getConditionType().equalsIgnoreCase(Constants.CONDITION_TYPE_CONDITION))
            mRadio_Condition.setChecked(true);
        else
            mRadio_Allergy.setChecked(true);
    }

    private void updateSaveButton() {
        mSaveBtn.setTag(Constants.UPDATE);
        mSaveBtn.setText(Constants.UPDATE);
    }

    private void findViewsById() {
        mCondition = (EditText) findViewById(R.id.condition);
        mStartDate = (EditText) findViewById(R.id.startDate);
        mStartDate.setInputType(InputType.TYPE_NULL);
        mRG_ConditionType = (RadioGroup) findViewById(R.id.radioBtnGroup);
        mRadio_Condition = (RadioButton) findViewById(R.id.radioBtnCondition);
        mRadio_Allergy = (RadioButton) findViewById(R.id.radioBtnAllergy);
        mAddAnother = (CheckBox) findViewById(R.id.addAnother);
        mTV_addAnother = (CheckedTextView) findViewById(R.id.tv_addAnother);
        mSaveBtn = (Button) findViewById(R.id.saveHealthBio);
        mSaveBtn.setTag(Constants.NEW);
    }


    public void saveOrUpdateHealthbio() {
        String condition = mCondition.getText().toString().trim();
        String startDate = mStartDate.getText().toString();
        int selectedId = mRG_ConditionType.getCheckedRadioButtonId();
        mRadioBtn_ConditionType = (RadioButton) findViewById(selectedId);
        String conditionType = mRadioBtn_ConditionType.getTag().toString();


        Context context = getApplicationContext();

        if (isValid()) {
            if (mSaveBtn.getTag().toString().equalsIgnoreCase(Constants.NEW)) {
                healthBioManager = new HealthBioManager(condition, conditionType, startDate);

                if (healthBioManager.save(context) == -1)
                    Toast.makeText(context, R.string.insert_error, Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(context, R.string.add_success, Toast.LENGTH_SHORT).show();
                    navigate();
                }

            } else {

                HealthBio healthBio = new HealthBio();
                healthBio.setCondition(condition);
                healthBio.setConditionType(conditionType);
                healthBio.setStartDate(startDate);
                healthBio.setId(healthBioManager.getHealthBio().getId());
                healthBioManager.setHealthBio(healthBio);

                if (healthBioManager.update(context) == -1)
                    Toast.makeText(context, R.string.update_error, Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(context, R.string.update_success, Toast.LENGTH_SHORT).show();
                    navigate();
                }
            }
        }
    }

    public void navigate() {
        Intent intent;
        if (mAddAnother.isChecked()) {
            intent = new Intent(this, AddOrUpdateHealthBioActivity.class);
        } else {
            intent = new Intent(context, MainActivity.class);
            MainActivity.currentFragment = HealthBioFragment.class.getName();
        }
        startActivity(intent);

        finish();
    }

    private boolean isValid() {
        boolean isValid = true;
        if (TextUtils.isEmpty(mCondition.getText().toString().trim()) && TextUtils.isEmpty(mStartDate.getText())) {
            AlertDialog.Builder warningDialog = new AlertDialog.Builder(this);
            warningDialog.setTitle(Constants.TITLE_WARNING);
            warningDialog.setMessage(R.string.warning_allMandatory);
            warningDialog.setPositiveButton(Constants.BUTTON_OK, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface alert, int which) {
                    alert.dismiss();
                }
            });
            warningDialog.show();
            isValid = false;
        } else if (TextUtils.isEmpty(mCondition.getText().toString().trim())) {
            mCondition.setError("Please provide a condition!");
            isValid = false;
        } else if (TextUtils.isEmpty(mStartDate.getText())) {
            mStartDate.setError("Please specify the date!");
            mStartDate.requestFocus();
            isValid = false;
        }
        return isValid;
    }
}
