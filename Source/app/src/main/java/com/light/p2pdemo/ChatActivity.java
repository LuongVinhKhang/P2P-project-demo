package com.light.p2pdemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    TextView textChatMes;
    Button btnSend;
    List<String> list;

    ChatAdapter chatAdapter;
    RecyclerView rvChat;

    InetAddress myIP, ownerIP, friendIP = null;
    List<InetAddress> groupIP;

    ChatServer chatServer;
    ChatClient chatClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        textChatMes = (TextView) findViewById(R.id.text_chat_message);
        btnSend = (Button) findViewById(R.id.button_chat_send);

        rvChat = (RecyclerView) findViewById(R.id.rv_chat_message);


        list = new ArrayList<String>();
        chatAdapter = new ChatAdapter(this, list);

        rvChat.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvChat.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvChat.addItemDecoration(dividerItemDecoration);

        rvChat.setAdapter(chatAdapter);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = textChatMes.getText().toString();
                onSendMes(data);
            }
        });


        // retrieve data
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            myIP = (InetAddress) extras.getSerializable("myIP");
            ownerIP = (InetAddress) extras.getSerializable("ownerIP");
        } else {
            Toast.makeText(ChatActivity.this, "Chat FAIL, no data from main activity", Toast.LENGTH_LONG).show();
            finish();
        }

        createChatServerSocket();
    }

    private void createChatServerSocket() {
        chatServer = new ChatServer(ChatActivity.this);
        chatServer.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void onReceiveMes(String data) {
        chatAdapter.add(data);

        int pos = data.indexOf("@");
        String ip = data.substring(0, pos - 1);
        String mes = data.substring(pos + 1, data.length() - 1);



        // create another socket to receive data
        createChatServerSocket();
    }

    public void onSendMes(String data) {
        // chatAdapter.add(data);

        chatClient = new ChatClient(ChatActivity.this);
        chatClient.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, data);
    }
}

