package com.tuan.sharedpreference.view.main;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tuan.sharedpreference.R;
import com.tuan.sharedpreference.databinding.ActivityMainBinding;
import com.tuan.sharedpreference.presenter.main.Contract;
import com.tuan.sharedpreference.presenter.main.Presenter;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings({"rawtypes", "unchecked"})
public class MainActivity extends AppCompatActivity implements Contract.View {
    private ActivityMainBinding mBinding;
    private Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        List<String> mData = Arrays.asList(getResources().getStringArray(R.array.payment_method));
        ArrayAdapter mAdapter = new ArrayAdapter(this, R.layout.spinner_item, mData);

        mBinding.spPaymentMethod.setAdapter(mAdapter);

        mPresenter = new Presenter(this);
        mPresenter.setView(this);
        mBinding.btnSave.setOnClickListener(v -> mPresenter.onSaveClick(mBinding));
    }

    @Override
    public void onSaveSuccess() {
        Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveFailed() {
        Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMissingInformation() {
        Toast.makeText(this, "Missing information", Toast.LENGTH_SHORT).show();
    }
}