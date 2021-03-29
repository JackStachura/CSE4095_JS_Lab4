package me.JStachuraRadioApp.model;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.io.IOException;

import me.JStachuraRadioApp.R;
import me.JStachuraRadioApp.ui.settings.SettingsFragment;


public class MainActivity extends AppCompatActivity {


    private final String TAG = "MAIN__";


    private Button internetRadioButton;
    public static MediaPlayer mediaPlayer;
    public boolean mBound = false;
    public static boolean radioOn;
    public static boolean radioWasOnBefore;
    public static int volume;
    public static int url_number;
    public static String url = "http://radio.wesleyan.edu:8000/stream";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        mediaPlayer = new MediaPlayer();
        volume = 100;
        url_number = 0;


    }
    public static void radioSetup(MediaPlayer mediaPlayer, String url_to_play) {

        mediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
        );

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                mediaPlayer.start();
                if(SettingsFragment.status != null) {
                    SettingsFragment.status.setText("");
                }
            }
        });

        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {

                return false;
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                mediaPlayer.reset();
            }
        });

        try {
            mediaPlayer.setDataSource(url_to_play);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }




}



