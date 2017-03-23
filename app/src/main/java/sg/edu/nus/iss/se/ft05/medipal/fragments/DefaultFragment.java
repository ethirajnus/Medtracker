package sg.edu.nus.iss.se.ft05.medipal.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
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
import sg.edu.nus.iss.se.ft05.medipal.model.Appointment;
import sg.edu.nus.iss.se.ft05.medipal.model.Consumption;
import static sg.edu.nus.iss.se.ft05.medipal.constants.Constants.*;

public class DefaultFragment extends Fragment {

    private Context context;
    private TextView textView;
    private Cursor cursor;
    private String date;
    private List<Appointment> appointments;
    private List<Consumption> consumptions;
    private static final String APPOINTMENTS="Today's Appointments";
    private static final String MEASUREMENTS="Your most recent measurements";
    private static final String CONSUMPTIONS="Your consumptions";
    private String content="";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_default,container,false);
        context=getActivity().getApplicationContext();

        Calendar calendar=Calendar.getInstance();
        Date d=calendar.getTime();
        date=new SimpleDateFormat(DATE_FORMAT).format(d);
       appointments=Appointment.findByDate(context,date);
        consumptions=Consumption.findByDate(context,date);
        content=content+APPOINTMENTS+"\n";


        for(Appointment appointment:appointments)
        {

            content=content+"Time:"+appointment.getTime()+"\n";
            content=content+"Clinic:"+appointment.getClinic()+"\n";
            content=content+"Test:"+appointment.getTest()+"\n";

        }

        content=content+CONSUMPTIONS+"\n";



        for(Consumption consumption:consumptions)
        {

            content=content+consumption.getMedicine(context)+"\n";
            content=content+consumption.getQuantity()+"\n";
        }

        textView=(TextView) view.findViewById(R.id.default_view);
        textView.setText(content);

        return view;
    }


}
