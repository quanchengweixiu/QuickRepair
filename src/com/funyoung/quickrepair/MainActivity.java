/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.funyoung.quickrepair;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.sherlock.navigationdrawer.compat.SherlockActionBarDrawerToggle;

import baidumapsdk.demo.BMapApiDemoMain;
import baidumapsdk.demo.R;

import java.util.Locale;

/**
 * This example illustrates a common usage of the DrawerLayout widget
 * in the Android support library.
 * <p/>
 * <p>When a navigation (left) drawer is present, the host activity should detect presses of
 * the action bar's Up affordance as a signal to open and close the navigation drawer. The
 * ActionBarDrawerToggle facilitates this behavior.
 * Items within the drawer should fall into one of two categories:</p>
 * <p/>
 * <ul>
 * <li><strong>View switches</strong>. A view switch follows the same basic policies as
 * list or tab navigation in that a view switch does not create navigation history.
 * This pattern should only be used at the root activity of a task, leaving some form
 * of Up navigation active for activities further down the navigation hierarchy.</li>
 * <li><strong>Selective Up</strong>. The drawer allows the user to choose an alternate
 * parent for Up navigation. This allows a user to jump across an app's navigation
 * hierarchy at will. The application should treat this as it treats Up navigation from
 * a different task, replacing the current task stack using TaskStackBuilder or similar.
 * This is the only form of navigation drawer that should be used outside of the root
 * activity of a task.</li>
 * </ul>
 * <p/>
 * <p>Right side drawers should be used for actions, not navigation. This follows the pattern
 * established by the Action Bar that navigation should be to the left and actions to the right.
 * An action should be an operation performed on the current contents of the window,
 * for example enabling or disabling a data overlay on top of the current content.</p>
 */
public class MainActivity extends SherlockFragmentActivity {

    private DrawerLayout mDrawerLayout;
//    private ListView listView;
//    private TextView mContent;

    private ActionBarHelper mActionBar;

    private SherlockActionBarDrawerToggle mDrawerToggle;

    public static final String FRAGMENT_DEFAULT = "FRAGMENT_DEFAULT";
    public static final String FRAGMENT_LOGIN = "FRAGMENT_LOGIN";
    public static final String FRAGMENT_MAP = "FRAGMENT_MAP";

    /**
     * Create a compatible helper that will manipulate the action bar if
     * available.
     */
    private ActionBarHelper createActionBarHelper() {
        return new ActionBarHelper();
    }

    public void finishLogin() {
        gotoDefaultView();
    }


    /**
     * A drawer listener can be used to respond to drawer events such as
     * becoming fully opened or closed. You should always prefer to perform
     * expensive operations such as drastic relayout when no animation is
     * currently in progress, either before or after the drawer animates.
     *
     * When using ActionBarDrawerToggle, all DrawerLayout listener methods
     * should be forwarded if the ActionBarDrawerToggle is not used as the
     * DrawerLayout listener directly.
     */
    private class DemoDrawerListener implements DrawerLayout.DrawerListener {
        @Override
        public void onDrawerOpened(View drawerView) {
            mDrawerToggle.onDrawerOpened(drawerView);
            mActionBar.onDrawerOpened();
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            mDrawerToggle.onDrawerClosed(drawerView);
            mActionBar.onDrawerClosed();
        }

        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            mDrawerToggle.onDrawerSlide(drawerView, slideOffset);
        }

        @Override
        public void onDrawerStateChanged(int newState) {
            mDrawerToggle.onDrawerStateChanged(newState);
        }
    }

    private class ActionBarHelper {
        private final ActionBar mActionBar;
        private CharSequence mDrawerTitle;
        private CharSequence mTitle;

        private ActionBarHelper() {
            mActionBar = getSupportActionBar();
        }

        public void init() {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setHomeButtonEnabled(true);
            mTitle = mDrawerTitle = getTitle();
        }

        /**
         * When the drawer is closed we restore the action bar state reflecting
         * the specific contents in view.
         */
        public void onDrawerClosed() {
            mActionBar.setTitle(mTitle);
        }

        /**
         * When the drawer is open we set the action bar to a generic title. The
         * action bar should only contain data relevant at the top level of the
         * nav hierarchy represented by the drawer, as the rest of your content
         * will be dimmed down and non-interactive.
         */
        public void onDrawerOpened() {
            mActionBar.setTitle(mDrawerTitle);
        }

        public void setTitle(CharSequence title) {
            mTitle = title;
        }
    }

//    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
//    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mPlanetTitles;

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);

        mTitle = mDrawerTitle = getTitle();
//        mPlanetTitles = getResources().getStringArray(R.array.planets_array);
        mPlanetTitles = getResources().getStringArray(R.array.qp_navigation_title_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mDrawerLayout.setDrawerListener(new DemoDrawerListener());
        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
//        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
//                R.layout.drawer_list_item, mPlanetTitles));
//        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());


        mDrawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mPlanetTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerList.setCacheColorHint(0);
        mDrawerList.setScrollingCacheEnabled(false);
        mDrawerList.setScrollContainer(false);
        mDrawerList.setFastScrollEnabled(true);
        mDrawerList.setSmoothScrollbarEnabled(true);

        mActionBar = createActionBarHelper();
        mActionBar.init();

        setupDrawerFooter();

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
//        mDrawerToggle = new ActionBarDrawerToggle(
//                this,                  /* host Activity */
//                mDrawerLayout,         /* DrawerLayout object */
//                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
//                R.string.drawer_open,  /* "open drawer" description for accessibility */
//                R.string.drawer_close  /* "close drawer" description for accessibility */
//                ) {
//            public void onDrawerClosed(View view) {
//                getActionBar().setTitle(mTitle);
//                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
//            }
//
//            public void onDrawerOpened(View drawerView) {
//                getActionBar().setTitle(mDrawerTitle);
//                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
//            }
//        };
//        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle = new SherlockActionBarDrawerToggle(this,
                mDrawerLayout, R.drawable.ic_drawer_light,
                R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();

        if (savedInstanceState == null) {
//            selectItem(0);
            selectNavigateItem(1);
        }
    }

    private View mFooter;
    private void setupDrawerFooter() {
        if (null != mDrawerList) {
            final boolean isLogin = isLogin();

            if (null == mFooter) {
//                LayoutInflater inflater = getLayoutInflater();
//                mFooter = inflater.inflate(R.layout.drawer_list_item, null);

                mFooter = new TextView(this);
                mDrawerList.addFooterView(mFooter);
            }

            if (mFooter instanceof TextView) {
                ((TextView)mFooter).setText(isLogin ? R.string.menu_user_logout :
                        R.string.action_login_user);
            }
            mFooter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isLogin) {
                        doLogout();
                    } else {
                        doLogin();
                    }
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        menu.findItem(R.id.action_toggle).setVisible(!drawerOpen);
        menu.findItem(R.id.action_toggle).setIcon(mCurrentFragment == fragmentPlanet ?
                R.drawable.ic_action_category : R.drawable.ic_action_map);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {
            case R.id.action_toggle:
                toggleView();
                return true;
        case R.id.action_websearch:
            // create intent to perform web search for this planet
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
            // catch event that there's no activity to handle intent
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
            }
            return true;
            case R.id.menu_demos:
                Intent i = new Intent(getApplicationContext(),
                        BMapApiDemoMain.class);
                if (i.resolveActivity(getPackageManager()) != null) {
                    startActivity(i);
                } else {
                    Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
                }
                return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    Fragment mCurrentFragment;
    private void toggleView() {
        if (mCurrentFragment == fragmentPlanet) {
            // goto map view
            gotoLocationFragment();
        } else {
            // goto default view with category list
            gotoDefaultView();
//            Intent intent = new Intent(this, LocationOverlayDemo.class);
//            // catch event that there's no activity to handle intent
//            if (intent.resolveActivity(getPackageManager()) != null) {
//                startActivity(intent);
//            } else {
//                Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
//            }
        }
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            selectItem(position);
            selectNavigateItem(position);
        }
    }

    SignUpFragment loginFragment;
    LocationOverlayFragment locationFragment;
    PlanetFragment fragmentPlanet;
    private void gotoLoinFragment() {
        if (null == loginFragment) {
            loginFragment = new SignUpFragment();
        }

        if (mCurrentFragment != loginFragment) {
            mCurrentFragment = loginFragment;
            gotoFragmentView(mCurrentFragment, FRAGMENT_LOGIN, FRAGMENT_DEFAULT);
        }

    }

    private void gotoLocationFragment() {
        if (null == locationFragment) {
            locationFragment = new LocationOverlayFragment();
        }

        if (mCurrentFragment != locationFragment) {
            mCurrentFragment = locationFragment;
            gotoFragmentView(mCurrentFragment, FRAGMENT_MAP, FRAGMENT_DEFAULT);
        }
    }
    private void gotoDefaultView() {
        if (null == fragmentPlanet) {
            fragmentPlanet = new PlanetFragment();
            Bundle args = new Bundle();
            args.putInt(PlanetFragment.ARG_PLANET_NUMBER, mDefaultPosition);
            fragmentPlanet.setArguments(args);
            gotoFragmentView(fragmentPlanet, FRAGMENT_DEFAULT, null);
            mCurrentFragment = fragmentPlanet;
        }

        if (mCurrentFragment != fragmentPlanet) {
            mCurrentFragment = fragmentPlanet;
            gotoFragmentView(mCurrentFragment,FRAGMENT_DEFAULT, null);
        }
    }

    private void gotoFragmentView(Fragment fragment, String name, String stackName) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (TextUtils.isEmpty(name)) {
            ft.replace(R.id.content_frame, fragment);
        } else {
            ft.replace(R.id.content_frame, fragment, name);
        }
        if (!TextUtils.isEmpty(stackName)) {
            ft.addToBackStack(stackName);
        }
        ft.commit();
    }

    private void selectNavigateItem(int position) {
        mActionBar.setTitle(mPlanetTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);

        switch (position) {
            case 0: // register or login
                gotoLoinFragment();
                break;
            case 1: // category
                gotoDefaultView();
                break;
            case 2: // search
                break;
            case 3: // book
                break;
            case 4: // inbox
                break;
        }
    }

    private int mDefaultPosition;
    private void selectItem(int position) {
        mDefaultPosition = position;
        boolean existing = null != fragmentPlanet;
        gotoDefaultView();
        // update the main content by replacing fragments
        if (existing) {
            fragmentPlanet.update(position);
        }

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mPlanetTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggle
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    // todo: login/logout user begin
    private void doLogin() {

    }
    private void doLogout() {
    }

    private boolean isLogin() {
        return false;
    }
    // todo: login/logout user end

    /**
     * Fragment that appears in the "content_frame", shows a planet
     */
    public static class PlanetFragment extends Fragment {
        public static final String ARG_PLANET_NUMBER = "planet_number";

        private View rootView;

        public PlanetFragment() {
            // Empty constructor required for fragment subclasses
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_planet, container, false);
            int i = getArguments().getInt(ARG_PLANET_NUMBER);
            update(i);
            return rootView;
        }

        public void update(int position) {
            String planet = getResources().getStringArray(R.array.planets_array)[position];

            int imageId = getResources().getIdentifier(planet.toLowerCase(Locale.getDefault()),
                    "drawable", getActivity().getPackageName());
            ((ImageView) rootView.findViewById(R.id.image)).setImageResource(imageId);
            getActivity().setTitle(planet);
        }
    }

    public static void invoke(Context context, BaseFragment.FragmentSession session,
                              Object o, Exception exception) {
        // todo: callback from login fragment after login close

    }


}