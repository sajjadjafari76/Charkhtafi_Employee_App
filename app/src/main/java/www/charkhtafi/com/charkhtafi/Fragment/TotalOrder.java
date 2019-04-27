package www.charkhtafi.com.charkhtafi.Fragment;


import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import www.charkhtafi.com.charkhtafi.Adapter.DeliveryAdapter;
import www.charkhtafi.com.charkhtafi.Adapter.OrderAdapter;
import www.charkhtafi.com.charkhtafi.Adapter.TotalOrderAdapter;
import www.charkhtafi.com.charkhtafi.AppController.AppController;
import www.charkhtafi.com.charkhtafi.Functions;
import www.charkhtafi.com.charkhtafi.Model.AllDeliveryModel;
import www.charkhtafi.com.charkhtafi.Model.DeliveryModel;
import www.charkhtafi.com.charkhtafi.Model.OrderFruitModel;
import www.charkhtafi.com.charkhtafi.Model.OrderModel;
import www.charkhtafi.com.charkhtafi.Model.TotalOrderCalModel;
import www.charkhtafi.com.charkhtafi.Model.TotalOrderModel;
import www.charkhtafi.com.charkhtafi.Model.TotalProductOrderModel;
import www.charkhtafi.com.charkhtafi.Network.StringRequest;
import www.charkhtafi.com.charkhtafi.R;
import www.charkhtafi.com.charkhtafi.Utils.Tools;
import www.charkhtafi.com.charkhtafi.Utils.Views.CustomTextView;

public class TotalOrder extends Fragment {

    private View view;
    private RecyclerView TotalOrderRecyclerView;
    private SwipeRefreshLayout Refresh;
    private LinearLayout Loading, TotalOrder_Connectivity, TotalOrder_Empty;
    //    private CardView CardView;
    private AlphaAnimation inAnimation;
    private AlphaAnimation outAnimation;
    private TotalOrderAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_total_order, container, false);
        INI();

        if (Tools.getInstance(getActivity()).isOnline()) {
            TotalOrder_Connectivity.setVisibility(View.GONE);

//            LoadingAsync async = new LoadingAsync();
//            async.execute();
            startAnimation();
            getTotalProducts();

        } else {
            TotalOrder_Connectivity.setVisibility(View.VISIBLE);
            endAnimation();
        }

        Refresh.setOnRefreshListener(() -> {

            if (Tools.getInstance(getActivity()).isOnline()) {
                TotalOrder_Connectivity.setVisibility(View.GONE);

//                    LoadingAsync async = new LoadingAsync();
//                    async.execute();
                TotalOrder_Empty.setVisibility(View.GONE);
                getTotalProducts();

            } else {
                TotalOrder_Connectivity.setVisibility(View.VISIBLE);
                TotalOrder_Empty.setVisibility(View.GONE);
                endAnimation();
            }
        });


        TotalOrderRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int topRowVerticalPosition =
                        (TotalOrderRecyclerView == null || TotalOrderRecyclerView.getChildCount() == 0) ?
                                0 : TotalOrderRecyclerView.getChildAt(0).getTop();
//                Log.e("getTop", TotalOrderRecyclerView.getChildAt(0).getTop()+"");
                Refresh.setEnabled(dx == 0 && topRowVerticalPosition >= 0);
            }
        });

        return view;

    }

    private void INI() {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        inAnimation = new AlphaAnimation(0f, 1f);
        outAnimation = new AlphaAnimation(1f, 0f);
        TotalOrderRecyclerView = view.findViewById(R.id.TotalOrderRecyclerView);
        Refresh = view.findViewById(R.id.TotalOrder_Refresh);
        Loading = view.findViewById(R.id.TotalOrderLoading);
//        CardView = view.findViewById(R.id.TotalOrder_CardView);
        TotalOrder_Connectivity = view.findViewById(R.id.TotalOrder_Connectivity);
        TotalOrder_Empty = view.findViewById(R.id.TotalOrder_Empty);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {

                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    Order Order = new Order();
                    transaction.replace(R.id.MainActivityFrameLayout, Order);
                    transaction.commit();

                    return true;
                }
            }
            return false;
        });

    }


//
//    private class LoadingAsync extends AsyncTask<Void,Void,Void> {
//
//        private AlphaAnimation inAnimation;
//        private AlphaAnimation outAnimation;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            inAnimation = new AlphaAnimation(0f, 1f);
//            inAnimation.setDuration(300);
//            Loading.setAnimation(inAnimation);
//            CardView.setVisibility(View.GONE);
//            Loading.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//
//            try {
////                for (int i = 0; i < 3; i++) {
////                    TimeUnit.SECONDS.sleep(1);
////                }
////
////                getActivity().runOnUiThread(new Runnable() {
////                    @Override
////                    public void run() {
////
////                        TotalOrderAdapter adapter = new TotalOrderAdapter(getdata(), getActivity());
////                        TotalOrderRecyclerView
////                                .setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
////                        TotalOrderRecyclerView.setAdapter(adapter);
////
////                    }
////                });
//
//                Functions.getInstance(getActivity()).TotalOrder(TotalOrder.this);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void v) {
//            super.onPostExecute(v);
//            outAnimation = new AlphaAnimation(1f, 0f);
//            outAnimation.setDuration(300);
//            Loading.setAnimation(outAnimation);
//            Loading.setVisibility(View.GONE);
//            CardView.setVisibility(View.VISIBLE);
//            Refresh.setRefreshing(false);
//        }
//    }


    public void getTotalProducts() {

        try {
            Map<String, String> params = new HashMap<>();
            params.put("OPR", "GETTOTALORDER");
            params.put("userid", Tools.getInstance(getContext()).read("UserId", ""));

            StringRequest loginRequest = new StringRequest(params, 0, new StringRequest.ResponseAction() {
                @Override
                public void onResponseAction(String response) {
                    Log.e("getTotalResponse", response);

                    try {
                        JSONObject object = new JSONObject(response);

                        if (object.has("error")) {
                            if (!object.getBoolean("error")) {

                                JSONObject Array = object.getJSONObject("msg");
                                JSONArray ProductArray = Array.getJSONArray("detail");


                                if (ProductArray.length() == 0) {
                                    Log.e("ddeerr","hhhhhjbjv");

                                    TotalOrder_Empty.setVisibility(View.VISIBLE);
                                    TotalOrderRecyclerView.setVisibility(View.GONE);
                                    Loading.setVisibility(View.GONE);
                                    endAnimation();
                                    TotalOrderRecyclerView.setVisibility(View.GONE); // because recycler visible when refresh after sending it to server

                                }else {

                                    TotalOrderModel totalOrderModelList = new TotalOrderModel();

                                    List<TotalProductOrderModel> TotalProduct = new ArrayList<>();
                                    for (int i = 0; i < ProductArray.length(); i++) {

                                        TotalProductOrderModel orderModel = new TotalProductOrderModel();
                                        JSONObject product = ProductArray.getJSONObject(i);

                                        orderModel.setFactorId(product.getString("r_id"));
                                        orderModel.setFeatureId(product.getString("pf_id"));
                                        orderModel.setProductId(product.getString("p_id"));
                                        orderModel.setUnit(product.getString("mu_title"));
                                        orderModel.setDegree(product.getString("pf_qualitygrade"));
                                        orderModel.setImage(product.getString("p_image"));
                                        orderModel.setName(product.getString("p_title"));
                                        orderModel.setWight(product.getString("Much"));
//                                    orderModel.setPriceType(product.getString(""));
                                        orderModel.setPrice(product.getString("pf_price"));


                                        TotalProduct.add(orderModel);
                                    }
                                    totalOrderModelList.setTotalOrder(TotalProduct);


                                    JSONArray BikeArray = Array.getJSONArray("bike");
                                    List<AllDeliveryModel> bikeList = new ArrayList<>();
                                    for (int i = 0; i < BikeArray.length(); i++) {

                                        AllDeliveryModel orderModel = new AllDeliveryModel();
                                        JSONObject product = BikeArray.getJSONObject(i);

                                        orderModel.setId(product.getString("bid"));
                                        orderModel.setName(product.getString("btitle"));

                                        bikeList.add(orderModel);
                                    }
                                    totalOrderModelList.setAllBuyer(bikeList);


                                    Log.e("ddd", totalOrderModelList.getTotalOrder() + " |");
                                    adapter = new TotalOrderAdapter(totalOrderModelList, getActivity(), TotalOrderRecyclerView);
                                    TotalOrderRecyclerView
                                            .setLayoutManager(new GridLayoutManager(getContext(), 1, LinearLayoutManager.VERTICAL, false));
                                    TotalOrderRecyclerView.setAdapter(adapter);

                                    endAnimation();
                                }

                            } else {
                                Toast.makeText(getContext(), object.getString("msg"), Toast.LENGTH_SHORT).show();
                                endAnimation();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("ErrorgetTotal", e.toString() + " |");
                        Tools.getInstance(getActivity()).ToastMessage("خطا در اتصال به سرور ، دوباره تلاش کنید.");
                        endAnimation();
                    }
                }

                @Override
                public void onErrorAction(VolleyError error) {
                    Log.e("ErrorgetTotal2", error.toString());
                    Tools.getInstance(getActivity()).ToastMessage("خطا در اتصال به سرور ، دوباره تلاش کنید.");
                    endAnimation();
                    TotalOrder_Empty.setVisibility(View.GONE);
                }
            });

            AppController.getInstance().addToRequestQueue(loginRequest);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ErrorgetgetTotal3", e.toString());
            Tools.getInstance(getActivity()).ToastMessage("خطا در اتصال به سرور ، دوباره تلاش کنید.");
            endAnimation();
            TotalOrder_Empty.setVisibility(View.GONE);
        }

    }

    public void startAnimation() {
        inAnimation.setDuration(300);
        Loading.setAnimation(inAnimation);
        TotalOrderRecyclerView.setVisibility(View.GONE);
        Loading.setVisibility(View.VISIBLE);
    }

    public void endAnimation() {
        outAnimation.setDuration(300);
        Loading.setAnimation(outAnimation);
        Loading.setVisibility(View.GONE);
        TotalOrderRecyclerView.setVisibility(View.VISIBLE);
        Refresh.setRefreshing(false);
    }

}
