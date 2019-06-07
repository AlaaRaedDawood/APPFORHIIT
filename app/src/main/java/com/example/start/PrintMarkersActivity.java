package com.example.start;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.hp.mss.hpprint.model.ImagePrintItem;
import com.hp.mss.hpprint.model.PrintItem;
import com.hp.mss.hpprint.model.PrintJobData;
import com.hp.mss.hpprint.model.asset.ImageAsset;
import com.hp.mss.hpprint.util.PrintUtil;
public class PrintMarkersActivity extends AppCompatActivity implements printerimagetargetlist.TopSectionListener , printerviewImage.viewSectionListener {
    //a list to store all the products
   private List<imagetarget> productList;

    //the recyclerview
//   private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_print_markers);
        Button backbutton =(Button)findViewById(R.id.button_back);
        backbutton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

               finish();
            }
        });
        final Animation anime_alpha = AnimationUtils.loadAnimation(this ,R.anim.anime_translate);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
// Replace the contents of the container with the new fragment
        ft.replace(R.id.fragmentHolder, new printerimagetargetlist());
// or ft.add(R.id.your_placeholder, new FooFragment());
// Complete the changes added above
        ft.commit();
        //getting the recyclerview from xml
//        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        //initializing the productlist
//        productList = new ArrayList<>();
//
//
//        //adding some items to our list
//        productList.add(
//                new imagetarget(
//                        1,
//                        "Image Target one",
//                        R.drawable.one));
//
//        productList.add(
//                new imagetarget(
//                        2,
//                        "Image Target two",
//                        R.drawable.two));
//        productList.add(
//                new imagetarget(
//                        3,
//                        "Image Target three",
//                        R.drawable.three));
//        productList.add(
//                new imagetarget(
//                        4,
//                        "Image Target four",
//                        R.drawable.four));
//        productList.add(
//                new imagetarget(
//                        5,
//                        "Image Target five",
//                        R.drawable.five));
//
//        imagetargetAdapter adapter = new imagetargetAdapter(this, productList);
//
//        //setting adapter to recyclerview
//        recyclerView.setAdapter(adapter);
//        adapter.setOnItemClickListener(new imagetargetAdapter.OnItemClickListener() {
//            @Override
//            public void onPrintClick(imagetarget imagetarget, View v) {
//                if(imagetarget.getId() > 0) {
//                    v.startAnimation(anime_alpha);
//                    ImageAsset imageAsset4x6 = new ImageAsset(PrintMarkersActivity.this, imagetarget.getImage(), ImageAsset.MeasurementUnits.INCHES, 20, 20);
//                    PrintItem printItemDefault = new ImagePrintItem(PrintItem.ScaleType.CENTER, imageAsset4x6);
//                    PrintJobData printJobData = new PrintJobData(PrintMarkersActivity.this, printItemDefault);
//                    printJobData.setJobName("Example");
//                    PrintUtil.setPrintJobData(printJobData);
//                    PrintUtil.print(PrintMarkersActivity.this);
//                } }
//        });


    }
    @Override
    public void backClick() {
        printerimagetargetlist f = new printerimagetargetlist();
        //f.setClickedText("One was clicked");
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragmentHolder , f);
        ft.commit();
        Log.i("alaa" , "back clicked");

    }
    //This code called by the Top Fragment called when the user clicked the button
    @Override
    public void createClick(imagetarget image) {
            printerviewImage  f = new printerviewImage();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            ft.replace(R.id.fragmentHolder , f);
            ft.commit();
            Log.i("alaa" , "one clicked");
            f.setClickedText(image);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
