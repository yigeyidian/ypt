package com.tanglang.ypt.bean;

import java.util.List;

/**
 * Author： Administrator
 */
public class DrugsBean {

    /**
     * status : Ok
     */

    private String status;
    private ResultsEntity results;

    public void setStatus(String status) {
        this.status = status;
    }

    public void setResults(ResultsEntity results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public ResultsEntity getResults() {
        return results;
    }

    public static class ResultsEntity {
        private int Count;
        private List<ListEntity> List;
        private List<String> Brands;

        public void setCount(int Count) {
            this.Count = Count;
        }

        public void setList(java.util.List<ListEntity> List) {
            this.List = List;
        }

        public void setBrands(java.util.List<String> Brands) {
            this.Brands = Brands;
        }

        public int getCount() {
            return Count;
        }

        public java.util.List<ListEntity> getList() {
            return List;
        }

        public java.util.List<String> getBrands() {
            return Brands;
        }

        public static class ListEntity {
            private int _id;
            private double avgprice;
            private String aliascn;
            private String namecn;
            private int newotc;
            private int medcare;
            private boolean basemed;
            private String refdrugcompanyname;
            private String gongneng;
            private String refdrugbrandname;
            private double score;
            private String listimg;
            private String titleimg;

            public void set_id(int _id) {
                this._id = _id;
            }

            public void setAvgprice(double avgprice) {
                this.avgprice = avgprice;
            }

            public void setAliascn(String aliascn) {
                this.aliascn = aliascn;
            }

            public void setNamecn(String namecn) {
                this.namecn = namecn;
            }

            public void setNewotc(int newotc) {
                this.newotc = newotc;
            }

            public void setMedcare(int medcare) {
                this.medcare = medcare;
            }

            public void setBasemed(boolean basemed) {
                this.basemed = basemed;
            }

            public void setRefdrugcompanyname(String refdrugcompanyname) {
                this.refdrugcompanyname = refdrugcompanyname;
            }

            public void setGongneng(String gongneng) {
                this.gongneng = gongneng;
            }

            public void setRefdrugbrandname(String refdrugbrandname) {
                this.refdrugbrandname = refdrugbrandname;
            }

            public void setScore(double score) {
                this.score = score;
            }

            public void setListimg(String listimg) {
                this.listimg = listimg;
            }

            public void setTitleimg(String titleimg) {
                this.titleimg = titleimg;
            }

            public int get_id() {
                return _id;
            }

            public double getAvgprice() {
                return avgprice;
            }

            public String getAliascn() {
                return aliascn;
            }

            public String getNamecn() {
                return namecn;
            }

            public int getNewotc() {
                return newotc;
            }

            public int getMedcare() {
                return medcare;
            }

            public boolean getBasemed() {
                return basemed;
            }

            public String getRefdrugcompanyname() {
                return refdrugcompanyname;
            }

            public String getGongneng() {
                return gongneng;
            }

            public String getRefdrugbrandname() {
                return refdrugbrandname;
            }

            public double getScore() {
                return score;
            }

            public String getListimg() {
                return listimg;
            }

            public String getTitleimg() {
                return titleimg;
            }
        }
    }
}
