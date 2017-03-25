package sg.edu.nus.iss.se.ft05.medipal.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.adapters.ConsumptionListAdapter;
import sg.edu.nus.iss.se.ft05.medipal.constants.Constants;
import sg.edu.nus.iss.se.ft05.medipal.dao.DBHelper;
import sg.edu.nus.iss.se.ft05.medipal.managers.CategoryManager;
import sg.edu.nus.iss.se.ft05.medipal.model.Consumption;

import static sg.edu.nus.iss.se.ft05.medipal.constants.Constants.DATE_FORMAT;
import static sg.edu.nus.iss.se.ft05.medipal.constants.Constants.MEDICINE_REMINDER_CANNOT_TURN_OFF_CATEGORY;
import static sg.edu.nus.iss.se.ft05.medipal.constants.Constants.TO_DATE_CANNOT_BE_GREATER_THAN_FROM_DATE;

/**
 * Created by ethi on 25/03/17.
 */

public class ConsumptionReportTab extends Fragment implements View.OnClickListener{



    private RecyclerView consumptionRecyclerView;
    private Context context;
    private ConsumptionListAdapter mAdapter;
    private Spinner category, filterBy, spinYear, spinMonth;
    private View view;
    private List<String> categoryList;
    private Map<String, Integer> categoriesMap;
    private EditText date;
    DatePickerDialog datePickerDialog;
    Calendar dateCalendar;
    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            DATE_FORMAT, Locale.ENGLISH);
    private String year;
    private Cursor cursor;
    private Integer medicineCategoryId;
    private String month;
    private EditText dateFrom,dateTo;
    private DatePickerDialog datePickerDialogFrom,datePickerDialogTo;
    private String dateFromText,dateToText;
    private Date dateObjFrom,dateObjTo;
    private Calendar dateCalendarFrom,dateCalendarTo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.consumption_report_tab, container, false);
        consumptionRecyclerView = (RecyclerView) view.findViewById(R.id.all_consumption_list_view);
        context = getActivity().getApplicationContext();
        findViewsById();

        // Set layout for the RecyclerView, because it's a list we are using the linear layout
        consumptionRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        // Get all guest info from the database and save in a cursor
        Cursor cursor = Consumption.findAll(context);

        // Create an adapter for that cursor to display the data
        mAdapter = new ConsumptionListAdapter(context, cursor);

        // Link the adapter to the RecyclerView
        consumptionRecyclerView.setAdapter(mAdapter);
        setListeners();
        setValues();

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

    private void setValues() {
        dateFrom.setText(formatter.format(Calendar.getInstance().getTime()));
        dateTo.setText(formatter.format(Calendar.getInstance().getTime()));
    }

    private void findViewsById() {
        dateFrom = (EditText) view.findViewById(R.id.fromDate);
        dateTo = (EditText) view.findViewById(R.id.toDate);
    }

    private void setListeners(){
        dateFrom.setOnClickListener(this);
        dateTo.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialogFrom = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dateCalendarFrom = Calendar.getInstance();
                dateCalendarFrom.set(year, monthOfYear, dayOfMonth);
                dateFrom.setText(formatter.format(dateCalendarFrom.getTime()));
            }
        },
                newCalendar.get(Calendar.YEAR),
                newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialogTo = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dateCalendarTo = Calendar.getInstance();
                dateCalendarTo.set(year, monthOfYear, dayOfMonth);
                dateTo.setText(formatter.format(dateCalendarTo.getTime()));
            }
        },
                newCalendar.get(Calendar.YEAR),
                newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));

        dateFrom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    dateFromText = dateFrom.getText().toString();

                    dateToText = dateTo.getText().toString();
                    if(dateToText.length() != 0)
                        checkDateAndSwapCursor();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        dateTo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    dateFromText = dateFrom.getText().toString();
                    dateToText = dateTo.getText().toString();
                    if(dateFromText.length() != 0)
                        checkDateAndSwapCursor();
                }
                

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }
    
    private void checkDateAndSwapCursor(){
        try {
            dateObjFrom = formatter.parse(dateFromText);
            dateObjTo = formatter.parse(dateToText);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(dateObjTo.after(dateObjFrom)){
            triggerFilterForDate();
        }
    }

    private void triggerFilterForDate() {
        Cursor cursor = Consumption.betweenDate(context,dateFromText,dateToText);
        mAdapter.swapCursor(cursor);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fromDate:
                datePickerDialogFrom.show();
                break;
            case R.id.toDate:
                try {
                    dateObjFrom = formatter.parse(dateFromText);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar newCalendar = Calendar.getInstance();
                datePickerDialogTo = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        dateCalendarTo = Calendar.getInstance();
                        dateCalendarTo.set(year, monthOfYear, dayOfMonth);
                        dateTo.setText(formatter.format(dateCalendarTo.getTime()));
                    }
                },
                        newCalendar.get(Calendar.YEAR),
                        newCalendar.get(Calendar.MONTH),
                        newCalendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialogTo.getDatePicker().setMinDate(dateObjFrom.getTime());
                datePickerDialogTo.show();
                break;
        }
    }
}
