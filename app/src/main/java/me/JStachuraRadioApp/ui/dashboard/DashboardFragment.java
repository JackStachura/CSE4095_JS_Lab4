package me.JStachuraRadioApp.ui.dashboard;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import me.JStachuraRadioApp.model.MainActivity;
import me.JStachuraRadioApp.R;





public class DashboardFragment extends Fragment {

    private me.JStachuraRadioApp.ui.dashboard.DashboardViewModel dashboardViewModel;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(me.JStachuraRadioApp.ui.dashboard.DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText("");
            }
        });
        Button internetRadioButton = root.findViewById(R.id.internet_radio_button);



        Log.i("TAG", getActivity().getLocalClassName().toString());
        MainActivity main = (MainActivity) getActivity();
        if (main.radioOn) internetRadioButton.setText("Turn radio OFF");
        else internetRadioButton.setText("Turn radio ON");
        internetRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (main.radioOn) { // ON so Turn OFF
                    main.radioOn = false;
                    internetRadioButton.setText("Turn radio ON");
                    if (main.mediaPlayer.isPlaying()) {
                        Log.i("TAG", "Radio is playing- turning off " );
                        main.radioWasOnBefore = true;
                    }
                    main.mediaPlayer.pause();
                } else { // OFF so Turn ON
                    main.radioOn = true;
                    internetRadioButton.setText("Turn radio OFF");
                    if (!main.mediaPlayer.isPlaying()) {
                        if (main.radioWasOnBefore) {
                            main.mediaPlayer.release();
                            main.mediaPlayer = new MediaPlayer();
                        }
                        main.radioSetup(main.mediaPlayer, main.url);
                        main.mediaPlayer.prepareAsync();
                    }
                }
                float log1=(float) ( 1 - (Math.log(100- main.volume)/Math.log(100)));
                main.mediaPlayer.setVolume(log1,log1);
            }
        });

        return root;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void setUpMediaPlayer() {
        Handler handler = null;

        HandlerThread handlerThread = new HandlerThread("media player") {
            @Override
            public void onLooperPrepared() {


            }
        };

    }




}
