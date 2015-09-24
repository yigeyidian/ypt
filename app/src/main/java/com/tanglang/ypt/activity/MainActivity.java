package com.tanglang.ypt.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.tanglang.ypt.R;
import com.tanglang.ypt.fragment.HomeFragment;
import com.tanglang.ypt.fragment.MineFragment;
import com.tanglang.ypt.fragment.NearFragment;
import com.tanglang.ypt.fragment.TypeFragment;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup rgTabber;
    private FragmentManager fm;

    private static final String[] TAB_NAMES = {"主页", "分类", "附近", "我的"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        rgTabber = (RadioGroup) findViewById(R.id.main_rg_tabber);

        rgTabber.setOnCheckedChangeListener(this);

        fm = getSupportFragmentManager();
        //changeFragment(0);
        ((RadioButton) rgTabber.getChildAt(0)).setChecked(true);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        for (int i = 0; i < group.getChildCount(); i++) {
            if (((RadioButton) group.getChildAt(i)).isChecked()) {
                changeFragment(i);
            }
        }
    }

    /**
     * 根据tab选择状态来改变现实内容
     */
    private void changeFragment(int index) {
        Fragment homeFragment = fm.findFragmentByTag(TAB_NAMES[0]);
        Fragment typeFragment = fm.findFragmentByTag(TAB_NAMES[0]);
        Fragment nearFragment = fm.findFragmentByTag(TAB_NAMES[1]);
        Fragment mineFragment = fm.findFragmentByTag(TAB_NAMES[2]);

        FragmentTransaction ft = fm.beginTransaction();
        if (homeFragment != null) {
            ft.hide(homeFragment);
        }
        if (typeFragment != null) {
            ft.hide(typeFragment);
        }
        if (nearFragment != null) {
            ft.hide(nearFragment);
        }
        if (mineFragment != null) {
            ft.hide(mineFragment);
        }

        switch (index) {
            case 0:
                if (homeFragment == null) {
                    ft.add(R.id.main_content, new HomeFragment(), TAB_NAMES[0]);
                } else {
                    ft.show(homeFragment);
                }
                break;

            case 1:
                if (typeFragment == null) {
                    ft.add(R.id.main_content, new TypeFragment(), TAB_NAMES[1]);
                } else {
                    ft.show(typeFragment);
                }
                break;

            case 2:
                if (nearFragment == null) {
                    ft.add(R.id.main_content, new NearFragment(), TAB_NAMES[2]);
                } else {
                    ft.show(nearFragment);
                }
                break;

            case 3:
                if (mineFragment == null) {
                    ft.add(R.id.main_content, new MineFragment(), TAB_NAMES[3]);
                } else {
                    ft.show(mineFragment);
                }
                break;
        }

        ft.commitAllowingStateLoss();

    }
}
