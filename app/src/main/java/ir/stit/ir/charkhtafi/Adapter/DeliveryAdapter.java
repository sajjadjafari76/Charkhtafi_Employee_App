package ir.stit.ir.charkhtafi.Adapter;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.github.florent37.expansionpanel.ExpansionLayout;
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.stit.ir.charkhtafi.AppController.AppController;
import ir.stit.ir.charkhtafi.Functions;
import ir.stit.ir.charkhtafi.Model.DeliveryModel;
import ir.stit.ir.charkhtafi.Model.OrderFruitModel;
import ir.stit.ir.charkhtafi.Network.StringRequest;
import ir.stit.ir.charkhtafi.R;
import ir.stit.ir.charkhtafi.Utils.Tools;
import ir.stit.ir.charkhtafi.Utils.Views.CFProvider;
import ir.stit.ir.charkhtafi.Utils.Views.CustomButton;
import ir.stit.ir.charkhtafi.Utils.Views.CustomTextView;


public class DeliveryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Functions.All {

    private List<DeliveryModel> data;
    private Activity context;
    private AlertDialog progress;
    private AlertDialog alertDialog;
    List<OrderFruitModel> data1;
    OrderFruitAdapter adapter;

    //    private static final int FOOTER = 0;
    private static final int DEFAULT = 1;

    public DeliveryAdapter(List<DeliveryModel> datas, Activity context) {
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
                        .inflate(R.layout.custom_delivery, parent, false));
//            case FOOTER:
//            return new myCustomBtn(LayoutInflater.from(context)
//                    .inflate(R.layout.custom_totalorder_btn, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        myCustomViewHolder holder1 = (myCustomViewHolder) holder;

        DeliveryModel model = data.get(position);

        final ExpansionLayoutCollection expansionLayoutCollection = new ExpansionLayoutCollection();
        expansionLayoutCollection.add(holder1.expansionLayout);
        expansionLayoutCollection.openOnlyOne(true);

        DeliveryFruitAdapter adapter = new DeliveryFruitAdapter(model.getData(), context);
        holder1.RecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        holder1.RecyclerView.setAdapter(adapter);

        if (data.get(position).isPreOrder()) {
            holder1.PreOrderImage.setVisibility(View.VISIBLE);
        }else {
            holder1.PreOrderImage.setVisibility(View.GONE);
        }

        holder1.Name.setText(model.getName());
        holder1.CustomOrder_Factor.setText("شماره فاکتور : ".concat(model.getId()));
//        holder1.Family.setText( " بدهکار : " + model.getDebtor());
        holder1.Family.setText(Tools.getInstance(context).FormattedPrice2(model.getTotalOrderPrice()).concat(" تومان"));
        holder1.Address.setText(model.getAddress());
        holder1.Payment.setText((model.getPayment().equals("0")) ? " پرداخت اینترنتی" : "پرداخت درب منزل");
        holder1.Time.setText(model.getTime());
        if ((model.getBikePrice().matches("\\d+(?:\\.\\d+)?"))) {
            holder1.Bike.setText(Tools.getInstance(context).FormattedPrice2(model.getBikePrice()).concat(" تومان"));
        } else {
            holder1.Bike.setText(model.getBikePrice());
        }

        int debtor = 0;
        for (int i = 0; i < model.getData().size(); i++) {

            debtor = debtor + Integer.parseInt(model.getData().get(i).getDebtor());

        }
        ((myCustomViewHolder) holder).Debtor.setText("بدهی: ".concat(Tools.getInstance(context).FormattedPrice2(String.valueOf(debtor)).concat(" تومان")));


        int finalDebtor = debtor;
        holder1.Ok.setOnClickListener(v -> {
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setView(R.layout.delivery_layout_dialog);

            alertDialog = dialog.create();
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            LayoutInflater inflater = context.getLayoutInflater();
            alertDialog.setContentView(inflater.inflate(R.layout.delivery_layout_dialog, null));
            alertDialog.setCancelable(true);
            alertDialog.show();

            //* StepOne *//
            Button delivered = alertDialog.findViewById(R.id.LayoutDialog_DeliveredBtn);
            Button reflection = alertDialog.findViewById(R.id.LayoutDialog_ReflectionBtn);
            LinearLayout stepOne = alertDialog.findViewById(R.id.LayoutDialog_StepOne);
            //* StepOne *//

            //** Reflected **//
            LinearLayout reflected = alertDialog.findViewById(R.id.LayoutDialog_Reflected);
            Button reflectedBtnBack = alertDialog.findViewById(R.id.LayoutDialog_ReflectedBack);
            Button reflectedBtnSend = alertDialog.findViewById(R.id.LayoutDialog_ReflectedSend);
            EditText reflectedDesc = alertDialog.findViewById(R.id.LayoutDialog_ReflectedDesc);
            //** Reflected **//

            //*** IsPaid ***//
            LinearLayout isPaid = alertDialog.findViewById(R.id.LayoutDialog_IsPaid);
            LinearLayout view = alertDialog.findViewById(R.id.LayoutDialog_View1);
            LinearLayout debutPaymentType = alertDialog.findViewById(R.id.LayoutDialog_DebutPaymentType);
            LinearLayout debutPaymentType_TrackCode = alertDialog.findViewById(R.id.LayoutDialog_DebutPaymentType_TrackCode);
            Switch isPaidSwitch = alertDialog.findViewById(R.id.LayoutDialog_IsPaidSwitch);
            Switch debutPaymentTypeSwitch = alertDialog.findViewById(R.id.LayoutDialog_DebutPaymentTypeSwitch);
            Button debutSend = alertDialog.findViewById(R.id.LayoutDialog_DebutSend);
            EditText trackingCode = alertDialog.findViewById(R.id.LayoutDialog_TrackingCode);
            TextView question = alertDialog.findViewById(R.id.LayoutDialog_Question);
            Button reflectedBtnBack1 = alertDialog.findViewById(R.id.LayoutDialog_ReflectedBack1);

            reflectedBtnBack1.setOnClickListener(v1 -> {
                stepOne.setVisibility(View.VISIBLE);
                view.setVisibility(View.GONE);
            });

            final String[] paymentType = {"0", "0"};
            isPaidSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> { // Paid or not
                if (isChecked) {
                    debutPaymentType.setVisibility(View.VISIBLE);
                    paymentType[1] = "1"; // Paid
                } else {
                    debutPaymentType.setVisibility(View.GONE);
                    paymentType[1] = "0"; // Did not pay
                }
            });

            debutPaymentTypeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> { // Cash or POS
                if (isChecked) {
                    debutPaymentType_TrackCode.setVisibility(View.VISIBLE);
                    paymentType[0] = "1"; // POS

                } else {
                    debutPaymentType_TrackCode.setVisibility(View.GONE);
                    paymentType[0] = "0"; // Cash
                }
            });

            debutSend.setOnClickListener(v2 -> {
                Log.e("kosdast12", model.getId() + " | " + trackingCode.getText().toString() + " | " + finalDebtor);
                if (model.getPayment().equals("0") && finalDebtor <= 0) {
                    sendFactorToServer(model.getId(), paymentType[0],
                            trackingCode.getText().toString(), String.valueOf(finalDebtor), paymentType[1], "0", position, model.getPayment()); // status 0-> without debtor
                } else {
                    sendFactorToServer(model.getId(), paymentType[0],
                            trackingCode.getText().toString(), String.valueOf(finalDebtor), paymentType[1], "1", position, model.getPayment()); // status 1-> with debtor
                }

            });
            //*** IsPaid ***//

            //* StepOne *//
            delivered.setOnClickListener(v1 -> {
                stepOne.setVisibility(View.GONE);
                view.setVisibility(View.VISIBLE);
                if (model.getPayment().equals("0") && finalDebtor <= 0) {
                    question.setVisibility(View.VISIBLE);
                    isPaid.setVisibility(View.GONE);
                } else {
                    question.setVisibility(View.GONE);

                    if (model.getPayment().equals("1")) {
                        isPaid.setVisibility(View.GONE);
                        debutPaymentType.setVisibility(View.VISIBLE);
                    } else {
                        isPaid.setVisibility(View.VISIBLE);
                        debutPaymentType.setVisibility(View.GONE);
                    }
                }
            });

            reflection.setOnClickListener(v1 -> {
                stepOne.setVisibility(View.GONE);
                reflected.setVisibility(View.VISIBLE);
            });
            //* StepOne *//

            //** Reflected **//
            reflectedBtnBack.setOnClickListener(v1 -> {
                stepOne.setVisibility(View.VISIBLE);
                reflected.setVisibility(View.GONE);
            });
            reflectedBtnSend.setOnClickListener(v1 -> {
                reflectedFactor(model.getId(), reflectedDesc.getText().toString(), position);
            });
            //** Reflected **//

            TextView title = alertDialog.findViewById(R.id.LayoutDialog_Title);


            title.setText("فاکتور " + model.getId() + " - " + model.getName());

        });


    }

    @Override
    public void Success(String text) {
        alertDialog.dismiss();
        Tools.getInstance(context).ToastMessage("سفارش با موفقیت به مشتری تحویل داده شد!");
    }

    @Override
    public void Failed(String error) {
        alertDialog.dismiss();
        Tools.getInstance(context).ToastMessage("خطایی رخ داد لطفا دوباره تلاش کنید!");
    }

    public class myCustomViewHolder extends RecyclerView.ViewHolder {

        private CustomTextView Name;
        private CustomTextView Family;
        private CustomTextView Address;
        private CustomTextView Payment;
        private CustomTextView Time;
        private CustomTextView CustomOrder_Factor;
        private CustomTextView Debtor, Bike;
        private ExpansionLayout expansionLayout;
        private RecyclerView RecyclerView;
        private ImageView Ok, PreOrderImage;

        public myCustomViewHolder(View itemView) {
            super(itemView);

            Name = itemView.findViewById(R.id.CustomDelivery_Name);
            Family = itemView.findViewById(R.id.CustomDelivery_Total);
            Address = itemView.findViewById(R.id.CustomDelivery_Address);
            Payment = itemView.findViewById(R.id.CustomDelivery_Payment);
            CustomOrder_Factor = itemView.findViewById(R.id.CustomOrder_Factor);
            Time = itemView.findViewById(R.id.CustomDelivery_Time);
            expansionLayout = itemView.findViewById(R.id.expansionLayout);
            RecyclerView = itemView.findViewById(R.id.Delivery_RecyclerView);
            Ok = itemView.findViewById(R.id.CustomDelivery_Ok);
            Debtor = itemView.findViewById(R.id.CustomDelivery_Debtor);
            Bike = itemView.findViewById(R.id.CustomDelivery_Bike);
            PreOrderImage = itemView.findViewById(R.id.CustomDelivery_PreOrderImage);

        }
    }

    public class myCustomBtn extends RecyclerView.ViewHolder {

        private CustomTextView btn;

        public myCustomBtn(View itemView) {
            super(itemView);

            btn = itemView.findViewById(R.id.Custom_TotalOrder_Btn);

        }
    }

    private List<OrderFruitModel> getdata() {

        data1 = new ArrayList<>();

        for (int i = 0; i < 3; i++) {

            if (i == 0) {
                OrderFruitModel model = new OrderFruitModel();
                model.setFruitId("126587");
                model.setName("سیب زمینی");
                model.setWeight("12");
                model.setTotalWeight("12");
                model.setPrice("1600");
                model.setImage("http://charkhtafi.golsamsepahan.charkhtafi/wp-content/uploads/2017/02/potato_png2391.png");
                data1.add(model);
            } else if (i == 1) {
                OrderFruitModel model = new OrderFruitModel();
                model.setFruitId("7687645");
                model.setName("آناناس");
                model.setWeight("1");
                model.setTotalWeight("1");
                model.setPrice("18000");
                model.setImage("https://charkhtafi.parsegard.charkhtafi/sites/default/files/content_types/food_nutrition/9571/main_image/pineapple.jpg");
                data1.add(model);
            } else if (i == 2) {
                OrderFruitModel model = new OrderFruitModel();
                model.setFruitId("786545");
                model.setName("هلو");
                model.setWeight("4");
                model.setTotalWeight("4");
                model.setPrice("1400");
                model.setImage("http://salamatezanan.charkhtafi/wp-content/uploads/peach-950x950.png");
                data1.add(model);
            }
        }
        return data1;
    }

    private void ShowDialog() {

        try {
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setView(R.layout.custom_delivery_survey);

            progress = dialog.create();
            progress.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            LayoutInflater inflater = context.getLayoutInflater();
            progress.setContentView(inflater.inflate(R.layout.custom_delivery_survey, null));
            progress.setCancelable(false);
            progress.show();

            RecyclerView recyclerView = progress.findViewById(R.id.CustomDeliverySurveyRecycler);
            CustomTextView btn = progress.findViewById(R.id.CustomDeliverySurvey_Btn);

            if (recyclerView != null && btn != null) {
//                SurveyAdapter adapter = new SurveyAdapter(getA(),context);
                recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(adapter);

                btn.setOnClickListener((View view) -> progress.dismiss());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ShowDialogConfirm(String Debtor, String OrderId) {

        try {

            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            LayoutInflater inflater = context.getLayoutInflater();
            alertDialog.setContentView(inflater.inflate(R.layout.exit_permission, null));
            alertDialog.setCancelable(false);
            alertDialog.show();

            CustomButton Yes = (CustomButton) alertDialog.findViewById(R.id.CustomPermission_Yes);
            CustomButton Cancel = (CustomButton) alertDialog.findViewById(R.id.CustomPermission_Cancel);
            CustomTextView text = alertDialog.findViewById(R.id.CustomPermission_Content);
            if (Yes != null && Cancel != null && text != null) {
                Yes.setTypeface(CFProvider.getIRANIANSANS(context));
                Cancel.setTypeface(CFProvider.getIRANIANSANS(context));

                text.setText("آیا مشتری مبلغ " + Debtor + " را پرداخت کرده است؟");

                Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Log.e("debtor", OrderId + " | " + Debtor );
                        Functions.getInstance(context).SendDelivery(OrderId, Debtor, DeliveryAdapter.this);
                    }
                });

                Yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Log.e("debtor", OrderId + " | " + Debtor );
                        Functions.getInstance(context).SendDelivery(OrderId, "0", DeliveryAdapter.this);
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<OrderFruitModel> getFruitdata() {

        data1 = new ArrayList<>();

        for (int i = 0; i < 3; i++) {

            if (i == 0) {
                OrderFruitModel model = new OrderFruitModel();
                model.setFruitId("126587");
                model.setName("سیب زمینی");
                model.setTotalWeight("12 کیلوگرم");
                model.setImage("http://charkhtafi.golsamsepahan.charkhtafi/wp-content/uploads/2017/02/potato_png2391.png");
                data1.add(model);
            } else if (i == 1) {
                OrderFruitModel model = new OrderFruitModel();
                model.setFruitId("7687645");
                model.setName("آناناس");
                model.setTotalWeight("1 عدد");
                model.setImage("https://charkhtafi.parsegard.charkhtafi/sites/default/files/content_types/food_nutrition/9571/main_image/pineapple.jpg");
                data1.add(model);
            } else if (i == 2) {
                OrderFruitModel model = new OrderFruitModel();
                model.setFruitId("786545");
                model.setName("هلو");
                model.setWeight("4");
                model.setTotalWeight("4 کیلوگرم");
                model.setPrice("1400");
                model.setImage("http://salamatezanan.charkhtafi/wp-content/uploads/peach-950x950.png");
                data1.add(model);
            }
        }
        return data1;
    }

    private void sendFactorToServer(String FactorId, String PayType, String TrackCode, String Debtor,
                                    String Paid, String Status, int position, String PaymentType) {

        try {
            Map<String, String> params = new HashMap<>();
            params.put("OPR", "DELIVERTOCUSTOMER");
//            params.put("userid", Tools.getInstance(context).read("UserId", ""));
//            if (Status.equals("1")) { // if debtor lower than zero or zero
            params.put("FactorId", FactorId);
            params.put("PayType", PayType);// POS OR Cash  0->cash 1->POS
            params.put("TrackCode", TrackCode);
            params.put("Debtor", Debtor);
            params.put("Paid", Paid); // 0-> Did not pay 1-> Paid
            params.put("PaymentType", PaymentType); // in home or online
//            }else {
//                params.put("Debtor", Debtor);
//            }

            StringRequest loginRequest = new StringRequest(params, 0, new StringRequest.ResponseAction() {
                @Override
                public void onResponseAction(String response) {
                    Log.e("FactorToServerResponse", response);

                    try {
                        JSONObject object = new JSONObject(response);

                        if (object.has("error")) {
                            if (!object.getBoolean("error")) {
                                Toast.makeText(context, object.getString("msg"), Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                                removeAt(position);

                            } else {
                                Toast.makeText(context, object.getString("msg"), Toast.LENGTH_SHORT).show();
//                                endAnimation();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("FactorToServerError1", e.toString() + " |");
                        Tools.getInstance(context).ToastMessage("خطا در اتصال به سرور ، دوباره تلاش کنید.");
//                        endAnimation();
                    }
                }

                @Override
                public void onErrorAction(VolleyError error) {
                    error.printStackTrace();
                    Log.e("FactorToServerError2", error.toString());
                    Tools.getInstance(context).ToastMessage("خطا در اتصال به سرور ، دوباره تلاش کنید.");
//                    endAnimation();
                }
            });

            AppController.getInstance().addToRequestQueue(loginRequest);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("FactorToServerError3", e.toString());
            Tools.getInstance(context).ToastMessage("خطا در اتصال به سرور ، دوباره تلاش کنید.");
        }
    }

    private void reflectedFactor(String FactorId, String Description, int position) {

        try {
            Map<String, String> params = new HashMap<>();
            params.put("OPR", "ORDERREFLECT");
            params.put("FactorId", FactorId);
            params.put("Desc", Description);

            StringRequest loginRequest = new StringRequest(params, 0, new StringRequest.ResponseAction() {
                @Override
                public void onResponseAction(String response) {
                    Log.e("reflectedFactorResponse", response);

                    try {
                        JSONObject object = new JSONObject(response);

                        if (object.has("error")) {
                            if (!object.getBoolean("error")) {
                                Toast.makeText(context, object.getString("msg"), Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                                removeAt(position);
                            } else {
                                Toast.makeText(context, object.getString("msg"), Toast.LENGTH_SHORT).show();
//                                endAnimation();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("reflectedFactorError1", e.toString() + " |");
                        Tools.getInstance(context).ToastMessage("خطا در اتصال به سرور ، دوباره تلاش کنید.");
//                        endAnimation();
                    }
                }

                @Override
                public void onErrorAction(VolleyError error) {
                    Log.e("reflectedFactorError2", error.toString());
                    Tools.getInstance(context).ToastMessage("خطا در اتصال به سرور ، دوباره تلاش کنید.");
//                    endAnimation();
                }
            });

            AppController.getInstance().addToRequestQueue(loginRequest);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("reflectedFactorError3", e.toString());
            Tools.getInstance(context).ToastMessage("خطا در اتصال به سرور ، دوباره تلاش کنید.");
//            endAnimation();
        }

    }

    public void removeAt(int position) {
        data.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

}
