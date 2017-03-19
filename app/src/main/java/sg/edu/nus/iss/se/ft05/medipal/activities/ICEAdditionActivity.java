package sg.edu.nus.iss.se.ft05.medipal.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.se.ft05.medipal.manager.ICEContactsManager;
import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.domain.ICEContact;
import sg.edu.nus.iss.se.ft05.medipal.fragments.IceFragment;

/**
 * Class for adding and updating new Emergency Contact
 *
 * @author Ashish Katre
 */
public class ICEAdditionActivity extends AppCompatActivity implements View.OnClickListener {


    //Constants for class
    public static final String ICE_BUNDLE_ACTION = "medipal_ice_action";
    public static final String ICE_BUNDLE_ACTION_EDIT = "edit";
    public static final String ICE_BUNDLE_ACTION_ID = "medipal_ice_id";
    public static final String ICE_BUTTON_NEW = "new";
    public static final String ICE_BUTTON_UPDATE = "update";
    public static final String ICE_BUTTON_UPDATE_TEXT = "UPDATE";
    public static final String ICE_HEADER_NEW = "New Contact";
    public static final String ICE_HEADER_EDIT = "Edit Contact";
    public static final String ICE_ERROR_INSERT = "Error adding Contact,Please try again later";

    // Contact types
    public static final String ICE_TYPE_NOK = "Next of Keen";
    public static final String ICE_TYPE_GP = "General Physician";
    public static final String ICE_TYPE_OTHER = "Other";

    // Error messages
    public static final String ICE_ERROR_NAME = "Please Enter Name";
    public static final String ICE_ERROR_DESC = "Please Enter Description";
    public static final String ICE_ERROR_PHONE = "Please enter Contact Number";

    // Elements of UI
    private EditText name;
    private EditText description;
    private EditText phone;
    private Spinner spinner;
    private Button button;

    // Domain class
    private ICEContactsManager contactManager;
    private ICEContact contact;

    /**
     * Method to run while creating UI for addition/Edit
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_ice_contacts);

        Context context = getApplicationContext();

        spinner = (Spinner) findViewById(R.id.spinner_ice_type);

        List<String> types = new ArrayList<>();
        types.add(ICE_TYPE_NOK);
        types.add(ICE_TYPE_GP);
        types.add(ICE_TYPE_OTHER);

        spinner.setAdapter(
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, types));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        name = (EditText) findViewById(R.id.editText_ice_name);
        description = (EditText) findViewById(R.id.editText_ice_description);
        phone = (EditText) findViewById(R.id.editText_ice_phone);
        button = (Button) findViewById(R.id.button_ice_addition);

        button.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();

        contactManager = new ICEContactsManager();

        // Check if its addition or edit
        if (null != bundle && bundle.getString(ICE_BUNDLE_ACTION).equalsIgnoreCase(ICE_BUNDLE_ACTION_EDIT)) {

            button.setTag(ICE_BUTTON_UPDATE);
            button.setText(ICE_BUTTON_UPDATE_TEXT);

            contact = contactManager.findById(context, bundle.getInt(ICE_BUNDLE_ACTION_ID));
            name.setText(contact.getName());
            description.setText(contact.getDescription());
            phone.setText(String.valueOf(contact.getPhone()));
            name.setTag(bundle.getInt(ICE_BUNDLE_ACTION_ID));

            setTitle(ICE_HEADER_EDIT);

        } else {

            if (5 <= contactManager.getMaxPriority(context)) {

                Toast.makeText(context, "Maximum 5 ICEContactsManager Allowed Please delete atleast 1 existing contactManager to add new contactManager", Toast.LENGTH_SHORT).show();
                navigateToMainAcitivity(context);
            }

            button.setTag(ICE_BUTTON_NEW);
            setTitle(ICE_HEADER_NEW);
        }
    }

    /**
     * @param view
     */
    @Override
    public void onClick(View view) {

        if (R.id.button_ice_addition == view.getId()) {

            if (validate()) {

                saveOrUpdateContact();
            }
        }
    }

    /**
     * Processing of save or update contactManager
     */
    private void saveOrUpdateContact() {


        if (ICE_BUTTON_NEW.equalsIgnoreCase(button.getTag().toString())) {

            ICEContactsManager newContactManager = new ICEContactsManager(contact, this);

            if (-1 == newContactManager.save(this)) {

                Toast.makeText(this, ICE_ERROR_INSERT, Toast.LENGTH_SHORT).show();

            } else {

                navigateToMainAcitivity(this);
            }
        } else {

            contactManager = new ICEContactsManager();
            contactManager.setICEContact(contact);

            if (-1 == contactManager.update(this)) {

                Toast.makeText(this, ICE_ERROR_INSERT, Toast.LENGTH_SHORT).show();

            } else {

                navigateToMainAcitivity(this);
            }
        }
    }

    /**
     * Validate Fields
     */
    private boolean validate() {

        boolean checkFields = true;

        String contactName = name.getText().toString();
        String contactDesc = description.getText().toString();
        String contactPhone = phone.getText().toString();
        String contactType = (String) spinner.getSelectedItem();

        if (TextUtils.isEmpty(contactName)) {

            name.setError(ICE_ERROR_NAME);
            checkFields = false;
        }

        if (TextUtils.isEmpty(contactDesc)) {

            description.setError(ICE_ERROR_DESC);
            checkFields = false;
        }

        if (TextUtils.isEmpty(contactPhone)) {

            phone.setError(ICE_ERROR_PHONE);
            checkFields = false;
        }

        if (checkFields) {

            if (null == contact) {

                contact = new ICEContact();
            }

            contact.setName(contactName);
            contact.setDescription(contactDesc);
            contact.setPhone(Long.parseLong(contactPhone));
            contact.setType(contactType);
        }

        return checkFields;
    }

    /**
     * Navigation to main activity
     *
     * @param context
     */
    private void navigateToMainAcitivity(Context context) {

        Intent intent = new Intent(context, MainActivity.class);
        MainActivity.currentFragment = IceFragment.class.getName();
        startActivity(intent);
    }
}
