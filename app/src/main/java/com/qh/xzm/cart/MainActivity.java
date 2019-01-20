package com.qh.xzm.cart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.qh.xzm.cart.adapter.CartAdapter;
import com.qh.xzm.cart.bean.Result;
import com.qh.xzm.cart.bean.Shop;
import com.qh.xzm.cart.http.DataCall;
import com.qh.xzm.cart.presenter.CartPresenter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements CartAdapter.TotalPriceListener {

    private ExpandableListView mListCart;
    /**
     * 全选
     */
    private CheckBox mCheckAll;
    /**
     * 价格：0￥
     */
    private TextView mGoodsSumPrice;
    private CartPresenter cartPresenter = new CartPresenter(new CartDataCall());
    private CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        //适配器
        cartAdapter = new CartAdapter(getBaseContext());
        mListCart.setAdapter(cartAdapter);
        //p层请求数据
        cartPresenter.request();
        //设置总价回调器
        cartAdapter.setTotalPriceListener(this);
        //设置去除二级列表的三角形
        mListCart.setGroupIndicator(null);
        //让其group不能被点击
        mListCart.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                return true;
            }
        });

        //全选操作
        mCheckAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cartAdapter.checkAll(isChecked);
            }
        });
    }

    private void initView() {
        mListCart = findViewById(R.id.list_cart);
        mCheckAll = findViewById(R.id.check_all);
        mGoodsSumPrice = findViewById(R.id.goods_sum_price);
    }

    /**
     * 计算价格
     * @param totalPrice
     */
    @Override
    public void totalPrice(double totalPrice) {
        mGoodsSumPrice.setText(String.valueOf(totalPrice));
    }


    class CartDataCall implements DataCall<Result<List<Shop>>> {
        @Override
        public void success(Result<List<Shop>> data) {
            if (data.getCode().equals("0")) {
                List<Shop> shopList = data.getData();
                cartAdapter.addAll(shopList);
                //遍历所有group,将所有项设置成默认展开
                int groupCount = shopList.size();
                for (int i = 0; i < groupCount; i++) {
                    mListCart.expandGroup(i);
                }
                cartAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getBaseContext(), data.getMsg() + "", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(Throwable e) {
            Toast.makeText(getBaseContext(), e + "", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 内存泄露
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        cartPresenter.unBind();
    }


}
