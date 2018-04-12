package com.tstl.kesouk.Activity;

/**
 * Created by user on 22-Feb-18.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.tstl.kesouk.DB.DB;
import com.tstl.kesouk.Fragments.Basket_Fragment;
import com.tstl.kesouk.Fragments.Customer_Fragment;
import com.tstl.kesouk.Fragments.Favorites_Fragment;
import com.tstl.kesouk.Fragments.Home_Fragment;
import com.tstl.kesouk.Fragments.Login_Fragment;
import com.tstl.kesouk.R;

import java.lang.reflect.Field;

public class TabMain_Activity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {

    FrameLayout simpleFrameLayout;
    DB db;
    public static NavigationView navigationView;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    DrawerLayout drawer;
    Toolbar mToolbar;
    public static EditText search;
    public static int home_signin_backpress=0,logout_backpress=0;
    ExpandableListView expandableList;
    public BottomNavigationView bottomNavigationView;
    public static TextView toolbar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_tab_main);
        db = new DB(TabMain_Activity.this);

        mToolbar = (Toolbar) findViewById(R.id.logintoolbar);
        mToolbar.setBackgroundColor(getResources().getColor(R.color.dark_green));
        setSupportActionBar(mToolbar);
        search = (EditText) findViewById(R.id.search_new);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);

        search.setVisibility(View.VISIBLE);
        toolbar_title.setVisibility(View.GONE);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        mActionBarDrawerToggle = new ActionBarDrawerToggle(this,
               drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

         drawer.setDrawerListener(mActionBarDrawerToggle);

          mActionBarDrawerToggle.syncState();

         navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

   search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager inputManager =
                            (InputMethodManager)
                                    getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(
                            getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    handled = true;
                }
                return handled;
            }
        });
   if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);



// get the reference of FrameLayout and TabLayout
        simpleFrameLayout = (FrameLayout) findViewById(R.id.rldContainer);

        Fragment fragment = null;

        if (db.getAllLogin().size() == 1) {
            fragment = new Customer_Fragment();
        } else if (db.getAllLogin().size() == 0) {
            fragment = new Home_Fragment();
        }

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.rldContainer, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
// perform setOnTabSelectedListener event on TabLayout

    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.action_item1:
                    if (db.getAllLogin().size() == 1) {
                        fragment = new Customer_Fragment();
                        loadFragment(fragment);
                    } else if (db.getAllLogin().size() == 0) {
                        fragment = new Home_Fragment();
                        loadFragment(fragment);
                    }

                    return true;
                case R.id.action_item2:
                    fragment = new Login_Fragment();
                    loadFragment(fragment);
                    return true;
                case R.id.action_item3:
                    fragment = new Login_Fragment();
                    loadFragment(fragment);
                    return true;
                case R.id.action_item4:
                    fragment = new Favorites_Fragment();
                    loadFragment(fragment);
                    return true;
                case R.id.action_item5:
                    fragment = new Basket_Fragment();
                    loadFragment(fragment);
                    return true;
            }

            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.rldContainer, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack("Some String");
        ft.commit();
    }
    private void enableExpandableList() {

        // expandableList = (ExpandableListView) view.findViewById(R.id.left_drawer);



      /*  expandableList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                home_category_selected_name=home_categories_list.get(groupPosition);
                Log.e("catg_name",home_category_selected_name);
                home_category_flag=2;
                drawer.closeDrawers();


                Browse_Category_Fragment newFragment = new Browse_Category_Fragment();
                Bundle args = new Bundle();
                args.putInt("position", groupPosition);
                newFragment.setArguments(args);

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container_fragment,newFragment);
                transaction.addToBackStack(null);
                transaction.commit();

                return true;
            }
        });
*/
        expandableList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
               /* Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();*/
            }
        });

        // Listview Group collasped listener
        expandableList.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
              /*  Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();
*/
            }
        });
        // Listview on child click listener
        expandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                // Temporary code:

                // till here
              /*  Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();*/
                return false;
            }
        });
    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        //  menuItem.setChecked(true);
                        drawer.closeDrawers();
                        return true;
                    }
                });
    }


    @Override
    public void onBackPressed() {

       /* List fragmentList = getSupportFragmentManager().getFragments();

        boolean handled = false;
        for(Fragment f : fragmentList) {
            if(f instanceof BaseFragment) {
                handled = ((BaseFragment)f).onBackPressed();

                if(handled) {
                    break;
                }
            }
        }

        if(!handled) {
            super.onBackPressed();
        }*/
  DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        if (db.getAllLogin().size() == 0) {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
            } else {
                Intent intent = new Intent(TabMain_Activity.this, Login_Register_Activity.class);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        }
        // super.onBackPressed();

        if (db.getAllLogin().size() == 1) {

       /*
            finish();
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);*/
/*
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            System.exit(0);*/

            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
            } else {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }

        } /*else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
            } else {
                Intent intent = new Intent(Navigation_Tab_Activity.this, Login_Register_Activity.class);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }*/
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

      /*  getMenuInflater().inflate(R.menu.item, menu);
        return true;*/
        super.onCreateOptionsMenu(menu);


        if(db.getAllLogin().size()==0) {
            mToolbar.inflateMenu(R.menu.item);
        }
        else
        {
            mToolbar.inflateMenu(R.menu.cust_item);
        }

        mToolbar.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return onOptionsItemSelected(item);
                    }
                });

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
       /* int id = item.getItemId();

        // Activate the navigation drawer toggle
        if (mActionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);*/

        if (mActionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.my_acc:
                if(db.getAllLogin().size()==0)
                {
                    home_signin_backpress = 1;
                    Intent intent2 = new Intent(TabMain_Activity.this, Login_Activity.class);
                    startActivity(intent2);
                    break;
                }
                else
                {
                    Intent my_acc = new Intent(TabMain_Activity.this, Profile_Activity.class);
                    startActivity(my_acc);
                }

                break;
            case R.id.my_orders:
              /*  if(db.getAllLogin().size()==0)
                {
                    home_signin_backpress = 1;
                    Intent intent2 = new Intent(TabMain_Activity.this, Login_Activity.class);
                    startActivity(intent2);
                    break;
                }
                else
                {
                     Intent my_addr = new Intent(TabMain_Activity.this, MyAddress_Activity.class);
                startActivity(my_addr);
                }*/

                break;
            case R.id.my_wallet:

                break;
            case R.id.contact:

                break;
            case R.id.about:

                break;
            case R.id.terms:

                break;
            case R.id.faq:

                break;
            case R.id.signin:

                if(db.getAllLogin().size()==0)
                {
                    home_signin_backpress = 1;
                    Intent intent2 = new Intent(TabMain_Activity.this, Login_Activity.class);
                    startActivity(intent2);
                    break;
                }
                else
                {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TabMain_Activity.this);
                    alertDialogBuilder.setMessage("Are you sure you want to Logout?");
                    alertDialogBuilder.setCancelable(false);

                    alertDialogBuilder.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    db.removeLogin();
                                 /* db.removeFname();
                                    db.removeLname();
                                    db.removeEmail();
                                    db.removeDob();
                                    db.removeMob();
                                    db.removeLandline();
                                    db.removePromotions();*/

                                    logout_backpress=1;
                                    Intent intent2 = new Intent(TabMain_Activity.this, Login_Activity.class);
                                    intent2.addCategory(Intent.CATEGORY_HOME);
                                    intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent2);
                                    finish();

                                }
                            });
                    alertDialogBuilder.setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    arg0.dismiss();


                                }
                            });


                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    alertDialog.getButton(alertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.app_bg));
                    ;


                }
                break;

            default:
                break;
        }

        return true;
    }

  @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);


        int id = item.getItemId();
        switch (id) {

            default:
                return true;
        }

    }
    public static class BottomNavigationViewHelper {

        public static void removeShiftMode(BottomNavigationView view) {
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
            try {
                Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
                shiftingMode.setAccessible(true);
                shiftingMode.setBoolean(menuView, false);
                shiftingMode.setAccessible(false);
                for (int i = 0; i < menuView.getChildCount(); i++) {
                    BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                    item.setShiftingMode(false);
                    // set once again checked value, so view will be updated
                    item.setChecked(item.getItemData().isChecked());
                }
            } catch (NoSuchFieldException e) {
                Log.e("ERROR NO SUCH FIELD", "Unable to get shift mode field");
            } catch (IllegalAccessException e) {
                Log.e("ERROR ILLEGAL ALG", "Unable to change value of shift mode");
            }
        }
    }
}