/**
 * Copyright 2010-present Facebook.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.funyoung.quickrepair;

import android.text.TextUtils;
import android.view.View;
import baidumapsdk.demo.R;

public class LoginFragment extends UserFragment {
    private static final String TAG = "LoginFragment";

    @Override
    protected int getLayoutRes() {
        return R.layout.login;
    }

    private View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String name = validateUserName();
            if (TextUtils.isEmpty(name)) {
                return;
            }

            String passwd = validatePassword();
            if (TextUtils.isEmpty(passwd)) {
                return;
            }

//            if (null == mUser) {
//                mUser = new ParseUser();
//            }
//            mUser.setUsername(name);
//            mUser.setPassword(passwd);
//
//            mUser.signUpInBackground(new SignUpCallback() {
//                public void done(ParseException e) {
//                    if (e == null) {
//                        // Hooray! Let them use the app now.
//                        invokeSession(FragmentSession.SELECTION, null);
//                    } else {
//                        // Sign up didn't succeed. Look at the ParseException
//                        // to figure out what went wrong
//                        ParseErrorMap.showToast(getActivity(), e);
//                    }
//                }
//            });
        }
    };

    @Override
    protected void onCreateViewFinish(final View rootView) {
        if (null != rootView) {
        }
    }

    @Override
    protected View.OnClickListener getClickListener() {
        return mListener;
    }


    private static final int CODE_COUNT = 4;
    private String verifyCode;

    @Override
    public void onStart() {
        super.onStart();
    }

    private boolean isMobileNumber(String text) {
        if (TextUtils.isEmpty(text)) {
            return false;
        }

        if (text.length() < 8) {
            return false;
        }

        /// todo : verify valid mobile number text
        return true;
    }

    // todo: mms gate could not recognize +86 prefix.
    private static final int MOBILE_NUM_LEN = 11;
    private String queryMyMobile() {
//        return "18618481850";
        return TelephonyUtils.getPhoneNumber(getActivity().getApplicationContext(), MOBILE_NUM_LEN);
    }
}
