package com.github.alexa4.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.github.alexa4.R;

public class MainActivity extends AppCompatActivity {
    private ViewPager mPager;
    private PagerAdapter mAdapter;

    private PlanetsFragment mPlanets = new PlanetsFragment();
    private PeopleFragment mPeople = new PeopleFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdapter = new CustomFragmentAdapter(getSupportFragmentManager());
        mPager = findViewById(R.id.fragment_pager);
        mPager.setAdapter(mAdapter);
    }


    /**
     * Custom implementation of adapter for ViewPager
     * This adapter contains 2 fragments
     * 1-st for people page
     * 2-nd for planets page
     */
    private class CustomFragmentAdapter extends FragmentPagerAdapter {

        CustomFragmentAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return mPeople;
            }

            return mPlanets;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0)
                return "People";

            return "Planets";
        }
    }
}
