package com.tanglang.ypt.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Author： Administrator
 */
public class HomeLayoutBean implements Serializable {
    public String code;
    public String op_type;
    public HomeList list;

    public class HomeList implements Serializable {
        public List<Banner> banner;
        public List<Type> types;
    }

    public class Type implements Serializable {
        public String id;
        public String type_name;
        public List<Drug> drug_list;
    }
}
