package www.charkhtafi.com.charkhtafi.Adapter;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import www.charkhtafi.com.charkhtafi.AppController.AppController;
import www.charkhtafi.com.charkhtafi.Fragment.Order;
import www.charkhtafi.com.charkhtafi.Functions;
import www.charkhtafi.com.charkhtafi.Model.AllDeliveryModel;
import www.charkhtafi.com.charkhtafi.Model.OrderFruitModel;
import www.charkhtafi.com.charkhtafi.Model.OrderModel;
import www.charkhtafi.com.charkhtafi.Network.StringRequest;
import www.charkhtafi.com.charkhtafi.R;
import www.charkhtafi.com.charkhtafi.Utils.Tools;
import www.charkhtafi.com.charkhtafi.Utils.Views.CFProvider;
import www.charkhtafi.com.charkhtafi.Utils.Views.CustomButton;
import www.charkhtafi.com.charkhtafi.Utils.Views.CustomTextView;


public class AllDeliveryAdapter extends RecyclerView.Adapter<AllDeliveryAdapter.myViewHolder> implements Functions.All {

    private List<OrderModel> data;
    private OrderAdapter orderAdapter;
    private Activity context;
    private AlertDialog progress;
    private int myPosition;
    private JSONArray alldata;
    private String OrderId;
    private AlertDialog dialog;
    private String total;
    private JSONObject jsonObject;
    private String TotalPrice;

    public AllDeliveryAdapter(List<OrderModel> datas, Activity context, int position, JSONObject object,
                              AlertDialog progress, OrderAdapter orderAdapter, String TotalPrice) {
        this.data = datas;
        this.context = context;
        this.myPosition = position;
//        this.alldata = alldata;
//        this.OrderId = OrderId;
        this.dialog = progress;
//        this.total = total;
        jsonObject = object;
        this.orderAdapter = orderAdapter;
        this.TotalPrice = TotalPrice;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new myViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.custom_all_delivery_name,parent,false));
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, final int position) {

        AllDeliveryModel model = data.get(0).getAllDelivery().get(position);

        holder.Name.setText(" آقای " + model.getName());

        holder.CustomAllDeliveryDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Functions.getInstance(context).SendToDelivery(model.getId(),
//                        AllDeliveryAdapter.this, alldata, OrderId, total);

                updateFactor(jsonObject, position);


            }
        });
    }

    @Override
    public int getItemCount() {
        return data.get(0).getAllDelivery().size();
    }

    @Override
    public void Success(String text) {
        dialog.dismiss();
        Tools.getInstance(context).ToastMessage("سفارش با موفقیت ارسال شد!");
    }

    @Override
    public void Failed(String error) {
        Tools.getInstance(context).ToastMessage("خطا در اتصال به سرور ، دوباره تلاش کنید.");
    }

    public interface AllSuccess{
        void success(String s);
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        private CustomTextView  Name;
        private RelativeLayout CustomAllDeliveryDone;

        myViewHolder(View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.CustomAllDeliveryName);
            CustomAllDeliveryDone = itemView.findViewById(R.id.CustomAllDeliveryDone);
        }

    }


    private void ShowDialog(final int position) {

        try {
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setView(R.layout.exit_permission);

            progress = dialog.create();
            progress.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            LayoutInflater inflater = context.getLayoutInflater();
            progress.setContentView(inflater.inflate(R.layout.exit_permission, null));
            progress.setCancelable(false);
            progress.show();

            CustomButton Yes = (CustomButton) progress.findViewById(R.id.CustomPermission_Yes);
            CustomButton Cancel = (CustomButton) progress.findViewById(R.id.CustomPermission_Cancel);
            CustomTextView Content =  progress.findViewById(R.id.CustomPermission_Content);
            Content.setText("آیا مایل به حذف این میوه از فاکتور هستید؟");
            if (Yes != null && Cancel != null) {
                Yes.setTypeface(CFProvider.getIRANIANSANS(context));
                Cancel.setTypeface(CFProvider.getIRANIANSANS(context));

                Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progress.dismiss();

                    }
                });

                Yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        removeAt(position);
                        progress.dismiss();
                    }
                });
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void removeAt(int position) {
        data.remove(position);
        orderAdapter.notifyItemRemoved(position);
        orderAdapter.notifyDataSetChanged();
    }

//    public void addAt( AllDeliveryModel model) {
//        data.add(model);
//        notifyDataSetChanged();
//    }


    public void updateFactor(JSONObject object, int bikePosition) {

//        Log.e("dateee", data.size() + " | " + bikePosition + " | " + myPosition);

        Log.e("totootototo", object.toString() + " |");
        try {
            Map<String, String> params = new HashMap<>();
            params.put("OPR", "UPDATEPRODUCTS");
            params.put("Data", object.toString());
            params.put("BikeId", data.get(0).getAllDelivery().get(bikePosition).getId());
            params.put("Total", TotalPrice);

            StringRequest loginRequest = new StringRequest(params, 0, new StringRequest.ResponseAction() {
                @Override
                public void onResponseAction(String response) {
                    Log.e("updateFactorResponse", response);

                    try {
                        JSONObject object = new JSONObject(response);

                        if (object.has("error")) {
                            if (!object.getBoolean("error")) {

                                Toast.makeText(context, object.getString("msg"), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                removeAt(myPosition);

                            } else {
                                Toast.makeText(context, object.getString("msg"), Toast.LENGTH_SHORT).show();
//                                endAnimation();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("updateFactorFactor", e.toString() + " |");
                        Tools.getInstance(context).ToastMessage("خطا در اتصال به سرور ، دوباره تلاش کنید.");
//                        endAnimation();
                    }
                }

                @Override
                public void onErrorAction(VolleyError error) {
                    Log.e("updateFactorFactor1", error.toString());
                    Tools.getInstance(context).ToastMessage("خطا در اتصال به سرور ، دوباره تلاش کنید.");
//                    endAnimation();
                }
            });

            AppController.getInstance().addToRequestQueue(loginRequest);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("updateFactorFactor2", e.toString());
            Tools.getInstance(context).ToastMessage("خطا در اتصال به سرور ، دوباره تلاش کنید.");
//            endAnimation();
        }

    }


}
