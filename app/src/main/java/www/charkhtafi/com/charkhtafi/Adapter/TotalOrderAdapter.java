package www.charkhtafi.com.charkhtafi.Adapter;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import www.charkhtafi.com.charkhtafi.Functions;
import www.charkhtafi.com.charkhtafi.Model.AllDeliveryModel;
import www.charkhtafi.com.charkhtafi.Model.TotalOrderModel;
import www.charkhtafi.com.charkhtafi.Model.TotalProductOrderModel;
import www.charkhtafi.com.charkhtafi.R;
import www.charkhtafi.com.charkhtafi.Utils.Tools;
import www.charkhtafi.com.charkhtafi.Utils.Views.CustomEdittext;
import www.charkhtafi.com.charkhtafi.Utils.Views.CustomTextView;

import static www.charkhtafi.com.charkhtafi.Globals.ApiURLIMAGE;

public class TotalOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private TotalOrderModel data;
    private Activity context;
    private AlertDialog progress;
    private RecyclerView TotalRecyclerView;

    private static final int FOOTER = 0;
    private static final int DEFAULT = 1;

    public TotalOrderAdapter(TotalOrderModel datas, Activity context, RecyclerView recyclerView) {
        this.context = context;
        this.data = datas;
        TotalRecyclerView = recyclerView;
        Log.e("ddd32", data.getTotalOrder().size() + " |");
    }

    @Override
    public int getItemCount() {
        return data.getTotalOrder().size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == data.getTotalOrder().size()) {
            return FOOTER;
        } else {
            return DEFAULT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case DEFAULT:
                return new myCustomViewHolder(LayoutInflater.from(context)
                        .inflate(R.layout.custom_totalorder, parent, false));
            case FOOTER:
                return new myCustomBtn(LayoutInflater.from(context)
                        .inflate(R.layout.custom_totalorder_btn, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof myCustomViewHolder) {
            myCustomViewHolder customViewHolder = (myCustomViewHolder) holder;
            TotalProductOrderModel model = data.getTotalOrder().get(position);

            customViewHolder.name.setText(model.getName().concat("/" + model.getUnit()));
            customViewHolder.price.setText(Tools.getInstance(context).FormattedPrice(model.getPrice()));
            customViewHolder.weight.setText(model.getWight() + " " + model.getUnit());
            customViewHolder.PriceDegree.setText(model.getDegree());

            Picasso.with(context).load(model.getImage()).into(customViewHolder.image);

            customViewHolder.newPrice.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
//                    customViewHolder.newPrice.setText(Tools.getInstance(context).FormattedPrice2(s.toString()));
                    model.setNewPrice(s.toString());
                }
            });

        } else {
//            TotalOrderModel adapter = data.get(position-1);

            try {

                myCustomBtn customBtn = (myCustomBtn) holder;

                customBtn.btn.setOnClickListener(v -> {
//                        Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();


                    ShowDialogBuyer(data.getAllBuyer());


                });

            } catch (Exception e) {
                Log.e("CompPhotoAdapterError", e.toString());
            }

        }

    }

    private void ShowDialogBuyer(List<AllDeliveryModel> models) {

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
            CustomTextView title = progress.findViewById(R.id.CustomAllDeliveryText);
            if (Add != null && cancel != null && title != null) {
                title.setText("لیست خریدارها");
                AllDeliveryForTotalAdapter allDeliveryAdapter = new AllDeliveryForTotalAdapter(models, context, data.getTotalOrder(), progress, TotalRecyclerView, data/*, context, position, alldata, OrderId, progress, toString()*/);
                Add.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                Add.setAdapter(allDeliveryAdapter);

                cancel.setOnClickListener(v -> progress.dismiss());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class myCustomViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView image;
        private CustomTextView name;
        private CustomTextView price;
        private CustomTextView weight;
        private CustomTextView priceType;
        private CustomEdittext newPrice;
        private CustomTextView PriceDegree;

        public myCustomViewHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.CustomTotalOrder_Image);
            name = itemView.findViewById(R.id.CustomTotalOrder_Name);
            price = itemView.findViewById(R.id.CustomTotalOrder_SitePriceNumber);
            weight = itemView.findViewById(R.id.CustomTotalOrder_Weight);
            newPrice = itemView.findViewById(R.id.CustomTotalOrder_NewPrice);
            priceType = itemView.findViewById(R.id.CustomTotalOrder_PriceType);
            PriceDegree = itemView.findViewById(R.id.CustomTotalOrder_PriceDegree);

        }
    }

    public class myCustomBtn extends RecyclerView.ViewHolder {

        private CustomTextView btn;

        public myCustomBtn(View itemView) {
            super(itemView);

            btn = itemView.findViewById(R.id.Custom_TotalOrder_Btn);

        }
    }
}
