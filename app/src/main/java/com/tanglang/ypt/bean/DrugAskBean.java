package com.tanglang.ypt.bean;

import java.util.List;

/**
 * Author： Administrator
 */
public class DrugAskBean {
    public String status;
    public Result results;

    public class Result{
        public int Count;
        public List<DrugAsk> List;
    }

    public class DrugAsk{
        public String age;
        public String answer;
        public String doctorname;
        public String mainbody;
        public String sex;
        public String username;
    }
}
