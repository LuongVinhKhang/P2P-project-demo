package com.light.p2pdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Grey on 3/26/2017.
 */

public class MyBroadCastReceiver extends BroadcastReceiver {

    private static final String TAG = "myTag mybcr";
    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;
    private MainActivity mActivity;
    // private MyServerSocket myServerSocket;

    public MyBroadCastReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel,
                               MainActivity activity) {
        super();
        this.mManager = manager;
        this.mChannel = channel;
        this.mActivity = activity;
    }


    @Override
    public void onReceive(Context context, final Intent intent) {
        String action = intent.getAction();
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            // Determine if Wifi P2P mode is enabled or not, alert
            // the Activity.
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                mActivity.setIsWifiP2pEnabled(true);
                Log.d(TAG, "p2p on");
            } else {
                mActivity.setIsWifiP2pEnabled(false);
                Log.d(TAG, "p2p off");
            }

        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            // The peer list has changed!  We should probably do something about
            // that.
            if (mManager != null) {
                mManager.requestPeers(mChannel, new WifiP2pManager.PeerListListener() {
                    @Override
                    public void onPeersAvailable(WifiP2pDeviceList peers) {
                        ArrayList<WifiP2pDevice> list = new ArrayList<>();
                        list.addAll(peers.getDeviceList());
                        mActivity.setDevice(list);
                        Log.d(TAG, "find peers");
                    }
                });
            }

        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            // Connection state changed!  We should probably do something about
            // that.''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
            if (mManager == null)
                return;

            final NetworkInfo networkInfo = intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);

            if (networkInfo.isConnected()) {
                Log.d(TAG, "connected");

//                if (myServerSocket == null) {
//                    Log.d(TAG, "start new server 1");
////                     myServerSocket = new MyServerSocket(mActivity);
//                    myServerSocket = new MySerSocStr(mActivity);
//                    myServerSocket.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//                } else if (myServerSocket.getStatus() == AsyncTask.Status.FINISHED) {
//                    Log.d(TAG, "start new server 2");
//                    myServerSocket = null;
////                    myServerSocket = new MyServerSocket(mActivity);
//                    myServerSocket = new MySerSocStr(mActivity);
//                    myServerSocket.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//                }

                mManager.requestConnectionInfo(mChannel, new WifiP2pManager.ConnectionInfoListener() {
                    @Override
                    public void onConnectionInfoAvailable(WifiP2pInfo info) {
                        mActivity.groupOwnerAddress = info.groupOwnerAddress;
                        Log.d(TAG, "ip owner " + info.groupOwnerAddress.toString());

                        if (info.groupFormed && info.isGroupOwner) {
                            Log.d(TAG, "host");
                            mActivity.isHost = true;
                            mActivity.textIsHost.setText("host");
                        } else if (info.groupFormed) {
                            Log.d(TAG, "not host");
                            mActivity.isHost = false;
                            mActivity.textIsHost.setText("not host");
                        }

                        // get my ip, if is host, ip = ip group owner
                        GetMemIP getMemIP = new GetMemIP();
                        mActivity.myIPAddress = getMemIP.getIP();

                        StringBuilder sb = new StringBuilder();
                        sb.append("group owner ip " + mActivity.groupOwnerAddress.toString() + "\n");
                        sb.append("my ip          " + mActivity.myIPAddress.toString() + "\n");
                        // sb.append("group mem ip   " + mActivity.groupMemAddress.toString() + "\n");
                        mActivity.textTemp.setText(sb.toString());
                    }
                });

            } else {
                mActivity.startSearch();
            }

        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {

        }
    }
}
