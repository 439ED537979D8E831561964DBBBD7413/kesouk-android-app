package com.tstl.kesouk.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.tstl.kesouk.DB.DB;
import com.tstl.kesouk.Fragments.Basket_Fragment;
import com.tstl.kesouk.Fragments.Customer_Fragment;
import com.tstl.kesouk.Fragments.Favorites_Fragment;
import com.tstl.kesouk.Fragments.Gift_Fragments;
import com.tstl.kesouk.Fragments.Home;
import com.tstl.kesouk.Fragments.Home_Fragment;
import com.tstl.kesouk.Fragments.Login_Fragment;
import com.tstl.kesouk.R;

import java.lang.reflect.Field;

/**
 * Created by user on 17-Jan-18.
 */
public class Navigation_Tab_Activity extends AppCompatActivity {
    public static  BottomNavigationView bottomNavigationView;
    DB db;
    String notification = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_tab_activity);
        db = new DB(Navigation_Tab_Activity.this);
        bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);

        if (db.getAllLogin().size() == 1) {
            loadFragment(new Customer_Fragment());
        } else if (db.getAllLogin().size() == 0) {
            loadFragment(new Home_Fragment());
        }


       /* bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.action_item1:
                                if (db.getAllLogin().size() == 1) {
                                    selectedFragment = Customer_Fragment.newInstance();
                                  //  BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);
                                } else if (db.getAllLogin().size() == 0) {
                                    selectedFragment = Home_Fragment.newInstance();
                                   // BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);
                                } else {
                                   // BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);
                                }
                                break;
                            case R.id.action_item2:
                                selectedFragment = Login_Fragment.newInstance();
                                //BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);
                                break;
                            case R.id.action_item3:
                                selectedFragment = Login_Fragment.newInstance();
                               // BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);
                                break;
                            case R.id.action_item4:
                                selectedFragment = Favorites_Fragment.newInstance();
                              //  BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);
                                break;
                            case R.id.action_item5:
                                selectedFragment = Basket_Fragment.newInstance();
                                //BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.container_fragment, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });
*/
     /*   if (db.getAllLogin().size() == 1) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container_fragment, Customer_Fragment.newInstance());
            transaction.commit();
           // bottomNavigationView.getMenu().getItem(0).setChecked(true);

        } else if (db.getAllLogin().size() == 0) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container_fragment, Home_Fragment.newInstance());
            transaction.commit();
           // bottomNavigationView.getMenu().getItem(0).setChecked(true);

        }

*/

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
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void subscribeToPushService() {
        FirebaseMessaging.getInstance().subscribeToTopic("news");
        Log.e("AndroidBash", "Subscribed");
        Toast.makeText(Navigation_Tab_Activity.this, "Subscribed", Toast.LENGTH_SHORT).show();
        String token = FirebaseInstanceId.getInstance().getToken();
        // Log and toast
        Log.e("AndroidBash", token);
        Toast.makeText(Navigation_Tab_Activity.this, token, Toast.LENGTH_SHORT).show();
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

        if (db.getAllLogin().size() == 0) {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
            } else {
                Intent intent = new Intent(Navigation_Tab_Activity.this, Login_Register_Activity.class);
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