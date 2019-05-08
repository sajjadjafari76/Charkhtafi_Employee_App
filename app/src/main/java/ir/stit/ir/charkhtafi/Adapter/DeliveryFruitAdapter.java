package ir.stit.ir.charkhtafi.Adapter;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ir.stit.ir.charkhtafi.Model.OrderFruitModel;
import ir.stit.ir.charkhtafi.R;
import ir.stit.ir.charkhtafi.Utils.Tools;
import ir.stit.ir.charkhtafi.Utils.Views.CFProvider;
import ir.stit.ir.charkhtafi.Utils.Views.CustomButton;
import ir.stit.ir.charkhtafi.Utils.Views.CustomTextView;


public class DeliveryFruitAdapter extends RecyclerView.Adapter<DeliveryFruitAdapter.myViewHolder> {

    private List<OrderFruitModel> data;
    private Activity context;
    private AlertDialog progress;
    private int positionall;

    public DeliveryFruitAdapter(List<OrderFruitModel> datas, Activity context) {
        this.data = datas;
        this.context = context;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new myViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.custom_delivery_fruit,parent,false));
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, final int position) {
        positionall = position;
        OrderFruitModel model = data.get(position);

        Picasso.with(context)
                .load(model.getImage())
                .placeholder(R.drawable.logo)
                .resize(200,200)
                .onlyScaleDown()
                .centerInside()
                .into(holder.Image);

        holder.Name.setText(model.getName().concat(" | " + model.getDegree()));
        double total = Double.parseDouble(model.getPrice()) * Float.parseFloat(model.getWeight());
        double calculateOff = total - (( total / 100) * Float.parseFloat(model.getOff()));
        holder.TotalPrice.setText(Tools.getInstance(context).FormattedPrice2(String.valueOf((int)calculateOff)).concat(" تومان"));

        holder.TotalWeight.setText(model.getWeight().concat(" " + model.getUnit()));
    }

    @Override
    public int getItemCount() {
//        Log.e("data", data.size()+"");
        return data.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        private CustomTextView  Name, TotalWeight, TotalPrice;
        private CircleImageView Image;

        myViewHolder(View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.DeliveryFruit_Name);
            Image = itemView.findViewById(R.id.DeliveryFruit_Image);
            TotalWeight = itemView.findViewById(R.id.DeliveryFruit_TotalWeight);
            TotalPrice = itemView.findViewById(R.id.DeliveryFruit_TotalPrice);
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

//    public void addAt( HashMap<String,Object> model) {
//        data.add(model);
//        notifyItemInserted(positionall);
//        notifyDataSetChanged();
//    }

}
