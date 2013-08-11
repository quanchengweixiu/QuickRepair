package com.funyoung.quickrepair.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.TextUtils;

import com.funyoung.quickrepair.model.User;

import baidumapsdk.demo.R;

/**
 * Created by yangfeng on 13-8-10.
 */
public class FragmentFactory {

    private static final String FRAGMENT_DEFAULT = "FRAGMENT_DEFAULT";
    private static final String FRAGMENT_LOGIN = "FRAGMENT_LOGIN";
    private static final String FRAGMENT_MAP = "FRAGMENT_MAP";

    private Fragment mCurrentFragment;
    private Fragment loginFragment;
    private Fragment locationFragment;
    private Fragment defaultFragment;

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

    // todo: goto user profile
    public void gotoProfileFragment(User user) {

    }

    private void gotoFragmentView(Fragment fragment, String name, String stackName) {
        FragmentTransaction ft = _fragmentManager.beginTransaction();
        if (TextUtils.isEmpty(name)) {
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
}
