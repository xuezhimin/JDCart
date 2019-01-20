package com.qh.xzm.cart.http;

public interface DataCall<T> {

    void success(T data);

    void fail(Throwable e);


}
