package com.cnpay.material.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.cnpay.material.R;
import com.cnpay.material.fragment.HomeFragment;
import com.cnpay.material.fragment.ListFragment;
import com.cnpay.material.fragment.TitleFragment;
import com.cnpay.material.fragment.ViewFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 带抽屉首页
 *
 * 包   名:     com.cnpay.material.activity
 * 类   名:     HomeActivity
 * 版权所有:     版权所有(C)2010-2016
 * 公   司:     深圳华夏通宝信息技术有限公司
 * 版   本:          V1.0
 * 时   间:     2016/12/2 0002 14:15
 * 作   者:     yuyucheng
 */
public class HomeActivity extends AppCompatActivity {
    /**
     * 整体DrawerLayout
     */
    private DrawerLayout drawerLayout;
    /**
     * 侧面抽屉布局
     */
    private NavigationView nav_view;
    /**ToolBar*/
    private Toolbar toolbar;

    /**Table*/
    private TabLayout tableLayout;
    /**ViewPager*/
    private ViewPager vp_container;

    private List<Fragment> pagerList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        findLayoutView();
        initSourceData();
        setDrawerListener();
        setTbListener();
    }

    private void findLayoutView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.home_drawer_layout);
        nav_view = (NavigationView) findViewById(R.id.home_nav_view);
        toolbar=(Toolbar)findViewById(R.id.home_toolBar);
        tableLayout=(TabLayout) findViewById(R.id.home_tab);
        vp_container=(ViewPager)findViewById(R.id.home_vp_container);
    }

    private void initSourceData(){
        pagerList=new ArrayList<>();
        pagerList.add(new HomeFragment());
        pagerList.add(new TitleFragment());
        pagerList.add(new ListFragment());
        pagerList.add(new ViewFragment());
    }

    /**
     * 设置Drawer与navView监听
     */
    private void setDrawerListener() {
        /* ActionBarDrawerToggle 实现了 DrawerLayout.DrawerListener*/
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(HomeActivity.this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        /**NavigationView 抽屉头部view点击事件*/
        View navHeaderView = nav_view.inflateHeaderView(R.layout.activity_home_drawer_header);
        ImageView headIv = (ImageView) navHeaderView.findViewById(R.id.home_iv_head);
        headIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "点击我的头像", Toast.LENGTH_SHORT).show();
            }
        });

        /**NavigationView 抽屉菜单item点击事件*/
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.home_item_home:
                        Toast.makeText(HomeActivity.this, "点击 Home", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.home_item_favorite:
                        Toast.makeText(HomeActivity.this, "点击 收藏", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.home_item_followers:
                        Toast.makeText(HomeActivity.this, "点击 群组", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.home_item_settings:
                        Toast.makeText(HomeActivity.this, "点击 Home", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.home_item_share:
                        Toast.makeText(HomeActivity.this, "点击 分享", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.home_item_feedback:
                        Toast.makeText(HomeActivity.this, "点击 反馈", Toast.LENGTH_SHORT).show();
                        break;
                }
                //关闭抽屉
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    /**TbLayout与ViewPager*/
    private void setTbListener(){
        /*TableLayout联动控制 如果通过 tableLayout.addTab(),会被移除*/
        tableLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tableLayout.setupWithViewPager(vp_container);
        /*ViewPager控制*/
        vp_container.setAdapter(new HomePagerAdapter(getSupportFragmentManager(),
                pagerList ));
        vp_container.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(HomeActivity.this,"选择 "+position+1,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /*ViewPager的Fragment适配器*/
    private class HomePagerAdapter extends FragmentPagerAdapter{
        List<Fragment> fragList;

        public HomePagerAdapter(FragmentManager fm, List<Fragment> fragList) {
            super(fm);
            this.fragList = fragList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragList.get(position);
        }

        @Override
        public int getCount() {
            return fragList != null ? fragList.size() : 0;
        }
    }
}
