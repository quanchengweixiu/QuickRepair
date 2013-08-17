
package com.funyoung.quickrepair.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.funyoung.quickrepair.SettingsActivity;
import com.funyoung.quickrepair.model.User;
import com.funyoung.qcwx.R;
import com.funyoung.quickrepair.transport.UsersClient;
import com.funyoung.quickrepair.utils.PerformanceUtils;

import baidumapsdk.demo.DemoApplication;

public class ProfileFragment extends BaseFragment {
    private static final String TAG = "ProfileFragment";

    private View mRootView;
    private User mUser;

    private View mNameView;
    private View mSexView;
    private View mAddressView;
    private View mPhoneView;
    private View mRankView;

    ChangeTextListener mChangeNameListener;
    ChangeTextListener mChangeGenderListener;
    ChangeTextListener mChangeAddressListener;
    ChangeTextListener mChangeMobileListener;
    ChangeTextListener mViewRankListener;

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
            View itemView = inflater.inflate(R.layout.simple_profile_item_photo, null);
            if (null != itemView) {
                ImageView photoView = (ImageView)itemView.findViewById(R.id.img_profile);
                if (null != photoView) {
                    final String url = mUser.getAvatarUrl();
                    // todo: set on background thread
                    if (!TextUtils.isEmpty(url)) {
//                        photoView.setImageURI(Uri.parse(url));
                    }
                    if (mUser.getGender() == User.GENDER_FEMALE) {
                        photoView.setImageResource(R.drawable.avatar_girl);
                    }
                }
            }
            profileContainer.addView(itemView);

//            View header = profileContainer.findViewById(R.id.profile_header);
//            if (null != header) {
//                ImageView photoView = (ImageView)header.findViewById(R.id.img_profile);
//                if (null != photoView) {
//                    final String url = mUser.getAvatarUrl();
//                    if (!TextUtils.isEmpty(url)) {
//                        photoView.setImageURI(Uri.parse(url));
//                    }
//                }
//            }

            mNameView = addItemBane(inflater, profileContainer, R.string.profile_user_name, mUser.getNickName());
            mSexView = addItemBane(inflater, profileContainer, R.string.profile_user_gender, getGenderLabel());
            mAddressView = addItemBane(inflater, profileContainer, R.string.profile_user_address, mUser.getAddress());
            mPhoneView = addItemBane(inflater, profileContainer, R.string.profile_user_mobile, mUser.getMobile());

            if (mUser.isProviderType()) {
                mRankView = addItemBane(inflater, profileContainer, R.string.profile_user_rank, "");
            } else {
                TextView tvContent = (TextView)mPhoneView.findViewById(R.id.tv_content);
                tvContent.setCompoundDrawables(null, null, null, null);
            }

            if (null == mChangeNameListener) {
                mChangeNameListener = new ChangeTextListener(mNameView, R.string.profile_user_name);
                mChangeGenderListener = new ChangeTextListener(mSexView, R.string.profile_user_gender);
                mChangeAddressListener = new ChangeTextListener(mAddressView, R.string.profile_user_address);
                mViewRankListener = new ChangeTextListener(mRankView, R.string.profile_user_rank);
            }

            mNameView.setOnClickListener(mChangeNameListener);
            mSexView.setOnClickListener(mChangeGenderListener);
            mAddressView.setOnClickListener(mChangeAddressListener);
            if (null != mRankView) {
                mRankView.setOnClickListener(mViewRankListener);
            }
            if (mUser.isProviderType()) {
                if (null == mChangeMobileListener) {
                    mChangeMobileListener = new ChangeTextListener(mPhoneView, R.string.profile_user_mobile);
                }
                mPhoneView.setOnClickListener(mChangeMobileListener);
            }
        }
    }

    private static class ChangeTextListener implements View.OnClickListener {
        private View mView;
        private int mKeyResId;
        public ChangeTextListener (View view, int keyResId) {
            mView = view;
            mKeyResId = keyResId;
        }

        @Override
        public void onClick(View view) {
            final Context context = mView.getContext();
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setIcon(android.R.drawable.ic_dialog_info).setTitle(mKeyResId);
            EditText editText = new EditText(context);
            TextView textView = (TextView)mView.findViewById(R.id.tv_content);
            editText.setText(textView.getText());
            editText.selectAll();
            builder.setView(editText);
            builder.setNegativeButton(android.R.string.cancel, null);
            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    switch (mKeyResId) {
                        case R.string.profile_user_name:
                            break;
                        case R.string.profile_user_gender:
                            break;
                        case R.string.profile_user_address:
                            break;
                        case R.string.profile_user_mobile:
                            break;
                        case R.string.profile_user_rank:
                            break;
                        default:
                            break;
                    }
                }
            });
            builder.show();
        }
    }

    private View addItemBane(LayoutInflater inflater, ViewGroup profileContainer,
                             int labelResId, String nameValue) {
        View itemView = inflater.inflate(R.layout.simple_profile_item, null);
        if (null != itemView) {
            TextView textView = (TextView)itemView.findViewById(R.id.tv_label);
            if (null != textView) {
                textView.setText(labelResId);
            }
            textView = (TextView)itemView.findViewById(R.id.tv_content);
            if (null != textView) {
                textView.setText(nameValue);
            }
        }
        profileContainer.addView(itemView);
        return itemView;
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
        refreshUI();
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
        ((DemoApplication)getActivity().getApplication()).setLoginUser(mUser);

        TextView textView;
        textView = (TextView)mNameView.findViewById(R.id.tv_content);
        textView.setText(mUser.getNickName());

        textView = (TextView)mSexView.findViewById(R.id.tv_content);
        textView.setText(getGenderLabel());

        textView = (TextView)mAddressView.findViewById(R.id.tv_content);
        textView.setText(mUser.getAddress());

        textView = (TextView)mPhoneView.findViewById(R.id.tv_content);
        textView.setText(mUser.getMobile());
    }

    private String getGenderLabel() {
        String[] labels = getResources().getStringArray(R.array.gender_labels);
        final int i = mUser.getGender();
        if (i < 0 || i >= labels.length) {
            return labels[0];
        }
        return labels[i];
    }
}
