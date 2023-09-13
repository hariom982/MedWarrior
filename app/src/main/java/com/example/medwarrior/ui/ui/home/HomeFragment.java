package com.example.medwarrior.ui.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.medwarrior.R;
import com.example.medwarrior.databinding.FragmentHomeBinding;
import com.example.medwarrior.ui.ui.chatwin;

import java.util.Calendar;
import java.util.Date;

public class HomeFragment extends Fragment implements SensorEventListener{
    private SensorManager sensorManager = null;
    private Sensor stepsensor;

    Date currenttime = Calendar.getInstance().getTime();
    private int totalsteps = 0;
    private int previewtotalsteps = 0;
    private ProgressBar progressBar;
    private TextView steps;
    ImageView chat;
    LinearLayout  linearlayout,linearlayout2,linearlayout3;

    private FragmentHomeBinding binding;

    @SuppressLint("ServiceCast")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        progressBar = root.findViewById(R.id.progressBar);
        steps = root.findViewById(R.id.steps);
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        stepsensor  = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        linearlayout2 = root.findViewById(R.id.linearlayout2);
        linearlayout3 = root.findViewById(R.id.linearlayout3);

//        chat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(),chatwin.class);
//                startActivity(intent);
//            }
//        });
        linearlayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getActivity(),fitnessact.class);
                startActivity(intent);
            }
        });
        linearlayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Breatheact.class);
                startActivity(intent);
            }
        });

        return root;
    }
    public void onResume(){
        super.onResume();
        if(stepsensor == null){
            Toast.makeText(getActivity(), "This device has no sensors", Toast.LENGTH_SHORT).show();
        }
        else {
            sensorManager.registerListener(this,stepsensor,sensorManager.SENSOR_DELAY_NORMAL);
        }
    }
    public void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER){
            totalsteps = (int) event.values[0];
            int currentsteps = totalsteps - previewtotalsteps;
            resetSteps();
            steps.setText(String.valueOf(currentsteps));
            progressBar.setProgress(currentsteps);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    public void resetSteps(){
        Calendar currenttime = Calendar.getInstance();
        //Calendar date = Calendar.getInstance();
        if(currenttime.get(Calendar.HOUR)==0 && currenttime.get(Calendar.MINUTE)==0){
//            editor.putInt("step",0);
//            editor.apply();
            totalsteps = 0;
            steps.setText(String.valueOf(totalsteps));
        }
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}