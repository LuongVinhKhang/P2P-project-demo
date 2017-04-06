package com.light.p2pdemo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int PORT = 8888;
    private static final String TAG = "myTAG main";
    private final IntentFilter intentFilter = new IntentFilter();

    public Boolean isHost = false;
    public InetAddress myIPAddress;
    public InetAddress groupOwnerAddress;
    public InetAddress groupMemAddress;

    public TextView textIsHost, textTemp;
    public Uri selectedImage;
    private WifiP2pManager.Channel mChannel;
    private WifiP2pManager mManager;
    private MyBroadCastReceiver mReceiver;
    private boolean mWifiDirectState;
    private ListView mList;
    private ArrayList<WifiP2pDevice> device = new ArrayList<>();
    private ArrayList<String> deviceName = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private Button btnRefresh, btnSend;
    private int RESULT_LOAD_IMAGE = 0;


    public void startSearch() {
        mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "init successfully");
                Toast.makeText(MainActivity.this, "start peers success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int reason) {
                Log.d(TAG, "init not successfully");
                Toast.makeText(MainActivity.this, "start peers success", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void stopSearch() {
        mManager.stopPeerDiscovery(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(MainActivity.this, "stop peers success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int reason) {
                Toast.makeText(MainActivity.this, "stop peers fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setIsWifiP2pEnabled(boolean state) {
        mWifiDirectState = state;
    }

    public void setDevice(List<WifiP2pDevice> d) {
        device.clear();
        device.addAll(d);
        deviceName.clear();

        for (WifiP2pDevice dv : d) {
            deviceName.add(dv.deviceName);
        }

        adapter.notifyDataSetChanged();
        Log.d(TAG, "Data changed");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);
        mReceiver = new MyBroadCastReceiver(mManager, mChannel, this);
        mList = (ListView) findViewById(R.id.list_view);
        adapter = new ArrayAdapter<>(this, R.layout.device_name_list, deviceName);
        mList.setAdapter(adapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {
                WifiP2pConfig config = new WifiP2pConfig();
                config.deviceAddress = device.get(position).deviceAddress;
                config.wps.setup = WpsInfo.PBC;
                mManager.connect(mChannel, config, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "connect success");
                        // btnSend.setVisibility(View.VISIBLE);

                        // start chat activity
                        Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                        intent.putExtra("myIP", myIPAddress);
                        intent.putExtra("ownerIP", groupOwnerAddress);

                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(int reason) {
                        Log.d(TAG, "connect fail");
                        btnSend.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });

        startSearch();

        btnRefresh = (Button) findViewById(R.id.button);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopSearch();
                startSearch();
            }
        });

        btnSend = (Button) findViewById(R.id.button_2);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // startGallery();
            }
        });

        textIsHost = (TextView) findViewById(R.id.textView);
        textTemp = (TextView) findViewById(R.id.text_temp);
    }

    public void startGallery() {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            selectedImage = data.getData();

//            MyClientSocket myClientSocket = new MyClientSocket(this);
//            myClientSocket.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//
//            MyClientSocketK myClientSocketK = new MyClientSocketK(this);
//            myClientSocketK.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

    public void updateList(String ipMem) {
        Toast.makeText(getApplicationContext(), ipMem, Toast.LENGTH_LONG).show();
        String t = textTemp.getText().toString();
        t += "\n" + ipMem;
        textTemp.setText(t);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }
}

