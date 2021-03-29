package me.JStachuraRadioApp.ui.settings;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.io.IOException;
import java.util.ArrayList;

import me.JStachuraRadioApp.model.MainActivity;
import me.JStachuraRadioApp.R;


public class SettingsFragment extends Fragment  {

    private me.JStachuraRadioApp.ui.settings.SettingsViewModel notificationsViewModel;
    public static TextView status;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(me.JStachuraRadioApp.ui.settings.SettingsViewModel.class);
        MainActivity main = (MainActivity) getActivity();
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);


        final int[] currVolume = {100};
        SeekBar volumeBar = (SeekBar) root.findViewById(R.id.seekBarVolume);
        volumeBar.setProgress(main.volume);
        float log1=(float) ( 1 - (Math.log(100- main.volume)/Math.log(100)));
        main.mediaPlayer.setVolume(log1,log1);
        volumeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                main.volume = progress;
                Log.i("Volume", "progress: " + progress);
                float log1=(float)( 1 - (Math.log(100- progress)/Math.log(100)));
                main.mediaPlayer.setVolume(log1,log1);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        Spinner spinner = root.findViewById(R.id.spinner_stations);
        ArrayList<String> stations = new ArrayList<String>();
        stations.add("http://radio.wesleyan.edu:8000/stream"); // WESU 88.1 FM Middletown, CT
        stations.add("http://stream.live.vc.bbcmedia.co.uk/bbc_radio_one"); //BBC Public Radio 1
        stations.add("http://stream.live.vc.bbcmedia.co.uk/bbc_radio_three\n"); //BBC Public Radio Classical
        stations.add("http://lpm.streamguys1.com/wfpk-popup"); //WFPK 91.9 FM Louisville, KY


        ArrayList<String> stations_text = new ArrayList<>();
        stations_text.add("WESU 88.1 FM Middletown, CT");
        stations_text.add("BBC Public Radio");
        stations_text.add("BBC Public Radio Classical");
        stations_text.add("WFPK 91.9 FM Louisville, KY");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, stations_text);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        spinner.setSelection(main.url_number);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MainActivity main = (MainActivity) getActivity();

                if(!stations.get(position).equals(main.url)) {
                    status = root.findViewById(R.id.status);
                    status.setText("Changing...");
                    if (main.mediaPlayer.isPlaying()) {
                        main.mediaPlayer.stop();
                        main.mediaPlayer.reset();
                        try {
                            main.mediaPlayer.setDataSource(stations.get(position));
                            main.url = stations.get(position);
                            main.url_number = position;
                            main.mediaPlayer.prepareAsync();


                        } catch (Error | IOException e) {
                            e.printStackTrace();
                            status.setText("There was an error accessing your station. The station may be off-air.");
                        }


                    }
                    else{
                        main.url = stations.get(position);
                        main.url_number = position;
                    }
                }

            }






            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return root;
    }


}