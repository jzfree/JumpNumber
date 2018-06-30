package com.jzfree.jumpnumber.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jzfree.jumpnumber.JumpNumber;

public class MainActivity extends AppCompatActivity {
    JumpNumber jumpNumber;
    TextView textViewLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jumpNumber = findViewById(R.id.jumpNumber);
//        jumpNumber.setNumber(98);
//        jumpNumber.setTextColor(Color.GREEN);
//        jumpNumber.setTextSize(50);

        textViewLog = findViewById(R.id.log);
        textViewLog.setMovementMethod(new ScrollingMovementMethod());

        Button buttonPlus = findViewById(R.id.button_plus);
        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = jumpNumber.plus();
                addLog("plus number: " + result + " " + jumpNumber.getNumber());
            }
        });

        Button buttonMinus = findViewById(R.id.button_minus);
        buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = jumpNumber.minus();
                addLog("minus number: " + result + " " + jumpNumber.getNumber());
            }
        });

        final EditText editTextNumber = findViewById(R.id.number);
        Button buttonSet = findViewById(R.id.button_set);
        buttonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpNumber.setNumber(Integer.parseInt(editTextNumber.getText().toString()));
                addLog("set number: " + jumpNumber.getNumber());
            }
        });
    }

    private void addLog(String log) {
        textViewLog.append("\n" + log);
    }
}
