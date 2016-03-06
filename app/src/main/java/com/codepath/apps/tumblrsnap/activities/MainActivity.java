package com.codepath.apps.tumblrsnap.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.tumblrsnap.R;
import com.codepath.apps.tumblrsnap.fragments.LoginFragment;
import com.codepath.apps.tumblrsnap.fragments.LoginFragment.OnLoginHandler;
import com.codepath.apps.tumblrsnap.fragments.PhotosFragment;
import com.codepath.apps.tumblrsnap.models.User;

public class MainActivity extends FragmentActivity implements OnLoginHandler {

    private Boolean mReturendWithResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mReturendWithResult = false;
    }

    @Override
    protected void onResume() {
        super.onResume();

       // updateFragments();
    }

//    @Override
//    protected void onPostResume() {
//        super.onPostResume();
//        if (mReturendWithResult) {
//            showLoginFragment();
//            mReturendWithResult = false;
//        }
//    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        updateFragments();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem settings = menu.findItem(R.id.action_settings);
        if (User.currentUser() == null) {
            settings.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        } else {
            settings.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }

        return true;
    }

    public void onSettingsButton(MenuItem menuItem) {
        Intent i = new Intent(this, SettingsActivity.class);
        startActivityForResult(i, 1);
    }

    @Override
    public void onLogin() {
        updateFragments();
    }

    private void updateFragments() {
//        if (mReturendWithResult) {
//            showLoginFragment();
//            return;
//        }
        if (User.currentUser() == null) {
            showLoginFragment();
        } else {
            showPhotosFragment();
        }
        invalidateOptionsMenu();
    }

    private void showPhotosFragment() {
        showFragment(PhotosFragment.class);
    }

    private void showLoginFragment() {
        showFragment(LoginFragment.class);
    }

    @SuppressWarnings("rawtypes")
    private void showFragment(Class activeFragmentClass) {
        Class[] fragmentClasses = new Class[] { LoginFragment.class,
                PhotosFragment.class };
        FragmentManager mgr = getSupportFragmentManager();
        FragmentTransaction transaction = mgr.beginTransaction();
        try {
            for (Class klass : fragmentClasses) {
                Fragment fragment = mgr.findFragmentByTag(klass.getName());
                if (klass == activeFragmentClass) {
                    if (fragment != null) {
                        transaction.show(fragment);
                    } else {
                        transaction.add(R.id.frmContent, (Fragment) klass.newInstance(), klass.getName());
                    }
                } else {
                    if (fragment != null) {
                        transaction.hide(fragment);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        transaction.commit();
    }

    //Todo Get info back from setting: showLoginFragment


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                Boolean result = data.getBooleanExtra("loggedOut", false);
                if (result) {
                    mReturendWithResult = true;
                }
            }
        }
    }

}
