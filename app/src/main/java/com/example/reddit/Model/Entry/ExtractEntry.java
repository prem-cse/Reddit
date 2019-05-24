package com.example.reddit.Model.Entry;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ExtractEntry {
    private static final String TAG = "ExtractEntry";
    String tag;
    String xml;
    String endtag;

    public ExtractEntry(){}

    public ExtractEntry(String tag, String xml) {
        this.tag = tag;
        this.xml = xml;
        this.endtag = "NONE";
    }
    public ExtractEntry(String tag, String xml,String endtag) {
        this.tag = tag;
        this.xml = xml;
        this.endtag = endtag;
    }

    public List<String> start(){
        List<String> result = new ArrayList<>();
        String[] spilted = null;
        String marker = null;

        if(endtag.equals("NONE")){
            marker = "\"";
            spilted = xml.split(tag);
        }else{
             marker = endtag;
             spilted = xml.split(tag);
        }
        for(int i=0;i<spilted.length;++i){
            String temp = spilted[i];
            int index = temp.indexOf(marker);
            if(index>0) {
                result.add(temp.substring(0, index));
                Log.d(TAG, "snipped : "+"("+tag+")"+" -> " + result.get(result.size() - 1));
            }
        }

        Log.d(TAG,"\n");
        return result;
    }
}
