package com.yongkang.layout.filemng;

import android.util.Log;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yong Kang on 06-May-17.
 */

public class FileMng {

    public static List<String> listFolder = new ArrayList<>();
    public static List<String> listFile = new ArrayList<>();

    public static void getImageFile(File dir) {
        File list[] = dir.listFiles();
        if (list != null && list.length > 0) {
            for (File file : list) {
                if (file.isDirectory()) {
                    getImageFile(file);
                } else {

                    if (file.getName().endsWith(".png")
                            || file.getName().endsWith(".jpg")
//                            || file.getName().endsWith(".jpeg")
//                            || file.getName().endsWith(".gif")
//                            || file.getName().endsWith(".bmp")
//                            || file.getName().endsWith(".webp")
                            ) {

                        listFile.add(file.getPath());

                        String temp = file.getPath().substring(0, file.getPath().lastIndexOf('/'));
                        if (!listFolder.contains(temp)) {
                            listFolder.add(temp);
                            Log.v("VinhKhang", temp);
                        }
                    }
                }
            }
        }
    }

    public static void getVideoFile(File dir) {
        File list[] = dir.listFiles();
        if (list != null && list.length > 0) {
            for (File file : list) {
                if (file.isDirectory()) {
                    getImageFile(file);
                } else {

                    if (file.getName().endsWith(".mp4")
                            || file.getName().endsWith(".3gpp")
                            ) {

                        listFile.add(file.getPath());

                        String temp = file.getPath().substring(0, file.getPath().lastIndexOf('/'));
                        if (!listFolder.contains(temp)) {
                            listFolder.add(temp);
                            Log.v("VinhKhang", temp);
                        }
                    }
                }
            }
        }
    }


    public static String getFileSize(File file) {

        Long size = file.length();
        String hrSize = "";

        double m = size / 1024.0;
        DecimalFormat dec = new DecimalFormat("0.00");

        if (m > 1) {
            if (m > 900) {
                m = m / 1024.0;
                hrSize = dec.format(m).concat(" MB");
            } else {
                hrSize = dec.format(m).concat(" KB");
            }
        } else {
            hrSize = dec.format(size).concat(" B");
        }
        return hrSize;
    }
}
