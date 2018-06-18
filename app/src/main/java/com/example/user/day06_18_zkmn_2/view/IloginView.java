package com.example.user.day06_18_zkmn_2.view;

import com.example.user.day06_18_zkmn_2.base.IView;
import com.example.user.day06_18_zkmn_2.bean.LoginBean;

public interface IloginView extends IView {
    void onError(String error);
    void onSuccess(LoginBean loginBean);
}
