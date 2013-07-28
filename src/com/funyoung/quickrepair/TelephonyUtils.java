package com.funyoung.quickrepair;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 * Created by yangfeng on 13-7-13.
 */
public class TelephonyUtils {
    private TelephonyUtils() {
        // no instance
    }

    public static String getPhoneNumber(Context context, int len) {
        TelephonyManager phoneMgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        if (len > 0) {
            return trimMobileNumber(phoneMgr.getLine1Number(), len);
        } else {
            return phoneMgr.getLine1Number();
        }
    }


    private static String trimMobileNumber(String mobile, int len) {
        if (TextUtils.isEmpty(mobile) || mobile.length() <= len) {
            return mobile;
        }

        final int offset = mobile.length() - len;
        return mobile.substring(offset);
    }

}
