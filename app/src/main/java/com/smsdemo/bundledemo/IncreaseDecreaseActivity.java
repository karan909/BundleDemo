package com.smsdemo.bundledemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class IncreaseDecreaseActivity extends AppCompatActivity {

    int minteger = 0;
    private Button btnInc, btnDec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_increase_decrease);

        btnInc = (Button) findViewById(R.id.increase);
        btnDec = (Button) findViewById(R.id.decrease);

        btnInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseInteger(v);
            }
        });


        btnDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseInteger(v);
            }
        });

    }

    public void increaseInteger(View view) {

        if(minteger < 10){
            minteger = minteger + 1;
            display(minteger);
        }


    }public void decreaseInteger(View view) {

        if(minteger > 0){

            minteger = minteger - 1;
            display(minteger);

        }


    }

    private void display(int number) {
        TextView displayInteger = (TextView) findViewById(
                R.id.integer_number);
        displayInteger.setText("" + number);
    }
}
