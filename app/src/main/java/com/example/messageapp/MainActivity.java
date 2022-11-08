package com.example.messageapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;
    EditText number, message;
    Button btnSend, btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        number = findViewById(R.id.et_PhoneNum);
        message = findViewById(R.id.et_Message);
        btnSend = findViewById(R.id.btn_Send);
        btnSend.setEnabled(false);
        btnClear = findViewById(R.id.btn_Clear);

        if (checkPermission(Manifest.permission.SEND_SMS))
        {
            btnSend.setEnabled(true);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);
        }

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Phone=number.getText().toString();
                String Msg=message.getText().toString();

                if (Phone == null || Phone.length() == 0 || Msg.length() == 0 || Msg == null)
                {
                    return;
                }
                if (checkPermission(Manifest.permission.SEND_SMS))
                {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(Phone, null, Msg, null, null);
                    Toast.makeText(MainActivity.this, "Message successful sent.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Message is not sent!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message.setText("");
                number.setText("");
            }
        });

        }

    public boolean checkPermission(String permission) {
        int check = ContextCompat.checkSelfPermission(this, permission);
        return(check == PackageManager.PERMISSION_GRANTED);
    }
}