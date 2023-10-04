package com.example.projecte0salmar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {

    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        text = (TextView) findViewById(R.id.titol);


        Button boto = (Button) findViewById(R.id.opt1);

        boto.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}