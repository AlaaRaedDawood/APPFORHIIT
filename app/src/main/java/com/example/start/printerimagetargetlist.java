package com.example.start;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import com.hp.mss.hpprint.model.ImagePrintItem;
import com.hp.mss.hpprint.model.PrintItem;
import com.hp.mss.hpprint.model.PrintJobData;
import com.hp.mss.hpprint.model.asset.ImageAsset;
import com.hp.mss.hpprint.util.PrintUtil;

import java.util.ArrayList;
import java.util.List;

public class printerimagetargetlist extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private List<imagetarget> productList;

    //the recyclerview
    private RecyclerView recyclerView;
    printerimagetargetlist.TopSectionListener activityCommander;

    public interface TopSectionListener{
        public void createClick(imagetarget image);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            activityCommander = (printerimagetargetlist.TopSectionListener) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString());
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_printerimagetargetlist, container, false);


        productList = new ArrayList<>();
        final Animation anime_alpha = AnimationUtils.loadAnimation(getActivity() ,R.anim.alpha_button);
        //getting the recyclerview from xml
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //initializing the productlist
        productList = new ArrayList<>();


        //adding some items to our list
        productList.add(
                new imagetarget(
                        1,
                        "Image Target one",
                        R.drawable.one));

        productList.add(
                new imagetarget(
                        2,
                        "Image Target two",
                        R.drawable.two));
        productList.add(
                new imagetarget(
                        3,
                        "Image Target three",
                        R.drawable.three));
        productList.add(
                new imagetarget(
                        4,
                        "Image Target four",
                        R.drawable.four));
        productList.add(
                new imagetarget(
                        5,
                        "Image Target five",
                        R.drawable.five));
        productList.add(
                new imagetarget(
                        6,
                        "Image Target six",
                        R.drawable.six));
        productList.add(
                new imagetarget(
                        7,
                        "Image Target seven",
                        R.drawable.seven));
        productList.add(
                new imagetarget(
                        8,
                        "Image Target eight",
                        R.drawable.eight));
        productList.add(
                new imagetarget(
                        9,
                        "Image Target nine",
                        R.drawable.nine));
        productList.add(
                new imagetarget(
                        10,
                        "Image Target ten",
                        R.drawable.ten));

        imagetargetAdapter adapter = new imagetargetAdapter( getActivity(), productList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new imagetargetAdapter.OnItemClickListener() {
            @Override
            public void onPrintClick(imagetarget imagetarget, View v) {
                if(imagetarget.getId() > 0) {
                    v.startAnimation(anime_alpha);
                    ImageAsset imageAsset4x6 = new ImageAsset( getActivity(), imagetarget.getImage(), ImageAsset.MeasurementUnits.INCHES, 20, 20);
                    PrintItem printItemDefault = new ImagePrintItem(PrintItem.ScaleType.CENTER, imageAsset4x6);
                    PrintJobData printJobData = new PrintJobData( getActivity(), printItemDefault);
                    printJobData.setJobName("Example");
                    PrintUtil.setPrintJobData(printJobData);
                    PrintUtil.print( getActivity());
                } }

            @Override
            public void onViewClick(imagetarget imagetarget, View v) {
                if(imagetarget.getId() > 0) {
                    v.startAnimation(anime_alpha);
                    buttonClicked(v,imagetarget);
                }
            }

        });

        return view;}
    public void buttonClicked(View view , imagetarget image){
        activityCommander.createClick(image);
    }
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_fragone, container, false);
//    }


}
