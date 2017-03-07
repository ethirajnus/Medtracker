package sg.edu.nus.iss.se.ft05.medipal;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class ListMedicine extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_medicine);
        TextDrawable drawable1 = TextDrawable.builder()
                .buildRound("A", Color.RED);
        TextDrawable drawable2 = TextDrawable.builder()
                .buildRound("A", Color.BLUE);
        TextDrawable drawable3 = TextDrawable.builder()
                .buildRound("A", Color.DKGRAY);

        ImageView image = (ImageView) findViewById(R.id.image_view);
        image.setImageDrawable(drawable1);
        ImageView image1 = (ImageView) findViewById(R.id.image_view1);
        image1.setImageDrawable(drawable2);
        ImageView image2 = (ImageView) findViewById(R.id.image_view2);
        image2.setImageDrawable(drawable3);
    }
}
