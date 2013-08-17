
package com.funyoung.quickrepair.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.funyoung.qcwx.R;
import com.funyoung.quickrepair.model.User;

import baidumapsdk.demo.DemoApplication;

public class PostFragment extends BaseFragment {
    private static final String TAG = "ProfileFragment";

    private View mRootView;

    private User mUser;

    private View mAddressView;
    private View mLocationView;
    private View mContactView;
    private View mPhoneView;

    private View mBrandView;
    private View mModelView;
    private View mDateView;

    private View mDescriptionView;

    private View mAttachView;
    private View mPublishButton;

//    ChangeTextListener mChangeNameListener;
//    ChangeTextListener mChangeGenderListener;
//    ChangeTextListener mChangeAddressListener;
//    ChangeTextListener mChangeMobileListener;
//    ChangeTextListener mViewRankListener;
//
//    private AsyncTask<Void, Void, String> mLoginTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_profile, container, false);

        if (null != mRootView) {
            initViews(inflater);
        }

//        performLoginTask();

        return mRootView;
    }

    private void initViews(LayoutInflater inflater) {
        if (null == mRootView) {
            Log.e(TAG, "initViews, error with null view root");
            return;
        }

        ViewGroup profileContainer = (ViewGroup)mRootView.findViewById(R.id.container);
        if (null == profileContainer) {
            Log.e(TAG, "initViews, error with null view container");
            return;
        }

        if (null == mUser) {
            mUser = ((DemoApplication)getActivity().getApplication()).getLoginUser();
            if (mUser == null) {
                Log.e(TAG, "initViews, error with null user found");
                return;
            }
        }

        if (profileContainer instanceof ViewGroup) {
            mAddressView = addItemBane(inflater, profileContainer, R.string.post_address, mUser.getAddress());
            mLocationView = addItemBane(inflater, profileContainer, R.string.post_location, R.string.post_hint_location);
            mContactView = addItemBane(inflater, profileContainer, R.string.post_contact, R.string.post_hint_limit_two_char);
            mPhoneView = addItemBane(inflater, profileContainer, R.string.profile_user_mobile, mUser.getMobile());

            mBrandView= addItemBane(inflater, profileContainer, R.string.post_brand, R.string.post_hint_limit_two_char);
            mModelView= addItemBane(inflater, profileContainer, R.string.post_model, R.string.post_hint_limit_two_char);
            mDateView = addItemBane(inflater, profileContainer, R.string.post_date, R.string.post_hint_date);

            mDescriptionView = addItemBane(inflater, profileContainer, R.string.post_description, R.string.post_hint_description);

//            mAttachView;
//            mPublishButton;
//            if (null == mChangeNameListener) {
//                mChangeNameListener = new ChangeTextListener(mNameView, R.string.profile_user_name);
//                mChangeGenderListener = new ChangeTextListener(mSexView, R.string.profile_user_gender);
//                mChangeAddressListener = new ChangeTextListener(mAddressView, R.string.profile_user_address);
//                mViewRankListener = new ChangeTextListener(mRankView, R.string.profile_user_rank);
//            }
//
//            mNameView.setOnClickListener(mChangeNameListener);
//            mSexView.setOnClickListener(mChangeGenderListener);
//            mAddressView.setOnClickListener(mChangeAddressListener);
//            mRankView.setOnClickListener(mViewRankListener);
//            mPhoneView.setOnClickListener(mChangeMobileListener);

            View itemView = inflater.inflate(R.layout.simple_post_attach_poto, null);
            if (null != itemView) {
                ImageView photoView = (ImageView)itemView.findViewById(R.id.img_profile);
                if (null != photoView) {
                }
            }
            profileContainer.addView(itemView);

            Button publishButton = new Button(getActivity());
            publishButton.setText(R.string.publish);
//            publishButton.setBackgroundResource(R.drawable.button_border);
            profileContainer.addView(publishButton);
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
                             int labelResId, int hintResId) {
        View itemView = inflater.inflate(R.layout.simple_post_edit_item, null);
        if (null != itemView) {
            TextView textView = (TextView)itemView.findViewById(R.id.tv_label);
            if (null != textView) {
                textView.setText(labelResId);
            }

            EditText editText = (EditText)itemView.findViewById(R.id.tv_content);
            if (null != textView) {
                editText.setHint(hintResId);
            }
        }
        profileContainer.addView(itemView);
        return itemView;
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
//        if (null != mLoginTask) {
//            mLoginTask.cancel(true);
//            mLoginTask = null;
//        }
    }

//    private void performLoginTask() {
//        if (null == mUser || mUser.getUid() <= 0) {
//            Log.e(TAG, "performLoginTask, skip without valid user.");
//            return;
//        }
//
//        if (null == mLoginTask) {
//            mLoginTask = new AsyncTask<Void, Void, String>() {
//                boolean mResult = false;
//                long startTime;
//                @Override
//                protected void onPreExecute() {
//                    mResult = false;
//                    startTime = System.currentTimeMillis();
//                }
//
//                @Override
//                protected String doInBackground(Void... voids) {
//                    try {
//                        User user = UsersClient.getProfile(getActivity(), mUser.getUid());
//                        if (null == user) {
//                            mResult = false;
//                        } else {
//                            mResult = true;
//                            mUser = user;
//                        }
//                        return "getProfile for " + mUser.getNickName();
//                    } catch (Exception e) {
//                        return "Login exception " + e.getMessage();
//                    }
//                }
//
//                @Override
//                protected void onPostExecute(String result) {
//                    final long diff = PerformanceUtils.showTimeDiff(startTime, System.currentTimeMillis());
//                    PerformanceUtils.showToast(getActivity(), result, diff);
//
//                    if (mResult) {
//                        refreshUI();
//                    } else {
//                    }
//                }
//            };
//        }
//        mLoginTask.execute();
//    }

//    public void updateProfile(User user) {
//        if (mUser != user) {
//            mUser = user;
//            refreshUI();
//        }
//    }

//    private void refreshUI() {
//        TextView textView;
//        textView = (TextView)mAddressView.findViewById(R.id.tv_content);
//        textView.setText(mUser.getAddress());
//
//        textView = (TextView)mPhoneView.findViewById(R.id.tv_content);
//        textView.setText(mUser.getMobile());
//    }
}
