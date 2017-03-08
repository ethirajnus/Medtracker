package sg.edu.nus.iss.se.ft05.medipal.fragments;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.Util.TextDrawable;
import sg.edu.nus.iss.se.ft05.medipal.activities.AddMedicine;
import sg.edu.nus.iss.se.ft05.medipal.activities.MainActivity;


/**
 * A placeholder fragment containing a simple view.
 */
public class MedicineFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.medicine_fragment, container, false);

        TextDrawable drawable1 = TextDrawable.builder()
                .buildRound("A", Color.RED);
        TextDrawable drawable2 = TextDrawable.builder()
                .buildRound("A", Color.BLUE);
        TextDrawable drawable3 = TextDrawable.builder()
                .buildRound("A", Color.DKGRAY);

        ImageView image = (ImageView) view.findViewById(R.id.image_view);
        image.setImageDrawable(drawable1);
        ImageView image1 = (ImageView) view.findViewById(R.id.image_view1);
        image1.setImageDrawable(drawable2);
        ImageView image2 = (ImageView) view.findViewById(R.id.image_view2);
        image2.setImageDrawable(drawable3);
        ((MainActivity)getActivity()).setFloatingActionButtonAction(AddMedicine.class);
        return view;

    }
}
