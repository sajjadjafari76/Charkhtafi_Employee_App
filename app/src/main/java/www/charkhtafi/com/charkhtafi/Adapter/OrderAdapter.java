package www.charkhtafi.com.charkhtafi.Adapter;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import www.charkhtafi.com.charkhtafi.AppController.AppController;
import www.charkhtafi.com.charkhtafi.Model.AllDeliveryModel;
import www.charkhtafi.com.charkhtafi.Model.OrderFruitModel;
import www.charkhtafi.com.charkhtafi.Model.OrderModel;
import www.charkhtafi.com.charkhtafi.Model.TotalOrderModel;
import www.charkhtafi.com.charkhtafi.Network.StringRequest;
import www.charkhtafi.com.charkhtafi.R;
import www.charkhtafi.com.charkhtafi.Utils.Tools;
import www.charkhtafi.com.charkhtafi.Utils.Views.CustomEdittext;
import www.charkhtafi.com.charkhtafi.Utils.Views.CustomTextView;


public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements AllDeliveryAdapter.AllSuccess {

    private List<OrderModel> data;
    private Activity context;
    private AlertDialog progress;
    private OrderFruitAdapter adapter;

    //    private static final int FOOTER = 0;
    private static final int DEFAULT = 1;

    public OrderAdapter(List<OrderModel> datas, Activity context) {
        this.context = context;
        this.data = datas;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position != data.size()) {
            return DEFAULT;
        } else {
            return DEFAULT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case DEFAULT:
                return new myCustomViewHolder(LayoutInflater.from(context)
                        .inflate(R.layout.custom_order, parent, false));
//            case FOOTER:
//            return new myCustomBtn(LayoutInflater.from(context)
//                    .inflate(R.layout.custom_totalorder_btn, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        try {
            adapter = new OrderFruitAdapter(data.get(position).getFruitModel(), context);
            ((myCustomViewHolder) holder).recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            ((myCustomViewHolder) holder).recyclerView.setAdapter(adapter);

            if (data.get(position).isPreOrder()) {
                ((myCustomViewHolder) holder).PreOrder.setVisibility(View.VISIBLE);
            }else {
                ((myCustomViewHolder) holder).PreOrder.setVisibility(View.GONE);
            }

            Log.e("logforcheck", data.get(position).getDescription() + " | " + position);

            if (data.get(position).getDescription() != null) {
                ((myCustomViewHolder) holder).Description.setText(" توضیحات : ".concat(data.get(position).getDescription()));
            } else {
                ((myCustomViewHolder) holder).Description.setText(" توضیحات : توضیحی وجود ندارد");
            }
            ((myCustomViewHolder) holder).CustomOrder_Factor.setText("شماره فاکتور : ".concat(data.get(position).getId()));

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("orderAdapter", e.toString() + " |");
        }

        ((myCustomViewHolder) holder).Send.setOnClickListener(v -> {

            JSONObject All = new JSONObject();

            try {
                All.put("FactorID", data.get(position).getId());


                JSONArray array = new JSONArray();
                double sum = 0;

                for (int i = 0; i < data.get(position).getFruitModel().size(); i++) {

                    try {
                        JSONObject object = new JSONObject();

                        object.put("OldPrice", data.get(position).getFruitModel().get(i).getPrice());
                        object.put("NewPrice",
                                (data.get(position).getFruitModel().get(i).getNewPrice() == null) ?
                                        data.get(position).getFruitModel().get(i).getPrice() :
                                        (!data.get(position).getFruitModel().get(i).getNewPrice().isEmpty()) ? data.get(position).getFruitModel().get(i).getNewPrice() :
                                                data.get(position).getFruitModel().get(i).getPrice()
                        );


                        object.put("OldWeight", data.get(position).getFruitModel().get(i).getWeight());
                        object.put("NewWeight", (data.get(position).getFruitModel().get(i).getNewWeight() == null) ?
                                data.get(position).getFruitModel().get(i).getWeight() :
                                (!data.get(position).getFruitModel().get(i).getNewWeight().isEmpty()) ? data.get(position).getFruitModel().get(i).getNewWeight() :
                                        data.get(position).getFruitModel().get(i).getWeight()
                        );

                        Log.e("dfhdf45454dghdhf", Integer.parseInt(object.getString("NewPrice")) + " | " + Float.parseFloat(object.getString("NewWeight")));
                        sum = sum + (Integer.parseInt(object.getString("NewPrice")) * Float.parseFloat(object.getString("NewWeight")));


                        object.put("PriceId", data.get(position).getFruitModel().get(i).getPriceId());
                        object.put("ProductId", data.get(position).getFruitModel().get(i).getFruitId());

                        array.put(object);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("OrderAdapterError", e.toString());
                    }
                }

                if (data.get(position).getDeliveryPrice().matches("\\d+(?:\\.\\d+)?")) {
                    All.put("Total", String.valueOf((((int) sum) + Integer.parseInt(data.get(position).getDeliveryPrice()))));
                } else {
                    All.put("Total", String.valueOf((int) sum));
                }

                All.put("ProductInfo", array);

                Log.e("dddddddddddddd", data.get(position).getTotalPrice()  + " |");
                ShowDialogDelivery(position, data, All, data.get(position).getTotalPrice());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        ((myCustomViewHolder) holder).No.setOnClickListener(v -> {
            sendActivatedFactor(data.get(position).getId(), "false", data.get(position).getNameUser(), ((myCustomViewHolder) holder), position);
        });

        ((myCustomViewHolder) holder).Yes.setOnClickListener(v -> {
            sendActivatedFactor(data.get(position).getId(), "true", data.get(position).getNameUser(), ((myCustomViewHolder) holder), position);
        });


        if (data.get(position).isActive()) {
            ((myCustomViewHolder) holder).View1.setVisibility(View.VISIBLE);
            ((myCustomViewHolder) holder).Active.setVisibility(View.GONE);
        } else {
            ((myCustomViewHolder) holder).View1.setVisibility(View.GONE);
            ((myCustomViewHolder) holder).Active.setVisibility(View.VISIBLE);
        }

    }

    ///// about delete row of recyclerView
    @Override
    public void success(String s) {
        removeAt(Integer.parseInt(s));
    }


    public class myCustomViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView recyclerView;
        private ImageView CustomOrder_Add;
        private CustomTextView Send, Description, CustomOrder_Factor;
        private TextView Yes, No;
        private RelativeLayout View1, Active;
        private ImageView PreOrder;

        public myCustomViewHolder(View itemView) {
            super(itemView);

            recyclerView = itemView.findViewById(R.id.CustomOrderRecycler);
//            CustomOrder_Add = itemView.findViewById(R.id.CustomOrder_Send);
            Send = itemView.findViewById(R.id.CustomOrder_Send);
            Description = itemView.findViewById(R.id.CustomOrderDescription);
            CustomOrder_Factor = itemView.findViewById(R.id.CustomOrder_Factor);
            Yes = itemView.findViewById(R.id.Order_Yes);
            No = itemView.findViewById(R.id.Order_No);
            View1 = itemView.findViewById(R.id.CustomOrder_View22);
            Active = itemView.findViewById(R.id.CustomOrder_Active);
            PreOrder = itemView.findViewById(R.id.CustomOrder_PreOrderImage);
        }
    }

    public class myCustomBtn extends RecyclerView.ViewHolder {

        private CustomTextView btn;

        public myCustomBtn(View itemView) {
            super(itemView);

            btn = itemView.findViewById(R.id.Custom_TotalOrder_Btn);


        }
    }


    private void ShowDialog(final int position) {

        try {
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setView(R.layout.custom_addfruit);

            progress = dialog.create();
            progress.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            LayoutInflater inflater = context.getLayoutInflater();
            progress.setContentView(inflater.inflate(R.layout.custom_addfruit, null));
            progress.setCancelable(false);
            progress.show();

            CardView Add = progress.findViewById(R.id.CustomAddFruit_Add);
            if (Add != null) {
                Add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        OrderFruitModel model1 = new OrderFruitModel();
                        model1.setImage("https://onehdwallpaper.com/wp-content/uploads/2016/05/Full-HD-Banana-Fruit-HD-Photos.jpg");
                        model1.setPrice("4000");
                        model1.setTotalWeight("3");
                        model1.setName("موز");
                        model1.setFruitId("125874");
                        model1.setWeight("3");

                        data.get(position).getFruitModel().add(model1);
                        adapter.notifyDataSetChanged();

//                        Log.e("data", data1.toString());
//                        data1.add(position,model1);
//                        adapter.notifyDataSetChanged();
//                        Log.e("data",data1.get(position).getName());

//                        data1.set(position,model1);
//                        adapter.notifyDataSetChanged();
//                        adapter.addAt(position,model1);
//                        adapter.addAt(model1);
//                        data1.add(model);
//                        adapter.notifyItemInserted(position);
//                        adapter.notifyItemChanged(position);
//                        adapter.notifyDataSetChanged();
                        progress.dismiss();
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void ShowDialogDelivery(final int position, List<OrderModel> models, JSONObject object, String TotlaPrice) {

        try {
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setView(R.layout.custom_all_delivery);

            progress = dialog.create();
//            progress.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            LayoutInflater inflater = context.getLayoutInflater();
            progress.setContentView(inflater.inflate(R.layout.custom_all_delivery, null));
            progress.setCancelable(false);
            progress.show();

            RecyclerView Add = progress.findViewById(R.id.CustomAllDelivery);
            ImageView cancel = progress.findViewById(R.id.CustomAllDeliveryCancel);
            if (Add != null && cancel != null) {
                AllDeliveryAdapter allDeliveryAdapter = new AllDeliveryAdapter(models, context, position, object, progress, this, TotlaPrice);
                Add.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                Add.setAdapter(allDeliveryAdapter);

                cancel.setOnClickListener(v -> progress.dismiss());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeAt(int position) {
        data.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }


    private void sendActivatedFactor(String FactorId, String Status, String Name, myCustomViewHolder holder, int position) {

        try {
            Map<String, String> params = new HashMap<>();
            params.put("OPR", "ASSENTRECIEPT");
            params.put("userid", Tools.getInstance(context).read("UserId", ""));
            params.put("rid", FactorId);
            params.put("status", Status);
            params.put("uname", Name);

            StringRequest loginRequest = new StringRequest(params, 0, new StringRequest.ResponseAction() {
                @Override
                public void onResponseAction(String response) {
                    Log.e("ActivedResponse", response);

                    try {
                        JSONObject object = new JSONObject(response);

                        if (object.has("error")) {
                            if (!object.getBoolean("error")) {

                                Toast.makeText(context, object.getString("msg"), Toast.LENGTH_SHORT).show();

                                if (Status.equals("true")) {

                                    holder.View1.setVisibility(View.VISIBLE);
                                    holder.Active.setVisibility(View.GONE);

                                } else {
                                    data.remove(position);
                                    notifyDataSetChanged();
                                }

                            } else {
                                Toast.makeText(context, object.getString("msg"), Toast.LENGTH_SHORT).show();
//                                endAnimation();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("ErrorgetFactor", e.toString() + " |");
                        Tools.getInstance(context).ToastMessage("خطا در اتصال به سرور ، دوباره تلاش کنید.");
//                        endAnimation();
                    }
                }

                @Override
                public void onErrorAction(VolleyError error) {
                    Log.e("ErrorgetFactor1", error.toString());
                    Tools.getInstance(context).ToastMessage("خطا در اتصال به سرور ، دوباره تلاش کنید.");
//                    endAnimation();
                }
            });

            AppController.getInstance().addToRequestQueue(loginRequest);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ErrorgetFactor2", e.toString());
            Tools.getInstance(context).ToastMessage("خطا در اتصال به سرور ، دوباره تلاش کنید.");
//            endAnimation();
        }

    }

}
