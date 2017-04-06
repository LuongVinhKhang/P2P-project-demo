package com.light.p2pdemo;

import android.content.ContentResolver;
import android.content.Context;
import android.os.AsyncTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by Grey on 3/29/2017.
 */

class MyClientSocket extends AsyncTask {
    MainActivity mainActivity;

    public MyClientSocket(MainActivity activity) {
        mainActivity = activity;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        Context context = mainActivity.getApplicationContext();
        int len;
        Socket socket = new Socket();
        byte buf[] = new byte[1024];

        try {
            /**
             * Create a client socket with the host,
             * port, and timeout information.
             */
            socket.bind(null);
            socket.connect((new InetSocketAddress(mainActivity.groupOwnerAddress, MainActivity.PORT)), 500);

            /**
             * Create a byte stream from a JPEG file and pipe it to the output stream
             * of the socket. This data will be retrieved by the server device.
             */
            OutputStream outputStream = socket.getOutputStream();
            ContentResolver cr = context.getContentResolver();
            InputStream inputStream = cr.openInputStream(mainActivity.selectedImage);

            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }

            outputStream.close();
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        /**
         * Clean up any open sockets when done
         * transferring or if an exception occurred.
         */ finally {
            if (socket != null) {
                if (socket.isConnected()) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        //catch logic
                    }
                }
            }
            mainActivity.selectedImage = null;
        }
        return null;
    }
}
