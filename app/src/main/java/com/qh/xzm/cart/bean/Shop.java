package com.qh.xzm.cart.bean;

import java.util.List;

public class Shop {

    private List<Goods> list ;

    private String sellerName;

    private String sellerid;

    boolean check;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public List<Goods> getList() {
        return list;
    }

    public void setList(List<Goods> list) {
        this.list = list;
    }

    public void setSellerName(String sellerName){
        this.sellerName = sellerName;
    }
    public String getSellerName(){
        return this.sellerName;
    }
    public void setSellerid(String sellerid){
        this.sellerid = sellerid;
    }
    public String getSellerid(){
        return this.sellerid;
    }


}
