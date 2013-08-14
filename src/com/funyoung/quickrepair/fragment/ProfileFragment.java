
package com.funyoung.quickrepair.fragment;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.funyoung.quickrepair.MainActivity;
import com.funyoung.quickrepair.model.User;
import com.funyoung.qcwx.R;
import com.funyoung.quickrepair.transport.UsersClient;
import com.funyoung.quickrepair.utils.PerformanceUtils;

import baidumapsdk.demo.DemoApplication;

public class ProfileFragment extends BaseFragment {
    private static final String TAG = "ProfileFragment";

    private View mRootView;
    private User mUser;

    private AsyncTask<Void, Void, String> mLoginTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_profile, container, false);

        if (null != mRootView) {
            initViews(inflater);
        }

        performLoginTask();

        return mRootView;
    }

    private void initViews(LayoutInflater inflater) {
        if (null == mRootView) {
            Log.e(TAG, "initViews, error with null view root");
            return;
        }

        if (mUser == null) {
            Log.e(TAG, "initViews, error with empty user");
            return;
        }

        ViewGroup profileContainer = (ViewGroup)mRootView.findViewById(R.id.container);
        if (null == profileContainer) {
            Log.e(TAG, "initViews, error with null view container");
            return;
        }

        if (profileContainer instanceof ViewGroup) {
            View header = profileContainer.findViewById(R.id.profile_header);
            if (null != header) {
                ImageView photoView = (ImageView)header.findViewById(R.id.img_profile);
                if (null != photoView) {
                    final String url = mUser.getAvatarUrl();
                    if (!TextUtils.isEmpty(url)) {
                        photoView.setImageURI(Uri.parse(url));
                    }
                }
            }

            final String name = mUser.getNickName();
            if (!TextUtils.isEmpty(name)) {
                addItemBane(inflater, profileContainer, "名字", name,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // todo: edit name
                            }
                        });
            }
        }
    }

    private void addItemBane(LayoutInflater inflater, ViewGroup profileContainer,
                             String nameLabel, String nameValue,
                             View.OnClickListener onClickListener) {
        View itemView = inflater.inflate(R.layout.simple_profile_item, profileContainer);
        if (null != itemView) {
            TextView textView = (TextView)itemView.findViewById(R.id.tv_label);
            if (null != textView) {
                textView.setText(nameLabel);
            }
            textView = (TextView)itemView.findViewById(R.id.tv_content);
            if (null != textView) {
                textView.setText(nameValue);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mLoginTask) {
            mLoginTask.cancel(true);
            mLoginTask = null;
        }
    }

    public void updateProfile(User user) {
        mUser = user;
    }


    private void performLoginTask() {
        if (null == mUser || mUser.getUid() <= 0) {
            Log.e(TAG, "performLoginTask, skip without valid user.");
            return;
        }

        if (null == mLoginTask) {
            mLoginTask = new AsyncTask<Void, Void, String>() {
                boolean mResult = false;
                long startTime;
                @Override
                protected void onPreExecute() {
                    mResult = false;
                    startTime = System.currentTimeMillis();
                }

                @Override
                protected String doInBackground(Void... voids) {
                    try {
                        User user = UsersClient.getProfile(getActivity(), mUser.getUid());
                        if (null == user) {
                            mResult = false;
                        } else {
                            mResult = true;
                            mUser = user;
                        }
                        return "getProfile for " + mUser.getNickName();
                    } catch (Exception e) {
                        return "Login exception " + e.getMessage();
                    }
                }

                @Override
                protected void onPostExecute(String result) {
                    final long diff = PerformanceUtils.showTimeDiff(startTime, System.currentTimeMillis());
                    PerformanceUtils.showToast(getActivity(), result, diff);

                    if (mResult) {
                        refreshUI();
                    } else {
                    }
                }
            };
        }
        mLoginTask.execute();
    }

    private void refreshUI() {
    }
}
