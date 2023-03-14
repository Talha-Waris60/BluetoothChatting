package com.example.bluetoothchatting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ScanDevice extends AppCompatActivity {
    ListView scan_Listview;
    Button btn_scan;

    // Declare ArrayList
    ArrayList<String> stringArrayList = new ArrayList<String>();
    ArrayAdapter<String> stringArrayAdapter;
    BluetoothAdapter myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_device);

        btn_scan = findViewById(R.id.btn_scan_Device);
        scan_Listview = findViewById(R.id.scan_Devices_List);

        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // This method discover nearby devices

                myBluetoothAdapter.startDiscovery();
            }
        });
        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(broadcastReceiver, intentFilter);

        stringArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, stringArrayList);
        scan_Listview.setAdapter(stringArrayAdapter);

    }

    // BroadCast Receiver outside OncreateMethod
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                stringArrayList.add(device.getName());
                stringArrayAdapter.notifyDataSetChanged();


            }
        }
    };
}