package ir.stit.ir.charkhtafi.Adapter;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.stit.ir.charkhtafi.AppController.AppController;
import ir.stit.ir.charkhtafi.Functions;
import ir.stit.ir.charkhtafi.Model.AllDeliveryModel;
import ir.stit.ir.charkhtafi.Model.OrderModel;
import ir.stit.ir.charkhtafi.Network.StringRequest;
import ir.stit.ir.charkhtafi.R;
import ir.stit.ir.charkhtafi.Utils.Tools;
import ir.stit.ir.charkhtafi.Utils.Views.CFProvider;
import ir.stit.ir.charkhtafi.Utils.Views.CustomButton;
import ir.stit.ir.charkhtafi.Utils.Views.CustomTextView;


public class AllDeliveryAdapter extends RecyclerView.Adapter<AllDeliveryAdapter.myViewHolder> {

    private List<OrderModel> data;
    private OrderAdapter orderAdapter;
    private Activity context;
    private AlertDialog progress;
    private int myPosition;
    private AlertDialog dialog;
    private JSONObject jsonObject;
    private String TotalPrice;

    // for wallet
    private int WalletStatus = 0, CreditorState = 0;
    private double NewWallet, lastUpdateWallet;


    public AllDeliveryAdapter(List<OrderModel> datas, Activity context, int position, JSONObject object,
                              AlertDialog progress, OrderAdapter orderAdapter, String TotalPrice, int WalletStatus,
                              int CreditorState, double NewWallet, double lastUpdateWallet) {
        this.data = datas;
        this.context = context;
        this.myPosition = position;
        this.dialog = progress;
        jsonObject = object;
        this.orderAdapter = orderAdapter;
        this.TotalPrice = TotalPrice;

        // for wallet
        this.WalletStatus = WalletStatus;
        this.CreditorState = CreditorState;
        this.NewWallet = NewWallet;
        this.lastUpdateWallet = lastUpdateWallet;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new myViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.custom_all_delivery_name, parent, false));
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, final int position) {

        AllDeliveryModel model = data.get(0).getAllDelivery().get(position);

        holder.Name.setText(" آقای " + model.getName());

        holder.CustomAllDeliveryDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.Progress.setVisibility(View.VISIBLE);
                holder.Image.setVisibility(View.GONE);

                updateFactor(jsonObject, position, holder);

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.get(0).getAllDelivery().size();
    }


    class myViewHolder extends RecyclerView.ViewHolder {

        private CustomTextView Name;
        private RelativeLayout CustomAllDeliveryDone;
        private ProgressBar Progress;
        private ImageView Image;

        myViewHolder(View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.CustomAllDeliveryName);
            CustomAllDeliveryDone = itemView.findViewById(R.id.CustomAllDeliveryDone);
            Progress = itemView.findViewById(R.id.CustomAllDeliveryName_Progress);
            Image = itemView.findViewById(R.id.CustomAllDeliveryName_Image);
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


    public void removeAt(int position) {
        data.remove(position);
        orderAdapter.notifyItemRemoved(position);
        orderAdapter.notifyDataSetChanged();
    }

//    public void addAt( AllDeliveryModel model) {
//        data.add(model);
//        notifyDataSetChanged();
//    }

    public void updateFactor(JSONObject object, int bikePosition, myViewHolder holder) {

//        Log.e("dateee", data.size() + " | " + bikePosition + " | " + myPosition);

        Log.e("totootototo", object.toString() + " |");
        try {
            Map<String, String> params = new HashMap<>();
            params.put("OPR", "UPDATEPRODUCTS");
            params.put("Data", object.toString());
            params.put("BikeId", data.get(0).getAllDelivery().get(bikePosition).getId());
            Log.e("TotalLast", TotalPrice + " |");
            params.put("Total", TotalPrice);

//            params.put("OldWallet", String.valueOf((int)data.get(myPosition).getWallet()));
//            params.put("NewWallet", String.valueOf((int) lastUpdateWallet));


//            params.put("walletStatus", String.valueOf(WalletStatus));
//            if (WalletStatus == 0) { // nothing
////                params.put("payType", Payment); // 0-> offline  1-> online
//            } else if (WalletStatus < 0) { // debtor
////                params.put("payType", Payment); // 0-> offline  1-> online

                //params.put("amountOfDebt", String.valueOf((int) Math.abs(lastUpdateWallet)));

//            } else if (WalletStatus > 0) { // creditor
//
//                params.put("CreditorStatus", String.valueOf(CreditorState));

//                params.put("amountOfDebt", String.valueOf((int) Math.abs(lastUpdateWallet)));


//                params.put("payType", Payment); // 2-> online/wallet  3-> local/wallet  4-> wallet

//                if (CreditorState == 1) { //1-> sum<creditor
//                    params.put("newAmountOfDebt", String.valueOf((int)Math.abs(NewWallet)));
//                }
//            }

            Log.e("TotlaLast1212", params.toString() + " |");

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
                                holder.Progress.setVisibility(View.GONE);
                                holder.Image.setVisibility(View.VISIBLE);

                            } else {
                                Toast.makeText(context, object.getString("msg"), Toast.LENGTH_SHORT).show();
                                holder.Progress.setVisibility(View.GONE);
                                holder.Image.setVisibility(View.VISIBLE);
//                                endAnimation();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("updateFactorFactor", e.toString() + " |");
                        Tools.getInstance(context).ToastMessage("خطا در اتصال به سرور ، دوباره تلاش کنید.");
                        holder.Progress.setVisibility(View.GONE);
                        holder.Image.setVisibility(View.VISIBLE);
//                        endAnimation();
                    }
                }

                @Override
                public void onErrorAction(VolleyError error) {
                    Log.e("updateFactorFactor1", error.toString());
                    Tools.getInstance(context).ToastMessage("خطا در اتصال به سرور ، دوباره تلاش کنید.");
                    holder.Progress.setVisibility(View.GONE);
                    holder.Image.setVisibility(View.VISIBLE);
//                    endAnimation();
                }
            });

            AppController.getInstance().addToRequestQueue(loginRequest);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("updateFactorFactor2", e.toString());
            Tools.getInstance(context).ToastMessage("خطا در اتصال به سرور ، دوباره تلاش کنید.");
            holder.Progress.setVisibility(View.GONE);
            holder.Image.setVisibility(View.VISIBLE);
//            endAnimation();
        }

    }


}
