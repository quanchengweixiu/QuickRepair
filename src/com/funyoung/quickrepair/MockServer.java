package com.funyoung.quickrepair;

import android.content.Context;
import android.graphics.drawable.Drawable;
import baidumapsdk.demo.R;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.platform.comapi.basestruct.GeoPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangfeng on 13-7-26.
 */
public class MockServer {
    private static final int MIN_NUM = 0;
    private static final int MAX_NUM = 100;

    private static int randomNum(int min, int max) {
        double num = (MAX_NUM * Math.random()) % MAX_NUM;
        if (num < min) {
            return min;
        } else if (num > max) {
            return max;
        } else {
            return (int)num;
        }
    }
    private static final int[] resIcons = {
            R.drawable.qp_map_overlay_blue,
            R.drawable.qp_map_overlay_red,
            R.drawable.qp_map_overlay_gray,
            R.drawable.qp_map_overlay_green,
    };
    private static final String[] labelPref = {
            "认证公司",
            "认证个人",
            "公司",
            "个人"
    };
    private static int randomTypeIndex(double seed) {
        return (int)(seed * 100) % (resIcons.length);
    }

    private static int randomIconId(int index) {
        return resIcons[index];
    }
    private static final String[] extra = {
            "空调",
            "家电",
            "Wall"
    };
    private static String randomExtraLabel(double seed) {
        double index = 100 * seed;
        return extra[((int)index) % extra.length];
    }
    private static OverlayItem generateItem(Drawable marker, double lat, double lon,
                                            String s, String ss) {
        GeoPoint point = new GeoPoint ((int)lat, (int)lon);
        OverlayItem item = new OverlayItem(point, s, ss);
        item.setMarker(marker);
        return item;
    }

    public static List queryItemList(Context context, GeoPoint center) {
        final int num = randomNum(MIN_NUM, MAX_NUM);
        List<OverlayItem> items = new ArrayList<OverlayItem>(num);
        Drawable marker;
        int index;
        double seed;
        for (int i = 0; i < num; ++i) {
            seed = Math.random();
            index = randomTypeIndex(seed);
            marker = context.getResources().getDrawable(randomIconId(index));
            String s = labelPref[index];

            double lat = center.getLatitudeE6() + (seed - 0.5) * 1E5;
            double lon = center.getLongitudeE6() + (Math.random() - 0.5) * 1E5;
            String ss = randomExtraLabel(seed);

            items.add(generateItem(marker, lat, lon, s, ss));
        }
        return items;
    }
}
