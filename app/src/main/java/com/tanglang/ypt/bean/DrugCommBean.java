package com.tanglang.ypt.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Author： Administrator
 */
public class DrugCommBean implements Serializable {
    public String status;
    public Result results;

    public class Result{
        public int Count;
        public List<DrugComm> List;
    }

    public class DrugComm{
        public int _id;
        public String content;
        public float effectscore;
        public int useful;
        public String username;
    }
}
