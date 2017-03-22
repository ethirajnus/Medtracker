package sg.edu.nus.iss.se.ft05.medipal.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


import java.util.Arrays;

import sg.edu.nus.iss.se.ft05.medipal.constants.Constants;
import sg.edu.nus.iss.se.ft05.medipal.model.Category;
import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.fragments.CategoryFragment;

import static sg.edu.nus.iss.se.ft05.medipal.constants.Constants.*;

public class AddOrUpdateCategory extends AppCompatActivity implements View.OnClickListener {

    Button saveButton;
    EditText name, code, description;
    CheckBox reminder;
    private Context context;

    private Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        context = getApplicationContext();
        findViewsById();
        setListeners();
        Bundle b = getIntent().getExtras();
        if (b != null && b.getString(ACTION).equalsIgnoreCase(EDIT)) {
            updateSaveButton();
            updateCategoryValues(b.getInt(ID));
            setTitle(EDIT_CATEGORY);
        } else {
            setTitle(NEW_CATEGORY);
        }

    }

    private void updateCategoryValues(int id) {
        category = Category.findById(context, id);
        name.setText(category.getCategoryName());
        code.setText(category.getCode().toUpperCase());
        if (Arrays.asList(Category.safeCategoryCodes).contains(category.getCode())) {
            reminder.setEnabled(false);
            code.setEnabled(false);
        }
        description.setText(category.getDescription());
        reminder.setChecked(category.getRemind());
        name.setTag(id);
    }

    private void updateSaveButton() {
        saveButton.setTag(UPDATE);
        saveButton.setText(UPDATE);
    }

    private void setListeners() {
        saveButton.setOnClickListener(this);
    }

    private void findViewsById() {
        name = (EditText) findViewById(R.id.categoryName);
        code = (EditText) findViewById(R.id.categoryCode);
        description = (EditText) findViewById(R.id.categoryDescription);
        reminder = (CheckBox) findViewById(R.id.categoryReminder);
        saveButton = (Button) findViewById(R.id.saveCategory);
        saveButton.setTag(NEW);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveCategory:
                saveOrUpdateCategory();
                break;

        }
    }

    public void saveOrUpdateCategory() {
        String categoryName = name.getText().toString();
        String categoryCode = code.getText().toString().toUpperCase();
        String categoryDescription = description.getText().toString();
        Boolean categoryReminder = reminder.isChecked();
        if (saveButton.getTag().toString().equalsIgnoreCase(NEW)) {
            category = new Category(categoryName, categoryCode, categoryDescription, categoryReminder);
            if (isValid()) {
                if (category.save(context) == -1) {
                    Toast.makeText(context, CATEGORY_NOT_SAVED, Toast.LENGTH_SHORT).show();
                } else {
                    navigateToMainAcitivity();

                }
            }

        } else {
            category.setRemind(categoryReminder);
            category.setCategoryName(categoryName);
            category.setCode(categoryCode);
            category.setDescription(categoryDescription);
            if (isValid()) {
                if (category.update(context) == -1) {
                    Toast.makeText(context, CATEGORY_NOT_UPDATED, Toast.LENGTH_SHORT).show();
                } else {
                    navigateToMainAcitivity();
                }
            }

        }

    }

    public void navigateToMainAcitivity() {
        Intent intent = new Intent(context, MainActivity.class);
        MainActivity.currentFragment = CategoryFragment.class.getName();
        startActivity(intent);
    }


    private boolean isValid() {
        boolean isValid = true;
        if (category.getCategoryName().isEmpty()) {
            name.setError("Please provide category name!");
            name.requestFocus();
            isValid = false;
        } else if (category.getCode().isEmpty()) {
            code.setError("Please specify code!");
            code.requestFocus();
            isValid = false;
        } else if (category.getDescription().isEmpty()) {
            description.setError("Please enter description");
            description.requestFocus();
            isValid = false;
        } else {
            isValid = validCategoryDBFields();
        }
        return isValid;
    }

    private boolean validCategoryDBFields() {
        String nameText = category.getCategoryName();
        String codeText = category.getCode();
        Boolean isValid = true;
        String cateGoryName = Category.findByName(context,nameText).getCategoryName();
        String cateGoryCode = Category.findByCode(context,codeText).getCode();

        if (cateGoryName != null &&  cateGoryName.equals(nameText)){
            name.setError("Please enter unique category name!");
            name.requestFocus();
            isValid = false;
        } else if (cateGoryCode != null && cateGoryCode.equals(codeText)){
            code.setError("Please enter unique code!");
            code.requestFocus();
            isValid = false;
        }
        return isValid;
    }
}
