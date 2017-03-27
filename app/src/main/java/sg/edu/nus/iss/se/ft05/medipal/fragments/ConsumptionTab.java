package sg.edu.nus.iss.se.ft05.medipal.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.adapters.ConsumptionListAdapter;
import sg.edu.nus.iss.se.ft05.medipal.managers.ConsumptionManager;

/**
 * Class for consumption tab
 */
public class ConsumptionTab extends Fragment {
    private Context context;
    private TextView textView;
    private Cursor cursor;
    private String date;
    private List<ConsumptionManager> consumptionManagers;
    private static final String CONSUMPTIONS = "Today's Consumptions";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private String content = "";
    private RecyclerView recyclerView;
    private ConsumptionListAdapter mAdapter;
    private TextView noConsumptions;

    public ConsumptionTab() {
        // Required empty public constructor
    }

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

        View view = inflater.inflate(R.layout.fragment_consumption_tab, container, false);
        context = getActivity().getApplicationContext();
        Calendar calendar = Calendar.getInstance();
        Date d = calendar.getTime();
        date = new SimpleDateFormat(DATE_FORMAT).format(d);
        context = getActivity().getApplicationContext();
        recyclerView = (RecyclerView) view.findViewById(R.id.all_consumptions_list_view);
        noConsumptions = (TextView) view.findViewById(R.id.tv_noConsumptions);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        Cursor cursor = ConsumptionManager.filterDate(context, date);
        mAdapter = new ConsumptionListAdapter(context, cursor);
        recyclerView.setAdapter(mAdapter);
        if(mAdapter != null ){
            noConsumptions.setVisibility((mAdapter.getItemCount() == 0)? View.VISIBLE : View.GONE);
            recyclerView.setVisibility((mAdapter.getItemCount() == 0)? View.GONE : View.VISIBLE);
        }
        return view;
    }


}
