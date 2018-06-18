package com.example.user.day06_18_zkmn_2.presenter;

import com.example.user.day06_18_zkmn_2.base.BasePresenter;
import com.example.user.day06_18_zkmn_2.bean.LoginBean;
import com.example.user.day06_18_zkmn_2.model.LoginModel;
import com.example.user.day06_18_zkmn_2.view.IloginView;

public class LoginPresenter extends BasePresenter<IloginView> {
    private  LoginModel mloginModel;

    public LoginPresenter(IloginView view) {
        super(view);
    }


    @Override
    protected void initModel() {
        mloginModel = new LoginModel();
    }

    public void login(String mobile,String password){
        if(mobile==null){
            view.onError("手机号不能为空");
        }
        mloginModel.login(mobile, password, new LoginModel.CallBack() {
            @Override
            public void onError(String error) {
                if(view!=null){
                    view.onError("有异常");
                }
            }
            @Override
            public void onSuccess(LoginBean loginBean) {
                if(view!=null){
                    view.onSuccess(loginBean);
                }
            }
        });
    }
}
