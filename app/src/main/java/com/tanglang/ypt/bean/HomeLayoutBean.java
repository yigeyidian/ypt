package com.tanglang.ypt.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Author： Administrator
 */
public class HomeLayoutBean {
    public String code;
    public String op_type;
    public HomeList list;

    public class HomeList{
        public List<Banner> banner;
        public List<Type> types;
    }

    public class Banner {
        public String desc;
        public String id;
        public String image_url;
        public String motion;
        public String target;
        public String title;
        public String type;
    }

    public class Type {
        public String id;
        public String type_name;
        public List<Drug> drug_list;
    }

    public class Drug {
        public String id;
        public String AliasCN;
        public String AvgPrice;
        public String BaseMed;
        public String MedCare;
        public String NameCN;
        public String NewOTC;
        public String TitleImg;
    }
}
