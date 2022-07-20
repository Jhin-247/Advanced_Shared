package com.tuan.sharedpreference.presenter.main;

import com.tuan.sharedpreference.databinding.ActivityMainBinding;

public interface Contract {
    interface Presenter{
        void onSaveClick(ActivityMainBinding binding);
    }
    interface View{
        void onSaveSuccess();
        void onSaveFailed();
        void onMissingInformation();
    }
}
