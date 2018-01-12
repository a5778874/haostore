package zzh.com.haostore.cart.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import zzh.com.haostore.R;
import zzh.com.haostore.cart.adapter.CartAdapter;
import zzh.com.haostore.cart.beans.CartBean;
import zzh.com.haostore.cart.utils.SqlUtils;

/**
 * Created by Administrator on 2017/8/10.
 */

public class CartFragment extends Fragment implements View.OnClickListener {
    private View view;
    private LinearLayout ll_empty_cart, ll_edit, ll_checkAll;
    private RecyclerView rv_cart;
    private TextView bt_edit, bt_editComplete;
    private  Button bt_delete;
    public static CartFragment fragment = null;
    private final String TAG = "tag";
    private List<CartBean> CartBeanList;
    private CartAdapter cartAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.cart_fragment, null);
        initView();

        return view;

    }


    private void initView() {
        ll_empty_cart = view.findViewById(R.id.ll_empty_shopcart);
        ll_edit = view.findViewById(R.id.ll_edit);
        ll_checkAll = view.findViewById(R.id.ll_check_all);
        bt_edit = view.findViewById(R.id.tv_shopcart_edit);
        bt_editComplete = view.findViewById(R.id.tv_shopcart_editComplete);
        bt_delete=view.findViewById(R.id.btn_delete);
        bt_edit.setOnClickListener(this);
        bt_editComplete.setOnClickListener(this);
        bt_delete.setOnClickListener(this);
        rv_cart = view.findViewById(R.id.cart_recyclerview);
        LoadCart();

    }

    public static Fragment getInstance() {
        if (null == fragment) {
            fragment = new CartFragment();
        }
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ................");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: ..........");
    }


    //fragment使用hide时，生命周期方法不掉用。onHiddenChanged会调用，显示为false，切换到其它fragment为true
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            LoadCart();

        }
    }


    //如果本地没数据，加载空的布局。有数据则显示列表布局
    private void LoadCart() {
        CartBeanList = SqlUtils.readLocal();
        Log.d(TAG, "initView: " + CartBeanList.size());
        //如果购物车没数据则显示空布局，有数据显示列表
        if (CartBeanList != null && CartBeanList.size() > 0) {
            ll_empty_cart.setVisibility(View.GONE);
            cartAdapter=new CartAdapter(getContext(), CartBeanList);
            rv_cart.setAdapter(cartAdapter);
            rv_cart.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        } else {
            ll_empty_cart.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_shopcart_edit:
                bt_edit.setVisibility(View.GONE);//隐藏编辑按钮
                bt_editComplete.setVisibility(View.VISIBLE);//显示完成按钮
                ll_checkAll.setVisibility(View.GONE);//隐藏结算栏
                ll_edit.setVisibility(View.VISIBLE);//打开编辑栏
                break;
            case R.id.tv_shopcart_editComplete:
                bt_edit.setVisibility(View.VISIBLE);//显示编辑按钮
                bt_editComplete.setVisibility(View.GONE);//隐藏完成按钮
                ll_checkAll.setVisibility(View.VISIBLE);//显示结算栏
                ll_edit.setVisibility(View.GONE);//隐藏编辑栏
                break;
            case R.id.btn_delete:
                cartAdapter.deleteDatas();
                break;
        }

    }
}
