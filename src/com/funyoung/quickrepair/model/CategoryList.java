package com.funyoung.quickrepair.model;

import com.parse.codec.binary.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by yangfeng on 13-8-14.
 */
public class CategoryList extends ArrayList<Integer> {
    public static CategoryList parseListFromJson(String str) throws JSONException {
        CategoryList category = new CategoryList();
        if (null != str) {
            String[] idArray = str.split(",");
            for (String id : idArray) {
                category.add(Integer.parseInt(id));
            }
        }
        return category;
    }
}