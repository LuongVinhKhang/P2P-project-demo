package com.yongkang.layout.fragment;

// method(s) to pass messages from fragments to MainActivity

import java.util.List;

public interface MainCallbacks {
    public void onMsgFromFragToMain(String sender, List<String> strValue);
    public void onMsgFromFolderTask(String sender, List<String>strValue);
    public void onMsgFromFileTask(String sender, List<String> strValue);
}