package sg.edu.nus.iss.se.ft05.medipal.fragments;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
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


public class ConsumptionTab extends Fragment {
    private Context context;
    private TextView textView;
    private Cursor cursor;
    private String date;
    private List<Consumption> consumptions;
    private static final String CONSUMPTIONS="Today's Consumptions";
    private static final String DATE_FORMAT="yyyy-MM-dd";
    private String content="";
    public ConsumptionTab() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_consumption_tab,container,false);
        context=getActivity().getApplicationContext();
        Calendar calendar=Calendar.getInstance();
        Date d=calendar.getTime();
        date=new SimpleDateFormat(DATE_FORMAT).format(d);
        content+=CONSUMPTIONS+"\n";
        consumptions=Consumption.findByDate(context,date);
        for(Consumption consumption:consumptions)
        {

            content=content+"Medicine: "+consumption.getMedicine(context).getName()+"\n";
            content=content+"Quantity:"+consumption.getQuantity()+"\n";
            content=content+"Time:"+consumption.getTime()+"\n";
        }
        textView=(TextView) view.findViewById(R.id.consumptionText);
        textView.setText(content);
        return view;
    }


}
