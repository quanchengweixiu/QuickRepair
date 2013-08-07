package com.funyoung.quickrepair.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by yangfeng on 13-8-7.
 */
public class PerformanceUtils {
    private static final String TAG = "PerformanceUtils";
    private static final boolean LOW_PERFORMANCE = true;

    public static long showTimeDiff(long start, long end) {
        return showTimeDiff(start, end, null);
    }

    public static long showTimeDiff(long start, long end, String msg) {
        if (LOW_PERFORMANCE) {
            final StringBuilder builder = new StringBuilder();
            if (TextUtils.isEmpty(msg)) {
                builder.append("showTimeDiff, ");
            } else {
                builder.append(msg).append(", ");
            }
            builder.append("start = ").append(start)
                    .append(" and end = ").append(end)
                    .append(", used = ").append(end - start);
            Log.i(TAG, builder.toString());
        }
        return end - start;
    }
}
