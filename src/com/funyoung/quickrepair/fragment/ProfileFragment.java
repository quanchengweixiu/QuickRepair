
package com.funyoung.quickrepair.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.funyoung.quickrepair.model.User;
import com.funyoung.qcwx.R;

public class ProfileFragment extends BaseFragment {
    private static final String TAG = "ProfileFragment";

    private View mRootView;
    private User mUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_profile, container, false);

        if (null != mRootView) {
        }

        return mRootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void updateProfile(User user) {
    }
}
