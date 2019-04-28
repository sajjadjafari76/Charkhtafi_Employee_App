package ir.stit.ir.charkhtafi.Adapter;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import ir.stit.ir.charkhtafi.R;
import ir.stit.ir.charkhtafi.Utils.Views.CFProvider;
import ir.stit.ir.charkhtafi.Utils.Views.CustomButton;
import ir.stit.ir.charkhtafi.Utils.Views.CustomTextView;


public class SurveyAdapter extends RecyclerView.Adapter<SurveyAdapter.myViewHolder> {

    private List<String> data;
    private Activity context;
    private AlertDialog progress;

    public SurveyAdapter(List<String> datas, Activity context) {
        this.data = datas;
        this.context = context;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        return new myViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.custom_survey_view, parent, false));
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, final int position) {

        final String model = data.get(position);

        holder.Text.setText(model);

        holder.Plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!holder.Counter.getText().toString().equals("5")) {
                    holder.Counter.setText((Integer.parseInt(holder.Counter.getText().toString()) + 1)+"");
                }
            }
        });
        holder.Minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!holder.Counter.getText().toString().equals("0")) {
                    holder.Counter.setText((Integer.parseInt(holder.Counter.getText().toString()) - 1)+"");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        private CustomTextView Text, Counter;
        private ImageView Plus, Minus;

        myViewHolder(View itemView) {
            super(itemView);
            Text = itemView.findViewById(R.id.CustomSurveyViewText);
            Plus = itemView.findViewById(R.id.CustomSurveyViewPlus);
            Minus = itemView.findViewById(R.id.CustomSurveyViewMinus);
            Counter = itemView.findViewById(R.id.CustomSurveyViewCounter);

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
                            removeAt(position);
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
            notifyItemRemoved(position);
            notifyDataSetChanged();
        }

        public void addAt(String model) {
            data.add(model);
            notifyDataSetChanged();
        }

    }
}