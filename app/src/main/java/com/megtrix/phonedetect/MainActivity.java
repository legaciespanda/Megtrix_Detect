package com.megtrix.phonedetect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.megtrix.phonedetector.MegtrixDetect;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.phoneTv);

        MegtrixDetect detect = new MegtrixDetect();
        detect.initiateSettings(this);


        String insert = detect.getUserPhone();

        textView.setText(insert);


    }
}
