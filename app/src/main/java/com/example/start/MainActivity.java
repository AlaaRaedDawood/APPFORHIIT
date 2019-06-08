package com.example.start;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.alaa.arhiit.UnityPlayerActivity;

import java.nio.BufferUnderflowException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.arch.lifecycle.ViewModelProviders.of;

public class MainActivity extends AppCompatActivity {
    private HiitViewModel hiitViewModel ;
    private int performanceCount = -1 ;
    private layoutTableDB usedLayout ;
    private ArrayList<String> date = new ArrayList<>() ;
    private ArrayList<Integer> heartRate = new ArrayList<>() ;
    private ArrayList<PathLine> layoutpaths = new ArrayList<>() ;
    private int maxheartrate ;
    private int minimumratio ;
    private int[] hiitRatioplay = {2,2,4,6};
    private int[] hiitRatiorest = {2,2,2,2};
    private  float targetTimeLimit  ;
    private  String lastGamePlayedDate = "" ;
    private   Button buttonPlay ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         hiitViewModel = of(this).get(HiitViewModel.class);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        CoordinatorLayout constraintLayout = (CoordinatorLayout)findViewById(R.id.mainLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();
          buttonPlay = (Button)(findViewById(R.id.button_play));
          //buttonPlay.setVisibility(View.GONE);
        buttonPlay.setText("Exercise");
        hiitViewModel.getAllLayouts().observe(MainActivity.this, new Observer<List<layoutTableDB>>() {
            @Override
            public void onChanged(@Nullable List<layoutTableDB> layouts) {
                usedLayout = null ;
                Log.i("playButton" , "usedLayout check is back");
                for(int i = 0 ; i < layouts.size() ; i++){
                    if(layouts.get(i).getUsed() ==1) {
                        usedLayout = layouts.get(i);
                        Log.i("playButton" , "usedLayout is in index = " + i);
                        break;
                    }}
                checkPlayButton();
            }});
        final Animation anime_translate = AnimationUtils.loadAnimation(this ,R.anim.anime_translate);

        Button buttonPerformance = (Button)(findViewById(R.id.button_performance));
        checkPerformance();
        checkPerformanceCount();
        buttonPerformance.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                v.startAnimation(anime_translate);
                Intent i = new Intent(MainActivity.this , performanceGraphActivity.class);
                //i.putExtra("d" , date);
               // i.putExtra("hr" , heartRate);
                startActivity(i);

            }
        });
        checkProfile();

        final Button buttonPrint = (Button)(findViewById(R.id.button_print));
       final Button buttonTrial = (Button)(findViewById(R.id.button_trial));

        buttonTrial.setVisibility(View.GONE);



        buttonPrint.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                v.startAnimation(anime_translate);
                Intent i = new Intent(MainActivity.this , PrintMarkersActivity.class);
                startActivity(i);

            }});
        buttonPlay.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
//                usedLayout = null ;
//                hiitViewModel.getAllLayouts().observe(MainActivity.this, new Observer<List<layoutTableDB>>() {
//                    @Override
//                    public void onChanged(@Nullable List<layoutTableDB> layouts) {
//
//                        for(int i = 0 ; i < layouts.size() ; i++){
//                            if(layouts.get(i).getUsed() ==1) {
//                                usedLayout = layouts.get(i);
//                                Log.i("playButton" , "usedLayout is in index = " + i);
//                                break;
//                            }}
//                        checkPlayButton();
//                    }
//
//                });
                //getUsedLayout();
                checkPerformance();
                final View view = v;
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String currentDateandTime = sdf.format(new Date());
                Log.i("performance date" , "today is " +currentDateandTime+" last " + lastGamePlayedDate);
                if(currentDateandTime.equals(lastGamePlayedDate)){
                    Toast toast = Toast.makeText(getApplicationContext(), "Only one game is allowed a day ", Toast.LENGTH_SHORT);
                    toast.show();
                }else {

                    if(usedLayout != null){
                  if(usedLayout.getTargetTime() == -1){
                      view.startAnimation(anime_translate);
                      doTrial();
                  }
                  else {
                      view.startAnimation(anime_translate);
                      doPlay();
                  }}else {
                        Toast toast = Toast.makeText(getApplicationContext(), "make sure to choose a layout first", Toast.LENGTH_SHORT);
                        toast.show();

                    }

                }
            }});

    //}
//        buttonPlay.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//                final View view = v;
//
//                hiitViewModel.getAllLayouts().observe(MainActivity.this, new Observer<List<layoutTableDB>>() {
//                    @Override
//                    public void onChanged(@Nullable List<layoutTableDB> layouts) {
//                        for(int i = 0 ; i < layouts.size() ; i++){
//                            if(layouts.get(i).getUsed() ==1){
//                                layoutpaths = layouts.get(i).getPathLines();
//                                Log.i("aaaaaaaaaaaaaaaaaaa" , "layoutpath used is " +layouts.get(i).getUsed() );
//                                Log.i("aaaaaaaaaaaaaaaaaaa" , "layoutpath size is " +layouts.get(i).getPathLines().size() );
//
//                                Log.i("aaaaaaaaaaaaaaaaaaa" , "layout in path size is " +layoutpaths.size() );
//                                break;
//                            }
//                        }
//
//
//
//                if(layoutpaths.size() != 0) {
//                    if (layoutpaths != null) {
//                        int[] point1ID = new int[layoutpaths.size()];;
//                        int[] point2ID = new int[layoutpaths.size()];
//                        float[] size = new float[layoutpaths.size()];
//                        Log.i("aaaaaaaaaaaaaaaaaaaaaa", "layout size is " + layoutpaths.size());
//                        for (int i = 0; i < layoutpaths.size(); i++) {
//                            Log.i("aaaaaaaaaaaaaaaaaaaaaa", "layout size is " + layoutpaths.get(i).getPoint1ID());
//                            point1ID[i] = layoutpaths.get(i).getPoint1ID();
//                            point2ID[i] = layoutpaths.get(i).getPoint2ID();
//                            size[i] = layoutpaths.get(i).getSize();
//                        }
//                        //profiles.get(0).getBirthdate();
//                        Log.i("aaaaaaaaaaaaaaaaaaaaaa " , " point1ID " + point1ID[0]);
//                            view.startAnimation(anime_translate);
//                            Intent intent = new Intent(MainActivity.this, UnityPlayerActivity.class);
//                            intent.putExtra("maxheartRate", maxheartrate);
//                            intent.putExtra("point1ID", point1ID);
//                            intent.putExtra("point2ID", point2ID);
//                            intent.putExtra("size", size);
//                            // startActivityForResult(intent, 1);
//                            startActivity(intent);
//                    }
//
//                }
//                    }});
//            }});

        Button buttonProfile = (Button)(findViewById(R.id.button_profile));
        buttonProfile.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                v.startAnimation(anime_translate);
                Intent i = new Intent(MainActivity.this , profileActivity.class);
                startActivity(i);

            }
        });
        Button buttonChooseLayout = (Button)(findViewById(R.id.button_chooseLayout));
        buttonChooseLayout.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                v.startAnimation(anime_translate);
                Intent i = new Intent(MainActivity.this , ChooseLayoutActivity.class);
                startActivity(i);

            }
        });

    }

    public void checkPlayButton() {
        buttonPlay.setText("Exercise");
        if(usedLayout != null){
         if(usedLayout.getTargetTime() == -1){
             buttonPlay.setText("pre-Exercise");
             Log.i("playButton", "problem");
        }else if(usedLayout.getTargetTime() != -1) {
             buttonPlay.setText("Exercise");
        }}
    }

    private void doTrial(){
        hiitViewModel.getAllLayouts().observe(MainActivity.this, new Observer<List<layoutTableDB>>() {
            @Override
            public void onChanged(@Nullable List<layoutTableDB> layouts) {
                for(int i = 0 ; i < layouts.size() ; i++){
                    if(layouts.get(i).getUsed() ==1){
                        usedLayout = layouts.get(i);
                        layoutpaths = layouts.get(i).getPathLines();
                        targetTimeLimit = layouts.get(i).getTargetTime();
                        targetTimeLimit = layouts.get(i).getTargetTime();

                        Log.i("aaaaaaaaaaaaaaaaaaa" , "layoutpath used is " +layouts.get(i).getUsed() );
                        Log.i("aaaaaaaaaaaaaaaaaaa" , "layoutpath size is " +layouts.get(i).getPathLines().size() );

                        Log.i("aaaaaaaaaaaaaaaaaaa" , "layout in path size is " +layoutpaths.size() );
                        break;
                    }
                }
                if(targetTimeLimit == -1){
                    Log.i("playButton" , "it is a TRIAL");
                    if(layoutpaths.size() != 0) {
                        if (layoutpaths != null) {
                            int[] point1ID = new int[layoutpaths.size()];;
                            int[] point2ID = new int[layoutpaths.size()];
                            float[] size = new float[layoutpaths.size()];
                            Log.i("aaaaaaaaaaaaaaaaaaaaaa", "layout size is " + layoutpaths.size());
                            for (int i = 0; i < layoutpaths.size(); i++) {
                                Log.i("PATHLINE", "layout point1ID is " + layoutpaths.get(i).getPoint1ID() + " point2ID "
                                        + layoutpaths.get(i).getPoint2ID() +" size is " + layoutpaths.get(i).getSize());
                                point1ID[i] = layoutpaths.get(i).getPoint1ID();
                                point2ID[i] = layoutpaths.get(i).getPoint2ID();
                                size[i] = layoutpaths.get(i).getSize();
                            }
                            //profiles.get(0).getBirthdate();
                            Log.i("aaaaaaaaaaaaaaaaaaaaaa " , " point1ID " + point1ID[0]);
                          //  view.startAnimation(anime_translate);
                            Intent intent = new Intent(MainActivity.this, UnityPlayerActivity.class);
                            intent.putExtra("maxheartRate", maxheartrate);
                            //intent.putExtra("minimumratio" , minimumratio);
                            intent.putExtra("point1ID", point1ID);
                            intent.putExtra("point2ID", point2ID);
                            intent.putExtra("size", size);
                            intent.putExtra("flag", 0);
                            startActivityForResult(intent, 1);
                            //startActivity(intent);
                        }

                    }
                }
            }});

    }
    private void doPlay(){
        hiitViewModel.getAllLayouts().observe(MainActivity.this, new Observer<List<layoutTableDB>>() {
            @Override
            public void onChanged(@Nullable List<layoutTableDB> layouts) {
                for(int i = 0 ; i < layouts.size() ; i++){
                    if(layouts.get(i).getUsed() ==1){
                        usedLayout = layouts.get(i);
                        layoutpaths = layouts.get(i).getPathLines();
                        targetTimeLimit = layouts.get(i).getTargetTime();

                        Log.i("aaaaaaaaaaaaaaaaaaa" , "layoutpath used is " +layouts.get(i).getUsed() );
                        Log.i("aaaaaaaaaaaaaaaaaaa" , "layoutpath size is " +layouts.get(i).getPathLines().size() );

                        Log.i("aaaaaaaaaaaaaaaaaaa" , "layout in path size is " +layoutpaths.size() );
                        break;
                    }
                }
                if(targetTimeLimit != -1){
            Log.i("playButton" , "it is a GAME");
            if(layoutpaths.size() != 0) {
                if (layoutpaths != null) {
                    int[] point1ID = new int[layoutpaths.size()];;
                    int[] point2ID = new int[layoutpaths.size()];
                    float[] size = new float[layoutpaths.size()];
                    Log.i("aaaaaaaaaaaaaaaaaaaaaa", "layout size is " + layoutpaths.size());
                    for (int i = 0; i < layoutpaths.size(); i++) {
                        Log.i("PATHLINE", "layout point1ID is " + layoutpaths.get(i).getPoint1ID() + " point2ID "
                                + layoutpaths.get(i).getPoint2ID() +" size is " + layoutpaths.get(i).getSize());
                        point1ID[i] = layoutpaths.get(i).getPoint1ID();
                        point2ID[i] = layoutpaths.get(i).getPoint2ID();
                        size[i] = layoutpaths.get(i).getSize();
                    }
                    //profiles.get(0).getBirthdate();
                    Log.i("aaaaaaaaaaaaaaaaaaaaaa " , " point1ID " + point1ID[0]);
                    //view.startAnimation(anime_translate);

                    Intent intent = new Intent(MainActivity.this, UnityPlayerActivity.class);
                    intent.putExtra("maxheartRate", maxheartrate);
                   // intent.putExtra("minimumratio" , minimumratio);
                    intent.putExtra("point1ID", point1ID);
                    intent.putExtra("point2ID", point2ID);
                    intent.putExtra("size", size);
                    intent.putExtra("flag", 1);

                    if(targetTimeLimit != -1) {
                        int t = (int) (targetTimeLimit +1 );
                        int restTimerLimit = -1 ;
                        int targetcountLimit = 0 ;
                        if(performanceCount > 3){
                             targetcountLimit = hiitRatioplay[3];
                        }else {
                             targetcountLimit = hiitRatioplay[performanceCount] ;
                        }

                        int ratio = hiitRatiorest[performanceCount];
                        Log.i("playgame" , "r=" + (performanceCount) + " targetcountLimit = " +targetcountLimit + " r ="+restTimerLimit);
                        restTimerLimit = t*ratio ;
                        intent.putExtra("targetTimerLimit", t);
                        intent.putExtra("restTimerLimit", restTimerLimit);
                        intent.putExtra("targetcountLimit", targetcountLimit);
                        startActivityForResult(intent, 1);
                    }
                    else {
                        Log.i("alaa" , "lkd fshlna");
                    }
                    //startActivity(intent);
                }

            }
                }
            }});
    }
    private void getUsedLayout() {
        hiitViewModel.getAllLayouts().observe(MainActivity.this, new Observer<List<layoutTableDB>>() {
            @Override
            public void onChanged(@Nullable List<layoutTableDB> layouts) {
                for(int i = 0 ; i < layouts.size() ; i++){
                    if(layouts.get(i).getUsed() ==1) {
                        usedLayout = layouts.get(i);
                        Log.i("playButton" , "usedLayout is in index = " + i);
                    }}}});
    }

    private void checkPerformance() {

        hiitViewModel.getAllPerformance().observe(MainActivity.this, new Observer<List<PerformanceTableDB>>() {
            @Override
            public void onChanged(@Nullable List<PerformanceTableDB> performances) {
                date.clear();
                heartRate.clear();

                if(performances.size() > 0 ){
                    targetTimeLimit  = performances.get(performances.size() - 1).getTime();
                    lastGamePlayedDate = performances.get(performances.size() - 1).getDate();
                }


                Log.i("performance " , " pppppppppppppp = " +performances.size());
                for (int i = 0; i < performances.size(); i++) {
                    date.add(performances.get(i).getDate());
                    heartRate.add(performances.get(i).getUser_heartRate());
                    Log.i("performance " , " date is = " +performances.get(i).getDate());
                    Log.i("performance " , " heartrate is = " +performances.get(i).getUser_heartRate());
                    // Toast toast =
                }}

        });
    }

    public void checkPerformanceCount(){
        hiitViewModel.getPerformanceSize().observe(this , new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer performanceSize) {
               performanceCount = (int)(performanceSize / 7) ;

                Log.i("DB" , "the profile size is " + performanceSize);

            }});
    }
    public void checkProfile(){
        hiitViewModel.getAllProfiles().observe(MainActivity.this, new Observer<List<ProfileTableDb>>() {
            @Override
            public void onChanged(@Nullable List<ProfileTableDb> profiles) {

                if (profiles.size() == 0) {

                    Toast toast = Toast.makeText(getApplicationContext(), "save your profile to be able to play ", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    int age = -1;
                    //int maxheartRate = 0;
                    try {
                        Log.i("aaaagggeee", " birthdate is  " + profiles.get(0).getBirthdate());
                        Date birthdate = new SimpleDateFormat("MM/dd/yyyy").parse(profiles.get(0).getBirthdate());
                        Date today = Calendar.getInstance().getTime();
                        Date date; // your date
                        Calendar cal = Calendar.getInstance();
                        // cal.setTime(date);
                        int year = cal.get(Calendar.YEAR);
                        Log.i("aaaagggeee", "cal year is " + year);
                        Log.i("aaaagggeee", "year is " + today.getYear());
                        Log.i("aaaagggeee", "difference year is " + (year - birthdate.getYear()));
                        int month = cal.get(Calendar.MONTH);
                        int day = cal.get(Calendar.DAY_OF_MONTH);
                        if (today.getMonth()+1 == birthdate.getMonth()+1) {
                            if (today.getDay() < birthdate.getDay()) {
                                Log.i("aaaagggeee","1");
                                age = (today.getYear() - birthdate.getYear())-1 ;
                            } else {
                                Log.i("aaaagggeee","2");
                                age = (today.getYear() - birthdate.getYear());
                            }
                        } else if ((today.getMonth()+1< birthdate.getMonth()+1) && (age == -1)) {
                            Log.i("aaaagggeee","3" + " t = " + today.getMonth() + " b= " + birthdate.getMonth());
                            age = (today.getYear() - birthdate.getYear())-1;
                        }
                        else if ((today.getMonth()+1 > birthdate.getMonth()+1) && (age == -1)) {
                            Log.i("aaaagggeee","4" + " t = " + today.getMonth()+1 + " b= " + birthdate.getMonth()+1);
                        age = (today.getYear() - birthdate.getYear())  ;
                        }
                        else if (age == -1) {
                            Log.i("aaaagggeee","5");
                            age = (today.getYear() - birthdate.getYear());
                        }
                    } catch (ParseException e) {
                        Log.i("Error", "birthdate was not saved in right format");
                        e.printStackTrace();
                    }
                    if (age != -1) {
                        if (profiles.get(0).getUser_gender().equals("female")) {
                            maxheartrate = (int) (206.9 - (0.67 * age));
                            Log.i("aaaagggeee", "age" + age + " heart rate " + maxheartrate);

                        } else {
                            maxheartrate = (int) (206.9 - (0.88 * age));

                        }
                        if(profiles.get(0).getUser_level() != -1){
                            minimumratio = profiles.get(0).getUser_level() ;
                        }


                    }

                }
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Intent i = getIntent() ;

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
               // Log.i("Pla")
                getUsedLayout();
                String result=data.getStringExtra("pos");
                //Log.i("alaa" , result);
                float targetsTime =data.getFloatExtra("targetsTime" , -1);
                float heartRate =data.getFloatExtra("maxHeartRate" , -1);
                int score = data.getIntExtra("points" , -1);
                Log.i("rounds" , "points = " + score);
                if((targetsTime != -1) && (heartRate != -1) && (score != -1) ){
                    if (usedLayout.getTargetTime() == -1){
                       layoutTableDB nlayout = new layoutTableDB(usedLayout.getLayout_name(),usedLayout.getIntersect(),usedLayout.getPathLines()
                       ,usedLayout.getIntersectPoints(),usedLayout.getStartPoints(),usedLayout.getStopPoints(),targetsTime,usedLayout.getUsed());
                       nlayout.setId(usedLayout.getId());
                       hiitViewModel.update(nlayout);
                    }else {
                        if(score != -2) {
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            String currentDateandTime = sdf.format(new Date());
                            Log.i("Android to Unity", "targets time = " + targetsTime);
                            Log.i("Android to Unity", "heartRate = " + heartRate);
                            PerformanceTableDB performance = new PerformanceTableDB(currentDateandTime, targetsTime, (int) heartRate, score);
                            hiitViewModel.insertPerformance(performance);
                        }
                    }
                  // String min = ((int)(targetsTime / 60)) + "";
                    //String seconds = (targetsTime % 60);
                    Toast toast = Toast.makeText(getApplicationContext(), "Welcome Back" , Toast.LENGTH_SHORT);

                    toast.show();

                }else {
                    Toast toast = Toast.makeText(getApplicationContext(), "error with failure " + heartRate, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast toast = Toast.makeText(getApplicationContext(), "NULL ", Toast.LENGTH_SHORT);
                toast.show();
                //Write your code if there's no result
            }
        }
    }//onActivityResult
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
