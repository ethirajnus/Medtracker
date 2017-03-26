package sg.edu.nus.iss.se.ft05.medipal.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.domain.Appointment;
import sg.edu.nus.iss.se.ft05.medipal.domain.Consumption;
import sg.edu.nus.iss.se.ft05.medipal.managers.AppointmentManager;
import sg.edu.nus.iss.se.ft05.medipal.managers.ConsumptionManager;

/**
 * Class for Default fragement
 */
public class DefaultFragment extends Fragment {

    private Context context;
    private TextView textView;
    private Cursor cursor;
    private String date;
    private List<Appointment> appointmentList;
    private List<Consumption> consumptionList;
    private static final String APPOINTMENTS = "Today's Appointments";
    private static final String MEASUREMENTS = "Your most recent measurements";
    private static final String CONSUMPTIONS = "Your consumptions";

    private static final String DATE_FORMAT = "dd-MM-yyyy";
    private static final String DATE_FORMAT_1 = "yyyy-MM-dd";

    private String content = "";

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_default, container, false);
        context = getActivity().getApplicationContext();

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);

        FloatingActionButton fabSOS = (FloatingActionButton) getActivity().findViewById(R.id.fabSOS);
        fabSOS.setVisibility(View.GONE);

        Calendar calendar = Calendar.getInstance();
        Date d = calendar.getTime();
        date = new SimpleDateFormat(DATE_FORMAT).format(d);

        appointmentList = AppointmentManager.findByDate(context, date);

        date = new SimpleDateFormat(DATE_FORMAT_1).format(d);
        consumptionList = ConsumptionManager.findByDate(context, date);
        content = content + APPOINTMENTS + "\n";


        for (Appointment appointment : appointmentList) {

            content = content + "Time:" + appointment.getTime() + "\n";
            content = content + "Clinic:" + appointment.getClinic() + "\n";
            content = content + "Description:" + appointment.getDescription() + "\n";

        }

        content = content + CONSUMPTIONS + "\n";


        for (Consumption consumption : consumptionList) {

            ConsumptionManager consumptionManagerMedicine = new ConsumptionManager();
            consumptionManagerMedicine.setConsumption(consumption);
            content = content + "Medicine: " + consumptionManagerMedicine.getMedicine(context).getName() + "\n";
            content = content + "Quantity:" + consumption.getQuantity() + "\n";
            content = content + "Time:" + consumption.getTime() + "\n";
        }

        textView = (TextView) view.findViewById(R.id.default_view);
        textView.setText(content);

        return view;
    }


}
