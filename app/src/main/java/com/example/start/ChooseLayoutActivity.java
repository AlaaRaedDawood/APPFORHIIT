package com.example.start;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

//the activity that allows the user to choose from the layout that available in db
public class ChooseLayoutActivity extends AppCompatActivity {
    private HiitViewModel hiitViewModel ;
    private ArrayList<layoutTableDB> layoutview = new ArrayList<layoutTableDB>();
    layoutTableDB checkedTrue ;
    private ArrayList<layoutTableDB> usedLayout = new ArrayList<layoutTableDB>();
    private layoutTableDB choosenLayout ;
    private int profileSize ;
    private int layoutSize = 0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_choose_layout);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final LayoutAdapter adapter = new LayoutAdapter();
        recyclerView.setAdapter(adapter);
        final TextView t  = (TextView)findViewById(R.id.usedLayout);

        hiitViewModel = ViewModelProviders.of(ChooseLayoutActivity.this).get(HiitViewModel.class);
        hiitViewModel.getAllLayouts().observe(ChooseLayoutActivity.this, new Observer<List<layoutTableDB>>() {
            @Override
            public void onChanged(@Nullable List<layoutTableDB> layouts) {
                layoutSize = layouts.size();
                layoutview.clear();
                for (int i = 0 ; i < layouts.size() ; i++){
                    if(layouts.get(i).getUsed() == 1){
                        checkedTrue = layouts.get(i);
                        layoutview.add(checkedTrue);
                        break ;
                    }
                }
                for (int i = 0 ; i < layouts.size() ; i++){
                    if(layouts.get(i).getUsed() == 0){
                        layoutview.add(layouts.get(i));
                    }
                }
                if(layoutSize > 0){
                    Toast toast=Toast.makeText(getApplicationContext(),"layout size is  " + layoutSize,Toast.LENGTH_SHORT);
                    // toast.setMargin(50,50);
                    toast.show();
                //adapter.setLayouts(layouts);
            }
                if(checkedTrue != null){
                    t.setText(checkedTrue.getLayout_name());
                }else{
                    t.setText("y msbrna");
                }
                adapter.setLayouts(layoutview);
            }
        });
        final Animation anime_alpha = AnimationUtils.loadAnimation(this ,R.anim.alpha_button);
        Button buttonBack = (Button) findViewById(R.id.button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                finish();
            }});
        adapter.setOnItemClickListener(new LayoutAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(layoutTableDB layout) {
                Log.i("touched" ,"the layout name is " + layout.getLayout_name()+ " the profile used is " + layout.getUsed() );
                Toast toast=Toast.makeText(getApplicationContext(),"the layout name is " + layout.getLayout_name()+ " the profile used is " + layout.getUsed(),Toast.LENGTH_SHORT);
                toast.show();
              //  hiitViewModel.delete(layout);
            }

            @Override
            public void onDeleteLayout(layoutTableDB layout , View v) {
                v.startAnimation(anime_alpha);
                hiitViewModel.delete(layout);
                if(layout != null) {
                    Toast toast = Toast.makeText(getApplicationContext(), "layout is deleted  ", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onViewLayout(layoutTableDB layout, View v) {
                v.startAnimation(anime_alpha);
                Intent intent = new Intent(ChooseLayoutActivity.this , ViewLayoutActivity.class);
                intent.putExtra("layoutTableDb" ,layout);
                startActivity(intent);

            }

            @Override
            public void onChecklayout(final layoutTableDB layout, View v) {
                v.startAnimation(anime_alpha);
                layoutTableDB newLayout ;
                if(checkedTrue != null){
                    Log.i("check" , "previous layout = " + checkedTrue.getLayout_name() +
                            " new checked layout = " + layout.getLayout_name());
                     newLayout = new layoutTableDB(checkedTrue.getLayout_name(),checkedTrue.getIntersect(),checkedTrue.getPathLines(),checkedTrue.getIntersectPoints()
                            ,checkedTrue.getStartPoints(),checkedTrue.getStopPoints(),checkedTrue.getTargetTime(),0);
                    newLayout.setId(checkedTrue.getId());
                    hiitViewModel.update(newLayout);
                }
                Log.i("checked" , "name of required layout " + layout.getLayout_name());
                 int count = 0 ;

                newLayout = new layoutTableDB(layout.getLayout_name(),layout.getIntersect(),layout.getPathLines(),layout.getIntersectPoints()
                        ,layout.getStartPoints(),layout.getStopPoints(),layout.getTargetTime(),1);
                newLayout.setId(layout.getId());
                checkedTrue = newLayout ;
                Log.i("checked" , "new checked profile " + newLayout.getLayout_name());
                hiitViewModel.update(newLayout);

            }
            @Override
            public void onEditlayout(final layoutTableDB layout, View v) {
                v.startAnimation(anime_alpha);
                Intent intent = new Intent(ChooseLayoutActivity.this , DrawLayoutActivity.class);
                // 0  if edit , 1 if create
                intent.putExtra("editORcreate" ,0);
                intent.putExtra("layoutTableDb" ,layout);
                startActivity(intent);


            }

        });

        hiitViewModel.getProfileSize().observe(this , new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer profilesize) {
                profileSize = profilesize ;
                Log.i("DB" , "the profile size is " + profilesize);

            }});

        Button add_btn = (Button) findViewById(R.id.addLayoutButton);
        add_btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if(profileSize == 0){
                    Toast toast=Toast.makeText(getApplicationContext(),"a profile must be saved",Toast.LENGTH_SHORT);
                    // toast.setMargin(50,50);
                    toast.show();
                }else {
                    v.startAnimation(anime_alpha);
                    Intent intent = new Intent(ChooseLayoutActivity.this , DrawLayoutActivity.class);
                    intent.putExtra("editORcreate" ,1);
                    startActivity(intent);
                }

            }});}






    }


