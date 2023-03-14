package com.example.bluetoothchatting;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btn_on, btn_off, btn_paired_Activity;
    int requestCodeForEnable = 1;

    // Create object of bluetoothAdapter
    BluetoothAdapter myBluetoothAdapter;
    // Intent
    Intent btEnablingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_on = findViewById(R.id.bt_on);
        btn_off = findViewById(R.id.bt_off);
        btn_paired_Activity = findViewById(R.id.iNext);


        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        btEnablingIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);


        // Create bluetooth on Method
        bluetoothONMethod();
        bluetoothOFFMethod();
        moveToNextActivity();
    }

    private void moveToNextActivity() {
        btn_paired_Activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iNext = new Intent(getApplicationContext(), PairdDevice.class);
                startActivity(iNext);
            }
        });
    }


    private void bluetoothOFFMethod() {
        btn_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myBluetoothAdapter.isEnabled()) {
                    myBluetoothAdapter.disable();
                    Toast.makeText(getApplicationContext(),"Bluetooth is Disable", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // Check Requestocde matched
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == requestCodeForEnable) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), "Bluetooth is Enable", Toast.LENGTH_SHORT).show();
                btn_on.setEnabled(true);
            } else if (requestCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Bluetooth Enabling cancel", Toast.LENGTH_SHORT).show();
                btn_on.setEnabled(true);
            }

        }
    }

    private void bluetoothONMethod() {
        btn_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check Device support Bluetooth or not
                if (myBluetoothAdapter == null) {
                    Toast.makeText(getApplicationContext(),"Bluetooth does not support on this device", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (!myBluetoothAdapter.isEnabled()) {
                        // To enable the bluetooth we need 2 objects
                        btn_on.setEnabled(false);
                        startActivityForResult(btEnablingIntent,requestCodeForEnable);
                    }
                }
            }
        });
    }
}