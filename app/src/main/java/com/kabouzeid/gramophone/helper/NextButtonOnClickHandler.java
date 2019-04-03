package com.kabouzeid.gramophone.helper;

import android.view.View;

/**
 * @author Karim Abou Zeid (kabouzeid)
 */
public class NextButtonOnClickHandler implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        MusicPlayerRemote.playNextSong();
    }
}
