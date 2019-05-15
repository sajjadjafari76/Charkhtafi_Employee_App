package ir.stit.ir.charkhtafi.Fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.stit.ir.charkhtafi.Adapter.OrderAdapter;
import ir.stit.ir.charkhtafi.AppController.AppController;
import ir.stit.ir.charkhtafi.Model.AllDeliveryModel;
import ir.stit.ir.charkhtafi.Model.OrderFruitModel;
import ir.stit.ir.charkhtafi.Model.OrderModel;
import ir.stit.ir.charkhtafi.Network.StringRequest;
import ir.stit.ir.charkhtafi.R;
import ir.stit.ir.charkhtafi.Utils.Tools;

public class Order extends Fragment {


    View view;

    private boolean doubleBackToExitPressedOnce;
    private ImageView CustomOrder_Add;
    private LinearLayout Loading, Order_Connectivity, Order_Empty;
    private OrderAdapter adapter;

    private RecyclerView orderRecycler;
    private SwipeRefreshLayout Refresh;
    private AlphaAnimation inAnimation;
    private AlphaAnimation outAnimation;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_order, container, false);
        INI();

        orderRecycler.setVisibility(View.VISIBLE);


        if (Tools.getInstance(getActivity()).isOnline()) {
            Order_Connectivity.setVisibility(View.GONE);
//            Refresh.setVisibility(View.VISIBLE);
//
            startAnimation();
            getFactor();

        } else {
            Order_Connectivity.setVisibility(View.VISIBLE);
//            Refresh.setVisibility(View.GONE);
            endAnimation();
        }

        Refresh.setOnRefreshListener(() -> {
            if (Tools.getInstance(getActivity()).isOnline()) {
                Order_Connectivity.setVisibility(View.GONE);
//                Refresh.setVisibility(View.VISIBLE);
                Order_Empty.setVisibility(View.GONE);

                getFactor();

            } else {
                Order_Connectivity.setVisibility(View.VISIBLE);
                Order_Empty.setVisibility(View.GONE);
//                Refresh.setVisibility(View.GONE);
                endAnimation();
            }
        });


        orderRecycler.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int topRowVerticalPosition =
                        (orderRecycler == null || orderRecycler.getChildCount() == 0) ?
                                0 : orderRecycler.getChildAt(0).getTop();
//                Log.e("getTop", orderRecycler.getChildAt(0).getTop()+"");
                Refresh.setEnabled(dx == 0 && topRowVerticalPosition >= 0);
            }

        });


        return view;
    }

    private void INI() {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        inAnimation = new AlphaAnimation(0f, 1f);
        outAnimation = new AlphaAnimation(1f, 0f);
        orderRecycler = view.findViewById(R.id.OrderRecyclerView);
        Loading = view.findViewById(R.id.OrderLoading);
        Refresh = view.findViewById(R.id.OrderRefresh);
        Order_Connectivity = view.findViewById(R.id.Order_Connectivity);
        Order_Empty = view.findViewById(R.id.Order_Empty);

    }

    private List<OrderModel> getdata() {

        List<OrderModel> data = new ArrayList<>();
        List<OrderFruitModel> orderModel = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            if (i == 0) {
                OrderModel model = new OrderModel();
                model.setId("1232");
                model.setDescription("سیب زمینی متوسط و تازه");

                List<OrderFruitModel> model11 = new ArrayList<>();
                OrderFruitModel model1 = new OrderFruitModel();
                model1.setFruitId("126587");
                model1.setName("سیب زمینی");
                model1.setWeight("12");
                model1.setTotalWeight("12");
                model1.setPrice("1600");
                model1.setImage("http://charkhtafi.golsamsepahan.charkhtafi/wp-content/uploads/2017/02/potato_png2391.png");
                model11.add(model1);
                model.setFruitModel(model11);
                data.add(model);
            } else if (i == 1) {
                OrderModel model = new OrderModel();
                model.setId("3654");
                model.setDescription("خیار های قلمی و اناناس با اندازه متوسط");

                List<OrderFruitModel> model22 = new ArrayList<>();
                OrderFruitModel model2 = new OrderFruitModel();
                model2.setFruitId("7687645");
                model2.setName("خیار");
                model2.setWeight("1");
                model2.setTotalWeight("1");
                model2.setPrice("18000");
                model2.setImage("https://charkhtafi.parsegard.charkhtafi/sites/default/files/content_types/food_nutrition/9571/main_image/pineapple.jpg");
                model22.add(model2);
                OrderFruitModel model3 = new OrderFruitModel();
                model3.setFruitId("7687645");
                model3.setName("آناناس");
                model3.setWeight("1");
                model3.setTotalWeight("1");
                model3.setPrice("18000");
                model3.setImage("https://charkhtafi.parsegard.charkhtafi/sites/default/files/content_types/food_nutrition/9571/main_image/pineapple.jpg");
                model22.add(model3);

                model.setFruitModel(model22);
                data.add(model);
            } else if (i == 2) {


                OrderModel model = new OrderModel();
                model.setId("3654");
                model.setDescription("اندازه هلو کوچیک");

                List<OrderFruitModel> model33 = new ArrayList<>();
                OrderFruitModel model3 = new OrderFruitModel();
                model3.setFruitId("786545");
                model3.setName("هلو");
                model3.setWeight("4");
                model3.setTotalWeight("4");
                model3.setPrice("1400");
                model3.setImage("http://salamatezanan.charkhtafi/wp-content/uploads/peach-950x950.png");
                model33.add(model3);

                model.setFruitModel(model33);
                data.add(model);
            } else if (i == 3) {


                OrderModel model = new OrderModel();
                model.setId("3654");
                model.setDescription("اندازه هلو کوچیک");

                List<OrderFruitModel> model33 = new ArrayList<>();
                OrderFruitModel model3 = new OrderFruitModel();
                model3.setFruitId("786545");
                model3.setName("هلو");
                model3.setWeight("4");
                model3.setTotalWeight("4");
                model3.setPrice("1400");
                model3.setImage("http://salamatezanan.charkhtafi/wp-content/uploads/peach-950x950.png");
                model33.add(model3);

                model.setFruitModel(model33);
                data.add(model);
            } else if (i == 4) {


                OrderModel model = new OrderModel();
                model.setId("3654");
                model.setDescription("اندازه هلو کوچیک");

                List<OrderFruitModel> model33 = new ArrayList<>();
                OrderFruitModel model3 = new OrderFruitModel();
                model3.setFruitId("786545");
                model3.setName("هلو");
                model3.setWeight("4");
                model3.setTotalWeight("4");
                model3.setPrice("1400");
                model3.setImage("http://salamatezanan.charkhtafi/wp-content/uploads/peach-950x950.png");
                model33.add(model3);

                model.setFruitModel(model33);
                data.add(model);
            } else if (i == 5) {


                OrderModel model = new OrderModel();
                model.setId("3654");
                model.setDescription("اندازه هلو کوچیک");

                List<OrderFruitModel> model33 = new ArrayList<>();
                OrderFruitModel model3 = new OrderFruitModel();
                model3.setFruitId("786545");
                model3.setName("هلو");
                model3.setWeight("4");
                model3.setTotalWeight("4");
                model3.setPrice("1400");
                model3.setImage("http://salamatezanan.charkhtafi/wp-content/uploads/peach-950x950.png");
                model33.add(model3);

                model.setFruitModel(model33);
                data.add(model);
            } else if (i == 6) {


                OrderModel model = new OrderModel();
                model.setId("3654");
                model.setDescription("88اندازه هلو کوچیک");

                List<OrderFruitModel> model33 = new ArrayList<>();
                OrderFruitModel model3 = new OrderFruitModel();
                model3.setFruitId("786545");
                model3.setName("هلو");
                model3.setWeight("4");
                model3.setTotalWeight("4");
                model3.setPrice("1400");
                model3.setImage("http://salamatezanan.charkhtafi/wp-content/uploads/peach-950x950.png");
                model33.add(model3);

                model.setFruitModel(model33);
                data.add(model);
            } else if (i == 7) {


                OrderModel model = new OrderModel();
                model.setId("3654");
                model.setDescription("77اندازه هلو کوچیک");

                List<OrderFruitModel> model33 = new ArrayList<>();
                OrderFruitModel model3 = new OrderFruitModel();
                model3.setFruitId("786545");
                model3.setName("هلو");
                model3.setWeight("4");
                model3.setTotalWeight("4");
                model3.setPrice("1400");
                model3.setImage("http://salamatezanan.charkhtafi/wp-content/uploads/peach-950x950.png");
                model33.add(model3);

                model.setFruitModel(model33);
                data.add(model);
            } else if (i == 8) {


                OrderModel model = new OrderModel();
                model.setId("3654");
                model.setDescription("66اندازه هلو کوچیک");

                List<OrderFruitModel> model33 = new ArrayList<>();
                OrderFruitModel model3 = new OrderFruitModel();
                model3.setFruitId("786545");
                model3.setName("هلو");
                model3.setWeight("4");
                model3.setTotalWeight("4");
                model3.setPrice("1400");
                model3.setImage("http://salamatezanan.charkhtafi/wp-content/uploads/peach-950x950.png");
                model33.add(model3);

                model.setFruitModel(model33);
                data.add(model);
            } else if (i == 9) {


                OrderModel model = new OrderModel();
                model.setId("3654");
                model.setDescription("55اندازه هلو کوچیک");

                List<OrderFruitModel> model33 = new ArrayList<>();
                OrderFruitModel model3 = new OrderFruitModel();
                model3.setFruitId("786545");
                model3.setName("هلو");
                model3.setWeight("4");
                model3.setTotalWeight("4");
                model3.setPrice("1400");
                model3.setImage("http://salamatezanan.charkhtafi/wp-content/uploads/peach-950x950.png");
                model33.add(model3);

                model.setFruitModel(model33);
                data.add(model);
            } else if (i == 10) {


                OrderModel model = new OrderModel();
                model.setId("3654");
                model.setDescription("44اندازه هلو کوچیک");

                List<OrderFruitModel> model33 = new ArrayList<>();
                OrderFruitModel model3 = new OrderFruitModel();
                model3.setFruitId("786545");
                model3.setName("هلو");
                model3.setWeight("4");
                model3.setTotalWeight("4");
                model3.setPrice("1400");
                model3.setImage("http://salamatezanan.charkhtafi/wp-content/uploads/peach-950x950.png");
                model33.add(model3);

                model.setFruitModel(model33);
                data.add(model);
            } else if (i == 11) {


                OrderModel model = new OrderModel();
                model.setId("3654");
                model.setDescription("33اندازه هلو کوچیک");

                List<OrderFruitModel> model33 = new ArrayList<>();
                OrderFruitModel model3 = new OrderFruitModel();
                model3.setFruitId("786545");
                model3.setName("هلو");
                model3.setWeight("4");
                model3.setTotalWeight("4");
                model3.setPrice("1400");
                model3.setImage("http://salamatezanan.charkhtafi/wp-content/uploads/peach-950x950.png");
                model33.add(model3);

                model.setFruitModel(model33);
                data.add(model);
            } else if (i == 12) {


                OrderModel model = new OrderModel();
                model.setId("3654");
                model.setDescription("22اندازه هلو کوچیک");

                List<OrderFruitModel> model33 = new ArrayList<>();
                OrderFruitModel model3 = new OrderFruitModel();
                model3.setFruitId("786545");
                model3.setName("هلو");
                model3.setWeight("4");
                model3.setTotalWeight("4");
                model3.setPrice("1400");
                model3.setImage("http://salamatezanan.charkhtafi/wp-content/uploads/peach-950x950.png");
                model33.add(model3);

                model.setFruitModel(model33);
                data.add(model);
            } else if (i == 13) {


                OrderModel model = new OrderModel();
                model.setId("3654");
                model.setDescription("11اندازه هلو کوچیک");

                List<OrderFruitModel> model33 = new ArrayList<>();
                OrderFruitModel model3 = new OrderFruitModel();
                model3.setFruitId("786545");
                model3.setName("هلو");
                model3.setWeight("4");
                model3.setTotalWeight("4");
                model3.setPrice("1400");
                model3.setImage("http://salamatezanan.charkhtafi/wp-content/uploads/peach-950x950.png");
                model33.add(model3);

                model.setFruitModel(model33);
                data.add(model);
            }
        }
        return data;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {

                        if (!doubleBackToExitPressedOnce) {
                            doubleBackToExitPressedOnce = true;
                            Tools.getInstance(getActivity()).ToastMessage("جهت خروج دکمه برگشت را مجددا کلیک کنید!");
                            new Handler().postDelayed(new Runnable() {

                                @Override
                                public void run() {
                                    doubleBackToExitPressedOnce = false;
                                }
                            }, 2000);
                        } else {
                            getActivity().finish();
                        }

                        return true;
                    }
                }
                return false;
            }
        });

    }

    public void getFactor() {

        try {
            Map<String, String> params = new HashMap<>();
            params.put("OPR", "GETREFRENCEDORDERS");
            params.put("userid", Tools.getInstance(getContext()).read("UserId", ""));

            StringRequest loginRequest = new StringRequest(params, 0, new StringRequest.ResponseAction() {
                @Override
                public void onResponseAction(String response) {
                    Log.e("getFactorResponse", response);

                    try {
                        JSONObject object = new JSONObject(response);

                        if (object.has("error")) {
                            if (!object.getBoolean("error")) {

                                JSONArray FactorArray = object.getJSONObject("msg").getJSONArray("OrderInfo");

                                List<OrderModel> orderModelList = new ArrayList<>();

//                                Log.e("sajjad", object.getJSONArray("msg").length() + " | ");
                                if (FactorArray.length() == 0) {

                                    Order_Empty.setVisibility(View.VISIBLE);
                                    orderRecycler.setVisibility(View.GONE);
                                    Loading.setVisibility(View.GONE);
                                    endAnimation();

                                    return;
                                }

                                for (int i = 0; i < FactorArray.length(); i++) {

                                    OrderModel orderModel = new OrderModel();
                                    JSONObject FactorObject = FactorArray.getJSONObject(i);

                                    orderModel.setId(FactorObject.getString("rid"));
                                    orderModel.setTotalPrice(FactorObject.getString("sumcast"));
                                    orderModel.setDeliveryPrice(FactorObject.getString("bikeprice"));
                                    orderModel.setActive(FactorObject.getBoolean("isAccepted"));
                                    orderModel.setPreOrder(FactorObject.getBoolean("orderType"));
                                    orderModel.setDescription(FactorObject.getString("description"));
                                    orderModel.setDomainOffPercent(Float.parseFloat(FactorObject.getString("offPercent")));
                                    orderModel.setDomainStatus(FactorObject.getInt("offDomain"));

                                    JSONArray FruitArray = FactorObject.getJSONArray("detail");
                                    List<OrderFruitModel> orderFruitModelList = new ArrayList<>();
                                    for (int j = 0; j < FruitArray.length(); j++) {

                                        OrderFruitModel fruitModel = new OrderFruitModel();
                                        JSONObject fruitObject = FruitArray.getJSONObject(j);

                                        fruitModel.setFruitId(fruitObject.getString("pid"));
                                        fruitModel.setWeight(fruitObject.getString("pmuch"));
                                        fruitModel.setImage(fruitObject.getString("pimg"));
                                        fruitModel.setName(fruitObject.getString("ptitle"));
                                        fruitModel.setUnit(fruitObject.getString("munit"));
                                        fruitModel.setPrice(fruitObject.getString("pprice"));
                                        fruitModel.setDegree(fruitObject.getString("pqg"));
                                        fruitModel.setPriceId(fruitObject.getString("pfid"));
                                        fruitModel.setOff(fruitObject.getString("poff"));

                                        double sum = Double.parseDouble(fruitObject.getString("pprice"))
                                                * Float.parseFloat(fruitObject.getString("pmuch"));
                                        fruitModel.setTotalWeight(String.valueOf((int) sum));
                                        fruitModel.setUnitId(fruitObject.getString("muid"));
                                        fruitModel.setDomainOff(fruitObject.getBoolean("hasOffer"));

                                        orderFruitModelList.add(fruitModel);
                                    }

                                    orderModel.setFruitModel(orderFruitModelList);


                                    if (orderModel.getAllDelivery() == null) {
                                        JSONArray BikeArray = object.getJSONObject("msg").getJSONArray("bikes");
                                        List<AllDeliveryModel> deliveryModelList = new ArrayList<>();
                                        for (int c = 0; c < BikeArray.length(); c++) {

                                            AllDeliveryModel deliveryModel = new AllDeliveryModel();
                                            JSONObject bikeObject = BikeArray.getJSONObject(c);

                                            deliveryModel.setId(bikeObject.getString("bid"));
                                            deliveryModel.setName(bikeObject.getString("btitle"));
                                            deliveryModelList.add(deliveryModel);
                                        }

                                        orderModel.setAllDelivery(deliveryModelList);
                                    }

                                    orderModelList.add(orderModel);
                                }

                                adapter = new OrderAdapter(orderModelList, getActivity());
                                orderRecycler
                                        .setLayoutManager(new GridLayoutManager(getContext(), 1, LinearLayoutManager.VERTICAL, false));
                                orderRecycler.setAdapter(adapter);


                                endAnimation();

                            } else {
                                Toast.makeText(getContext(), object.getString("msg"), Toast.LENGTH_SHORT).show();
                                endAnimation();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("ErrorgetFactor", e.toString() + " |");
                        Tools.getInstance(getActivity()).ToastMessage("خطا در اتصال به سرور ، دوباره تلاش کنید.");
                        endAnimation();
                    }
                }

                @Override
                public void onErrorAction(VolleyError error) {
                    Log.e("ErrorgetFactor1", error.toString());
                    Tools.getInstance(getActivity()).ToastMessage("خطا در اتصال به سرور ، دوباره تلاش کنید.");
                    endAnimation();
                    Order_Empty.setVisibility(View.GONE);
                }
            });

            AppController.getInstance().addToRequestQueue(loginRequest);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ErrorgetFactor2", e.toString());
            Tools.getInstance(getActivity()).ToastMessage("خطا در اتصال به سرور ، دوباره تلاش کنید.");
            endAnimation();
            Order_Empty.setVisibility(View.GONE);
        }

    }

    public void startAnimation() {
        inAnimation.setDuration(300);
        Loading.setAnimation(inAnimation);
        orderRecycler.setVisibility(View.GONE);
        Loading.setVisibility(View.VISIBLE);
    }

    public void endAnimation() {
        outAnimation.setDuration(300);
        Loading.setAnimation(outAnimation);
        Loading.setVisibility(View.GONE);
        orderRecycler.setVisibility(View.VISIBLE);
        Refresh.setRefreshing(false);
    }

}
