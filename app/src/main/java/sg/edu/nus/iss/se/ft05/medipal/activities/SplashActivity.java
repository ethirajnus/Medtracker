package sg.edu.nus.iss.se.ft05.medipal.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.managers.PrefManager;



/**
 * class to show splash activity
 * @author Moushumi Seal
 */
public class SplashActivity extends AppCompatActivity {

    /**
     * Duration of wait
     */
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    private PrefManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_screen);
        prefManager = new PrefManager(this);
        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent mainIntent;
                if(prefManager.isShowHelpScreens())
                    mainIntent = new Intent(SplashActivity.this,HelpActivity.class);
                else
                    mainIntent = new Intent(SplashActivity.this,MainActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
