package com.crupee.lottery.dashboard.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crupee.lottery.R;
import com.crupee.lottery.dashboard.model.NumberRangeDTO;
import com.crupee.lottery.utility.PrefUtils;

import java.util.ArrayList;

public class LotteryNumberRangeRecyclerView extends RecyclerView.Adapter<LotteryNumberRangeRecyclerView.ViewHolder> {

    //variables
    public static String TAG = "LotteryNumberRangeRecyclerView";
    Context mContext;
    ArrayList<NumberRangeDTO> lotteryList = new ArrayList<>();
    ArrayList<Integer> appendList = new ArrayList<>();


    public LotteryNumberRangeRecyclerView(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.number_range_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.bindLottery(lotteryList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return lotteryList.size();
    }

    public void addLotteryList(ArrayList<NumberRangeDTO> list) {
        lotteryList = new ArrayList<>();
        lotteryList.addAll(list);
        notifyDataSetChanged();
    }

    public ArrayList<NumberRangeDTO> returnLotteryList() {
        return lotteryList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView number_textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            number_textView = (TextView) itemView.findViewById(R.id.number_textView);
        }

        public void bindLottery(final NumberRangeDTO numberRangeDTO, final int position) {

            number_textView.setText(numberRangeDTO.getNum_value());

            number_textView.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View v) {

                    LotteryListRecyclerView lotteryListRecyclerView = null;
                    if (appendList.size() < 6) {

                        number_textView.setBackground(mContext.getResources().getDrawable(R.drawable.grey_circlular_lottery_number_view));
                        number_textView.setTextColor(mContext.getResources().getColor(R.color.white));
                        number_textView.setClickable(false);

                        int value = Integer.parseInt(numberRangeDTO.getNum_value());
                        int pos = appendList.size();
                        appendList.add(pos, value);

                        for (int i = 0; i < appendList.size(); i++) {
                            if (i == 0) {
                                lotteryListRecyclerView.num_ber_tv1.setText(appendList.get(i).toString());
                            } else if (i == 1) {
                                lotteryListRecyclerView.num_ber_tv2.setText(appendList.get(i).toString());
                            } else if (i == 2) {
                                lotteryListRecyclerView.num_ber_tv3.setText(appendList.get(i).toString());
                            } else if (i == 3) {
                                lotteryListRecyclerView.num_ber_tv4.setText(appendList.get(i).toString());
                            } else if (i == 4) {
                                lotteryListRecyclerView.num_ber_tv5.setText(appendList.get(i).toString());
                            } else if (i == 5) {
                                lotteryListRecyclerView.num_ber_tv6.setText(appendList.get(i).toString());
                            }

                        }
                        if (appendList.size() == 6) {

                            //save the selected lottery number to Preference
                            lotteryListRecyclerView.number_Save.setVisibility(View.VISIBLE);
                            PrefUtils.saveSelectedLotteryNumber(mContext, appendList, "selected_lottery_number");
                        }

                    }

                }
            });

        }
    }
}
