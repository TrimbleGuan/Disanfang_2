package com.example.user.day06_18_zkmn_2.model;

import android.util.Log;

import com.example.user.day06_18_zkmn_2.OkHttpUtil;
import com.example.user.day06_18_zkmn_2.bean.LoginBean;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class LoginModel {
    private static final String TAG = "LoginModel";
    public void login(String modile, String password, final CallBack callBack){

        final String url = "https://www.zhaoapi.cn/user/login";
        Map<String,String> map = new HashMap<>();
        map.put("mobile",modile);
        map.put("password",password);
        OkHttpUtil httpUtil = OkHttpUtil.getInteace();
        httpUtil.doPost(url, map, new OkHttpUtil.OkCallBack() {
            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "失败了---");
            }

            @Override
            public void onResponse(String json) {
                Gson gson = new Gson();
                LoginBean loginBean = gson.fromJson(json, LoginBean.class);
                String code = loginBean.getCode();
                String msg = loginBean.getMsg();
                if("0".equalsIgnoreCase(code)){
                    callBack.onSuccess(loginBean);
                }else{
                    callBack.onError("解析失败");
                }
            }
        });
    }
    public interface CallBack{
        void onError(String error);
        void onSuccess(LoginBean loginBean);
    }
}
