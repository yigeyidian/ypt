package com.tanglang.ypt.bean;

import java.util.List;

/**
 * Author： Administrator
 */
public class DrugPriceBean {
    public String status;
    public Result results;

    public class Result {
        public City City;
        public List<DrugShop> Online;
    }

    public class City {
        public int Count;
        public List<ShopAdd> List;
    }

    public class DrugShop {
        public int drugdetaid;
        public int pharmaciesid;
        public float price;
        public String refonlybuylogo;
        public String refpharmaciesnamecn;
        public String refpharphone;
        public String refspecifications;
        public String saleurl;
        public String wapurl;
    }

    public class ShopAdd {
        public String address;
        public boolean is24hours;
        public boolean ismedicalinsurance;
        public String pharmaciesnamecn;
        public String phone;
    }
}
