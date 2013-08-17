/*
 * Copyright (C) 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.funyoung.quickrepair;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.funyoung.quickrepair.model.User;

import com.funyoung.qcwx.R;

public class SettingsActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getPreferenceManager().setSharedPreferencesName(Preferences.NAME);
        addPreferencesFromResource(R.xml.preferences);
    }

    /**
     * Starts the PreferencesActivity for the specified user.
     *
     * @param context The application's environment.
     */
    static void show(Context context) {
        final Intent intent = new Intent(context, SettingsActivity.class);
        context.startActivity(intent);
    }
    public static User getLoginUser(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Preferences.NAME, 0);
        long uid = prefs.getLong(Preferences.KEY_USER_ID, -1);
        if (uid > 0) {
            final String nickName = prefs.getString(Preferences.KEY_USER_NICKNAME, String.valueOf(uid));
            final String avatarUrl = prefs.getString(Preferences.KEY_USER_AVATAR, "");
            final String address = prefs.getString(Preferences.KEY_USER_ADDRESS, "");
            final String mobile = prefs.getString(Preferences.KEY_USER_MOBILE, "");
            User user = new User(uid, nickName, avatarUrl, address, mobile);
            return user;
        } else {
            return null;
        }
    }
    public static void setLoginUser(Context context, User user) {
        if (null != user) {
            SharedPreferences prefs = context.getSharedPreferences(Preferences.NAME, 0);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putLong(Preferences.KEY_USER_ID, user.getUid());
            editor.putString(Preferences.KEY_USER_NICKNAME, user.getNickName());
            editor.putString(Preferences.KEY_USER_AVATAR, user.getAvatarUrl());
            editor.putString(Preferences.KEY_USER_ADDRESS, user.getAddress());
            editor.putString(Preferences.KEY_USER_MOBILE, user.getMobile());
            editor.commit();
        }
    }
    public static boolean hasLoginUser(Context context) {
        return null != getLoginUser(context);
    }
}
