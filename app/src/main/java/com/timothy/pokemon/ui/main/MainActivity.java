package com.timothy.pokemon.ui.main;

import android.os.Bundle;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.timothy.common.arouter.ARouterPath;
import com.timothy.common.base.BaseActivity;
import com.timothy.common.report.Report;
import com.timothy.common.report.ReportActions;
import com.timothy.common.report.ReportRefer;
import com.timothy.pokemon.R;
import com.timothy.pokemon.databinding.ActivityMainBinding;

@Route(path = ARouterPath.PATH_MAIN_ACTIVITY, name = "MainActivity")
public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        BottomNavigationView navView = mDataBinding.mainNavView;
        navView.setOnItemSelectedListener(item -> {
            Report.Helper.newInstance()
                    .setAction(ReportActions.ACTION_ITEM_CLICK)
                    .setActionParam(item.getTitle().toString())
                    .setRefer(ReportRefer.REFER_MAIN_ACTIVITY)
                    .build();
            return true;
        });

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.id_navigation_explore, R.id.id_navigation_tools, R.id.id_navigation_view, R.id.id_navigation_mine)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.id_activity_fragment_container);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

}