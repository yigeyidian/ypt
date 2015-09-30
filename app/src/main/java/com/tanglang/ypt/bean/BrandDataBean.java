package com.tanglang.ypt.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Author： Administrator
 */
public class BrandDataBean implements Serializable {
    public Data results;
    public String status;

    public class Data implements Serializable {
        public List<Drug> List;
        public int Count;
    }
}
