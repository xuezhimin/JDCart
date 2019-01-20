package com.qh.xzm.cart.http;

import com.qh.xzm.cart.bean.Result;
import com.qh.xzm.cart.bean.Shop;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;


public interface RequestInterface {

    //关键字搜索
    @GET("product/getCarts?uid=75")
    Observable<Result<List<Shop>>> cartData();


}
