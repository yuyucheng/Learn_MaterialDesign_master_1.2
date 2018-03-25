package com.cnpay.material.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.cnpay.material.R;

/**
 * Material风格
 * ToolBar+RecyclerView
 *
 * @date: 2018/1/2 0002 下午 5:18
 * @author: yuyucheng
 */
public class MaterialListActivity extends AppCompatActivity {
    private CoordinatorLayout root_layout;//基础父控件
    private AppBarLayout appBarLayout;   //Toolbar布局
    private CollapsingToolbarLayout collapsToolbar;//可扩展Toolbar
    private LinearLayout head_ui_layout;//头部填充布局

    private RecyclerView list_rcy;
    private FloatingActionButton bt_fac;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        baseLayoutSet();
        setListData();
    }

    private void baseLayoutSet() {
        root_layout = (CoordinatorLayout) findViewById(R.id.list_root_layout);
        appBarLayout = (AppBarLayout) findViewById(R.id.list_appBarLayout);
        collapsToolbar = (CollapsingToolbarLayout) findViewById(R.id.list_collapsingToolbar);
        head_ui_layout = (LinearLayout) findViewById(R.id.list_head_layout);

        /**设置Toolbar*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.list_toolBar);
        setSupportActionBar(toolbar);
        //toolbar.setNavigationIcon();//返回按钮图标
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //标题栏返回
                onBackPressed();
            }
        });

        /**设置HeadLayout*/
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上则不会显示
                if (verticalOffset <= -head_ui_layout.getHeight() / 2) {
                    collapsToolbar.setTitle("列表页");
                } else {
                    collapsToolbar.setTitle(" ");
                }
            }
        });

        list_rcy=(RecyclerView)findViewById(R.id.list_rv_data);
        bt_fac=(FloatingActionButton)findViewById(R.id.list_fb_menu);
    }

    private void setListData(){
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        list_rcy.setLayoutManager(layoutManager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.list_setting:
                //点击标题设置
                break;
            case R.id.list_camera:
                //点击标题相机
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
