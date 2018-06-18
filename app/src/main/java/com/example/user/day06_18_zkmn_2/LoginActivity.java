package com.example.user.day06_18_zkmn_2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.day06_18_zkmn_2.base.BaseActivity;
import com.example.user.day06_18_zkmn_2.bean.LoginBean;
import com.example.user.day06_18_zkmn_2.presenter.LoginPresenter;
import com.example.user.day06_18_zkmn_2.view.IloginView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.Map;

import static com.umeng.commonsdk.stateless.UMSLEnvelopeBuild.mContext;

public class LoginActivity extends BaseActivity<LoginPresenter> implements IloginView {

    private Button login_btn;
    private EditText login_edit;
    private EditText reg_edit;

    @Override
    protected LoginPresenter providerPresenter() {
        return new LoginPresenter(this);
    }
    @Override
    protected void initDatas() {

    }

    @Override
    protected void initViews() {
        login_btn = findViewById(R.id.login_btn);
        login_edit = findViewById(R.id.login_edit);
        reg_edit = findViewById(R.id.reg_edit);
    }

    @Override
    protected int providerLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initListener() {
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login_edit1 = login_edit.getText().toString();
                String reg_edit1 = reg_edit.getText().toString();
                Toast.makeText(LoginActivity.this,"---"+login_edit1,Toast.LENGTH_LONG).show();

                presenter.login(login_edit1,reg_edit1);
            }
        });
        login_btn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                UMShareAPI mShareAPI = UMShareAPI.get(LoginActivity.this);
                //mShareAPI.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.QQ, authListener);
                UMImage image = new UMImage(LoginActivity.this, R.mipmap.ic_launcher);//本地文件
                new ShareAction(LoginActivity.this).withText("hello")
                        .setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN)
                        .withMedia(image)
                        .open();
                return true;
            }
        });
        reg_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

    }
    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(LoginActivity.this,"成功                                        了",Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(LoginActivity.this,"失                                            败"+t.getMessage(),Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(LoginActivity.this,"取消                                          了",Toast.LENGTH_LONG).show();

        }
    };
    UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

            Toast.makeText(mContext, "成功了", Toast.LENGTH_LONG).show();

        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {

            Toast.makeText(mContext, "失败：" + t.getMessage(),                                     Toast.LENGTH_LONG).show();
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(mContext, "取消了", Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void onError(String error) {
        Toast.makeText(LoginActivity.this,"失败了",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuccess(LoginBean loginBean) {
        Toast.makeText(LoginActivity.this,"成功了",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public Context context() {
        return this;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
