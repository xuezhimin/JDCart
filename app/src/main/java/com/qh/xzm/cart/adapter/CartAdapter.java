package com.qh.xzm.cart.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qh.xzm.cart.R;
import com.qh.xzm.cart.bean.Goods;
import com.qh.xzm.cart.bean.Shop;
import com.qh.xzm.cart.view.AddSubLayout;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<Shop> mShopList = new ArrayList<>();

    public CartAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void addAll(List<Shop> data) {
        if (data != null)
            mShopList.addAll(data);
    }

    @Override
    public int getGroupCount() {
        return mShopList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mShopList.get(groupPosition).getList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mShopList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mShopList.get(groupPosition).getList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    //父类视图
    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHodler groupHodler = null;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.cart_group_item, null);
            groupHodler = new GroupHodler();
            groupHodler.checkBox = convertView.findViewById(R.id.checkBox);
            convertView.setTag(groupHodler);
        } else {
            groupHodler = (GroupHodler) convertView.getTag();
        }
        final Shop shop = mShopList.get(groupPosition);
        //给商家赋值（名称）
        groupHodler.checkBox.setText(shop.getSellerName());
        //设置商铺选中状态
        groupHodler.checkBox.setChecked(shop.isCheck());
        //选中  的点击事件
        groupHodler.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBox = (CheckBox) view;
                shop.setCheck(checkBox.isChecked());
                //得到商品信息
                List<Goods> goodsList = mShopList.get(groupPosition).getList();
                //商品信息循环赋值
                for (int i = 0; i < goodsList.size(); i++) {
                    //商铺选中则商品必须选中
                    goodsList.get(i).setSelected(checkBox.isChecked() ? 1 : 0);
                }
                notifyDataSetChanged();
                //计算价格
                calculatePrice();
            }
        });
        return convertView;
    }

    //子类视图
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder childHolder = null;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.cart_child_item, null);
            childHolder = new ChildHolder();
            childHolder.text = convertView.findViewById(R.id.text);
            childHolder.price = convertView.findViewById(R.id.text_price);
            childHolder.image = convertView.findViewById(R.id.image);
            childHolder.addSub = convertView.findViewById(R.id.add_sub_layout);
            childHolder.check = convertView.findViewById(R.id.cart_goods_check);
            convertView.setTag(childHolder);
        } else {
            childHolder = (ChildHolder) convertView.getTag();
        }

        final Goods goods = mShopList.get(groupPosition).getList().get(childPosition);
        //商品标题
        childHolder.text.setText(goods.getTitle());
        //单价
        childHolder.price.setText("单价：" + goods.getPrice());
        String imageurl = "https" + goods.getImages().split("https")[1];
        Log.i("xzm", "imageUrl: " + imageurl);
        //加载图片
        imageurl = imageurl.substring(0, imageurl.lastIndexOf(".jpg") + ".jpg".length());
        Glide.with(mContext).load(imageurl).into(childHolder.image);
        //设置复选框   点击选中， 价格发生变化（计算价格 ）
        //修改之后
        childHolder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBox = (CheckBox) view;
                goods.setSelected(checkBox.isChecked() ? 1 : 0);
                //计算价格
                calculatePrice();
            }
        });

        //设置加减器
        childHolder.addSub.setCount(goods.getNum());
        childHolder.addSub.setAddSubListener(new AddSubLayout.AddSubListener() {
            @Override
            public void addSub(int count) {
                goods.setNum(count);
                //计算价格
                calculatePrice();
            }
        });

        if (goods.getSelected() == 0) {
            childHolder.check.setChecked(false);
        } else {
            childHolder.check.setChecked(true);
        }

        return convertView;
    }


    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    /**
     * 计算价格
     */
    private void calculatePrice() {
        //设置初始值
        double totalPrice = 0;
        //循环的商家
        for (int i = 0; i < mShopList.size(); i++) {
            Shop shop = mShopList.get(i);
            //循环商品
            for (int j = 0; j < shop.getList().size(); j++) {
                Goods goods = shop.getList().get(j);
                //如果是选中状态
                if (goods.getSelected() == 1) {
                    float price = (float) goods.getPrice();
                    int num = goods.getNum();
                    totalPrice += price * num;
//                    totalPrice = totalPrice + goods.getNum() * goods.getPrice();
//                    totalPrice = totalPrice + goods.getCount() * goods.getPrice();
                }
            }
        }
        if (totalPriceListener != null) {
            totalPriceListener.totalPrice(totalPrice);
        }

    }

    /**
     * 全选和反选
     *
     * @param isCheck
     */
    public void checkAll(boolean isCheck) {
        //循环商家
        for (int i = 0; i < mShopList.size(); i++) {
            Shop shop = mShopList.get(i);
            shop.setCheck(isCheck);
            for (int j = 0; j < shop.getList().size(); j++) {
                Goods goods = shop.getList().get(j);
                goods.setSelected(isCheck ? 1 : 0);
            }
        }
        notifyDataSetChanged();
        calculatePrice();
    }


    //接口回调
    public interface TotalPriceListener {
        void totalPrice(double totalPrice);
    }

    private TotalPriceListener totalPriceListener;

    public void setTotalPriceListener(TotalPriceListener totalPriceListener) {
        this.totalPriceListener = totalPriceListener;
    }


    //内部父类
    class GroupHodler {
        CheckBox checkBox;
    }

    //内部子类
    class ChildHolder {
        CheckBox check;
        TextView text;
        TextView price;
        ImageView image;
        AddSubLayout addSub;
    }


}
