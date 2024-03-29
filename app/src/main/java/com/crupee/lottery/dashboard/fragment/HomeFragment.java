package com.crupee.lottery.dashboard.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crupee.lottery.R;
import com.crupee.lottery.dashboard.activity.MainActivity;
import com.crupee.lottery.dashboard.adapter.LotteryDialogRecyclerViewAdapter;
import com.crupee.lottery.dashboard.adapter.LotteryListRecyclerView;
import com.crupee.lottery.dashboard.model.LotteryDTO;
import com.crupee.lottery.utility.PrefUtils;
import com.crupee.lottery.utility.Screenshot;
import com.google.zxing.WriterException;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Locale;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;

import static android.content.Context.WINDOW_SERVICE;

public class HomeFragment extends Fragment {

    //views
    RelativeLayout main;
    RecyclerView lottery_list_rv;
    Button buy_more_ticket_btn, print_ticket_btn;
    public static TextView lottery_ticket_amount;


    //variables
    private final String TAG = HomeFragment.class.getSimpleName();
    ArrayList<LotteryDTO> lotteryList = new ArrayList<>();

    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    String savePath = Environment.getExternalStorageDirectory().getPath() + "/QRCode/";


    //instances
    LotteryListRecyclerView adapter;


    public HomeFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

//        PrefUtils.saveLanguage(getContext(), true, "en", "1");
//        PrefUtils.saveLanguage(getContext(), true, "ne", "2");
//        PrefUtils.saveLanguage(getContext(), true, "ko", "3");

        if (PrefUtils.isLanguageSelected(getContext())) {

            String language = PrefUtils.returnlanguageSelected(getContext());
            Locale locale = new Locale(language);
            Locale.setDefault(locale);

            Resources res = getResources();
            Configuration config = new Configuration(res.getConfiguration());
            config.locale = locale;
            config.setLocale(locale);
            config.setLayoutDirection(locale);
            res.updateConfiguration(config, res.getDisplayMetrics());
        }

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        main = (RelativeLayout) view.findViewById(R.id.main);
        lottery_list_rv = (RecyclerView) view.findViewById(R.id.lottery_list_rv);
        lotteryList.add(new LotteryDTO("", "", "", "", "", "", "quick"));

        buy_more_ticket_btn = (Button) view.findViewById(R.id.buy_more_ticket_btn);
        print_ticket_btn = (Button) view.findViewById(R.id.print_ticket_btn);
        lottery_ticket_amount = (TextView) view.findViewById(R.id.lottery_ticket_amount);

        adapter = new LotteryListRecyclerView(getContext());
        adapter.addLotteryList(lotteryList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        lottery_list_rv.setLayoutManager(mLayoutManager);
        lottery_list_rv.setAdapter(adapter);

        //click methods
        clickMethod();
    }

    private void clickMethod() {

        buy_more_ticket_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<LotteryDTO> lotteryDTOList = PrefUtils.returnLotteryList(getContext(), "lotterykey");

                try {
                    lotteryDTOList.add(new LotteryDTO("", "", "", "", "", "", "quick"));
                    adapter.addLotteryList(lotteryDTOList);

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });

        print_ticket_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //generate QR code
                try {

                    if (PrefUtils.returnLotteryList(getContext(), "lotterykey").size() > 0) {
                        // generateQRCode(adapter.returnLotteryList());
                        showLotteryListDialog(PrefUtils.returnLotteryList(getContext(), "lotterykey"));
                    } else {
                        Toast.makeText(getContext(), getResources().getString(R.string.no_lottery_available), Toast.LENGTH_SHORT).show();
                    }
                } catch (NullPointerException e) {

                    Toast.makeText(getContext(), getResources().getString(R.string.no_lottery_available), Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    /*
     * show the generated lottery list in dialog with image save(screen shot) option
     * */

    private void showLotteryListDialog(ArrayList<LotteryDTO> lotteryList) {

        final Dialog d = new Dialog(getContext());
        d.setContentView(R.layout.dialog_qr_lottery_detail);
        d.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        final LinearLayout top_layout = (LinearLayout) d.findViewById(R.id.top_layout);
        final RecyclerView lottery_list = (RecyclerView) d.findViewById(R.id.lottery_list);
        final LinearLayout bottom_layout = (LinearLayout) d.findViewById(R.id.bottom_layout);
        //final ImageView imageView = (ImageView) d.findViewById(R.id.imageView);
        TextView screenshot_Save = (TextView) d.findViewById(R.id.screenshot_Save);


        LotteryDialogRecyclerViewAdapter adapter = new LotteryDialogRecyclerViewAdapter(getContext());
        adapter.addLotteryList(lotteryList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        lottery_list.setLayoutManager(mLayoutManager);
        lottery_list.setAdapter(adapter);


        Window dialogWindow = d.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);

        lp.y = 300; // The new position of the Y coordinates

        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // Width
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT; // Height

        // The system will call this function when the Window Attributes when the change, can be called directly by application of above the window parameters change, also can use setAttributes
        d.onWindowAttributesChanged(lp);
        dialogWindow.setAttributes(lp);

        d.show();

        screenshot_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bitmap b1 = Screenshot.loadBitmapFromView(top_layout);
                //Bitmap b = Screenshot.getRecyclerViewScreenshot(lottery_list);
                Bitmap b2 = Screenshot.getScreenshotFromRecyclerView(lottery_list);
                //Bitmap b = Screenshot.getScreenshot(lottery_list);
                Bitmap b3 = Screenshot.loadBitmapFromView(bottom_layout);
                ArrayList<Bitmap> bitmapList = new ArrayList<>();
                bitmapList.add(b1);
                bitmapList.add(b2);
                //bitmapList.add(b3);

//                Bitmap b = Screenshot.combineImageIntoOne(bitmapList);
//                Screenshot.saveTempBitmap(b, getContext());

                Bitmap b = Screenshot.finalcombieimage(b1, b2, getContext());
                String path = Screenshot.saveTempBitmap(b, getContext());
                //imageView.setImageBitmap(b);


                //saving the image as a pdf and open the pdf fron pdf viewer
                try {

                    Document document = new Document();

                    String setDirectoryName = "Khushilottery";
                    File root = new File(Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                            setDirectoryName);

                    final File myDir = new File(root + "/lottery_images_pdf");
                    myDir.mkdirs();

                    final String fname = "example.pdf";

                    //Log.d("Home Fragment : ", "file path " + myDir);
                    // Toast.makeText(mContext, "Image saved to " + myDir, Toast.LENGTH_SHORT).show();
                    File file = new File(myDir, fname);

                    //String directoryPath = android.os.Environment.getExternalStorageDirectory().toString();

                    PdfWriter.getInstance(document, new FileOutputStream(file)); //  Change pdf's name.

                    document.open();

                    Image image = Image.getInstance(path);  // Change image's name and extension.

                    float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                            - document.rightMargin() - 0) / image.getWidth()) * 100; // 0 means you have no indentation. If you have any, change it.
                    image.scalePercent(scaler);
                    image.setAlignment(Image.ALIGN_CENTER | Image.ALIGN_TOP);

                    document.add(image);
                    document.close();


                    // opening the pdf file of the pdf generated above
                    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                    StrictMode.setVmPolicy(builder.build());

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (BadElementException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }
        });
    }




    /*
     * this method generate the QR code for the ticket number that we provided
     *
     * */

    public void generateQRCode(ArrayList<LotteryDTO> list) {

        String lotteryTicketNumber = "";

        for (int i = 0; i < list.size(); i++) {
            lotteryTicketNumber = lotteryTicketNumber + list.get(i).getNum1() + " " + list.get(i).getNum2() + " " + list.get(i).getNum3() + " " + list.get(i).getNum4() + " " + list.get(i).getNum5() + " " + list.get(i).getNum6() + "\n";
        }
        //String lotteryTicketNumber = num1 + " " + num2 + " " + num3 + " " + num4 + " " + num5 + " " + num6;

        final Dialog d = new Dialog(getContext());
        d.setContentView(R.layout.dialog_qr);
        d.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        TextView qrTitle = (TextView) d.findViewById(R.id.qrTitle);
//        TextView qrNum1 = (TextView) d.findViewById(R.id.qrNum1);
//        TextView qrNum2 = (TextView) d.findViewById(R.id.qrNum2);
//        TextView qrNum3 = (TextView) d.findViewById(R.id.qrNum3);
//        TextView qrNum4 = (TextView) d.findViewById(R.id.qrNum4);
//        TextView qrNum5 = (TextView) d.findViewById(R.id.qrNum5);
//        TextView qrNum6 = (TextView) d.findViewById(R.id.qrNum6);
        TextView qrError = (TextView) d.findViewById(R.id.qrError);
        TextView qrSave = (TextView) d.findViewById(R.id.qrSave);

        qrError.setVisibility(View.GONE);
        ImageView qrImage = (ImageView) d.findViewById(R.id.qrImage);
        qrImage.setVisibility(View.GONE);

        Window dialogWindow = d.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);

        lp.y = 300; // The new position of the Y coordinates

        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // Width
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT; // Height

        // The system will call this function when the Window Attributes when the change, can be called directly by application of above the window parameters change, also can use setAttributes
        d.onWindowAttributesChanged(lp);
        dialogWindow.setAttributes(lp);

        d.show();


        qrTitle.setText("QR code for lottery List");
//        qrNum1.setText(num1);
//        qrNum2.setText(num2);
//        qrNum3.setText(num3);
//        qrNum4.setText(num4);
//        qrNum5.setText(num5);
//        qrNum6.setText(num6);
        if (list.size() > 0) {
            WindowManager manager = (WindowManager) getContext().getSystemService(WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            int width = point.x;
            int height = point.y;
            int smallerDimension = width < height ? width : height;
            smallerDimension = smallerDimension * 3 / 4;

            qrgEncoder = new QRGEncoder(
                    lotteryTicketNumber, null,
                    QRGContents.Type.TEXT,
                    smallerDimension);
            try {
                bitmap = qrgEncoder.encodeAsBitmap();
                qrImage.setVisibility(View.VISIBLE);
                qrError.setVisibility(View.GONE);
                qrImage.setImageBitmap(bitmap);
            } catch (WriterException e) {
                Log.v(TAG, e.toString());
            }
        } else {
            qrImage.setVisibility(View.GONE);
            qrError.setVisibility(View.VISIBLE);
            qrError.setError("Failed to generate QR code");
        }


        qrSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean save;
                String result;

                try {
                    save = QRGSaver.save(savePath, "Khushilottery", bitmap, QRGContents.ImageType.IMAGE_JPEG);
                    result = save ? "Image Saved" : "Image Not Saved";
                    Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                d.dismiss();
            }
        });
    }

}
