package org.ganchai.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jayfeng.lesscode.core.AdapterLess;
import com.jayfeng.lesscode.core.ViewLess;

import org.ganchai.R;
import org.ganchai.config.WebConfig;
import org.ganchai.extend.ExtendListFragment;
import org.ganchai.fragment.HomeDigestListFragment;


public class MainActivity extends BaseActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private CoordinatorLayout coordinatorLayout;
    private NavigationView navigationView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton floatingActionButton;

    private FragmentPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        initView();
    }

    private void initView() {
        drawerLayout = ViewLess.$(this, R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.hello_world, R.string.hello_world);
        navigationView = ViewLess.$(this, R.id.nav_view);
        coordinatorLayout = ViewLess.$(this, R.id.content);
        tabLayout = ViewLess.$(this, R.id.tabs);
        viewPager = ViewLess.$(this, R.id.viewpager);
        floatingActionButton = ViewLess.$(this, R.id.fab);

        drawerLayout.setDrawerListener(drawerToggle);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        final int menuItemId = menuItem.getItemId();

                        drawerLayout.closeDrawers();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                if (menuItemId == R.id.nav_setting) {
                                    Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                                    startActivity(intent);
                                } else if (menuItemId == R.id.nav_must) {
                                    Intent intent = new Intent(MainActivity.this, MustActivity.class);
                                    startActivity(intent);
                                }
                            }
                        }, 200);

                        return true;
                    }
                });

        initViewPager();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PostActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initViewPager() {
        viewPagerAdapter = AdapterLess.$pager(getSupportFragmentManager(),
                getResources().getStringArray(R.array.home_titles).length,
                new AdapterLess.FullFragmentPagerCallBack() {
                    @Override
                    public Fragment getItem(int i) {
                        Fragment fragment;
                        if (i == 0) {
                            Bundle bundle = new Bundle();
                            bundle.putInt(HomeDigestListFragment.KEY_DIGEST_TYPE, WebConfig.DIGEST_TYPE_ANDROID);
                            fragment = new HomeDigestListFragment();
                            fragment.setArguments(bundle);
                        } else if (i == 1) {
                            fragment = new ExtendListFragment();
                        } else if (i == 2) {
                            Bundle bundle = new Bundle();
                            bundle.putInt(HomeDigestListFragment.KEY_DIGEST_TYPE, WebConfig.DIGEST_TYPE_ANY);
                            fragment = new HomeDigestListFragment();
                            fragment.setArguments(bundle);
                        } else {
                            fragment = new HomeDigestListFragment();
                        }
                        return fragment;
                    }

                    @Override
                    public String getPageTitle(int i) {
                        return getResources().getStringArray(R.array.home_titles)[i];
                    }
                });
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent intent = new Intent(MainActivity.this, xyz.ganchai.activity.MainActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected boolean isToolbarCanBack() {
        return false;
    }
}
