package com.qh.xzm.cart.presenter;

import com.qh.xzm.cart.bean.Result;
import com.qh.xzm.cart.bean.Shop;
import com.qh.xzm.cart.http.DataCall;
import com.qh.xzm.cart.http.NetWorkManger;
import com.qh.xzm.cart.http.RequestInterface;

import java.util.List;

import io.reactivex.Observable;

public class CartPresenter extends BasePresenter {

    public CartPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        RequestInterface requestInterface = NetWorkManger.getInstance().create(RequestInterface.class);
        Observable<Result<List<Shop>>> cartData = requestInterface.cartData();
        return cartData;
    }
}
