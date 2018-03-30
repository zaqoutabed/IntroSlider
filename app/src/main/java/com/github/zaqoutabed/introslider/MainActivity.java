package com.github.zaqoutabed.introslider;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private LinearLayout dots_layout;
    private ViewPager view_pager;
    private Button next_btn, skip_btn;
    private TextView[] dosts;
    private ViewPagerAdapter viewPagerAdapter;
    private int[] layouts = {
            R.layout.screen_1_layout,
            R.layout.screen_2_layout,
            R.layout.screen_3_layout,
            R.layout.screen_4_layout};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        bindViews();
        addDots(0);
        viewPagerAdapter = new ViewPagerAdapter(this, layouts);

        view_pager.setAdapter(viewPagerAdapter);
        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                addDots(position);
                if (layouts.length - 1 == position) {
                    next_btn.setText(getString(R.string.start));
                    skip_btn.setVisibility(View.GONE);
                } else {
                    next_btn.setText(getString(R.string.next));
                    skip_btn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPage = getCurrentLayout(1);
                if (currentPage < layouts.length) {
                    view_pager.setCurrentItem(currentPage);
                } else {
                    launchHomeActivity();
                }
            }
        });
        skip_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                launchHomeActivity();
            }
        });
    }

    private void launchHomeActivity() {
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void addDots(int currentPage) {
        dosts = new TextView[layouts.length];
        dots_layout.removeAllViews();
        for (int i = 0; i < dosts.length; i++) {
            dosts[i] = new TextView(this);
            dosts[i].setText(Html.fromHtml("&#8226;"));
            dosts[i].setTextSize(35);
            dosts[i].setTextColor(getResources().getColor(R.color.inactive_dot));
            dots_layout.addView(dosts[i]);
        }
        if (dosts.length > 0) {
            dosts[currentPage].setTextColor(getResources().getColor(R.color.active_dot));
        }
    }

    private int getCurrentLayout(int i) {
        return view_pager.getCurrentItem() + i;
    }

    private void bindViews() {
        dots_layout = findViewById(R.id.dots_layout);
        view_pager = findViewById(R.id.view_pager);
        next_btn = findViewById(R.id.next_btn);
        skip_btn = findViewById(R.id.skip_btn);

    }
}
