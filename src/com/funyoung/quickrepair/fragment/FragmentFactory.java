package com.funyoung.quickrepair.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.TextUtils;

import com.funyoung.quickrepair.model.User;

import com.funyoung.qcwx.R;

/**
 * Created by yangfeng on 13-8-10.
 */
public class FragmentFactory {

    private static final String FRAGMENT_DEFAULT = "FRAGMENT_DEFAULT";
    private static final String FRAGMENT_LOGIN = "FRAGMENT_LOGIN";
    private static final String FRAGMENT_MAP = "FRAGMENT_MAP";
    private static final String FRAGMENT_PROFILE = "FRAGMENT_PROFILE";

    private Fragment mCurrentFragment;
    private Fragment loginFragment;
    private Fragment locationFragment;
    private Fragment defaultFragment;
    private Fragment profileFragment;
    private Fragment postFragment;
    public void gotoLoinFragment() {
        if (null == loginFragment) {
            loginFragment = new SignUpFragment();
        }

        if (mCurrentFragment != loginFragment) {
            mCurrentFragment = loginFragment;
            gotoFragmentView(mCurrentFragment, FRAGMENT_LOGIN, FRAGMENT_DEFAULT);
        }

    }

    public void gotoLocationFragment() {
        if (null == locationFragment) {
            locationFragment = new LocationOverlayFragment();
        }

        if (mCurrentFragment != locationFragment) {
            mCurrentFragment = locationFragment;
            gotoFragmentView(mCurrentFragment, FRAGMENT_MAP, FRAGMENT_DEFAULT);
        }
    }
    public  void gotoDefaultView() {
        if (null == defaultFragment) {
            defaultFragment = new CategoryGridFragment();
            Bundle args = new Bundle();
//            args.putInt(PlanetFragment.ARG_PLANET_NUMBER, mDefaultPosition);
            defaultFragment.setArguments(args);
        }

        if (mCurrentFragment != defaultFragment) {
            mCurrentFragment = defaultFragment;
            gotoFragmentView(mCurrentFragment, FRAGMENT_DEFAULT, null);
        }
    }

    public void gotoProfileFragment(User user) {
        if (null == profileFragment) {
            ProfileFragment fragment = new ProfileFragment();
            fragment.updateProfile(user);
            profileFragment = fragment;
        }

        if (mCurrentFragment != profileFragment) {
            Bundle args = user.toBundle();
            profileFragment.setArguments(args);
            mCurrentFragment = profileFragment;
            gotoFragmentView(mCurrentFragment,  FRAGMENT_PROFILE,  FRAGMENT_DEFAULT);
        } else {
            ((ProfileFragment)mCurrentFragment).updateProfile(user);
        }
    }

    public void gotoPostFragment(User user) {
        if (null == postFragment) {
            PostFragment fragment = new PostFragment();
            postFragment = fragment;
        }

        if (mCurrentFragment != postFragment) {
            mCurrentFragment = postFragment;
            gotoFragmentView(mCurrentFragment,  FRAGMENT_PROFILE,  FRAGMENT_DEFAULT);
        }
        ((PostFragment)mCurrentFragment).updateProfile(user);
    }
    private void gotoFragmentView(Fragment fragment, String name, String stackName) {
        FragmentTransaction ft = _fragmentManager.beginTransaction();
        if (true && TextUtils.isEmpty(name)) {
            ft.replace(R.id.content_frame, fragment);
        } else {
            ft.replace(R.id.content_frame, fragment, name);
        }
        if (!TextUtils.isEmpty(stackName)) {
            ft.addToBackStack(stackName);
        }
        ft.commit();
    }

    private static FragmentFactory _instance;
    private static FragmentManager _fragmentManager;
    public static FragmentFactory getInstance(Activity context) {
        if (null == _instance) {
            _instance = new FragmentFactory();
        }

        if (null == _fragmentManager) {
            _fragmentManager = context.getFragmentManager();
        }

        return _instance;
    }

    public boolean isDefaultFragment() {
        return mCurrentFragment == defaultFragment;
    }

    public void onDestroy() {
        defaultFragment = null;
        locationFragment = null;
        loginFragment = null;
        profileFragment = null;
        postFragment = null;
        _instance = null;
    }
}
