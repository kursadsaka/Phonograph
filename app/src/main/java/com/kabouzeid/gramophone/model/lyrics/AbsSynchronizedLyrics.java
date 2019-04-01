package com.kabouzeid.gramophone.model.lyrics;

import android.util.SparseArray;

public abstract class AbsSynchronizedLyrics extends Lyrics {
    private static final int TIME_OFFSET_MS = 500; // time adjustment to display line before it actually starts

    protected final SparseArray<String> lines = new SparseArray<>();
    protected int offset = 0;
    private int lineCount=1;
    public int getLineCount(){
        return lineCount;
    }
    public void changeLineCount(){
        lineCount=((lineCount + 2) % 6);
    }
    public String getLine(int time) {
        time += offset + AbsSynchronizedLyrics.TIME_OFFSET_MS;

        int lastLineTime = lines.keyAt(0);
        for (int i = 0; i < lines.size(); i++) {
            int lineTime = lines.keyAt(i);

            if (time >= lineTime) {
                lastLineTime = lineTime;
            } else {
                break;
            }
        }
        String allLines=lines.get(lastLineTime);    //for 1 line
        if(lineCount==3){       //for 3 lines
            String before="";
            String after="";
            if(lines.indexOfKey(lastLineTime)!=0) {
                int beforeLastLine=lines.keyAt(lines.indexOfKey(lastLineTime) - 1);
                before=lines.get(beforeLastLine);
            }
            if(lines.indexOfKey(lastLineTime)<lines.size()-1) {
                int afterLastLine=lines.keyAt(lines.indexOfKey(lastLineTime) + 1);
                after=lines.get(afterLastLine);
            }
            String current=lines.get(lastLineTime);
            allLines=before+"\n"+current+"\n"+after;
        }else if(lineCount==5){     //for 5 lines
            String before="";
            String beforeBefore="";
            String after="";
            String afterAfter="";
            if(lines.indexOfKey(lastLineTime)!=0) {
                int beforeLastLine=lines.keyAt(lines.indexOfKey(lastLineTime) - 1);
                before=lines.get(beforeLastLine);
            }
            if(lines.indexOfKey(lastLineTime)!=0 && lines.indexOfKey(lastLineTime)!=1) {
                int beforeBeforeLastLine=lines.keyAt(lines.indexOfKey(lastLineTime) - 2);
                beforeBefore=lines.get(beforeBeforeLastLine);
            }
            if(lines.indexOfKey(lastLineTime)<lines.size()-1) {
                int afterLastLine=lines.keyAt(lines.indexOfKey(lastLineTime) + 1);
                after=lines.get(afterLastLine);
            }
            if(lines.indexOfKey(lastLineTime)<lines.size()-1 && lines.indexOfKey(lastLineTime)<lines.size()-2) {
                int afterAfterLastLine=lines.keyAt(lines.indexOfKey(lastLineTime) + 2);
                afterAfter=lines.get(afterAfterLastLine);
            }
            String current=lines.get(lastLineTime);
            allLines=beforeBefore+"\n"+before+"\n"+current+"\n"+after+"\n"+afterAfter;
        }

        return allLines;
    }

    public boolean isSynchronized() {
        return true;
    }

    public boolean isValid() {
        parse(true);
        return valid;
    }

    @Override
    public String getText() {
        parse(false);

        if (valid) {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < lines.size(); i++) {
                String line = lines.valueAt(i);
                sb.append(line).append("\r\n");
            }

            return sb.toString().trim().replaceAll("(\r?\n){3,}", "\r\n\r\n");
        }

        return super.getText();
    }
}
