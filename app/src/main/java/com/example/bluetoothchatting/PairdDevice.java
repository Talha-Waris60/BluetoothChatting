package com.example.bluetoothchatting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.Set;

public class PairdDevice extends AppCompatActivity {
    Button button, btn_scanActivity;
    ListView paired_Device_list;
    BluetoothAdapter bluetoothAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paird_device);
        button = findViewById(R.id.paired_button);
        paired_Device_list = findViewById(R.id.paired_Devices_List);
        btn_scanActivity = findViewById(R.id.scan_btn);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        exeButton();

        btn_scanActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iScan = new Intent(PairdDevice.this,ScanDevice.class);
                startActivity(iScan);
            }
        });
    }

    private void exeButton() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Set<BluetoothDevice> bt = bluetoothAdapter.getBondedDevices();
                // Create an array that size is similar to bt
                String[] strings = new String[bt.size()];
                int index = 0;
                
                if (bt.size()>0) {
                    for (BluetoothDevice device : bt) {
                        strings[index] = device.getName();
                        index ++;
                    }
                    ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, strings);
                    paired_Device_list.setAdapter(stringArrayAdapter);
                }
            }
        });
    }
}