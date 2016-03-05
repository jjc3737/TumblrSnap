package com.codepath.apps.tumblrsnap.fragments;

import android.app.DialogFragment;
import android.widget.Button;
import android.widget.EditText;

import com.codepath.apps.tumblrsnap.R;
import com.codepath.apps.tumblrsnap.TumblrClient;

import butterknife.Bind;

/**
 * Created by JaneChung on 3/4/16.
 */
public class CommentFragment extends DialogFragment {

    @Bind(R.id.etComment)
    EditText commentTExt;
    @Bind(R.id.btnComment)
    Button commentButton;

    private TumblrClient client;



}
