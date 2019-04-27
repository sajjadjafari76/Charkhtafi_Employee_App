package www.charkhtafi.com.charkhtafi.Adapter;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.charkhtafi.com.charkhtafi.AppController.AppController;
import www.charkhtafi.com.charkhtafi.Model.AllDeliveryModel;
import www.charkhtafi.com.charkhtafi.Model.TotalOrderModel;
import www.charkhtafi.com.charkhtafi.Model.TotalProductOrderModel;
import www.charkhtafi.com.charkhtafi.Network.StringRequest;
import www.charkhtafi.com.charkhtafi.R;
import www.charkhtafi.com.charkhtafi.Utils.Tools;
import www.charkhtafi.com.charkhtafi.Utils.Views.CFProvider;
import www.charkhtafi.com.charkhtafi.Utils.Views.CustomButton;
import www.charkhtafi.com.charkhtafi.Utils.Views.CustomTextView;

public class AllDeliveryForTotalAdapter extends RecyclerView.Adapter<AllDeliveryForTotalAdapter.myViewHolder>{

    private List<AllDeliveryModel> data;
    private List<TotalProductOrderModel> TotalOrder;
    private Activity context;
    private AlertDialog progress;
    private AlertDialog alertDialog;
    private RecyclerView recyclerView;
    private TotalOrderModel PreviousData;

    public AllDeliveryForTotalAdapter(List<AllDeliveryModel> datas, Activity context, List<TotalProductOrderModel> TotalOrder,
                                      AlertDialog alertDialog, RecyclerView TotalRecyclerView, TotalOrderModel PreviousData) {
        this.data = datas;
        this.context = context;
        this.TotalOrder = TotalOrder;
        recyclerView = TotalRecyclerView;
        this.alertDialog = alertDialog;
        this.PreviousData = PreviousData;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new myViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.custom_all_delivery_name, parent, false));
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, final int position) {

        AllDeliveryModel model = data.get(position);

        holder.Name.setText(" آقای " + model.getName());

        holder.CustomAllDeliveryDone.setOnClickListener(v -> {
            try {

                JSONObject All = new JSONObject();

                JSONArray Product = new JSONArray();
                for (int i = 0; i < TotalOrder.size(); i++) {
                    JSONObject myProduct = new JSONObject();

                    myProduct.put("FactorId", TotalOrder.get(i).getFactorId());
                    myProduct.put("ProductId", TotalOrder.get(i).getProductId());
                    myProduct.put("FeatureId", TotalOrder.get(i).getFeatureId());
                    myProduct.put("OldPrice", TotalOrder.get(i).getPrice());
                    myProduct.put("NewPrice",
                            (TotalOrder.get(i).getNewPrice() != null) ? TotalOrder.get(i).getNewPrice() : TotalOrder.get(i).getPrice());

                    Product.put(myProduct);
                }
                All.put("Product", Product);
                All.put("BikeId", model.getId());

                updateFactor(All);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class myViewHolder extends RecyclerView.ViewHolder {

        private CustomTextView Name;
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

            CustomButton Yes =  progress.findViewById(R.id.CustomPermission_Yes);
            CustomButton Cancel =  progress.findViewById(R.id.CustomPermission_Cancel);
            CustomTextView Content = progress.findViewById(R.id.CustomPermission_Content);
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void updateFactor(JSONObject object) {

        try {
            Map<String, String> params = new HashMap<>();

            object.put("userid",Tools.getInstance(context).read("UserId",""));

            params.put("OPR", "UPDATEPRICEOFGROUP");
            params.put("Data", object.toString());

            StringRequest loginRequest = new StringRequest(params, 0, new StringRequest.ResponseAction() {
                @Override
                public void onResponseAction(String response) {
                    Log.e("TotalUpdateFactorRes", response);

                    try {
                        JSONObject object = new JSONObject(response);

                        if (object.has("error")) {
                            if (!object.getBoolean("error")) {

                                Toast.makeText(context, object.getString("msg"), Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                                recyclerView.setVisibility(View.GONE);
                                PreviousData.getTotalOrder().clear(); PreviousData.getAllBuyer().clear();

                            } else {
                                Toast.makeText(context, object.getString("msg"), Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("TotalupdateFactorError", e.toString() + " |");
                        Tools.getInstance(context).ToastMessage("خطا در اتصال به سرور ، دوباره تلاش کنید.");
//                        endAnimation();
                    }
                }

                @Override
                public void onErrorAction(VolleyError error) {
                    Log.e("TotalUpdateFactorError1", error.toString());
                    Tools.getInstance(context).ToastMessage("خطا در اتصال به سرور ، دوباره تلاش کنید.");
//                    endAnimation();
                }
            });

            AppController.getInstance().addToRequestQueue(loginRequest);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("TotalUpdateFactorError2", e.toString());
            Tools.getInstance(context).ToastMessage("خطا در اتصال به سرور ، دوباره تلاش کنید.");
//            endAnimation();
        }
    }

}
