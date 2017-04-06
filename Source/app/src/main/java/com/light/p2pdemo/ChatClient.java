package com.light.p2pdemo;

import android.os.AsyncTask;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by Yong Kang on 04-Apr-17.
 */

public class ChatClient extends AsyncTask<String, Boolean, Boolean> {

    private ChatActivity chatActivity;

    public ChatClient(ChatActivity chatActivity) {
        this.chatActivity = chatActivity;
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        Socket socket = new Socket();

        try {
            socket.bind(null);

            if (chatActivity.friendIP == null) {
                chatActivity.friendIP = chatActivity.ownerIP;
            }

            Log.d("myTag", "sent to " + chatActivity.friendIP.toString());
            socket.connect((new InetSocketAddress(chatActivity.friendIP, MainActivity.PORT)), 500);

            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            if (chatActivity.myIP == null) {
                chatActivity.myIP = socket.getInetAddress();
            }

            String t = chatActivity.myIP + "@" + strings[0];

            dataOutputStream.writeUTF(t);
            dataOutputStream.flush();

            dataOutputStream.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            if (socket.isConnected()) {
                try {
                    socket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            if (socket.isConnected()) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (result) { // sent ok
            Log.d("myTag", "sent from client");
        } else { // send fail
            Log.d("myTag", "sent fail");
        }
    }
}
