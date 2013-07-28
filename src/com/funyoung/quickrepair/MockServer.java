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
    private static final int MAX_NUM = 80;

    private static int randomNum(int min, int max) {
        double num = 10 * Math.random();
        if (num < min) {
            return min;
        } else if (num > max) {
            return max;
        } else {
            return (int)num;
        }
    }
    private static int randomIndex(int max) {
        double index = 100 * Math.random();
        return ((int)index) % max;
    }
    private static final int[] resIcons = {
            R.drawable.icon_marka,
            R.drawable.icon_markb,
            R.drawable.icon_markc,
            R.drawable.icon_markd,
            R.drawable.nav_turn_via_1
    };
    private static int randomIconId(double seed) {
        final int index = (int)(seed * 100) % (resIcons.length);
        return resIcons[index];
    }
    private static final String[] labelPref = {
            "Super star",
            "Man",
            "Hero"
    };
    private static final String[] extra = {
            "空调",
            "家电",
            "Wall"
    };
    private static OverlayItem generateItem(GeoPoint center, Drawable marker,
                                            Double latSeed, Double lonSeed) {
//        double lat = (center.latitude + latSeed - 0.5);
//        double lon = (center.longitude + lonSeed - 0.5);
        double lat = center.getLatitudeE6() + (latSeed - 0.5) * 1E5;
        double lon = center.getLongitudeE6() + (lonSeed - 0.5) * 1E5;
        GeoPoint point = new GeoPoint ((int)lat, (int)lon);
        String s = labelPref[randomIndex(labelPref.length)];
        String ss = extra[randomIndex(extra.length)];
        OverlayItem item = new OverlayItem(point, s, ss);
        item.setMarker(marker);
        return item;
    }

    public static List queryItemList(Context context, GeoPoint center) {
        final int num = randomNum(MIN_NUM, MAX_NUM);
        List<OverlayItem> items = new ArrayList<OverlayItem>(num);
        Drawable marker;
        double seed;
        for (int i = 0; i < num; ++i) {
            seed = Math.random();
            marker = context.getResources().getDrawable(randomIconId(seed));
            items.add(generateItem(center, marker, seed, Math.random()));
        }
        return items;
    }
}
