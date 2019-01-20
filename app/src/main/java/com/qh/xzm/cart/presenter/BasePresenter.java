package com.qh.xzm.cart.presenter;

import com.qh.xzm.cart.bean.Result;
import com.qh.xzm.cart.http.DataCall;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public abstract class BasePresenter {

    private DataCall dataCall;

    public BasePresenter(DataCall dataCall) {
        this.dataCall = dataCall;
    }

    protected abstract Observable observable(Object... args);

    public void request(Object... args) {
        observable(args)
                .compose(new ObservableTransformer() {
                    @Override
                    public ObservableSource apply(Observable upstream) {
                        return upstream.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread());
                    }
                })
                .subscribe(new Consumer<Result>() {
                    @Override
                    public void accept(Result result) throws Exception {
                        dataCall.success(result);
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dataCall.fail(throwable);
                    }
                });

    }

    /**
     * @author lmx
     * @date 2019/1/18
     * 内存泄露的解决
     */
    public void unBind() {
        dataCall = null;
    }


}
