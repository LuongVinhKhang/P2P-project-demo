package com.yongkang.layout;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.yongkang.layout.fragment.FragmentImage;
import com.yongkang.layout.fragment.MainCallbacks;

import java.util.List;


public class FileMainActivity extends AppCompatActivity implements MainCallbacks {

    public ProgressDialog progressDialog;
    FragmentTransaction fragmentTransaction;
    FragmentImage fragmentImage;
    FragmentPagerItemAdapter adapter;
    ViewPager viewPager;
    SmartTabLayout viewPagerTab;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_main);

//        fragmentTransaction = getFragmentManager().beginTransaction();
//        fragmentImage = FragmentImage.newInstance("first-blue");
//        fragmentTransaction.replace(R.id.main_holder_blue, blueFragment);
//        fragmentTransaction.commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.file_main_toolbar);
        toolbar.setTitle(R.string.titleSelect);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add(R.string.titlePhoto, FragmentImage.class)
                .add(R.string.titleVideo, FragmentImage.class)
                .add(R.string.titleAudio, FragmentImage.class)
//                .add(R.string.titleDocument, FragmentImage.class)
//                .add(R.string.titleFile, FragmentImage.class)
//                .add(R.string.titleRecently, FragmentImage.class)
                .create());

        viewPager = (ViewPager) findViewById(R.id.file_main_viewpager);
        viewPager.setAdapter(adapter);

        viewPagerTab = (SmartTabLayout) findViewById(R.id.file_main_viewpagertab);
        viewPagerTab.setViewPager(viewPager);

        fab = (FloatingActionButton) findViewById(R.id.file_main_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = viewPager.getCurrentItem();

                switch (pos) {
                    case 0: {
                        FragmentImage fm = (FragmentImage) adapter.getPage(0);
                        fm.onMsgFromMainToFragment("send");
                        break;
                    }
                    case 1: {
//                        FragmentVideo fm = (FragmentImage) adapter.getPage(0);
//                        fm.onMsgFromMainToFragment("Ahihi");
                        break;
                    }

                }
            }
        });

        progressDialog = new ProgressDialog(FileMainActivity.this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.file_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.menu_file_main_uncheck) {
            Toast.makeText(getApplicationContext(), "check", Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.menu_file_main_check) {
            Toast.makeText(getApplicationContext(), "check", Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.menu_file_main_search) {
            Toast.makeText(getApplicationContext(), "This function is not yet installed", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMsgFromFragToMain(String sender, List<String> strValue) {

        String temp = "";

        for (String s : strValue) {
            Log.e("VinhKhang send file", s);
            temp+=s+"\n";
        }

        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Send this")
                .setMessage(temp)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create();

        dialog.show();
    }

    @Override
    public void onMsgFromFolderTask(String sender, List<String> strValue) {
        int ID = Integer.parseInt(sender);
        switch (ID) {
            case R.string.titlePhoto:
                FragmentImage fm = (FragmentImage) adapter.getPage(getPos(ID));
                fm.onMsgFromFolderTask(strValue);
            case R.string.titleVideo:
                break;
            case R.string.titleAudio:
                break;
            case R.string.titleDocument:
                break;
            case R.string.titleFile:
                break;
            case R.string.titleRecently:
                break;
            default:
                break;
        }

        strValue.clear();
    }

    @Override
    public void onMsgFromFileTask(String sender, List<String> strValue) {
        int ID = Integer.parseInt(sender);
        switch (ID) {
            case R.string.titlePhoto:
                FragmentImage fm = (FragmentImage) adapter.getPage(getPos(ID));
                fm.onMsgFromFileTask(strValue);
            case R.string.titleVideo:
                break;
            case R.string.titleAudio:
                break;
            case R.string.titleDocument:
                break;
            case R.string.titleFile:
                break;
            case R.string.titleRecently:
                break;
            default:
                break;
        }

        strValue.clear();
    }


    private Integer getPos(int ID) {
        switch (ID) {
            case R.string.titlePhoto:
                return 0;
            case R.string.titleVideo:
                return 1;
            case R.string.titleAudio:
                return 2;
            case R.string.titleDocument:
                return 3;
            case R.string.titleFile:
                return 3;
            case R.string.titleRecently:
                return 5;
            default:
                return -1;
        }
    }
}
