package ir.stit.ir.charkhtafi.Adapter;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ir.stit.ir.charkhtafi.Model.OrderFruitModel;
import ir.stit.ir.charkhtafi.R;
import ir.stit.ir.charkhtafi.Utils.Tools;
import ir.stit.ir.charkhtafi.Utils.Views.CFProvider;
import ir.stit.ir.charkhtafi.Utils.Views.CustomButton;
import ir.stit.ir.charkhtafi.Utils.Views.CustomEdittext;
import ir.stit.ir.charkhtafi.Utils.Views.CustomTextView;


public class OrderFruitAdapter extends RecyclerView.Adapter<OrderFruitAdapter.myViewHolder> {

    private List<OrderFruitModel> data;
    private Activity context;
    private AlertDialog progress;
    private int positionall;

    public OrderFruitAdapter(List<OrderFruitModel> datas, Activity context) {
        this.data = datas;
        this.context = context;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new myViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.custom_order_fruit,parent,false));
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, final int position) {
        positionall = position;

        final OrderFruitModel model = data.get(position);

        Picasso.with(context)
                .load(model.getImage())
                .placeholder(R.drawable.logo)
                .resize(200,200)
                .onlyScaleDown()
                .centerInside()
                .into(holder.Image);

        holder.Name.setText(model.getName().concat("/ " + model.getDegree()));
        holder.TotalWeight.setText(/*model.getTotalWeight().concat(" " +*/ model.getUnit()/*)*/);
        holder.Price.setText(Tools.getInstance(context).FormattedPrice2(model.getPrice()));
        holder.Weight.setText(model.getWeight());


        try {
//            updateData.Data(holder,position);
//            holder.Bind(model, position, listener);
        }catch (Exception ee) {
            ee.printStackTrace();
        }

//        holder.Delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ShowDialog(position);
//            }
//        });

        holder.NewPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                model.setNewPrice(s.toString());
            }
        });

        holder.NewWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                model.setNewWeight(s.toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        private ImageView CustomNavigationIcon;
        private CustomTextView CustomNavigationText, Name, TotalWeight
                , Weight, Price;
        private ImageView Delete;
        private CircleImageView Image;
        private CustomEdittext NewPrice, NewWeight;

        myViewHolder(View itemView) {
            super(itemView);
            CustomNavigationIcon = itemView.findViewById(R.id.CustomNavigationIcon);
            CustomNavigationText = itemView.findViewById(R.id.CustomNavigationText);
            Name = itemView.findViewById(R.id.OrderFruit_Name);
//            Delete = itemView.findViewById(R.id.OrderFruit_Delete);
            Image = itemView.findViewById(R.id.OrderFruit_Image);
            TotalWeight = itemView.findViewById(R.id.OrderFruit_TotalWeight);
            Weight = itemView.findViewById(R.id.OrderFruit_Weight);
            Price = itemView.findViewById(R.id.OrderFruit_Price);
            NewPrice = itemView.findViewById(R.id.OrderFruit_NewPrice);
            NewWeight = itemView.findViewById(R.id.OrderFruit_NewWeight);


        }


//        public void Bind(final OrderFruitModel model, final int position, final onItemClickListener listener){
//       listener.onPosition(position);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    listener.onItemClick(model);
//
//                    Toast.makeText(context, "position is : " + position, Toast.LENGTH_SHORT).show();
//                }
//            });
//        }

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

    public void addAt( OrderFruitModel model) {
        data.add(model);
        notifyItemInserted(positionall);
        notifyDataSetChanged();
    }

}
