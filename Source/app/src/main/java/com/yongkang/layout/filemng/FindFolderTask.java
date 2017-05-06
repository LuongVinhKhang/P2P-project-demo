package com.yongkang.layout.filemng;


import android.os.AsyncTask;
import android.os.Environment;

import com.yongkang.layout.FileMainActivity;
import com.yongkang.layout.R;
import com.yongkang.layout.fragment.FragmentCustom;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yong Kang on 06-May-17.
 */

public class FindFolderTask extends AsyncTask<Integer, Void, Boolean> {

    FileMainActivity main;
    String sender;

    List<String> listFolder = new ArrayList<>();
    List<String> listFile = new ArrayList<>();

    public FindFolderTask(FileMainActivity main, String sender) {
        this.main = main;
        this.sender = sender;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        main.progressDialog.show();
    }

    @Override
    protected Boolean doInBackground(Integer... ID) {

        if (ID[0] == R.string.titlePhoto) {
//            String path1 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM";
//            String path2 = System.getenv("SECONDARY_STORAGE") + "/DCIM";

            String path1 = Environment.getExternalStorageDirectory().getAbsolutePath();
            String path2 = System.getenv("SECONDARY_STORAGE");

            // listFolder.add("Internal");
            FileMng.getImageFile(new File(path1));
            listFolder.addAll(FileMng.listFolder);
            listFile.addAll(FileMng.listFile);
            // listFolder.add("External");
            FileMng.getImageFile(new File(path2));
            listFolder.addAll(FileMng.listFolder);
            listFile.addAll(FileMng.listFile);
        }

        return true;
    }


    @Override
    protected void onPostExecute(Boolean value) {
        super.onPostExecute(value);
        main.progressDialog.dismiss();
        main.onMsgFromFolderTask(sender, listFolder);
        main.onMsgFromFileTask(sender, listFile);

        FileMng.listFolder.clear();
        FileMng.listFile.clear();
        listFolder.clear();
        listFile.clear();
    }
}
