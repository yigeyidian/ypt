package com.tanglang.ypt.bean;

import java.util.List;

/**
 * Author： Administrator
 */
public class DrugSearchBean {
    public String status;
    public List<Item> results;

    public class Item{
        public String showname;
        public int recordcount;
    }
}
