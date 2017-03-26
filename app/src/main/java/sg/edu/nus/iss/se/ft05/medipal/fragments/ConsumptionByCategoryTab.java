package sg.edu.nus.iss.se.ft05.medipal.fragments;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.os.Bundle;

import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.adapters.ConsumptionListAdapter;
import sg.edu.nus.iss.se.ft05.medipal.constants.Constants;
import sg.edu.nus.iss.se.ft05.medipal.dao.DBHelper;
import sg.edu.nus.iss.se.ft05.medipal.managers.CategoryManager;
import sg.edu.nus.iss.se.ft05.medipal.managers.ConsumptionManager;

import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.content.Context;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

import static sg.edu.nus.iss.se.ft05.medipal.constants.Constants.DATE_FORMAT;

/**
 * Class for consumption based on category
 */
public class ConsumptionByCategoryTab extends Fragment implements View.OnClickListener {


    private RecyclerView consumptionRecyclerView;
    private Context context;
    private ConsumptionListAdapter mAdapter;
    private Spinner category, filterBy, spinYear, spinMonth;
    private View view;
    private List<String> categoryList;
    private Map<String, Integer> categoriesMap;
    private EditText date;
    DatePickerDialog datePickerDialogDay, datePickerDialogWeek;
    Calendar dateCalendar;
    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            DATE_FORMAT, Locale.ENGLISH);
    private String year;
    private Cursor cursor;
    private Integer medicineCategoryId;
    private String month;
    private EditText week;
    private String dateFrom, dateTo;
    private ConsumptionManager consumptionManager;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.consumption_by_category_tab, container, false);
        consumptionRecyclerView = (RecyclerView) view.findViewById(R.id.all_consumption_list_view);
        context = getActivity().getApplicationContext();
        findViewsById();
        setListeners();
        populateDropDownList();
        // Set layout for the RecyclerView, because it's a list we are using the linear layout
        consumptionRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        // Get all guest info from the database and save in a cursor
        Cursor cursor = ConsumptionManager.findAll(context);

        // Create an adapter for that cursor to display the data
        mAdapter = new ConsumptionListAdapter(context, cursor);

        // Link the adapter to the RecyclerView
        consumptionRecyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            /**
             *
             * @param recyclerView
             * @param viewHolder
             * @param target
             * @return
             */
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //do nothing, we only care about swiping
                return false;
            }

            /**
             *
             * @param viewHolder
             * @param swipeDir
             */
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                mAdapter.swapCursor(ConsumptionManager.findAll(context));
                //get the id of the item being swiped
                int id = (int) viewHolder.itemView.getTag();
                //remove from DB

                consumptionManager = new ConsumptionManager();
                consumptionManager.findById(context, id);
                AlertDialog.Builder warningDialog = new AlertDialog.Builder(getActivity(), R.style.AppTheme_Dialog);
                warningDialog.setTitle(Constants.TITLE_WARNING);
                warningDialog.setMessage(R.string.warning_delete);
                warningDialog.setPositiveButton(Constants.BUTTON_YES, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface alert, int which) {
                        //remove from DB
                        consumptionManager.delete(context);
                        Toast.makeText(context, R.string.delete_success, Toast.LENGTH_SHORT).show();
                        //update the list
                        mAdapter.swapCursor(ConsumptionManager.findAll(context));
                        alert.dismiss();
                    }
                });
                warningDialog.setNegativeButton(Constants.BUTTON_NO, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface alert, int which) {
                        alert.dismiss();
                    }
                });
                warningDialog.show();
            }

        }).attachToRecyclerView(consumptionRecyclerView);
        return view;
    }


    private void findViewsById() {

        category = (Spinner) view.findViewById(R.id.medicineCategory);
        filterBy = (Spinner) view.findViewById(R.id.categoryFilterBy);
        spinYear = (Spinner) view.findViewById(R.id.categoryYearSpin);
        spinMonth = (Spinner) view.findViewById(R.id.categoryMonthSpin);
        date = (EditText) view.findViewById(R.id.categoryMedicineDateIssued);
        week = (EditText) view.findViewById(R.id.categoryWeek);
    }


    private void setListeners() {

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                medicineCategoryId = categoriesMap.get(category.getSelectedItem());
                spinMonth.setVisibility(View.INVISIBLE);
                date.setVisibility(View.INVISIBLE);
                triggerFilterForYear();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        date.setOnClickListener(this);
        week.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialogDay = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dateCalendar = Calendar.getInstance();
                dateCalendar.set(year, monthOfYear, dayOfMonth);
                date.setText(formatter.format(dateCalendar.getTime()));
            }
        },
                newCalendar.get(Calendar.YEAR),
                newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialogWeek = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dateCalendar = Calendar.getInstance();
                dateCalendar.set(year, monthOfYear, dayOfMonth);
                week.setText(formatter.format(dateCalendar.getTime()));
            }
        },
                newCalendar.get(Calendar.YEAR),
                newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));


        filterBy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String filterByText = filterBy.getSelectedItem().toString();
                if (filterByText.contentEquals("Year")) {
                    spinYear.setVisibility(View.VISIBLE);
                    spinMonth.setVisibility(View.INVISIBLE);
                    week.setVisibility(View.INVISIBLE);
                    date.setVisibility(View.INVISIBLE);
                    triggerFilterForYear();
                } else if ((filterByText.contentEquals("Month"))) {
                    spinYear.setVisibility(View.VISIBLE);
                    spinMonth.setVisibility(View.VISIBLE);
                    week.setVisibility(View.INVISIBLE);
                    date.setVisibility(View.INVISIBLE);
                    triggerFilterForMonth();
                } else if ((filterByText.contentEquals("Week"))) {
                    spinYear.setVisibility(View.INVISIBLE);
                    spinMonth.setVisibility(View.INVISIBLE);
                    week.setVisibility(View.VISIBLE);
                    date.setVisibility(View.INVISIBLE);
                } else if ((filterByText.contentEquals("Day"))) {
                    spinYear.setVisibility(View.INVISIBLE);
                    spinMonth.setVisibility(View.INVISIBLE);
                    week.setVisibility(View.INVISIBLE);
                    date.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    triggerFilterForDate();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        week.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    triggerFilterForWeek();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        spinYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                triggerFilterForYear();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        spinMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                triggerFilterForMonth();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

    }


    private void triggerFilterForYear() {
        year = spinYear.getSelectedItem().toString();
        cursor = ConsumptionManager.fetchByCategoryAndYear(context, medicineCategoryId, year);
        mAdapter.swapCursor(cursor);
    }

    private void triggerFilterForMonth() {
        month = spinMonth.getSelectedItem().toString();
        if (month.length() == 1) {
            month = "0" + month;
        }
        cursor = ConsumptionManager.fetchByCategoryAndMonth(context, medicineCategoryId, year, month);
        mAdapter.swapCursor(cursor);
    }

    private void triggerFilterForDate() {
        cursor = ConsumptionManager.fetchByCategoryAndDate(context, medicineCategoryId, date.getText().toString());
        mAdapter.swapCursor(cursor);
    }

    private void triggerFilterForWeek() {
        Date selectedDateObj = new Date();
        String selectedDate = week.getText().toString();
        try {
            selectedDateObj = formatter.parse(selectedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(selectedDateObj);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        Date StartDate = calendar.getTime();
        calendar.add(Calendar.DATE, 6);
        Date EndDate = calendar.getTime();
        dateFrom = formatter.format(StartDate);
        dateTo = formatter.format(EndDate);
        cursor = ConsumptionManager.fetchByCategoryAndBetweenDates(context, medicineCategoryId, dateFrom, dateTo);
        mAdapter.swapCursor(cursor);
    }


    private void populateDropDownList() {
        Cursor mCursor = CategoryManager.fetchAllCategoriesWithId(context);
        categoryList = new ArrayList<>();
        categoriesMap = new HashMap<>();
        while (mCursor.moveToNext()) {
            int id = mCursor.getInt(mCursor.getColumnIndex(DBHelper.CATEGORY_KEY_ID));
            String categoryName = mCursor.getString(mCursor.getColumnIndex(DBHelper.CATEGORY_KEY_CATEGORY));
            categoryList.add(categoryName); //add the item
            categoriesMap.put(categoryName, id);
        }

        ArrayAdapter<String> categoryDataAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, categoryList);

        // Drop down layout style - list view with radio button
        categoryDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        category.setAdapter(categoryDataAdapter);

        ArrayList<String> years = new ArrayList<>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 2000; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, years);


        spinYear.setAdapter(yearAdapter);
        spinYear.setSelection(years.size() - 1);

        ArrayList<String> months = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            months.add(Integer.toString(i));
        }
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, months);

        spinMonth.setAdapter(monthAdapter);

    }

    /**
     * view
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.categoryMedicineDateIssued:
                datePickerDialogDay.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialogDay.show();
                break;
            case R.id.categoryWeek:
                datePickerDialogWeek.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialogWeek.show();
                break;
        }
    }


}
