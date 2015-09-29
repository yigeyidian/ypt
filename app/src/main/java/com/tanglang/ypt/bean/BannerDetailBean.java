package com.tanglang.ypt.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Author： Administrator
 */
public class BannerDetailBean implements Serializable {
    public String code;
    public String op_type;
    public HomeList list;

    public class HomeList implements Serializable {
        public String activity_name;
        public String banner_image_url;
        public String banner_jump_url;
        public String id;

        public List<Drug> drug_list;
    }
}
