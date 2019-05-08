package ir.stit.ir.charkhtafi.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.stit.ir.charkhtafi.Adapter.DeliveryAdapter;
import ir.stit.ir.charkhtafi.AppController.AppController;
import ir.stit.ir.charkhtafi.Model.DeliveryModel;
import ir.stit.ir.charkhtafi.Model.OrderFruitModel;
import ir.stit.ir.charkhtafi.Network.StringRequest;
import ir.stit.ir.charkhtafi.R;
import ir.stit.ir.charkhtafi.Utils.Tools;

public class Delivery extends Fragment {

    private View view;
    private RecyclerView DeliveryRecyclerView;
    private LinearLayout Loading, Delivery_Connectivity, Delivery_Empty;
    private SwipeRefreshLayout DeliveryRefresh;
    private AlphaAnimation inAnimation;
    private AlphaAnimation outAnimation;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_delivery, container, false);
        INI();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        inAnimation = new AlphaAnimation(0f, 1f);
        outAnimation = new AlphaAnimation(1f, 0f);

        if (Tools.getInstance(getActivity()).isOnline()) {
            Delivery_Connectivity.setVisibility(View.GONE);

            getBikeData();

        } else {
            Delivery_Connectivity.setVisibility(View.VISIBLE);
        }

        DeliveryRefresh.setOnRefreshListener(() -> {
            if (Tools.getInstance(getActivity()).isOnline()) {
                Delivery_Connectivity.setVisibility(View.GONE);

//                    LoadingAsync async = new LoadingAsync();
//                    async.execute();
                getBikeData();

            } else {
                Delivery_Connectivity.setVisibility(View.VISIBLE);
            }
        });


        DeliveryRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int topRowVerticalPosition =
                        (DeliveryRecyclerView == null || DeliveryRecyclerView.getChildCount() == 0) ?
                                0 : DeliveryRecyclerView.getChildAt(0).getTop();
//                Log.e("getTop", DeliveryRecyclerView.getChildAt(0).getTop() + "");
                DeliveryRefresh.setEnabled(dx == 0 && topRowVerticalPosition >= 0);
            }
        });


        return view;
    }

    private void INI() {
        DeliveryRecyclerView = view.findViewById(R.id.DeliveryRecyclerView);
        Loading = view.findViewById(R.id.DeliveryLoading);
        DeliveryRefresh = view.findViewById(R.id.DeliveryRefresh);
        Delivery_Connectivity = view.findViewById(R.id.Delivery_Connectivity);
        Delivery_Empty = view.findViewById(R.id.Delivery_Empty);
    }


    public void getBikeData() {

        try {
            Map<String, String> params = new HashMap<>();
            params.put("OPR", "GETBIKEREFRENCEDRECIEPT");
            params.put("userid", Tools.getInstance(getContext()).read("UserId", ""));
            Log.e("dfd",Tools.getInstance(getContext()).read("UserId", "") + " |" );


            StringRequest loginRequest = new StringRequest(params, 0, new StringRequest.ResponseAction() {
                @Override
                public void onResponseAction(String response) {
                    Log.e("getBikeDataResponse", response);

                    try {
                        JSONObject object = new JSONObject(response);

                        if (object.has("error")) {
                            if (!object.getBoolean("error")) {

                                List<DeliveryModel> DeliveryModels = new ArrayList<>();

                                JSONArray DeliveryArray = object.getJSONArray("msg");

                                for (int i = 0; i < DeliveryArray.length(); i++) {

                                    JSONObject deliveryObject = DeliveryArray.getJSONObject(i);
                                    DeliveryModel deliveryModel = new DeliveryModel();

                                    deliveryModel.setId(deliveryObject.getString("rid"));
                                    deliveryModel.setPayment(deliveryObject.getString("paymentType"));
                                    deliveryModel.setTime((deliveryObject.has("delivrytime")) ?
                                            deliveryObject.getString("delivrytime") : "");
                                    deliveryModel.setAddress(deliveryObject.getString("address"));
                                    deliveryModel.setName(deliveryObject.getString("customername"));
                                    deliveryModel.setTotalOrderPrice(deliveryObject.getString("sumcast"));
                                    deliveryModel.setBikePrice(deliveryObject.has("bikeprice") ?
                                            deliveryObject.getString("bikeprice") : "");
                                    deliveryModel.setPreOrder(deliveryObject.getBoolean("orderType"));

                                    JSONArray PriceArray = deliveryObject.getJSONArray("detail");
                                    List<OrderFruitModel> orderFruitModelList = new ArrayList<>();
                                    for (int j = 0; j < PriceArray.length(); j++) {

                                        JSONObject fruitModel = PriceArray.getJSONObject(j);
                                        OrderFruitModel orderFruitModel = new OrderFruitModel();

                                        orderFruitModel.setPriceId(fruitModel.getString("pfid"));
                                        orderFruitModel.setPrice((fruitModel.has("pprice")) ? fruitModel.getString("pprice") :"1");
                                        orderFruitModel.setDegree(fruitModel.getString("pqg"));
                                        orderFruitModel.setUnit(fruitModel.getString("munit"));
                                        orderFruitModel.setUnitId(fruitModel.getString("muid"));
                                        orderFruitModel.setName(fruitModel.getString("ptitle"));
                                        orderFruitModel.setImage(fruitModel.getString("pimg"));
                                        orderFruitModel.setWeight(fruitModel.getString("pmuch"));
                                        orderFruitModel.setDebtor((fruitModel.has("debtour")) ? fruitModel.getString("debtour") :"1");
                                        orderFruitModel.setOff((fruitModel.has("poff")) ? fruitModel.getString("poff") :"0");

                                        orderFruitModelList.add(orderFruitModel);

                                    }

                                    deliveryModel.setData(orderFruitModelList);

                                    DeliveryModels.add(deliveryModel);
                                }

                                DeliveryAdapter adapter = new DeliveryAdapter(DeliveryModels, getActivity());
                                DeliveryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()
                                        , LinearLayoutManager.VERTICAL, false));
                                DeliveryRecyclerView.setAdapter(adapter);

                                endAnimation();

                            } else {
                                Toast.makeText(getContext(), object.getString("msg"), Toast.LENGTH_SHORT).show();
                                endAnimation();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("getBikeDataLogin", e.toString() + " |");
                        Tools.getInstance(getActivity()).ToastMessage("خطا در اتصال به سرور ، دوباره تلاش کنید.");
                        endAnimation();
                    }
                }

                @Override
                public void onErrorAction(VolleyError error) {
                    Log.e("getBikeDataLogin1", error.toString());
                    Tools.getInstance(getActivity()).ToastMessage("خطا در اتصال به سرور ، دوباره تلاش کنید.");
                    endAnimation();
                }
            });

            AppController.getInstance().addToRequestQueue(loginRequest);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ErrorLogin2", e.toString());
            Tools.getInstance(getActivity()).ToastMessage("خطا در اتصال به سرور ، دوباره تلاش کنید.");
            endAnimation();
        }

    }


    public void startAnimation() {
        inAnimation.setDuration(300);
        Loading.setAnimation(inAnimation);
        DeliveryRecyclerView.setVisibility(View.GONE);
        Loading.setVisibility(View.VISIBLE);
    }

    public void endAnimation() {
        outAnimation.setDuration(300);
        Loading.setAnimation(outAnimation);
        Loading.setVisibility(View.GONE);
        DeliveryRecyclerView.setVisibility(View.VISIBLE);
        DeliveryRefresh.setRefreshing(false);
    }



}
