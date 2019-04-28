package ir.stit.ir.charkhtafi.Adapter;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.HashMap;
import java.util.List;

import ir.stit.ir.charkhtafi.Functions;
import ir.stit.ir.charkhtafi.Model.AllDeliveryModel;
import ir.stit.ir.charkhtafi.R;
import ir.stit.ir.charkhtafi.Utils.Tools;
import ir.stit.ir.charkhtafi.Utils.Views.CFProvider;
import ir.stit.ir.charkhtafi.Utils.Views.CustomButton;
import ir.stit.ir.charkhtafi.Utils.Views.CustomTextView;


public class AllBuyerAdapter extends RecyclerView.Adapter<AllBuyerAdapter.myViewHolder> implements Functions.All {

    private List<AllDeliveryModel> data;
    private Activity context;
    private AlertDialog progress;
    private int position;
    private  List<HashMap<String, Object>> alldata;
    private String OrderId;
    private AlertDialog dialog;

    public AllBuyerAdapter(List<AllDeliveryModel> datas, Activity context, int position,
                           List<HashMap<String, Object>> alldata, String OrderId, AlertDialog dialog) {
        this.data = datas;
        this.context = context;
        this.position = position;
        this.alldata = alldata;
        this.OrderId = OrderId;
        this.dialog = dialog;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new myViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.custom_all_delivery_name,parent,false));
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, final int position) {

        AllDeliveryModel model = data.get(position);

        holder.Name.setText(" آقای " + model.getName());

        holder.CustomAllDeliveryDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Functions.getInstance(context).SendToDelivery(model.getId(),
//                        AllBuyerAdapter.this, alldata, OrderId);

                Functions.getInstance(context).SendTotalData(alldata, AllBuyerAdapter.this);

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
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
                        removeAt(position);
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
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public void addAt( AllDeliveryModel model) {
        data.add(model);
        notifyDataSetChanged();
    }

}
