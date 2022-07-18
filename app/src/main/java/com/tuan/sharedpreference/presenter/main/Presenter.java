package com.tuan.sharedpreference.presenter.main;

import android.content.Context;

public class Presenter implements Contract.Presenter {
    private Contract.View mView;
    private Context mContext;

    public void setView(Contract.View view){
        this.mView = view;
    }

    public Presenter(Context context){
        this.mContext = context;
    }





}
