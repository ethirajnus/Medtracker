package sg.edu.nus.iss.se.ft05.medipal.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.adapters.ConsumptionListAdapter;
import sg.edu.nus.iss.se.ft05.medipal.dao.DBHelper;
import sg.edu.nus.iss.se.ft05.medipal.model.Consumption;
import sg.edu.nus.iss.se.ft05.medipal.managers.MedicineManager;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static sg.edu.nus.iss.se.ft05.medipal.constants.Constants.DATE_FORMAT;


/**
 * Created by ethi on 23/03/17.
 */

public class ConsumptionByMedicineTab extends Fragment implements View.OnClickListener {

    View view;
    RecyclerView consumptionRecyclerView;
    Context context;
    Cursor cursor;
    DatePickerDialog datePickerDialogDay,datePickerDialogWeek;
    Calendar dateCalendar;
    private ConsumptionListAdapter mAdapter;
    private Spinner medicine, filterBy, spinYear, spinMonth;
    private List<String> medicineList;
    private Map<String, Integer> medicinesMap;
    private EditText date;

    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            DATE_FORMAT, Locale.ENGLISH);
    private Integer medicineId;
    private String year;
    private String month;
    private EditText week;
    private String dateFrom,dateTo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.consumption_by_medicine_tab, container, false);
        consumptionRecyclerView = (RecyclerView) view.findViewById(R.id.all_consumption_list_view);
        context = getActivity().getApplicationContext();
        findViewsById();
        setListeners();
        populateDropDownList();
        // Set layout for the RecyclerView, because it's a list we are using the linear layout
        consumptionRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        // Get all guest info from the database and save in a cursor
        cursor = Consumption.findAll(context);

        // Create an adapter for that cursor to display the data
        mAdapter = new ConsumptionListAdapter(context, cursor);

        // Link the adapter to the RecyclerView
        consumptionRecyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //do nothing, we only care about swiping
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                //get the id of the item being swiped
                int id = (int) viewHolder.itemView.getTag();
                //remove from DB
                Consumption consumption = Consumption.findById(context, id);
                consumption.delete(context);
                //update the list
                mAdapter.swapCursor(Consumption.findAll(context));
            }

        }).attachToRecyclerView(consumptionRecyclerView);
        return view;
    }

    private void findViewsById() {
        medicine = (Spinner) view.findViewById(R.id.consumptionMedicine);
        filterBy = (Spinner) view.findViewById(R.id.filterBy);
        spinYear = (Spinner) view.findViewById(R.id.yearSpin);
        spinMonth = (Spinner) view.findViewById(R.id.monthSpin);
        date = (EditText) view.findViewById(R.id.medicineDateIssued);
        week = (EditText) view.findViewById(R.id.medicineWeek);

    }

    private void setListeners() {
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
        medicine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                medicineId = medicinesMap.get(medicine.getSelectedItem());
                spinMonth.setVisibility(View.INVISIBLE);
                    date.setVisibility(View.INVISIBLE);
                    triggerFilterForYear();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

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
                }
                else if ((filterByText.contentEquals("Day"))) {
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
        cursor = Consumption.fetchByMedicineAndYear(context, medicineId, year);
        mAdapter.swapCursor(cursor);
    }

    private void triggerFilterForMonth() {
        month = spinMonth.getSelectedItem().toString();
        if (month.length() == 1) {
            month = "0" + month;
        }
        cursor = Consumption.fetchByMedicineAndMonth(context, medicineId, year, month);
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
        calendar.add(Calendar.DATE,6);
        Date EndDate = calendar.getTime();
        dateFrom = formatter.format(StartDate);
        dateTo = formatter.format(EndDate);
        cursor = Consumption.fetchByMedicineAndBetweenDates(context, medicineId,dateFrom,dateTo );
        mAdapter.swapCursor(cursor);
    }

    private void triggerFilterForDate() {
        cursor = Consumption.fetchByMedicineAndDate(context, medicineId, date.getText().toString());
        mAdapter.swapCursor(cursor);
    }


    private void populateDropDownList() {
        Cursor mCursor = MedicineManager.fetchAllMedicinesWithId(context);
        medicineList = new ArrayList<>();
        medicinesMap = new HashMap<>();
        for (mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext()) {
            int id = mCursor.getInt(mCursor.getColumnIndex(DBHelper.MEDICINE_KEY_ID));
            String medicineName = mCursor.getString(mCursor.getColumnIndex(DBHelper.MEDICINE_KEY_MEDICINE));
            medicineList.add(medicineName); //add the item
            medicinesMap.put(medicineName, id);
        }

        ArrayAdapter<String> medicineDataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, medicineList);

        // Drop down layout style - list view with radio button
        medicineDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        medicine.setAdapter(medicineDataAdapter);

        ArrayList<String> years = new ArrayList<>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 2000; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, years);


        spinYear.setAdapter(yearAdapter);
        spinYear.setSelection(years.size() -1);

        ArrayList<String> months = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            months.add(Integer.toString(i));
        }
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, months);

        spinMonth.setAdapter(monthAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.medicineDateIssued:
                datePickerDialogDay.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialogDay.show();
                break;
            case R.id.medicineWeek:
                datePickerDialogWeek.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialogWeek.show();
                break;
        }
    }
}
