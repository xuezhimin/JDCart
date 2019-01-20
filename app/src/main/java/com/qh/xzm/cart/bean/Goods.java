package com.qh.xzm.cart.bean;

public class Goods {

    private double bargainPrice;

    private String createtime;

    private String detailUrl;

    private String images;

    private int num;

    private int pid;

    private double price;

    private int pscid;

    private int selected;

    private int sellerid;

    private String subhead;

    private String title;
    private int count = 1;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setBargainPrice(double bargainPrice) {
        this.bargainPrice = bargainPrice;
    }

    public double getBargainPrice() {
        return this.bargainPrice;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getCreatetime() {
        return this.createtime;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String getDetailUrl() {
        return this.detailUrl;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getImages() {
        return this.images;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getNum() {
        return this.num;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getPid() {
        return this.pid;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPscid(int pscid) {
        this.pscid = pscid;
    }

    public int getPscid() {
        return this.pscid;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public int getSelected() {
        return this.selected;
    }

    public void setSellerid(int sellerid) {
        this.sellerid = sellerid;
    }

    public int getSellerid() {
        return this.sellerid;
    }

    public void setSubhead(String subhead) {
        this.subhead = subhead;
    }

    public String getSubhead() {
        return this.subhead;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }


}
