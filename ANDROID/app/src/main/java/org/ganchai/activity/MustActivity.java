package org.ganchai.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.jayfeng.lesscode.core.AdapterLess;
import com.jayfeng.lesscode.core.ViewLess;

import org.ganchai.R;
import org.ganchai.extend.ExtendListFragment;
import org.ganchai.fragment.HomeDigestListFragment;
import org.ganchai.fragment.MustListFragment;

public class MustActivity extends BaseActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private FragmentPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_must);

        initToolbar();
        initView();
    }

    private void initView() {
        tabLayout = ViewLess.$(this, R.id.tabs);
        viewPager = ViewLess.$(this, R.id.viewpager);

        initViewPager();
    }

    private void initViewPager() {
        viewPagerAdapter = AdapterLess.$pager(getSupportFragmentManager(),
                getResources().getStringArray(R.array.must_titles).length,
                new AdapterLess.FullFragmentPagerCallBack() {
                    @Override
                    public Fragment getItem(int i) {
                        Fragment fragment;
                        if (i == 0) {
                            fragment = new MustListFragment();
                        } else if (i == 1) {
                            fragment = new MustListFragment();
                        } else {
                            fragment = new HomeDigestListFragment();
                        }
                        return fragment;
                    }

                    @Override
                    public String getPageTitle(int i) {
                        return getResources().getStringArray(R.array.must_titles)[i];
                    }
                });
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
