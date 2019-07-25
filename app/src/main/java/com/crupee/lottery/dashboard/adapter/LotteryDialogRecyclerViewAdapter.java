package com.crupee.lottery.dashboard.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crupee.lottery.R;
import com.crupee.lottery.dashboard.model.LotteryDTO;

import java.util.ArrayList;

public class LotteryDialogRecyclerViewAdapter extends RecyclerView.Adapter<LotteryDialogRecyclerViewAdapter.ViewHolder> {

    public static String TAG = "LotteryDialogRecyclerViewAdapter";
    Context mContext;
    ArrayList<LotteryDTO> lotteryList = new ArrayList<>();
    ArrayList<Integer> randomNumberList = new ArrayList<>();


    public LotteryDialogRecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.qr_lottery_item, parent, false);
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

    public void addLotteryList(ArrayList<LotteryDTO> list) {
        lotteryList = new ArrayList<>();
        lotteryList.addAll(list);
        notifyDataSetChanged();
    }

    public ArrayList<LotteryDTO> returnLotteryList() {
        return lotteryList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView random_number_tv1, random_number_tv2, random_number_tv3, random_number_tv4, random_number_tv5, random_number_tv6;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            random_number_tv1 = (TextView) itemView.findViewById(R.id.qrNum1);
            random_number_tv2 = (TextView) itemView.findViewById(R.id.qrNum2);
            random_number_tv3 = (TextView) itemView.findViewById(R.id.qrNum3);
            random_number_tv4 = (TextView) itemView.findViewById(R.id.qrNum4);
            random_number_tv5 = (TextView) itemView.findViewById(R.id.qrNum5);
            random_number_tv6 = (TextView) itemView.findViewById(R.id.qrNum6);

        }

        public void bindLottery(LotteryDTO lotteryDTO, final int position) {

            random_number_tv1.setText(lotteryDTO.getNum1());
            random_number_tv2.setText(lotteryDTO.getNum2());
            random_number_tv3.setText(lotteryDTO.getNum3());
            random_number_tv4.setText(lotteryDTO.getNum4());
            random_number_tv5.setText(lotteryDTO.getNum5());
            random_number_tv6.setText(lotteryDTO.getNum6());
        }
    }

}
