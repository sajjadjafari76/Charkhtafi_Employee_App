package ir.stit.ir.charkhtafi.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.stit.ir.charkhtafi.Adapter.EditPriceAdapter;
import ir.stit.ir.charkhtafi.AppController.AppController;
import ir.stit.ir.charkhtafi.Model.EditPriceModel;
import ir.stit.ir.charkhtafi.Network.StringRequest;
import ir.stit.ir.charkhtafi.R;
import ir.stit.ir.charkhtafi.Utils.Tools;
import ir.stit.ir.charkhtafi.Utils.Views.CFProvider;


public class Edit_Price extends Fragment {

    private View view;
    private RecyclerView TotalOrderRecyclerView;
    private LinearLayout Loading;
    private SwipeRefreshLayout Refresh;
    private LinearLayout Connectivity, EditPrice_Empty;
    private Button Edit_Price_Btn;
    private AlphaAnimation inAnimation;
    private AlphaAnimation outAnimation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit__price, container, false);
        INI();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        inAnimation = new AlphaAnimation(0f, 1f);
        outAnimation = new AlphaAnimation(1f, 0f);

        if (Tools.getInstance(getActivity()).isOnline()) {
            startAnimation();
            getProduct();

        } else {
            Connectivity.setVisibility(View.VISIBLE);
            endAnimation();
        }


        Refresh.setOnRefreshListener(() -> {
            if (Tools.getInstance(getActivity()).isOnline()) {
                EditPrice_Empty.setVisibility(View.GONE);
                startAnimation();
                getProduct();
            } else {
                Connectivity.setVisibility(View.VISIBLE);
                endAnimation();
            }
        });

        Edit_Price_Btn.setOnClickListener(v -> {
            if (Tools.getInstance(getActivity()).isOnline()) {
                startAnimation();
                getProduct();
            } else {
                Connectivity.setVisibility(View.VISIBLE);
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
        TotalOrderRecyclerView = view.findViewById(R.id.EditPriceRecyclerView);
        Loading = view.findViewById(R.id.EditPriceLoading);
        Refresh = view.findViewById(R.id.EditPriceRefresh);
        Connectivity = view.findViewById(R.id.Edit_Price_Connectivity);
        Edit_Price_Btn = view.findViewById(R.id.Edit_Price_Btn);
        EditPrice_Empty = view.findViewById(R.id.EditPrice_Empty);
        Edit_Price_Btn.setTypeface(CFProvider.getIRANIANSANS(getActivity()));
    }

    private List<EditPriceModel> getData() {
        List<EditPriceModel> data = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            if (i == 0) {
                EditPriceModel model = new EditPriceModel();
                model.setId("1432");
                model.setImage("https://onehdwallpaper.charkhtafi/wp-content/uploads/2016/05/Full-HD-Banana-Fruit-HD-Photos.jpg");
                model.setName("موز");
                model.setPrice("4000");
                model.setType("کیلویی");
                data.add(model);
            } else if (i == 1) {
                EditPriceModel model = new EditPriceModel();
                model.setId("365481");
                model.setImage("http://charkhtafi.golsamsepahan.charkhtafi/wp-content/uploads/2017/02/potato_png2391.png");
                model.setName("سیب زمینی روز");
                model.setPrice("20000");
                model.setType("جعبه ایی");
                data.add(model);
            } else if (i == 2) {
                EditPriceModel model = new EditPriceModel();
                model.setId("785584");
                model.setImage("https://charkhtafi.parsegard.charkhtafi/sites/default/files/content_types/food_nutrition/9571/main_image/pineapple.jpg");
                model.setName("آناناس");
                model.setPrice("15000");
                model.setType("دونه ایی");
                data.add(model);
            } else if (i == 3) {
                EditPriceModel model = new EditPriceModel();
                model.setId("454");
                model.setImage("http://salamatezanan.charkhtafi/wp-content/uploads/peach-950x950.png");
                model.setName("هلو");
                model.setPrice("4000");
                model.setType("کیلویی");
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


                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        Order Order = new Order();
                        transaction.replace(R.id.MainActivityFrameLayout, Order);
                        transaction.commit();

                        return true;
                    }
                }
                return false;
            }
        });
    }

    public void getProduct() {

        try {
            Map<String, String> params = new HashMap<>();
            params.put("OPR", "GETPRODUCT");
            params.put("uid", Tools.getInstance(getContext()).read("UserId", ""));

            StringRequest loginRequest = new StringRequest(params, 0, new StringRequest.ResponseAction() {
                @Override
                public void onResponseAction(String response) {
                    Log.e("getProductResponse", response);

                    try {
                        JSONObject object = new JSONObject(response);

                        if (object.has("error")) {
                            if (!object.getBoolean("error")) {

                                JSONArray arrayMsg = object.getJSONArray("msg");
                                List<EditPriceModel> listProduct = new ArrayList<>();

                                if (arrayMsg.length() == 0) { // when do not any product

                                    Connectivity.setVisibility(View.GONE);
                                    EditPrice_Empty.setVisibility(View.VISIBLE);
                                    TotalOrderRecyclerView.setVisibility(View.GONE);
                                    endAnimation();

                                    return;
                                }

                                for (int i = 0; i < arrayMsg.length(); i++) {
                                    JSONObject product = arrayMsg.getJSONObject(i);

                                    JSONArray PriceArray = product.getJSONArray("features");

                                    for (int j = 0; j < PriceArray.length(); j++) {
                                        JSONObject PriceObject = PriceArray.getJSONObject(j);
                                        EditPriceModel model = new EditPriceModel();
                                        model.setProduct_Id(product.getString("pid"));
                                        model.setName(product.getString("ptitle"));
                                        model.setImage(product.getString("pimage"));
                                        model.setId(PriceObject.getString("featid"));
                                        model.setType(PriceObject.getString("qg"));
                                        model.setPrice(PriceObject.getString("price"));

                                        listProduct.add(model);
                                    }
                                }

                                EditPriceAdapter adapter = new EditPriceAdapter(listProduct, getActivity());
                                TotalOrderRecyclerView
                                        .setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                                TotalOrderRecyclerView.setAdapter(adapter);

                                endAnimation();

                            } else {
                                Toast.makeText(getContext(), object.getString("msg"), Toast.LENGTH_SHORT).show();
                                endAnimation();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("ErrorLogin", e.toString() + " |");
                        Tools.getInstance(getActivity()).ToastMessage("خطا در اتصال به سرور ، دوباره تلاش کنید.");
                        endAnimation();
                    }
                }

                @Override
                public void onErrorAction(VolleyError error) {
                    Log.e("ErrorLogin1", error.toString());
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
