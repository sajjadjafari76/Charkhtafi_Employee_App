package ir.stit.ir.charkhtafi.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.github.florent37.expansionpanel.ExpansionLayout;
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import ir.stit.ir.charkhtafi.AppController.AppController;
import ir.stit.ir.charkhtafi.Model.EditPriceModel;
import ir.stit.ir.charkhtafi.Network.StringRequest;
import ir.stit.ir.charkhtafi.R;
import ir.stit.ir.charkhtafi.Utils.Tools;
import ir.stit.ir.charkhtafi.Utils.Views.CustomEdittext;
import ir.stit.ir.charkhtafi.Utils.Views.CustomTextView;


public class EditPriceAdapter extends RecyclerView.Adapter<EditPriceAdapter.myViewHolder> {

    private List<EditPriceModel> data;
    private Activity context;

    public EditPriceAdapter(List<EditPriceModel> datas, Activity context) {
        this.data = datas;
        this.context = context;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new myViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.custom_edit_price, parent, false));
    }

    @Override
    public void onBindViewHolder(final myViewHolder holder, final int position) {

        final ExpansionLayoutCollection expansionLayoutCollection = new ExpansionLayoutCollection();
        expansionLayoutCollection.add(holder.expansionLayout);
        expansionLayoutCollection.openOnlyOne(false);
//
        final EditPriceModel model = data.get(position);

//        holder.Type.setText(model.getType());
        holder.Name.setText(model.getName() + " - " + model.getType());
        Log.e("pricePrice", model.getPrice() + " |");
        holder.Price.setText(Tools.getInstance(context).FormattedPrice2(model.getPrice()) + " تومان");

        Picasso.with(context)
                .load(model.getImage())
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .resize(200, 200)
                .onlyScaleDown()
                .centerInside()
                .into(holder.Image);


        holder.CustomEditPrice_Ok.setOnClickListener(v -> {

            if (!holder.EditText.getText().toString().equals("")) {
                if (!holder.EditText.getText().toString().equals(model.getPrice())) {
                    Log.e("edittext", holder.EditText.getText().toString() + " |");
                    holder.Progress.setVisibility(View.VISIBLE);
                    holder.CustomEditPrice_Ok.setVisibility(View.GONE);
                    updateProductPrice(model.getProduct_Id(), model.getId(), holder.EditText.getText().toString(), position, holder.Progress, holder.CustomEditPrice_Ok);
                } else {
                    Toast.makeText(context, "قیمت قبلی را نمیتواند وارد کنید", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "قیمت محصول نمیتواند خالی باشد", Toast.LENGTH_SHORT).show();
            }

            holder.EditText.setText("");
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        ExpansionLayout expansionLayout;
        private CircleImageView Image;
        private CustomTextView Name;
        private CustomTextView Price;
        private ImageView CustomEditPrice_Ok;
        private CustomEdittext EditText;
        private ProgressBar Progress;

        myViewHolder(View itemView) {
            super(itemView);
            expansionLayout = itemView.findViewById(R.id.expansionLayout);
            Image = itemView.findViewById(R.id.CustomEditPrice_Image);
            Name = itemView.findViewById(R.id.CustomEditPrice_Name);
            Price = itemView.findViewById(R.id.CustomEditPrice_Price);
            CustomEditPrice_Ok = itemView.findViewById(R.id.CustomEditPrice_Ok);
            EditText = itemView.findViewById(R.id.CustomEditPrice_EditText);
            Progress = itemView.findViewById(R.id.CustomEditPrice_Progress);
        }
    }

    private void updateProductPrice(String ProductId, String FeatureId, String NewPrice, int position, ProgressBar progressBar, ImageView check) {

        try {
            Map<String, String> params = new HashMap<>();
            params.put("OPR", "MODIFYPROD");
            params.put("uid", Tools.getInstance(context).read("UserId", ""));
            params.put("pid", ProductId);
            params.put("fid", FeatureId);
            params.put("nprice", NewPrice);

            StringRequest loginRequest = new StringRequest(params, 0, new StringRequest.ResponseAction() {
                @Override
                public void onResponseAction(String response) {
                    Log.e("updateProductResponse", response);

                    try {
                        JSONObject object = new JSONObject(response);

                        if (object.has("error")) {
                            if (!object.getBoolean("error")) {

                                changeData(NewPrice, position);
                                Toast.makeText(context, object.getString("msg"), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                check.setVisibility(View.VISIBLE);

                            } else {
                                Toast.makeText(context, object.getString("msg"), Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("updateProductError", e.toString() + " |");
                        Tools.getInstance(context).ToastMessage("خطا در اتصال به سرور ، دوباره تلاش کنید.");
                    }
                }

                @Override
                public void onErrorAction(VolleyError error) {
                    Log.e("updateProductError1", error.toString());
                    Tools.getInstance(context).ToastMessage("خطا در اتصال به سرور ، دوباره تلاش کنید.");
                }
            });

            AppController.getInstance().addToRequestQueue(loginRequest);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("updateProductError2", e.toString());
            Tools.getInstance(context).ToastMessage("خطا در اتصال به سرور ، دوباره تلاش کنید.");
        }
    }

    private void changeData(String NewPrice, int position) {
        data.get(position).setPrice(NewPrice);
        notifyItemChanged(position);
        notifyDataSetChanged();
    }

    private String regexCommafy(String inputNum)
    {
        String regex = "(\\d)(?=(\\d{3})+$)";
        String [] splittedNum = inputNum.split("\\.");
        if(splittedNum.length==2)
        {
            return splittedNum[0].replaceAll(regex, "$1,")+"."+splittedNum[1];
        }
        else
        {
            return inputNum.replaceAll(regex, "$1,");
        }
    }

    private String stringParserCommafy(String inputNum) {

        String commafiedNum="";
        Character firstChar= inputNum.charAt(0);

        //If there is a positive or negative number sign,
        //then put the number sign to the commafiedNum and remove the sign from inputNum.
        if(firstChar=='+' || firstChar=='-')
        {
            commafiedNum = commafiedNum + Character.toString(firstChar);
            inputNum=inputNum.replaceAll("[-\\+]", "");
        }

        //If the input number has decimal places,
        //then split it into two, save the first part to inputNum
        //and save the second part to decimalNum which will be appended to the final result at the end.
        String [] splittedNum = inputNum.split("\\.");
        String decimalNum="";
        if(splittedNum.length==2)
        {
            inputNum=splittedNum[0];
            decimalNum="."+splittedNum[1];
        }

        //The main logic for adding commas to the number.
        int numLength = inputNum.length();
        for (int i=0; i<numLength; i++) {
            if ((numLength-i)%3 == 0 && i != 0) {
                commafiedNum += ",";
            }
            commafiedNum += inputNum.charAt(i);
        }

        return commafiedNum+decimalNum;
    }

}
