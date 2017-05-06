package com.yongkang.layout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DeviceActivity extends AppCompatActivity {

    TextView textStatus;
    RecyclerView rcv;
    DeviceAdapter adapter;
    List<DeviceModel> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);

        Toolbar toolbar = (Toolbar) findViewById(R.id.device_toolbar);
        toolbar.setTitle(R.string.titledevice);
        setSupportActionBar(toolbar);

        textStatus = (TextView) findViewById(R.id.device_text_status);
        rcv = (RecyclerView) findViewById(R.id.device_rcv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcv.setLayoutManager(linearLayoutManager);
        rcv.setItemAnimator(new DefaultItemAnimator());


        list.add(new DeviceModel(false, "192.168.1.1"));
        list.add(new DeviceModel(false, "192.168.1.2"));
        list.add(new DeviceModel(true, "192.168.1.3"));
        list.add(new DeviceModel(false, "192.168.1.4"));
        list.add(new DeviceModel(false, "192.168.1.5"));
        list.add(new DeviceModel(false, "192.168.1.6"));
        list.add(new DeviceModel(false, "192.168.1.7"));
        list.add(new DeviceModel(false, "192.168.1.8"));
        list.add(new DeviceModel(false, "192.168.1.9"));
        list.add(new DeviceModel(false, "192.168.1.10"));



        adapter = new DeviceAdapter(this, list);
        rcv.setAdapter(adapter);
    }
}
