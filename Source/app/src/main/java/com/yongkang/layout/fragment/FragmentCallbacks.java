package com.yongkang.layout.fragment;

import java.util.List;

// method(s) to pass messages from MainActivity to Fragments
public interface FragmentCallbacks {
    public void onMsgFromMainToFragment(String strValue);
    public void onMsgFromFolderTask(List<String>strValue);
    public void onMsgFromFileTask(List<String> strValue);
}