package sg.edu.nus.iss.se.ft05.medipal.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import sg.edu.nus.iss.se.ft05.medipal.Category;
import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.fragments.CategoryFragment;

public class AddOrUpdateCategory extends AppCompatActivity implements View.OnClickListener {

    Button saveButton;
    EditText name,code,description;
    CheckBox reminder;

    private Category category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        findViewsById();
        setListeners();
        Bundle b = getIntent().getExtras();
        if(b != null && b.getString("action").equalsIgnoreCase("edit")){
            updateSaveButton();
            updateCategoryValues(b.getLong("id"));
            setTitle("Edit Category");
        }else{
            setTitle("New Category");
        }

    }

    private void updateCategoryValues(Long id) {
        category = Category.findById(getApplicationContext(),id);
        name.setText(category.getCategoryName());
        code.setText(category.getCode());
        description.setText(category.getDescription());
        reminder.setChecked(category.getRemind());
        name.setTag(id);
    }

    private void updateSaveButton() {
        saveButton.setTag("update");
        saveButton.setText("Update");
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
        saveButton.setTag("New");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveCategory:
                saveOrUpdateCategory();
                break;

        }
    }

    public void saveOrUpdateCategory(){
        String categoryName = name.getText().toString();
        String categoryCode = code.getText().toString();
        String categoryDescription = code.getText().toString();
        Boolean categoryReminder = reminder.isChecked();
        if(saveButton.getTag().toString().equalsIgnoreCase("New")){
            Category category = new Category(categoryName,categoryCode,categoryDescription,categoryReminder);
            if(category.save(getApplicationContext())== -1){
                Toast.makeText(getApplicationContext(), "Category was not inserted properly,Please try again later", Toast.LENGTH_SHORT).show();
            }
            else {
                navigateToMainAcitivity();

            }
        }
        else {
            category.setRemind(categoryReminder);
            category.setCategoryName(categoryName);
            category.setCode(categoryCode);
            category.setDescription(categoryDescription);
            if(category.update(getApplicationContext())== -1){
                Toast.makeText(getApplicationContext(), "Category was not updated properly,Please try again later", Toast.LENGTH_SHORT).show();
            }
            else {
                navigateToMainAcitivity();
            }

        }

    }

    public void navigateToMainAcitivity(){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        MainActivity.currentFragment= CategoryFragment.class.getName();
        startActivity(intent);

    }
}
