package com.yongkang.layout.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.yongkang.layout.FileMainActivity;
import com.yongkang.layout.GridSpacingItemDecoration;
import com.yongkang.layout.R;
import com.yongkang.layout.filemng.FileMng;
import com.yongkang.layout.filemng.FindFolderTask;
import com.yongkang.layout.filemng.ImageModel;
import com.yongkang.layout.filemng.RcvImage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FragmentImage extends Fragment implements FragmentCallbacks {

    FileMainActivity main;
    RcvImage imageAdapter;
    RecyclerView rcvImage;
    Spinner spinner;

    List<String> listFolder = new ArrayList<>();
    List<String> listFile = new ArrayList<>();

    FindFolderTask findFolderTask;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        int position = FragmentPagerItem.getPosition(getArguments());

        if (!(getActivity() instanceof MainCallbacks)) {
            throw new IllegalStateException("Activity must implement MainCallbacks");
        }
        main = (FileMainActivity) getActivity();

        spinner = (Spinner) view.findViewById(R.id.fm_image_spinner);
        rcvImage = (RecyclerView) view.findViewById(R.id.fm_image_rv);

        // find all folder
        findFolderTask = new FindFolderTask(main, R.string.titlePhoto + "");
        findFolderTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, R.string.titlePhoto);
    }

    @Override
    public void onMsgFromMainToFragment(String strValue) {
        if (strValue.equals("send")){

            main.onMsgFromFragToMain(R.string.titlePhoto + "", imageAdapter.getSelectedItem());
        }
    }

    @Override
    public void onMsgFromFolderTask(List<String> strValue) {
        listFolder = new ArrayList<>();
        listFolder.addAll(strValue);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(main.getApplicationContext(),
                R.layout.item_spinner_custom, listFolder);
        spinner.setAdapter(spinnerAdapter);


    }

    @Override
    public void onMsgFromFileTask(List<String> strValue) {
        listFile = new ArrayList<>();
        listFile.addAll(strValue);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(main, 2);
        rcvImage.addItemDecoration(new GridSpacingItemDecoration(2, 5, false));
        rcvImage.setLayoutManager(gridLayoutManager);
        rcvImage.setItemAnimator(new DefaultItemAnimator());
        rcvImage.setNestedScrollingEnabled(false);

        List<ImageModel> list = new ArrayList<>();
        ImageModel model;
        int index = 0;
        for (String item : listFile) {
            model = new ImageModel(item, FileMng.getFileSize(new File(item)));
            list.add(model);
            if (index++ > 30) {
                break;
            }
        }

        imageAdapter = new RcvImage(main, list);
        rcvImage.setAdapter(imageAdapter);

        main.progressDialog.dismiss();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(main.getApplicationContext(), listFolder.get(i), Toast.LENGTH_LONG).show();


                List<ImageModel> list = new ArrayList<>();
                ImageModel model;
                int index = 0;

                String selectFolder = listFolder.get(i);

                for (String item : listFile) {
                    model = new ImageModel(item, FileMng.getFileSize(new File(item)));
                    if (item.contains(selectFolder)) {
                        list.add(model);
                        if (index++ > 30) {
                            break;
                        }
                    }
                }

                imageAdapter = new RcvImage(main, list);
                rcvImage.setAdapter(imageAdapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
