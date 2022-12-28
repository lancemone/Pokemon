package com.timothy.pokemon.ui.tradition.color;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.timothy.common.arouter.ARouterPath;
import com.timothy.common.base.BaseMVVMActivity;
import com.timothy.common.manager.AppManager;
import com.timothy.pokemon.R;
import com.timothy.pokemon.data.tradition.TraditionColorData;
import com.timothy.pokemon.databinding.ActivityTraditionColorBinding;

import java.util.List;

@Route(path = ARouterPath.PATH_TRADITION_COLOR_ACTIVITY)
public class TraditionColorActivity extends BaseMVVMActivity<ActivityTraditionColorBinding, TraditionColorViewModel>{

    @Override
    protected TraditionColorViewModel getViewModel() {
        return new ViewModelProvider(this).get(TraditionColorViewModel.class);
    }

    @Override
    protected void observeData() {
        mViewModel.getTraditionColorList().observe(this, new Observer<List<TraditionColorData>>() {
            @Override
            public void onChanged(List<TraditionColorData> traditionColorData) {
                if (traditionColorData.isEmpty()){
                    mDataBinding.tvWithColor.setVisibility(View.GONE);
                }else {
                    TraditionColorData colorData = traditionColorData.get(0);
                    Log.d("motao", "colorData name=" + colorData.name);
                    mDataBinding.setColorData(colorData);
                    mDataBinding.tvWithColor.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tradition_color;
    }

    @Override
    protected void initView() {
    }
}
