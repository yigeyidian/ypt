package com.tanglang.ypt.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Author： Administrator
 */
public class DrugDetail implements Serializable {
    public Result results;
    public String status;

    public class Result implements Serializable {
        public ArrayList<Commtent> Comments;
        public ArrayList<SameDrug> SameDrugs;
        public int _id;
        public String adr;
        public String aliascn;
        public double avgprice;
        public boolean basemed;
        public String categorycode;
        public String childrentaboo;
        public String codename;
        public String composition;
        public String contraindication;
        public int curtotalorder;
        public String depttype;
        public int[] diseaseids;
        public String drugattribute;
        public int[] drugdetaids;
        public ArrayList<String> drugeffect;
        public int drugtype;
        public String elderlytaboo;
        public int externaluse;
        public String formula;
        public String gongneng;
        public int[] gongnengtags;
        public String inappropriatecrowd;
        public String interaction;
        public String listimg;
        public String material;
        public double maxstoreprice;
        public int medcare;
        public double minstoreprice;
        public String namecn;
        public String nameen;
        public int newotc;
        public String note;
        public int originplace;
        public int parentid;
        public String pharmacology;
        public String pregnantwomentaboo;
        public int pretotalorder;
        public int priceshopcount;
        public String refcontacttel;
        public String refcorpaddress;
        public ArrayList<String> refdiseasename;
        public String refdrugcompanyname;
        public ArrayList<String> refspecifications;
        public ArrayList<String> reftagname;
        public int repliedcount;
        public float score;
        public String shelflife;
        public String specificationdate;
        public String standard;
        public String storage;
        public String suitablecrowd;
        public String titleimg;
        public String unit;
        public String usage;
        public boolean userdrug;
    }

    public class Commtent implements Serializable {
        public int _id;
        public String content;
        public int useful;
        public String username;
    }

    public class SameDrug implements Serializable {
        public int _id;
        public String aliascn;
        public float avgprice;
        public boolean basemed;
        public String gongneng;
        public String listimg;
        public int medcare;
        public String namecn;
        public int newotc;
        public String refdrugcompanyname;
        public float score;
        public String titleimg;
    }
}
