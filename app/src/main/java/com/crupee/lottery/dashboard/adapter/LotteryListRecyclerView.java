package com.crupee.lottery.dashboard.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


import com.crupee.lottery.R;
import com.crupee.lottery.dashboard.fragment.HomeFragment;
import com.crupee.lottery.dashboard.model.LotteryDTO;
import com.crupee.lottery.dashboard.model.NumberRangeDTO;
import com.crupee.lottery.utility.PrefUtils;

import java.util.ArrayList;


public class LotteryListRecyclerView extends RecyclerView.Adapter<LotteryListRecyclerView.ViewHolder> {

    //view
    public static TextView num_ber_tv1, num_ber_tv2, num_ber_tv3, num_ber_tv4, num_ber_tv5, num_ber_tv6, number_Save;

    //variable
    public static String TAG = "LotteryListRecyclerView";
    Context mContext;
    ArrayList<LotteryDTO> lotteryList = new ArrayList<>();
    ArrayList<Integer> randomNumberList = new ArrayList<>();
    ArrayList<NumberRangeDTO> numRangeList;
    int PreferencePosition;


    public LotteryListRecyclerView(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.lottery_item, parent, false);
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

        /*
        *
          views for the recycler item
        *
        */
        TextView random_number_tv1, random_number_tv2, random_number_tv3, random_number_tv4, random_number_tv5, random_number_tv6;
        TextView quickpick_btn, cancel_btn;


        /* Dialog object initialization for lottery number selection */
        Dialog d;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            random_number_tv1 = (TextView) itemView.findViewById(R.id.random_number_tv1);
            random_number_tv2 = (TextView) itemView.findViewById(R.id.random_number_tv2);
            random_number_tv3 = (TextView) itemView.findViewById(R.id.random_number_tv3);
            random_number_tv4 = (TextView) itemView.findViewById(R.id.random_number_tv4);
            random_number_tv5 = (TextView) itemView.findViewById(R.id.random_number_tv5);
            random_number_tv6 = (TextView) itemView.findViewById(R.id.random_number_tv6);
            quickpick_btn = (TextView) itemView.findViewById(R.id.quickpick_btn);
            quickpick_btn.setVisibility(View.VISIBLE);
            cancel_btn = (TextView) itemView.findViewById(R.id.cancel_btn);
            cancel_btn.setVisibility(View.GONE);

        }

        public void bindLottery(LotteryDTO lotteryDTO, final int position) {

            random_number_tv1.setText(lotteryDTO.getNum1());
            random_number_tv2.setText(lotteryDTO.getNum2());
            random_number_tv3.setText(lotteryDTO.getNum3());
            random_number_tv4.setText(lotteryDTO.getNum4());
            random_number_tv5.setText(lotteryDTO.getNum5());
            random_number_tv6.setText(lotteryDTO.getNum6());

            //handling the visibility of the button in the lottery list (quick pick or cancel)
            if (lotteryList.get(position).getResult().equalsIgnoreCase("quick")) {
                quickpick_btn.setVisibility(View.VISIBLE);
                cancel_btn.setVisibility(View.GONE);
            } else if (lotteryList.get(position).getResult().equalsIgnoreCase("cancel")) {
                quickpick_btn.setVisibility(View.GONE);
                cancel_btn.setVisibility(View.VISIBLE);
            }

            quickpick_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //handling the visibility of the button in the lottery list (quick pick or cancel)
                    quickpick_btn.setVisibility(View.GONE);
                    cancel_btn.setVisibility(View.VISIBLE);

                    //sending the range of the number and generate the random number
                    NoRepeatRandom nrr = new NoRepeatRandom(0, 45);
                    for (int i = 0; i < 6; i++) {
                        int rn_generated = nrr.GetRandom();
                        randomNumberList.add(i, rn_generated);
                        System.out.println("Random number: " + rn_generated);
                    }

                    //assigning the generated randon number in the ticket textview
                    random_number_tv1.setText(String.valueOf(randomNumberList.get(0)));
                    random_number_tv2.setText(String.valueOf(randomNumberList.get(1)));
                    random_number_tv3.setText(String.valueOf(randomNumberList.get(2)));
                    random_number_tv4.setText(String.valueOf(randomNumberList.get(3)));
                    random_number_tv5.setText(String.valueOf(randomNumberList.get(4)));
                    random_number_tv6.setText(String.valueOf(randomNumberList.get(5)));

                    //assign the random number generated to the clicked ticket item
                    LotteryDTO lotteryDTO1 = new LotteryDTO();
                    lotteryList.set(position, new LotteryDTO(
                            String.valueOf(randomNumberList.get(0)),
                            String.valueOf(randomNumberList.get(1)),
                            String.valueOf(randomNumberList.get(2)),
                            String.valueOf(randomNumberList.get(3)),
                            String.valueOf(randomNumberList.get(4)),
                            String.valueOf(randomNumberList.get(5)), "cancel"
                    ));

                    //saving the list of lottery in shared preference
                    PrefUtils.saveLotteryList(mContext, lotteryList, "lotterykey");

                    //to update the amount
                    updateLotteryAmount();

                }
            });

            cancel_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ArrayList<LotteryDTO> newLotteryList = new ArrayList<>();
                    for (int i = 0; i < lotteryList.size(); i++) {
                        if (position == i) {
                        } else {

                            lotteryList.set(position, new LotteryDTO(
                                    lotteryList.get(position).getNum1(),
                                    lotteryList.get(position).getNum2(),
                                    lotteryList.get(position).getNum3(),
                                    lotteryList.get(position).getNum4(),
                                    lotteryList.get(position).getNum5(),
                                    lotteryList.get(position).getNum6(),
                                    "cancel"));

                            newLotteryList.add(lotteryList.get(i));
                        }
                    }

                    // assign the new lottery list to the adapter list
                    lotteryList = newLotteryList;

                    //to avoid null pointer exception
                    if (lotteryList.size() != 0) {
                        if (lotteryList.get(lotteryList.size() - 1).getNum1().isEmpty()) {
                            lotteryList.remove(lotteryList.size() - 1);
                            notifyDataSetChanged();
                        } else {
                            notifyDataSetChanged();
                        }
                    }

                    //saving the list of lottery in shared preference
                    PrefUtils.saveLotteryList(mContext, lotteryList, "lotterykey");

                    //handling the visibility of the button in the lottery list (quick pick or cancel)
                    quickpick_btn.setVisibility(View.VISIBLE);
                    cancel_btn.setVisibility(View.GONE);

                    //to update the amount
                    updateLotteryAmount();

                }
            });


            /*
             * these click are for the selection of lottery number if the lottery number section is only empty
             * */
            random_number_tv1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (lotteryList.get(position).getResult().equalsIgnoreCase("cancel")) {

                    } else {
                        PreferencePosition = position;
                        setYourChoiceNumber();
                    }
                }
            });

            random_number_tv2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (lotteryList.get(position).getResult().equalsIgnoreCase("cancel")) {

                    } else {
                        PreferencePosition = position;
                        setYourChoiceNumber();
                    }

                }
            });

            random_number_tv3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (lotteryList.get(position).getResult().equalsIgnoreCase("cancel")) {

                    } else {
                        PreferencePosition = position;
                        setYourChoiceNumber();
                    }
                }
            });

            random_number_tv4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (lotteryList.get(position).getResult().equalsIgnoreCase("cancel")) {

                    } else {
                        PreferencePosition = position;
                        setYourChoiceNumber();
                    }
                }
            });

            random_number_tv5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (lotteryList.get(position).getResult().equalsIgnoreCase("cancel")) {

                    } else {
                        PreferencePosition = position;
                        setYourChoiceNumber();
                    }
                }
            });

            random_number_tv6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (lotteryList.get(position).getResult().equalsIgnoreCase("cancel")) {

                    } else {
                        PreferencePosition = position;
                        setYourChoiceNumber();
                    }
                }
            });

        }

        /*
         * method to display the dialog of lottery number selection
         *
         */

        private void setYourChoiceNumber() {
            selectLotteryNumberDialog();
        }

        /*
         * Method that display the dialog of lottery number selection
         * */
        private void selectLotteryNumberDialog() {


            d = new Dialog(mContext);
            d.setContentView(R.layout.dialog_select_lottery_number);
            d.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            num_ber_tv1 = (TextView) d.findViewById(R.id.number_tv1);
            num_ber_tv2 = (TextView) d.findViewById(R.id.number_tv2);
            num_ber_tv3 = (TextView) d.findViewById(R.id.number_tv3);
            num_ber_tv4 = (TextView) d.findViewById(R.id.number_tv4);
            num_ber_tv5 = (TextView) d.findViewById(R.id.number_tv5);
            num_ber_tv6 = (TextView) d.findViewById(R.id.number_tv6);
            number_Save = (TextView) d.findViewById(R.id.number_Save);
            number_Save.setVisibility(View.GONE);

            RecyclerView lottery_number_range_list = (RecyclerView) d.findViewById(R.id.lottery_number_range_list);

            getLotteryRangeNumber();

            Window dialogWindow = d.getWindow();
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);

            lp.y = 100; // The new position of the Y coordinates

            lp.width = WindowManager.LayoutParams.MATCH_PARENT; // Width
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT; // Height

            // The system will call this function when the Window Attributes when the change, can be called directly by application of above the window parameters change, also can use setAttributes
            d.onWindowAttributesChanged(lp);
            dialogWindow.setAttributes(lp);

            d.show();

            // set up the RecyclerView
            int numberOfColumns = 8;
            LotteryNumberRangeRecyclerView adapter = new LotteryNumberRangeRecyclerView(mContext);
            adapter.addLotteryList(numRangeList);
            lottery_number_range_list.setLayoutManager(new GridLayoutManager(mContext, numberOfColumns));
            lottery_number_range_list.setAdapter(adapter);

            /*
             * this click save the selected lottery number to the list of previous arraylist for print
             * */
            number_Save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<Integer> selectedList = PrefUtils.returnSelectedLotteryNumber(mContext, "selected_lottery_number");
                    setSelectedLottery(selectedList);
                }
            });
        }


        /*
         * Range of the lottery number that should be available for users to choose
         * */
        private void getLotteryRangeNumber() {

            numRangeList = new ArrayList<>();
            numRangeList.add(new NumberRangeDTO("0"));
            numRangeList.add(new NumberRangeDTO("1"));
            numRangeList.add(new NumberRangeDTO("2"));
            numRangeList.add(new NumberRangeDTO("3"));
            numRangeList.add(new NumberRangeDTO("4"));
            numRangeList.add(new NumberRangeDTO("5"));
            numRangeList.add(new NumberRangeDTO("6"));
            numRangeList.add(new NumberRangeDTO("7"));
            numRangeList.add(new NumberRangeDTO("8"));
            numRangeList.add(new NumberRangeDTO("9"));
            numRangeList.add(new NumberRangeDTO("10"));
            numRangeList.add(new NumberRangeDTO("11"));
            numRangeList.add(new NumberRangeDTO("12"));
            numRangeList.add(new NumberRangeDTO("13"));
            numRangeList.add(new NumberRangeDTO("14"));
            numRangeList.add(new NumberRangeDTO("15"));
            numRangeList.add(new NumberRangeDTO("16"));
            numRangeList.add(new NumberRangeDTO("17"));
            numRangeList.add(new NumberRangeDTO("18"));
            numRangeList.add(new NumberRangeDTO("19"));
            numRangeList.add(new NumberRangeDTO("20"));
            numRangeList.add(new NumberRangeDTO("21"));
            numRangeList.add(new NumberRangeDTO("22"));
            numRangeList.add(new NumberRangeDTO("23"));
            numRangeList.add(new NumberRangeDTO("24"));
            numRangeList.add(new NumberRangeDTO("25"));
            numRangeList.add(new NumberRangeDTO("26"));
            numRangeList.add(new NumberRangeDTO("27"));
            numRangeList.add(new NumberRangeDTO("28"));
            numRangeList.add(new NumberRangeDTO("29"));
            numRangeList.add(new NumberRangeDTO("30"));
            numRangeList.add(new NumberRangeDTO("31"));
            numRangeList.add(new NumberRangeDTO("32"));
            numRangeList.add(new NumberRangeDTO("33"));
            numRangeList.add(new NumberRangeDTO("34"));
            numRangeList.add(new NumberRangeDTO("35"));
            numRangeList.add(new NumberRangeDTO("36"));
            numRangeList.add(new NumberRangeDTO("37"));
            numRangeList.add(new NumberRangeDTO("38"));
            numRangeList.add(new NumberRangeDTO("39"));
            numRangeList.add(new NumberRangeDTO("40"));
            numRangeList.add(new NumberRangeDTO("41"));
            numRangeList.add(new NumberRangeDTO("42"));
            numRangeList.add(new NumberRangeDTO("43"));
            numRangeList.add(new NumberRangeDTO("44"));
            numRangeList.add(new NumberRangeDTO("45"));
        }


        /*
         * this method save the selected lottery number to the list of previous arraylist for print
         * */

        public void setSelectedLottery(ArrayList<Integer> appendList) {

            d.dismiss();

            random_number_tv1.setText(appendList.get(0).toString());
            random_number_tv2.setText(appendList.get(1).toString());
            random_number_tv3.setText(appendList.get(2).toString());
            random_number_tv4.setText(appendList.get(3).toString());
            random_number_tv5.setText(appendList.get(4).toString());
            random_number_tv6.setText(appendList.get(5).toString());

            //handling the visibility of the button in the lottery list (quick pick or cancel)
            quickpick_btn.setVisibility(View.GONE);
            cancel_btn.setVisibility(View.VISIBLE);

            //Toast.makeText(mContext, "Position == " + PreferencePosition, Toast.LENGTH_SHORT).show();


            //assign the random number generated to the clicked ticket item
            LotteryDTO lotteryDTO1 = new LotteryDTO();
            lotteryList.set(PreferencePosition, new LotteryDTO(
                    String.valueOf(appendList.get(0)),
                    String.valueOf(appendList.get(1)),
                    String.valueOf(appendList.get(2)),
                    String.valueOf(appendList.get(3)),
                    String.valueOf(appendList.get(4)),
                    String.valueOf(appendList.get(5)), "cancel"
            ));

            //saving the list of lottery in shared preference
            PrefUtils.saveLotteryList(mContext, lotteryList, "lotterykey");

            //to update the amount
            updateLotteryAmount();
        }


        /*
         * this class will assign the value in integer array from min value to max value that we will provide
         * and do not repeat the same number in the  lottery ticket
         *
         */
        public class NoRepeatRandom {
            private int[] number = null;
            private int N = -1;
            private int size = 0;

            public NoRepeatRandom(int minVal, int maxVal) {
                Log.d(TAG, "======= Inside NoRepeatRandom Method =========");
                N = (maxVal - minVal) + 1;
                Log.d(TAG, "Value of N : " + N);
                number = new int[N];
                int n = minVal;
                for (int i = 0; i < N; i++) {
                    number[i] = n++;
                    Log.d(TAG, "Value of number" + "[" + i + "]" + number[i]);
                }
                size = N;
                Log.d(TAG, "Value of size : " + size);
            }

            public void Reset() {
                size = N;
            }

            // Returns -1 if none left
            public int GetRandom() {
                Log.d(TAG, "===== Inside GetRandom Method ======");
                Log.d(TAG, "Value of size : " + size);
                if (size <= 0) return 0;
                int index = (int) (size * Math.random());
                Log.d(TAG, "Value of index : " + index);
                int randNum = number[index];
                Log.d(TAG, "Value of randNum : " + randNum);

                // Swap current value with current last, so we don't actually
                // have to remove anything, and our list still contains everything
                // if we want to reset
                number[index] = number[size - 1];

                number[size - 1] = randNum;
                Log.d(TAG, "Value of number[--size] : " + number[--size]);

                for (int i = 0; i < size; i++) {
                    Log.d(TAG, "Value of number" + "[" + i + "]" + number[i]);
                }

                return randNum;
            }
        }


        /*
         * this method will update the total amount of ticket
         *
         */
        public void updateLotteryAmount() {
            String amount = "0";
            ArrayList<LotteryDTO> lotteryDTOList = PrefUtils.returnLotteryList(mContext, "lotterykey");
            amount = String.valueOf(lotteryDTOList.size() * 100);
            HomeFragment.lottery_ticket_amount.setText(amount);
        }


    }

}
