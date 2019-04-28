package ir.stit.ir.charkhtafi;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import co.ronash.pushe.Pushe;
import ir.stit.ir.charkhtafi.Adapter.NavigationAdapter;
import ir.stit.ir.charkhtafi.Fragment.Delivery;
import ir.stit.ir.charkhtafi.Fragment.Order;
import ir.stit.ir.charkhtafi.Model.NavModel;
import ir.stit.ir.charkhtafi.Utils.Tools;
import ir.stit.ir.charkhtafi.Utils.Views.CustomTextView;

public class MainActivity extends AppCompatActivity {

    public static CustomTextView MainActivityTitle;
    private CustomTextView Header_Name, Header_Type;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private RecyclerView RecyclerViewNavigation;
    public enum UserType {
        MainKargozar,
        SecondKargozar,
        Bike
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        INI();
        setSupportActionBar(toolbar);
        setUpToolbar();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        Pushe.initialize(this,true);


//        ProfileLoad.Type = "LimitLessUser";
        //LimitLessUser
        //BikeDelivery
        NavigationAdapter adapter1 = new NavigationAdapter(getNavdata(), MainActivity.this, drawer);
        RecyclerViewNavigation
                .setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false));
        RecyclerViewNavigation.setAdapter(adapter1);


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment Selected;
        if (Tools.getInstance(getApplicationContext()).read("UserType","").equals("3213655546")) {
            Selected = new Order();
            Log.e("Selected", "Ordered");
        }else {
            Selected = new Delivery();
            Log.e("Selected", "Delivery");
        }
        transaction.replace(R.id.MainActivityFrameLayout, Selected);
        transaction.commit();

//        Header_Name.setText( "نام : " + Tools.getInstance(getApplicationContext()).read("FirstName",""));

    }

    private void INI() {
        drawer = findViewById(R.id.MainActivityOrderNavigation);
        toolbar = findViewById(R.id.MainActivityOrderToolbar);
        RecyclerViewNavigation = findViewById(R.id.TotalOrderRecyclerViewNavigation);
        MainActivityTitle = findViewById(R.id.MainActivityTitle);
        Header_Name = findViewById(R.id.Header_Name);
        Header_Type = findViewById(R.id.Header_Type);

        Header_Name.setText("نام : ".concat(Tools.getInstance(getBaseContext()).read("UserName","")));

        switch (Tools.getInstance(getBaseContext()).read("UserType","")) {
            case "3213655546":
                Header_Type.setText( "نوع : " .concat("کارگزار نوع اول"));
                break;
            case "3213655547":
                Header_Type.setText( "نوع : " .concat("کارگزار نوع دوم"));
                break;
            case "3213655548":
                Header_Type.setText( "نوع : " .concat("پیک"));
                break;
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_navigation:
                if (drawer.isDrawerOpen(Gravity.START|Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.START|Gravity.RIGHT);
                }else {
                    drawer.openDrawer(Gravity.START|Gravity.RIGHT);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpToolbar() {

        toggle = new ActionBarDrawerToggle(this,drawer, toolbar,0,0);

    }

    private List<NavModel> getNavdata() {
        List<NavModel> data = new ArrayList<>();

        switch (Tools.getInstance(getApplicationContext()).read("UserType","")) {
            case "3213655546":
                for (int i = 0; i < 4; i++) {
                    switch (i) {
                        case 0:
                            NavModel one = new NavModel();
                            one.setImage(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_basket_shop));
                            one.setText("لیست محصولات");
                            data.add(one);
                            break;
                        case 1:
                            NavModel two = new NavModel();
                            two.setImage(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_shopping_basket_1));
                            two.setText("مجموع سفارشات");
                            data.add(two);
                            break;
                        case 2:
                            NavModel three = new NavModel();
                            three.setImage(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_shopping_basket_2));
                            three.setText("لیست سفارشات");
                            data.add(three);
                            break;
                        case 3:
                            NavModel end = new NavModel();
                            end.setImage(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_logout));
                            end.setText("خروج");
                            data.add(end);
                            break;
//                            case 4:
//                            NavModel end1 = new NavModel();
//                            end1.setImage(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_logout));
//                            end1.setText("خروج");
//                            data.add(end1);
//                            break;
                    }
                }
                break;
            case "3213655547":
                for (int i = 0; i < 3; i++) {
                    switch (i) {
                        case 0:
                            NavModel two = new NavModel();
                            two.setImage(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_shopping_basket_1));
                            two.setText("مجموع سفارش ها");
                            data.add(two);
                            break;
                        case 1:
                            NavModel three = new NavModel();
                            three.setImage(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_shopping_basket_2));
                            three.setText("لیست سفارش ها");
                            data.add(three);
                            break;
                        case 2:
                            NavModel end = new NavModel();
                            end.setImage(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_logout));
                            end.setText("خروج");
                            data.add(end);
                            break;
                    }
                }
                break;
            case "3213655548":
                for (int i = 0; i < 2; i++) {
                    switch (i) {
                        case 0:
                            NavModel three = new NavModel();
                            three.setImage(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_shopping_basket_2));
                            three.setText("لیست سفارش ها");
                            data.add(three);
                            break;
                        case 1:
                            NavModel end = new NavModel();
                            end.setImage(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_logout));
                            end.setText("خروج");
                            data.add(end);
                            break;
                    }
                }
                break;
        }

        return data;
    }


}
