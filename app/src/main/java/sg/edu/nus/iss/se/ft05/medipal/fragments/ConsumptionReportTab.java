package sg.edu.nus.iss.se.ft05.medipal.fragments;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.adapters.ConsumptionListAdapter;
import sg.edu.nus.iss.se.ft05.medipal.constants.Constants;
import sg.edu.nus.iss.se.ft05.medipal.domain.Consumption;
import sg.edu.nus.iss.se.ft05.medipal.managers.ConsumptionManager;

import static sg.edu.nus.iss.se.ft05.medipal.constants.Constants.DATE_FORMAT;
import static sg.edu.nus.iss.se.ft05.medipal.constants.Constants.FROM_DATE_AFTER_TO_DATE;
import static sg.edu.nus.iss.se.ft05.medipal.dao.DBHelper.CONSUMPTION_KEY_DATE;
import static sg.edu.nus.iss.se.ft05.medipal.dao.DBHelper.CONSUMPTION_KEY_MEDICINEID;
import static sg.edu.nus.iss.se.ft05.medipal.dao.DBHelper.CONSUMPTION_KEY_QUANTITY;
import static sg.edu.nus.iss.se.ft05.medipal.dao.DBHelper.CONSUMPTION_KEY_TIME;

/**
 * Class for consumption report
 * @author Abinaya
 */
public class ConsumptionReportTab extends Fragment implements View.OnClickListener {

    private static final int PERMISSION_EXTERNAL_STORAGE_WRITE = 1;
    private RecyclerView consumptionRecyclerView;
    private Context context;
    private ConsumptionListAdapter mAdapter;
    private View view;
    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            DATE_FORMAT, Locale.ENGLISH);
    private EditText dateFrom, dateTo;
    private DatePickerDialog datePickerDialogFrom, datePickerDialogTo;
    private String dateFromText, dateToText;
    private Date dateObjFrom, dateObjTo;
    private Calendar dateCalendarFrom, dateCalendarTo;
    private ConsumptionManager consumptionManager;
    private Cursor cursor;
    private TextView noConsumptions;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    /**
     *
     * @param menu
     * @param inflater
     */
    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.consumption_report_share, menu);
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

        view = inflater.inflate(R.layout.consumption_report_tab, container, false);
        consumptionRecyclerView = (RecyclerView) view.findViewById(R.id.all_consumption_list_view);
        context = getActivity().getApplicationContext();
        findViewsById();

        // Set layout for the RecyclerView, because it's a list we are using the linear layout
        consumptionRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        String currentDate = formatter.format(Calendar.getInstance().getTime());
        cursor = ConsumptionManager.betweenDate(context,currentDate,currentDate);

        // Create an adapter for that cursor to display the data
        mAdapter = new ConsumptionListAdapter(context, cursor,consumptionRecyclerView,noConsumptions);

        // Link the adapter to the RecyclerView
        consumptionRecyclerView.setAdapter(mAdapter);
        setListeners();
        setValues();
        checkForEmptyList();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //do nothing, we only care about swiping
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

                //update the list
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
                        checkForEmptyList();
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

    private void setValues() {
        dateFrom.setText(formatter.format(Calendar.getInstance().getTime()));
        dateTo.setText(formatter.format(Calendar.getInstance().getTime()));
    }

    private void findViewsById() {
        dateFrom = (EditText) view.findViewById(R.id.fromDate);
        dateTo = (EditText) view.findViewById(R.id.toDate);
        noConsumptions = (TextView) view.findViewById(R.id.tv_noConsumptions);
    }

    private void setListeners() {
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
                    if (dateToText.length() != 0)
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
                    if (dateFromText.length() != 0)
                        checkDateAndSwapCursor();
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    private void checkDateAndSwapCursor() {
        try {
            dateObjFrom = formatter.parse(dateFromText);
            dateObjTo = formatter.parse(dateToText);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(dateObjTo.before(dateObjFrom)){
            Toast.makeText(context, FROM_DATE_AFTER_TO_DATE, Toast.LENGTH_SHORT).show();
        }
        else{
            triggerFilterForDate();
        }
    }

    private void triggerFilterForDate() {
        cursor = ConsumptionManager.betweenDate(context, dateFromText, dateToText);
        mAdapter.swapCursor(cursor);
        checkForEmptyList();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.share_consumption:
                SendEmail();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void SendEmail(){
        String filename="consumption.csv";
        createFile(context,filename,fetchContent());
        File myFile = new File("/sdcard/"+"/MediPal/"+filename);
        Uri path = Uri.fromFile(myFile);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Consumption");
        intent.putExtra(Intent.EXTRA_TEXT, "Please find the report attached for  Consumption");
        intent .putExtra(Intent.EXTRA_STREAM, path);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void createFile(Context context, String sFileName, String sBody) {


        if (context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(context, "App does not have Permission to Store File", Toast.LENGTH_SHORT).show();

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_EXTERNAL_STORAGE_WRITE);

        } else {
            try {
                File root = new File(Environment.getExternalStorageDirectory(), "MediPal");
                if (!root.exists()) {
                    root.mkdirs();
                }
                File gpxfile = new File(root, sFileName);
                FileWriter writer = new FileWriter(gpxfile);
                writer.append(sBody);
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    private String fetchContent() {
        return new StringBuilder().append("Consumption\n\n")
                .append("Medicine,Quantity,Date,Time\n")
                .append(fetchMeasurementAsString()).toString();
    }

    private String fetchMeasurementAsString() {
        cursor.moveToFirst();
        String consumptions = "";
        while (!cursor.isAfterLast()) {
            Consumption consumption = new Consumption();
            consumption.setMedicineId(cursor.getInt(cursor.getColumnIndex(CONSUMPTION_KEY_MEDICINEID)));
            consumption.setQuantity(cursor.getInt(cursor.getColumnIndex(CONSUMPTION_KEY_QUANTITY)));
            consumption.setDate(cursor.getString(cursor.getColumnIndex(CONSUMPTION_KEY_DATE)));
            consumption.setTime(cursor.getString(cursor.getColumnIndex(CONSUMPTION_KEY_TIME)));
            ConsumptionManager consumptionManager = new ConsumptionManager();
            consumptionManager.setConsumption(consumption);
            consumptions+= consumptionManager.getMedicine(context).getName()+","+consumption.toString();
            cursor.moveToNext();
        }
        return consumptions;
    }

    /**
     * view
     * @param v
     */
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
    private void checkForEmptyList(){
        if(mAdapter != null ){
            noConsumptions.setVisibility((mAdapter.getItemCount() == 0)? View.VISIBLE : View.GONE);
            consumptionRecyclerView.setVisibility((mAdapter.getItemCount() == 0)? View.GONE : View.VISIBLE);
        }
    }
}
