package com.funyoung.quickrepair.model;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by yangfeng on 13-8-11.
 */
public class User {
    public static final String KEY_UID = "uid";
    public static final String KEY_NAME = "nickname";
    public static final String KEY_AVATAR = "header";

    private long mUid;
    private String mNickName;
    private String mAvatarUrl;

    public User(long uid, String nickName, String avatarUrl) {
        mUid = uid;
        mNickName = nickName;
        mAvatarUrl = avatarUrl;
    }

    public long getUid() {
        return mUid;
    }

    public String getNickName() {
        if (TextUtils.isEmpty(mNickName)) {
            return String.valueOf(mUid);
        }
        return mNickName;
    }

    public String getAvatarUrl() {
        if (null == mAvatarUrl) {
            return "";
        }
        return mAvatarUrl;
    }

    public static User parseFromJson(String jstr) throws Exception {
        try {
            JSONObject jsonObject = new JSONObject(jstr);
            if (null == jsonObject) {
                throw new Exception("Invalid json to convert as User " + jstr);
            }
            long uid = jsonObject.optLong(KEY_UID);
            String nickName= jsonObject.optString(KEY_NAME);
            String avatarUrl = jsonObject.optString(KEY_AVATAR);
            return new User(uid, nickName, avatarUrl);
        } catch (JSONException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }
}
