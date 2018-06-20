package com.yeuyt.mygit.ui.fragment.login;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.yeuyt.mygit.R;
import com.yeuyt.mygit.di.ComponentHolder;
import com.yeuyt.mygit.presenter.contract.LoginContract;

import javax.inject.Inject;

public class LoginFragment extends DialogFragment implements LoginContract.View {

    @Inject
    LoginContract.Presenter presenter;
    @Inject
    Context context;
    private TextView login;
    private TextView cancel;
    private TextInputEditText name;
    private TextInputEditText password;

    private LoginCallBack callBack;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);

        login = view.findViewById(R.id.login);
        cancel = view.findViewById(R.id.cancel);
        name = view.findViewById(R.id.name);
        password = view.findViewById(R.id.password);
        initData(savedInstanceState);

        return view;
    }
    private void initDagger() {
        ComponentHolder.loginComponent.inject(this);
        presenter.attachView(this);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            dialog.getWindow().setLayout((int) (dm.widthPixels * 0.75), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initDagger();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.login(name.getText().toString(), password.getText().toString());
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.onCancelLogin();
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public Context getViewContext() {
        return context;
    }

    public void setLoginCallBack(LoginCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void showOnSucess() {
        callBack.onSuccessLogin();
    }

    public interface LoginCallBack {
        void onSuccessLogin();
        void onCancelLogin();
    }
}
