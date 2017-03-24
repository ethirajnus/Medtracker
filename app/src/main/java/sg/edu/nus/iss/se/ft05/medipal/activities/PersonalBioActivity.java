package sg.edu.nus.iss.se.ft05.medipal.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.constants.Constants;
import sg.edu.nus.iss.se.ft05.medipal.dao.PersonalBioDAO;
import sg.edu.nus.iss.se.ft05.medipal.dao.PersonalBioDAOImpl;
import sg.edu.nus.iss.se.ft05.medipal.model.PersonalBio;

/**
 * @author Moushumi Seal
 */
public class PersonalBioActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener{

    private EditText mName, mDob, mIdNo, mBuildingName, mLocation,
            mStreetName, mLevel, mUnitNo, mPostalCode, mHeight;
    private Spinner mSpn_bloodType;
    private Button mSaveBtn;
    private Calendar dateCalendar;
    private TextView mTv_bloodType;


    private static final SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.ENGLISH);

    DatePickerDialog datePickerDialog;

    private Context context;
    private PersonalBio personalBio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_bio);
        setTitle(Constants.TITLE_PERSONAL_BIO);
        Context context = getApplicationContext();
        findViewsById();
        setListeners();
        Bundle b = getIntent().getExtras();
        if(b != null){
            switch(b.getString(Constants.ACTION)){
                case Constants.VIEW:
                    mName.setEnabled(false);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar().setHomeButtonEnabled(true);
                    mSaveBtn.setText(Constants.EDIT);
                    makeFieldsEditable(false);
                    getPersonalbioValuesById(b.getInt("userId"));
                    break;
                case Constants.NEW:
                    mSaveBtn.setText(Constants.SAVE);
                    break;
            }
        }

        findViewsById();
        setListeners();
    }

    private void findViewsById(){
        mName = (EditText)findViewById(R.id.name);
        mDob = (EditText)findViewById(R.id.dob);
        mDob.setInputType(InputType.TYPE_NULL);
        mIdNo = (EditText)findViewById(R.id.idNo);
        mBuildingName = (EditText) findViewById(R.id.buildingName);
        mLocation= (EditText) findViewById(R.id.location);
        mStreetName = (EditText) findViewById(R.id.streetName);
        mLevel = (EditText) findViewById(R.id.level);
        mUnitNo = (EditText) findViewById(R.id.unitNo);
        mPostalCode = (EditText) findViewById(R.id.postalCode);
        mHeight = (EditText) findViewById(R.id.height);
        mSpn_bloodType = (Spinner) findViewById(R.id.spn_bloodType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.blood_type_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpn_bloodType.setAdapter(adapter);
        mTv_bloodType = (TextView) findViewById(R.id.tv_bloodType_value);
        mSaveBtn = (Button) findViewById(R.id.savePersonalBio);
    }

    private void setListeners() {
        mSaveBtn.setOnClickListener(this);
        mDob.setOnClickListener(this);
        mDob.setOnFocusChangeListener(this);
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
                mDob.setError(null);
                datePickerDialog.show();
                break;
            case R.id.savePersonalBio:
                if(mSaveBtn.getText().equals(Constants.SAVE))
                 savePersonalbio();
                else if(mSaveBtn.getText().equals(Constants.EDIT)){
                    mSaveBtn.setText(Constants.UPDATE);
                    makeFieldsEditable(true);
                } else
                    updatePersonalBio();
                break;
        }
    }
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.dob:
                if(hasFocus) {
                    datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                    mDob.setError(null);
                    datePickerDialog.show();
                }
                break;
        }
    }
    

    private void makeFieldsEditable(boolean enable) {
        mDob.setEnabled(enable);
        mDob.setOnClickListener(null);
        mIdNo.setEnabled(enable);
        mIdNo.setEnabled(enable);
        mBuildingName.setEnabled(enable);
        mLocation.setEnabled(enable);
        mStreetName.setEnabled(enable);
        mLevel.setEnabled(enable);
        mUnitNo.setEnabled(enable);
        mPostalCode.setEnabled(enable);
        mHeight.setEnabled(enable);
        if(!enable) {
            mSpn_bloodType.setVisibility(View.GONE);
            mTv_bloodType.setVisibility(View.VISIBLE);
        } else {
            mSpn_bloodType.setVisibility(View.VISIBLE);
            mTv_bloodType.setVisibility(View.GONE);
        }
    }


    private void getPersonalbioValuesById(int id) {
        personalBio = PersonalBio.findById(getApplicationContext(), id);
        mName.setText(personalBio.getName());
        mDob.setText(personalBio.getDob());
        mIdNo.setText(personalBio.getIdNo());
        String address[] = personalBio.getAddress().split(",");
        mBuildingName.setText(address[0]);
        mLocation.setText(address[1]);
        mStreetName.setText(address[2]);
        mLevel.setText(address[3]);
        mUnitNo.setText(address[4]);
        mPostalCode.setText(personalBio.getPostalCode());
        mHeight.setText(personalBio.getHeight());
        mSpn_bloodType.setSelection(Arrays.asList(
                getResources().getStringArray(R.array.blood_type_list))
                .indexOf(personalBio.getBloodType()));
        mTv_bloodType.setText(personalBio.getBloodType());
    }

    /**
     * Save Personal bio details
     */
    public void savePersonalbio() {
        if(isValid()) {
            String name = mName.getText().toString();
            String dob = mDob.getText().toString();
            String idNo = mIdNo.getText().toString();
            String address = getAddress();
            String postalCode = mPostalCode.getText().toString();
            String height = mHeight.getText().toString();
            String bloodType = mSpn_bloodType.getSelectedItem().toString();
            personalBio = new PersonalBio(name,dob,idNo,address,postalCode,height,bloodType);
            context = getApplicationContext();
            PersonalBioDAO personalBioDAO = new PersonalBioDAOImpl(context);
            if (personalBio.save(context) == -1)
                Toast.makeText(context, R.string.insert_error, Toast.LENGTH_SHORT).show();
            else {
                Toast.makeText(context, R.string.add_success, Toast.LENGTH_SHORT).show();
                int id = personalBioDAO.findPersonalBioId(name, dob, idNo);
                Intent resultIntent = new Intent();
                resultIntent.putExtra("personId", id);
                resultIntent.putExtra("personName", name);
                setResult(RESULT_OK, resultIntent);
            }
            finish();
        }
    }

    private String getAddress() {
        String buildingName = mBuildingName.getText().toString();
        String location = mLocation.getText().toString();
        String streetName = mStreetName.getText().toString();
        String level = mLevel.getText().toString();
        String unitNo = mUnitNo.getText().toString();
        return (buildingName + "," + location + "," + streetName + "," + level + "," + unitNo);
    }

    /**
     * Update Personal bio details
     */
    private void updatePersonalBio(){
        if(isValid()) {
            personalBio.setName(mName.getText().toString());
            personalBio.setDob(mDob.getText().toString());
            personalBio.setIdNo(mIdNo.getText().toString());
            String address = getAddress();
            personalBio.setAddress(address);
            personalBio.setPostalCode(mPostalCode.getText().toString());
            personalBio.setHeight(mHeight.getText().toString());
            personalBio.setBloodType(mSpn_bloodType.getSelectedItem().toString());
            mTv_bloodType.setText(mSpn_bloodType.getSelectedItem().toString());
            context = getApplicationContext();
            if (personalBio.update(context) == -1) {
                Toast.makeText(context, R.string.insert_error, Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(context, R.string.update_success, Toast.LENGTH_SHORT).show();
                makeFieldsEditable(false);
                mSaveBtn.setText(Constants.EDIT);
            }
        }
    }

    /**
     * Validating fields
     */
    private boolean isValid() {
        boolean isvalid = false;
        if(isMandatoryFieldsFilled()) {
            isvalid = true;
            if(!mName.getText().toString().matches(Constants.PATTERN_LETTERS_ONLY)){
                mName.setError(Constants.INVALID_NAME);
                mName.requestFocus();
                isvalid = false;
            }
            if(!mIdNo.getText().toString().matches(Constants.PATTERN_ALPHANUMERIC)){
                mIdNo.setError(Constants.INVALID_ID_NO);
                mIdNo.requestFocus();
                isvalid = false;
            }
            if(mBuildingName.getText().toString().matches(Constants.PATTERN_COMMA)){
                mBuildingName.setError(Constants.INVALID_BUILDING_NAME);
                mBuildingName.requestFocus();
                isvalid = false;
            }
            if(mLocation.getText().toString().matches(Constants.PATTERN_COMMA)){
                mLocation.setError(Constants.INVALID_LOCATION);
                mLocation.requestFocus();
                isvalid = false;
            }
            if(mStreetName.getText().toString().matches(Constants.PATTERN_COMMA)){
                mStreetName.setError(Constants.INVALID_STREET_NAME);
                mStreetName.requestFocus();
                isvalid = false;
            }
            if (mHeight.getText().toString().matches(Constants.PATTERN_ZERO)) {
                mHeight.setError(Constants.INVALID_HEIGHT);
                mHeight.requestFocus();
                isvalid = false;
            }
            if(mPostalCode.getText().toString().matches(Constants.PATTERN_ZERO)) {
                mPostalCode.setError(Constants.INVALID_POSTAL_CODE);
                mPostalCode.requestFocus();
                isvalid = false;
            }
        }
        return isvalid;
    }

    /**
     * checking if mandatory fields are blank
     */
    private boolean isMandatoryFieldsFilled() {
        boolean isValid = true;
        if (TextUtils.isEmpty(mName.getText().toString().trim())
                && TextUtils.isEmpty(mDob.getText().toString().trim())
                && TextUtils.isEmpty(mIdNo.getText().toString().trim())
                && TextUtils.isEmpty(mBuildingName.getText().toString().trim())
                && TextUtils.isEmpty(mLocation.getText().toString().trim())
                && TextUtils.isEmpty(mStreetName.getText().toString().trim())
                && TextUtils.isEmpty(mLevel.getText().toString().trim())
                && TextUtils.isEmpty(mUnitNo.getText().toString().trim())
                && TextUtils.isEmpty(mPostalCode.getText().toString().trim())
                && TextUtils.isEmpty(mHeight.getText().toString().trim())) {
            AlertDialog.Builder warningDialog = new AlertDialog.Builder(this);
            warningDialog.setTitle(Constants.TITLE_WARNING);
            warningDialog.setMessage(R.string.warning);
            warningDialog.setPositiveButton(Constants.OK_BUTTON, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface alert, int which) {
                    alert.dismiss();
                }
            });
            warningDialog.show();
            isValid = false;
        } else {
            if (TextUtils.isEmpty(mName.getText().toString().trim())) {
                mName.setError(Constants.EMPTY_PERSONAL_BIO_NAME);
                isValid = false;
            }else if (TextUtils.isEmpty(mDob.getText())) {
                mDob.setError(Constants.EMPTY_DOB);
                mDob.requestFocus();
                isValid = false;
            }else if (TextUtils.isEmpty(mIdNo.getText().toString().trim())) {
                mIdNo.setError(Constants.EMPTY_IDNO);
                mIdNo.requestFocus();
                isValid = false;
            }else if (TextUtils.isEmpty(mHeight.getText().toString().trim())) {
                mHeight.setError(Constants.EMPTY_HEIGHT);
                mHeight.requestFocus();
                isValid = false;
            }else if (TextUtils.isEmpty(mBuildingName.getText().toString().trim())) {
                mBuildingName.setError(Constants.EMPTY_BUILDING_NAME);
                mBuildingName.requestFocus();
                isValid = false;
            }else if (TextUtils.isEmpty(mLocation.getText().toString().trim())) {
                mLocation.setError(Constants.EMPTY_LOCATION);
                mLocation.requestFocus();
                isValid = false;
            }else if (TextUtils.isEmpty(mStreetName.getText().toString().trim())) {
                mStreetName.setError(Constants.EMPTY_STREET_NAME);
                mStreetName.requestFocus();
                isValid = false;
            }else if (TextUtils.isEmpty(mLevel.getText().toString().trim())) {
                mLevel.setError(Constants.EMPTY_LEVEL);
                mLevel.requestFocus();
                isValid = false;
            }else if (TextUtils.isEmpty(mUnitNo.getText().toString().trim())) {
                mUnitNo.setError(Constants.EMPTY_UNIT_NO);
                mUnitNo.requestFocus();
                isValid = false;
            }else if (TextUtils.isEmpty(mPostalCode.getText().toString().trim())) {
                mPostalCode.setError(Constants.EMPTY_POSTAL_CODE);
                mPostalCode.requestFocus();
                isValid = false;
            }
        }
        return isValid;
    }
}
