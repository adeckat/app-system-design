package com.ngahuynh.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.ngahuynh.myapplication.R;
import com.ngahuynh.myapplication.helper.User;
import com.ngahuynh.myapplication.helper.Utils;
import com.ngahuynh.myapplication.fragment.MenuFragment;

public class DetailActivity extends AppCompatActivity implements MenuFragment.MenuClickListener{

    private TextView mTitle;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mTitle = findViewById(R.id.tv_bar_title);

        boolean usingTablets = findViewById(R.id.menu_content_panel) != null;
        Bundle bundle = getIntent().getExtras();
        int menuId = bundle.getInt("MENU_ID", 1);

        mFragmentManager = getSupportFragmentManager();
        Utils.setTitle(mTitle, menuId);
        if (usingTablets) {
            MenuFragment menuFragment = new MenuFragment();
            menuFragment.setMenuClickListener(this);
            mFragmentManager.beginTransaction().replace(R.id.menu_content_panel, menuFragment, "MENU").commit();
        }
        Utils.displayDetailFragment(mFragmentManager, R.id.detail_content_panel, menuId);

        ImageButton edit = (ImageButton) findViewById(R.id.btn_profile_pic);

        findViewById(R.id.btn_profile_pic).setOnClickListener(v -> {
//            Intent intent = new Intent(this, MainActivity.class);
//            intent.putExtra("NAVIGATION", 1); //Start ProfileFragment
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("MENU_ID", Utils.PROFILE_ID);
            startActivity(intent);
        });

        findViewById(R.id.btn_menu).setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onClick(int menuId) {
        Utils.setTitle(mTitle, menuId);
        Utils.displayDetailFragment(mFragmentManager, R.id.detail_content_panel, menuId);
    }
}