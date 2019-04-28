package ir.stit.ir.charkhtafi.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.List;

import ir.stit.ir.charkhtafi.Fragment.Delivery;
import ir.stit.ir.charkhtafi.Fragment.Edit_Price;
import ir.stit.ir.charkhtafi.Login;
import ir.stit.ir.charkhtafi.Model.NavModel;
import ir.stit.ir.charkhtafi.Fragment.Order;
import ir.stit.ir.charkhtafi.R;
import ir.stit.ir.charkhtafi.Fragment.TotalOrder;
import ir.stit.ir.charkhtafi.Utils.Tools;
import ir.stit.ir.charkhtafi.Utils.Views.CFProvider;
import ir.stit.ir.charkhtafi.Utils.Views.CustomButton;
import ir.stit.ir.charkhtafi.Utils.Views.CustomTextView;

import static ir.stit.ir.charkhtafi.MainActivity.MainActivityTitle;


public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.myViewHolder> {

    private List<NavModel> data;
    private Activity context;
    private AlertDialog alertDialog;
    private DrawerLayout drawerLayout;

    public NavigationAdapter(List<NavModel> model, Activity context, DrawerLayout drawerLayout) {
        this.data = model;
        this.context = context;
        this.drawerLayout = drawerLayout;
        Log.e("UserType", Tools.getInstance(context).read("UserType","") + " |");
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new myViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.custom_navigation,parent,false));
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, final int position) {
        NavModel mydata = data.get(position);
        holder.CustomNavigationIcon.setImageDrawable(mydata.getImage());
        holder.CustomNavigationText.setText(mydata.getText());

        holder.CustomNavigationRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();

                Log.e("UserType11", Tools.getInstance(context).read("UserType","") + " |");



                switch (Tools.getInstance(context).read("UserType","")) {
                    case "3213655546": // MainKargozar
                        switch (position) {
                            case 0:
                                Edit_Price edit_price = new Edit_Price();
                                transaction.replace(R.id.MainActivityFrameLayout, edit_price);
                                transaction.commit();
                                MainActivityTitle.setText("لیست محصولات");
                                closeNavigation();
                                break;
                            case 1:
                                TotalOrder totalOrder = new TotalOrder();
                                transaction.replace(R.id.MainActivityFrameLayout, totalOrder);
                                transaction.commit();
                                MainActivityTitle.setText("مجموع سفارش ها");
                                closeNavigation();
                                break;
                            case 2:
                                Order order = new Order();
                                transaction.replace(R.id.MainActivityFrameLayout, order);
                                transaction.commit();
                                MainActivityTitle.setText("لیست سفارش ها");
                                closeNavigation();
                                break;
                            case 3:
                                ShowDialog();
                                break;
                        }
                        break;
                    case "3213655547": // SecondKargozar
                        switch (position) {
                            case 0:
                                TotalOrder totalOrder = new TotalOrder();
                                transaction.replace(R.id.MainActivityFrameLayout, totalOrder);
                                transaction.commit();
                                MainActivityTitle.setText("مجموع سفارش ها");
                                closeNavigation();
                                break;
                            case 1:
                                Order order = new Order();
                                transaction.replace(R.id.MainActivityFrameLayout, order);
                                transaction.commit();
                                MainActivityTitle.setText("لیست سفارش ها");
                                closeNavigation();
                                break;
                            case 2:
                                ShowDialog();
                                break;
                        }
                        break;
                    case "3213655548": //bike
                        switch (position) {
                            case 0:
                                Delivery order = new Delivery();
                                transaction.replace(R.id.MainActivityFrameLayout, order);
                                transaction.commit();
                                MainActivityTitle.setText("لیست سفارش ها");
                                closeNavigation();
                                break;
                            case 1:
                                ShowDialog();
                                break;
                        }
                        break;
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        ImageView CustomNavigationIcon;
        CustomTextView CustomNavigationText;
        RelativeLayout CustomNavigationRoot;

        myViewHolder(View itemView) {
            super(itemView);
            CustomNavigationIcon = itemView.findViewById(R.id.CustomNavigationIcon);
            CustomNavigationText =  itemView.findViewById(R.id.CustomNavigationText);
            CustomNavigationRoot = itemView.findViewById(R.id.CustomNavigationRoot);
        }
    }

    private void ShowDialog() {

        try {
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setView(R.layout.exit_permission);

            alertDialog = dialog.create();
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            LayoutInflater inflater = context.getLayoutInflater();
            alertDialog.setContentView(inflater.inflate(R.layout.exit_permission, null));
            alertDialog.setCancelable(false);
            alertDialog.show();

            CustomButton Yes = (CustomButton) alertDialog.findViewById(R.id.CustomPermission_Yes);
            CustomButton Cancel = (CustomButton) alertDialog.findViewById(R.id.CustomPermission_Cancel);
            if (Yes != null && Cancel != null) {
                Yes.setTypeface(CFProvider.getIRANIANSANS(context));
                Cancel.setTypeface(CFProvider.getIRANIANSANS(context));

                Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                Yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Tools.getInstance(context).write("UserId", "");
                        alertDialog.dismiss();
                        Intent intent = new Intent(context, Login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    void closeNavigation() {
        if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
            drawerLayout.closeDrawers();
        }
    }

}
