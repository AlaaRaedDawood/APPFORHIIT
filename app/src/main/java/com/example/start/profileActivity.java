package com.example.start;

import android.app.DatePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.arch.lifecycle.ViewModelProviders.*;

public class profileActivity extends AppCompatActivity {
private Button button_back ;
private int profileId = -1 ;
private HiitViewModel hiitViewModel ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profile);

        final Animation anime_alpha = AnimationUtils.loadAnimation(this ,R.anim.alpha_button);
        //return back to menu
        button_back = (Button) findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                v.startAnimation(anime_alpha);
                finish();
            }});
        final Calendar calender = Calendar.getInstance();
        final EditText birthdate_editText = (EditText)findViewById(R.id.editText_birthdateText) ;
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODdateO Auto-generated method stub
                calender.set(Calendar.YEAR, year);
                calender.set(Calendar.MONTH, monthOfYear);
                calender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                birthdate_editText.setText(sdf.format(calender.getTime()));
            }

        };
        //SetBirthdate

        birthdate_editText.setOnClickListener(new View.OnClickListener() {
             @Override
            public void onClick(View v) {
                new DatePickerDialog(profileActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        ,date , calender
                        .get(Calendar.YEAR), calender.get(Calendar.MONTH),
                        calender.get(Calendar.DAY_OF_MONTH)).show();

            }});

        hiitViewModel = of(this).get(HiitViewModel.class);
        hiitViewModel.getProfileSize().observe(this , new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer profilesize) {
                Log.i("DB" , "the profile size is " + profilesize);

            }});
   final Button btn_save = (Button)findViewById(R.id.button_save);
   final Button btn_edit = (Button)findViewById(R.id.button_edit);
   final EditText userName_et = ((EditText)findViewById(R.id.editText_profile_name));
   final EditText birthdate_et = ((EditText)findViewById(R.id.editText_birthdateText));
   final EditText height_et = ((EditText)findViewById(R.id.editText_profile_height));
   //final EditText weight_et = ((EditText)findViewById(R.id.editText_profile_weight));
   final RadioButton female_rb= (RadioButton) findViewById(R.id.female_radio_btn);
   final RadioButton male_rb= (RadioButton) findViewById(R.id.male_radio_btn);
   final RadioButton upper_rb= (RadioButton) findViewById(R.id.up_radio_btn);
   final RadioButton lower_rb= (RadioButton) findViewById(R.id.lower_radio_btn);
   final RadioButton none_rb= (RadioButton) findViewById(R.id.none_radio_btn);
        final RadioButton beginner_rb= (RadioButton) findViewById(R.id.b_radio_btn);
        final RadioButton advanced_rb= (RadioButton) findViewById(R.id.a_radio_btn);
        final RadioButton intermediate_rb= (RadioButton) findViewById(R.id.i_radio_btn);
    btn_save.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
         String name = userName_et.getText().toString();
         String birthdate = birthdate_et.getText().toString();
         int height = Integer.parseInt(height_et.getText().toString());
        // int weight = Integer.parseInt(weight_et.getText().toString());
         String gender = "";

         if(female_rb.isChecked()){
             gender = "female" ;
         }else {
             gender = "male";
         }
         String backProblems = "" ;
         int user_level = -1 ;
         if(upper_rb.isChecked()){
             backProblems = "upper";
         }
         if(lower_rb.isChecked()){
             backProblems = "lower";
         }if (none_rb.isChecked()){
                backProblems = "none";
            }

            if(beginner_rb.isChecked()){
                user_level = 65;
            }
            if(advanced_rb.isChecked()){
                user_level = 80;
            }if (intermediate_rb.isChecked()){
                user_level = 75;
            }
            if((!name.isEmpty()) && (!birthdate.isEmpty()) && (height != 0)){
                //create new Profile
                ProfileTableDb profile = new ProfileTableDb(name,height,gender,birthdate,backProblems,user_level);
                hiitViewModel.insertProfile(profile);
                Toast toast=Toast.makeText(getApplicationContext(),"The data was saved successfully",Toast.LENGTH_SHORT);
                toast.show();
            }else {
                Toast toast = Toast.makeText(getApplicationContext(), "Make sure all data aren't empty ", Toast.LENGTH_SHORT);
                toast.show();
            }

        }
    });
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = userName_et.getText().toString();
                int height = Integer.parseInt(height_et.getText().toString());
               // int weight = Integer.parseInt(weight_et.getText().toString());
                String birthdate = birthdate_et.getText().toString();
                String gender = "" ;
                int user_level = -1 ;

                if(female_rb.isChecked()){
                    gender = "female" ;
                }else {
                    gender = "male";
                }
                String backProblems = "" ;
                if(upper_rb.isChecked()){
                    backProblems = "upper";
                }
                if(lower_rb.isChecked()){
                    backProblems = "lower";
                }if (none_rb.isChecked()){
                    backProblems = "none";
                }
                if(beginner_rb.isChecked()){
                    user_level = 65;
                }
                if(advanced_rb.isChecked()){
                    user_level = 80;
                }if (intermediate_rb.isChecked()){
                    user_level = 75;
                }
                //update new Profile
                if((!name.isEmpty()) && (!birthdate.isEmpty()) && (height != 0)){

                if(profileId != -1){
                ProfileTableDb profile = new ProfileTableDb(name,height,gender,birthdate,backProblems ,user_level);
                profile.setId(profileId);
                hiitViewModel.updateProfile(profile);
                    Toast toast=Toast.makeText(getApplicationContext(),"The data was updated successfully",Toast.LENGTH_SHORT);
                    toast.show();
            }}
            else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Make sure all data aren't empty ", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });


                hiitViewModel.getAllProfiles().observe(this , new Observer<List<ProfileTableDb>>() {
             @Override
             public void onChanged(@Nullable List<ProfileTableDb> profiles) {

                 if(profiles.size() == 0){
                     Log.i("alaa" ,"gamdaaa");

                     btn_edit.setVisibility(View.GONE);
                 }else{
                     Log.i("alaa" ,"gamdaaa" + profiles.size());
                     btn_edit.setVisibility(View.VISIBLE);
                     btn_save.setVisibility(View.GONE);
                     profileId = profiles.get(0).getId();
                     userName_et.setText(profiles.get(0).getUser_name());
                     birthdate_et.setText(profiles.get(0).getBirthdate());
                     height_et.setText(Integer.toString(profiles.get(0).getUser_height()));
                     ///weight_et.setText(Integer.toString(profiles.get(0).getUser_weight()));
                     if(profiles.get(0).getUser_gender().equals("female")){
                         female_rb.setChecked(true);
                         male_rb.setChecked(false);
                     }else {
                         female_rb.setChecked(false);
                         male_rb.setChecked(true);
                     }
                     if(profiles.get(0).getUser_backproblems().equals("upper")){
                         upper_rb.setChecked(true);
                         lower_rb.setChecked(false);
                         none_rb.setChecked(false);
                     }
                     if(profiles.get(0).getUser_backproblems().equals("lower")){
                         upper_rb.setChecked(false);
                         lower_rb.setChecked(true);
                         none_rb.setChecked(false);
                     }
                     if(profiles.get(0).getUser_backproblems().equals("none")){
                         upper_rb.setChecked(false);
                         lower_rb.setChecked(false);
                         none_rb.setChecked(true);
                     }
                     if(profiles.get(0).getUser_level() == 65){
                         beginner_rb.setChecked(true);
                         intermediate_rb.setChecked(false);
                         advanced_rb.setChecked(false);
                     }
                     if(profiles.get(0).getUser_level() == 80){
                         beginner_rb.setChecked(false);
                         intermediate_rb.setChecked(false);
                         advanced_rb.setChecked(true);
                     }
                     if(profiles.get(0).getUser_level() == 75){
                         beginner_rb.setChecked(false);
                         intermediate_rb.setChecked(true);
                         advanced_rb.setChecked(false);
                     }


                 }


             }
         });

         //int profileSize = hiitViewModel.getProfileSize();
       // int p = 0 ;






    }

}
