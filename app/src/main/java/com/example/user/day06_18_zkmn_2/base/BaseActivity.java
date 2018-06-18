package com.example.user.day06_18_zkmn_2.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {
    protected P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(providerLayout());
        initViews();
        initDatas();
        initListener();
        presenter = providerPresenter();
    }

    protected abstract P providerPresenter();

    protected abstract void initListener();

    protected abstract void initDatas();

    protected abstract void initViews();

    protected abstract int providerLayout();


}
