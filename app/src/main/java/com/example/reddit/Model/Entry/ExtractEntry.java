package com.example.reddit.Model.Entry;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ExtractEntry {
    private static final String TAG = "ExtractEntry";
    String tag;
    String xml;

    public ExtractEntry(){}

    public ExtractEntry(String tag, String xml) {
        this.tag = tag;
        this.xml = xml;
    }

    public List<String> start(){
        List<String> result = new ArrayList<>();
        String[] spilted = xml.split(tag);
        for(int i=0;i<spilted.length;++i){
            String temp = spilted[i];
            int index = temp.indexOf("\"");
            if(index>0) {
                result.add(temp.substring(0, index));
                Log.d(TAG, "snipped "+"("+tag+")"+" : " + result.get(result.size() - 1));
            }
        }

        Log.d(TAG,"\n");
        return result;
    }
}
