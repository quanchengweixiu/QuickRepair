package com.funyoung.quickrepair.model;

/**
 * Created by yangfeng on 13-8-18.
 * "uid":"XXX" //当前登录用户的uid
 category: xxx  // 大分类id
 sub_category:xxx // 小分类id
 description：”XXX” // 描述信息
 “latitude”:”XXXX” //维度
 “longitude”XXXX” //经度
 address：详细地址
 area：区域
 brand:品牌
 version:型号
 createyear:年份
 photo[]: “XXX” // 图片URL列表， 图片文件需先调接口生成URL
 */
public class Post {
    public long uid;
    public int category;
    public int subCategory;
    public long latitude;
    public long longitude;

    public String description;
    public String address;
    public String area;
    public String brand;
    public String contact;
    public String mobile;

    public String model;
    public String createYear;
    public String[] photos;

    // remote attribute
    public long createTime;
    public long updateTime;
}
