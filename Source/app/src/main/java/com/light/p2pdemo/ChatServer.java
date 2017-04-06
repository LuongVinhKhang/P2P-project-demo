package com.light.p2pdemo;

import android.os.AsyncTask;
import android.util.Log;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Yong Kang on 04-Apr-17.
 */

public class ChatServer extends AsyncTask<String, Boolean, String> {

    private ChatActivity chatActivity;

    public ChatServer(ChatActivity chatActivity) {
        this.chatActivity = chatActivity;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            Socket client = serverSocket.accept();

            DataInputStream dataInputStream = new DataInputStream(client.getInputStream());

            String result = dataInputStream.readUTF();

            chatActivity.friendIP = client.getInetAddress();
            Log.d("myTag chat", "friend ip " + chatActivity.friendIP.toString());

            serverSocket.close();
            dataInputStream.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            chatActivity.onReceiveMes(result);
        }
    }
}
